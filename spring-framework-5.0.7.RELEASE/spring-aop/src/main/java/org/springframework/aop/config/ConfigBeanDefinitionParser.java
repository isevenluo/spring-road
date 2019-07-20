/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.aspectj.AspectJAfterAdvice;
import org.springframework.aop.aspectj.AspectJAfterReturningAdvice;
import org.springframework.aop.aspectj.AspectJAfterThrowingAdvice;
import org.springframework.aop.aspectj.AspectJAroundAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.aop.aspectj.DeclareParentsAdvisor;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.parsing.ParseState;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * {@link BeanDefinitionParser} for the {@code <aop:config>} tag.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @author Adrian Colyer
 * @author Mark Fisher
 * @author Ramnivas Laddad
 * @since 2.0
 */
class ConfigBeanDefinitionParser implements BeanDefinitionParser {

	private static final String ASPECT = "aspect";
	private static final String EXPRESSION = "expression";
	private static final String ID = "id";
	private static final String POINTCUT = "pointcut";
	private static final String ADVICE_BEAN_NAME = "adviceBeanName";
	private static final String ADVISOR = "advisor";
	private static final String ADVICE_REF = "advice-ref";
	private static final String POINTCUT_REF = "pointcut-ref";
	private static final String REF = "ref";
	private static final String BEFORE = "before";
	private static final String DECLARE_PARENTS = "declare-parents";
	private static final String TYPE_PATTERN = "types-matching";
	private static final String DEFAULT_IMPL = "default-impl";
	private static final String DELEGATE_REF = "delegate-ref";
	private static final String IMPLEMENT_INTERFACE = "implement-interface";
	private static final String AFTER = "after";
	private static final String AFTER_RETURNING_ELEMENT = "after-returning";
	private static final String AFTER_THROWING_ELEMENT = "after-throwing";
	private static final String AROUND = "around";
	private static final String RETURNING = "returning";
	private static final String RETURNING_PROPERTY = "returningName";
	private static final String THROWING = "throwing";
	private static final String THROWING_PROPERTY = "throwingName";
	private static final String ARG_NAMES = "arg-names";
	private static final String ARG_NAMES_PROPERTY = "argumentNames";
	private static final String ASPECT_NAME_PROPERTY = "aspectName";
	private static final String DECLARATION_ORDER_PROPERTY = "declarationOrder";
	private static final String ORDER_PROPERTY = "order";
	private static final int METHOD_INDEX = 0;
	private static final int POINTCUT_INDEX = 1;
	private static final int ASPECT_INSTANCE_FACTORY_INDEX = 2;

	private ParseState parseState = new ParseState();

	/**
	 * element : 就是<aop:config>标签元素对象
	 */
	@Override
	@Nullable
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		CompositeComponentDefinition compositeDef =
				new CompositeComponentDefinition(element.getTagName(), parserContext.extractSource(element));
		parserContext.pushContainingComponent(compositeDef);

		// 向IoC容器中注册 AspectJAwareAdvisorAutoProxyCreator 类的BeanDefinition：（用于创建AOP代理对象的）
		// BeanPostProcessor可以对实例化之后的bean进行一些操作
		// AspectJAwareAdvisorAutoProxyCreator 实现了BeanPostProcessor接口,可以对目标对象实例化之后，创建对应的代理对象
		configureAutoProxyCreator(parserContext, element);

		// 获取<aop:config>标签的子标签<aop:aspect>、<aop:advisor> 、<aop:pointcut>
		List<Element> childElts = DomUtils.getChildElements(element);
		for (Element elt: childElts) {
			// 获取子标签的节点名称或者叫元素名称
			String localName = parserContext.getDelegate().getLocalName(elt);
			if (POINTCUT.equals(localName)) {
				// 解析<aop:pointcut>标签 
				// 产生一个AspectJExpressionPointcut的BeanDefinition对象，并注册
				parsePointcut(elt, parserContext);
			}
			else if (ADVISOR.equals(localName)) {
				// 解析<aop:advisor>标签
				// 产生一个DefaultBeanFactoryPointcutAdvisor的BeanDefinition对象，并注册
				parseAdvisor(elt, parserContext);
			}
			else if (ASPECT.equals(localName)) {
				// 解析<aop:aspect>标签
				// 产生了很多BeanDefinition对象
				// aop:after等标签对应5个BeanDefinition对象
				// aop:after标签的method属性对应1个BeanDefinition对象
				// 最终的AspectJPointcutAdvisor BeanDefinition类
				
				parseAspect(elt, parserContext);
			}
		}

		parserContext.popAndRegisterContainingComponent();
		return null;
	}

	/**
	 * Configures the auto proxy creator needed to support the {@link BeanDefinition BeanDefinitions}
	 * created by the '{@code <aop:config/>}' tag. Will force class proxying if the
	 * '{@code proxy-target-class}' attribute is set to '{@code true}'.
	 * @see AopNamespaceUtils
	 */
	private void configureAutoProxyCreator(ParserContext parserContext, Element element) {
		// 注册AspectJAwareAdvisorAutoProxyCreator类的BeanDefinition
		AopNamespaceUtils.registerAspectJAutoProxyCreatorIfNecessary(parserContext, element);
	}

	/**
	 * Parses the supplied {@code <advisor>} element and registers the resulting
	 * {@link org.springframework.aop.Advisor} and any resulting {@link org.springframework.aop.Pointcut}
	 * with the supplied {@link BeanDefinitionRegistry}.
	 */
	private void parseAdvisor(Element advisorElement, ParserContext parserContext) {
		// 创建默认的增强器BeanDefinition：DefaultBeanFactoryPointcutAdvisor
		AbstractBeanDefinition advisorDef = createAdvisorBeanDefinition(advisorElement, parserContext);
		// 获取id属性
		String id = advisorElement.getAttribute(ID);

		try {
			this.parseState.push(new AdvisorEntry(id));
			String advisorBeanName = id;
			// 注册DefaultBeanFactoryPointcutAdvisor对应的BeanDefinition对象到注册器中
			if (StringUtils.hasText(advisorBeanName)) {
				parserContext.getRegistry().registerBeanDefinition(advisorBeanName, advisorDef);
			}
			else {
				advisorBeanName = parserContext.getReaderContext().registerWithGeneratedName(advisorDef);
			}

			// 获取切入点属性
			Object pointcut = parsePointcutProperty(advisorElement, parserContext);
			if (pointcut instanceof BeanDefinition) {
				advisorDef.getPropertyValues().add(POINTCUT, pointcut);
				parserContext.registerComponent(
						new AdvisorComponentDefinition(advisorBeanName, advisorDef, (BeanDefinition) pointcut));
			}
			else if (pointcut instanceof String) {
				advisorDef.getPropertyValues().add(POINTCUT, new RuntimeBeanReference((String) pointcut));
				parserContext.registerComponent(
						new AdvisorComponentDefinition(advisorBeanName, advisorDef));
			}
		}
		finally {
			this.parseState.pop();
		}
	}

	/**
	 * Create a {@link RootBeanDefinition} for the advisor described in the supplied. Does <strong>not</strong>
	 * parse any associated '{@code pointcut}' or '{@code pointcut-ref}' attributes.
	 */
	private AbstractBeanDefinition createAdvisorBeanDefinition(Element advisorElement, ParserContext parserContext) {
		// 创建默认的增强器BeanDefinition：DefaultBeanFactoryPointcutAdvisor
		RootBeanDefinition advisorDefinition = new RootBeanDefinition(DefaultBeanFactoryPointcutAdvisor.class);
		advisorDefinition.setSource(parserContext.extractSource(advisorElement));

		// 获取<aop:advisor>标签的advice-ref属性，该属性值对应一个Advice
		String adviceRef = advisorElement.getAttribute(ADVICE_REF);
		// 如果<aop:advisor>标签的advice-ref属性为空，则报错
		if (!StringUtils.hasText(adviceRef)) {
			parserContext.getReaderContext().error(
					"'advice-ref' attribute contains empty value.", advisorElement, this.parseState.snapshot());
		}
		// 设置DefaultBeanFactoryPointcutAdvisor类的adviceBeanName属性:<aop:advisor>标签的advice-ref属性引用
		else {
			advisorDefinition.getPropertyValues().add(
					ADVICE_BEAN_NAME, new RuntimeBeanNameReference(adviceRef));
		}

		if (advisorElement.hasAttribute(ORDER_PROPERTY)) {
			advisorDefinition.getPropertyValues().add(
					ORDER_PROPERTY, advisorElement.getAttribute(ORDER_PROPERTY));
		}

		return advisorDefinition;
	}

	private void parseAspect(Element aspectElement, ParserContext parserContext) {
		// 获取<aop:aspect>标签的id属性值
		String aspectId = aspectElement.getAttribute(ID);
		// 获取<aop:aspect>标签的ref属性值，也就是增强类的引用名称
		String aspectName = aspectElement.getAttribute(REF);

		try {
			this.parseState.push(new AspectEntry(aspectId, aspectName));
			List<BeanDefinition> beanDefinitions = new ArrayList<>();
			List<BeanReference> beanReferences = new ArrayList<>();

			// 处理<aop:aspect>标签的<aop:declare-parents>子标签
			List<Element> declareParents = DomUtils.getChildElementsByTagName(aspectElement, DECLARE_PARENTS);
			for (int i = METHOD_INDEX; i < declareParents.size(); i++) {
				Element declareParentsElement = declareParents.get(i);
				beanDefinitions.add(parseDeclareParents(declareParentsElement, parserContext));
			}

			// We have to parse "advice" and all the advice kinds in one loop, to get the
			// ordering semantics right.
			
			// 获取<aop:aspect>标签的所有子标签
			NodeList nodeList = aspectElement.getChildNodes();
			boolean adviceFoundAlready = false;
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				// 判断是否是<aop:before>、<aop:after>、<aop:after-returning>、<aop:after-throwing method="">、<aop:around method="">这五个标签
				if (isAdviceNode(node, parserContext)) {
					if (!adviceFoundAlready) {
						adviceFoundAlready = true;
						if (!StringUtils.hasText(aspectName)) {
							parserContext.getReaderContext().error(
									"<aspect> tag needs aspect bean reference via 'ref' attribute when declaring advices.",
									aspectElement, this.parseState.snapshot());
							return;
						}
						beanReferences.add(new RuntimeBeanReference(aspectName));
					}
					// 解析<aop:before>等五个子标签
					// 方法主要做了三件事：
					//		1、根据织入方式（before、after这些）创建RootBeanDefinition，名为adviceDef即advice定义
					//		2、将上一步创建的RootBeanDefinition写入一个新的RootBeanDefinition，构造一个新的对象，名为advisorDefinition，即advisor定义
					//		3、将advisorDefinition注册到DefaultListableBeanFactory中
					AbstractBeanDefinition advisorDefinition = parseAdvice(
							aspectName, i, aspectElement, (Element) node, parserContext, beanDefinitions, beanReferences);
					beanDefinitions.add(advisorDefinition);
				}
			}

			AspectComponentDefinition aspectComponentDefinition = createAspectComponentDefinition(
					aspectElement, aspectId, beanDefinitions, beanReferences, parserContext);
			parserContext.pushContainingComponent(aspectComponentDefinition);

			// 得到所有<aop:aspect>下的<aop:pointcut>子标签
			List<Element> pointcuts = DomUtils.getChildElementsByTagName(aspectElement, POINTCUT);
			for (Element pointcutElement : pointcuts) {
				// 解析<aop:pointcut>子标签
				parsePointcut(pointcutElement, parserContext);
			}

			parserContext.popAndRegisterContainingComponent();
		}
		finally {
			this.parseState.pop();
		}
	}

	private AspectComponentDefinition createAspectComponentDefinition(
			Element aspectElement, String aspectId, List<BeanDefinition> beanDefs,
			List<BeanReference> beanRefs, ParserContext parserContext) {

		BeanDefinition[] beanDefArray = beanDefs.toArray(new BeanDefinition[0]);
		BeanReference[] beanRefArray = beanRefs.toArray(new BeanReference[0]);
		Object source = parserContext.extractSource(aspectElement);
		return new AspectComponentDefinition(aspectId, beanDefArray, beanRefArray, source);
	}

	/**
	 * Return {@code true} if the supplied node describes an advice type. May be one of:
	 * '{@code before}', '{@code after}', '{@code after-returning}',
	 * '{@code after-throwing}' or '{@code around}'.
	 */
	private boolean isAdviceNode(Node aNode, ParserContext parserContext) {
		if (!(aNode instanceof Element)) {
			return false;
		}
		else {
			String name = parserContext.getDelegate().getLocalName(aNode);
			return (BEFORE.equals(name) || AFTER.equals(name) || AFTER_RETURNING_ELEMENT.equals(name) ||
					AFTER_THROWING_ELEMENT.equals(name) || AROUND.equals(name));
		}
	}

	/**
	 * Parse a '{@code declare-parents}' element and register the appropriate
	 * DeclareParentsAdvisor with the BeanDefinitionRegistry encapsulated in the
	 * supplied ParserContext.
	 */
	private AbstractBeanDefinition parseDeclareParents(Element declareParentsElement, ParserContext parserContext) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(DeclareParentsAdvisor.class);
		builder.addConstructorArgValue(declareParentsElement.getAttribute(IMPLEMENT_INTERFACE));
		builder.addConstructorArgValue(declareParentsElement.getAttribute(TYPE_PATTERN));

		String defaultImpl = declareParentsElement.getAttribute(DEFAULT_IMPL);
		String delegateRef = declareParentsElement.getAttribute(DELEGATE_REF);

		if (StringUtils.hasText(defaultImpl) && !StringUtils.hasText(delegateRef)) {
			builder.addConstructorArgValue(defaultImpl);
		}
		else if (StringUtils.hasText(delegateRef) && !StringUtils.hasText(defaultImpl)) {
			builder.addConstructorArgReference(delegateRef);
		}
		else {
			parserContext.getReaderContext().error(
					"Exactly one of the " + DEFAULT_IMPL + " or " + DELEGATE_REF + " attributes must be specified",
					declareParentsElement, this.parseState.snapshot());
		}

		AbstractBeanDefinition definition = builder.getBeanDefinition();
		definition.setSource(parserContext.extractSource(declareParentsElement));
		parserContext.getReaderContext().registerWithGeneratedName(definition);
		return definition;
	}

	/**
	 * Parses one of '{@code before}', '{@code after}', '{@code after-returning}',
	 * '{@code after-throwing}' or '{@code around}' and registers the resulting
	 * BeanDefinition with the supplied BeanDefinitionRegistry.
	 * @return the generated advice RootBeanDefinition
	 */
	private AbstractBeanDefinition parseAdvice(
			String aspectName, int order, Element aspectElement, Element adviceElement, ParserContext parserContext,
			List<BeanDefinition> beanDefinitions, List<BeanReference> beanReferences) {

		try {
			this.parseState.push(new AdviceEntry(parserContext.getDelegate().getLocalName(adviceElement)));

			// create the method factory bean
			// 创建方法工厂Bean的BeanDefinition对象：用于获取Advice增强类的Method对象
			RootBeanDefinition methodDefinition = new RootBeanDefinition(MethodLocatingFactoryBean.class);
			// 设置MethodLocatingFactoryBean的targetBeanName为advice类的引用名称
			methodDefinition.getPropertyValues().add("targetBeanName", aspectName);
			// 设置MethodLocatingFactoryBean的methodName为<aop:after>标签的method属性值（也就是advice方法名称）
			methodDefinition.getPropertyValues().add("methodName", adviceElement.getAttribute("method"));
			methodDefinition.setSynthetic(true);

			// create instance factory definition
			// 创建实例工厂BeanDefinition：用于创建增强类的实例
			RootBeanDefinition aspectFactoryDef =
					new RootBeanDefinition(SimpleBeanFactoryAwareAspectInstanceFactory.class);
			// 设置SimpleBeanFactoryAwareAspectInstanceFactory的aspectBeanName为advice类的引用名称
			aspectFactoryDef.getPropertyValues().add("aspectBeanName", aspectName);
			aspectFactoryDef.setSynthetic(true);
			
			//以上的两个BeanDefinition的作用主要是通过反射调用Advice对象的指定方法
			// method.invoke(obj,args)

			// register the pointcut
			// 通知增强类的BeanDefinition对象（核心）
			AbstractBeanDefinition adviceDef = createAdviceDefinition(
					adviceElement, parserContext, aspectName, order, methodDefinition, aspectFactoryDef,
					beanDefinitions, beanReferences);

			// configure the advisor
			// 通知器类的BeanDefinition对象
			RootBeanDefinition advisorDefinition = new RootBeanDefinition(AspectJPointcutAdvisor.class);
			advisorDefinition.setSource(parserContext.extractSource(adviceElement));
			// 给通知器类设置Advice对象属性值
			advisorDefinition.getConstructorArgumentValues().addGenericArgumentValue(adviceDef);
			if (aspectElement.hasAttribute(ORDER_PROPERTY)) {
				advisorDefinition.getPropertyValues().add(
						ORDER_PROPERTY, aspectElement.getAttribute(ORDER_PROPERTY));
			}

			// register the final advisor
			// 将advisorDefinition注册到IoC容器中
			parserContext.getReaderContext().registerWithGeneratedName(advisorDefinition);

			return advisorDefinition;
		}
		finally {
			this.parseState.pop();
		}
	}

	/**
	 * Creates the RootBeanDefinition for a POJO advice bean. Also causes pointcut
	 * parsing to occur so that the pointcut may be associate with the advice bean.
	 * This same pointcut is also configured as the pointcut for the enclosing
	 * Advisor definition using the supplied MutablePropertyValues.
	 */
	private AbstractBeanDefinition createAdviceDefinition(
			Element adviceElement, ParserContext parserContext, String aspectName, int order,
			RootBeanDefinition methodDef, RootBeanDefinition aspectFactoryDef,
			List<BeanDefinition> beanDefinitions, List<BeanReference> beanReferences) {

		// 根据通知类型的不同，分别创建对应的BeanDefinition对象(可以去看看getAdviceClass方法)
		RootBeanDefinition adviceDefinition = new RootBeanDefinition(getAdviceClass(adviceElement, parserContext));
		adviceDefinition.setSource(parserContext.extractSource(adviceElement));

		// 为不同的增强通知类，添加统一的属性值
		adviceDefinition.getPropertyValues().add(ASPECT_NAME_PROPERTY, aspectName);
		adviceDefinition.getPropertyValues().add(DECLARATION_ORDER_PROPERTY, order);

		// 为不同的增强通知类，添加对应的属性值
		if (adviceElement.hasAttribute(RETURNING)) {
			adviceDefinition.getPropertyValues().add(
					RETURNING_PROPERTY, adviceElement.getAttribute(RETURNING));
		}
		if (adviceElement.hasAttribute(THROWING)) {
			adviceDefinition.getPropertyValues().add(
					THROWING_PROPERTY, adviceElement.getAttribute(THROWING));
		}
		if (adviceElement.hasAttribute(ARG_NAMES)) {
			adviceDefinition.getPropertyValues().add(
					ARG_NAMES_PROPERTY, adviceElement.getAttribute(ARG_NAMES));
		}

		// 设置构造参数
		ConstructorArgumentValues cav = adviceDefinition.getConstructorArgumentValues();
		// 设置第一个构造参数：方法工厂对象的BeanDefinition
		cav.addIndexedArgumentValue(METHOD_INDEX, methodDef);

		// 解析<aop:before>、<aop:after>、<aop:after-returning>标签中的pointcut或者pointcut-ref属性
		Object pointcut = parsePointcutProperty(adviceElement, parserContext);
		// 设置第二个构造参数：切入点BeanDefinition
		if (pointcut instanceof BeanDefinition) {
			cav.addIndexedArgumentValue(POINTCUT_INDEX, pointcut);
			beanDefinitions.add((BeanDefinition) pointcut);
		}
		else if (pointcut instanceof String) {
			RuntimeBeanReference pointcutRef = new RuntimeBeanReference((String) pointcut);
			cav.addIndexedArgumentValue(POINTCUT_INDEX, pointcutRef);
			beanReferences.add(pointcutRef);
		}
		// 设置第三个构造参数：实例工厂BeanDefinition
		cav.addIndexedArgumentValue(ASPECT_INSTANCE_FACTORY_INDEX, aspectFactoryDef);

		return adviceDefinition;
	}

	/**
	 * Gets the advice implementation class corresponding to the supplied {@link Element}.
	 */
	private Class<?> getAdviceClass(Element adviceElement, ParserContext parserContext) {
		// 获取标签名称，比如aop:before标签对应的标签名是before
		String elementName = parserContext.getDelegate().getLocalName(adviceElement);
		// 处理<aop:before>标签
		if (BEFORE.equals(elementName)) {
			return AspectJMethodBeforeAdvice.class;
		}
		// 处理<aop:after>标签
		else if (AFTER.equals(elementName)) {
			return AspectJAfterAdvice.class;
		}
		// 处理<aop:after-returning>标签
		else if (AFTER_RETURNING_ELEMENT.equals(elementName)) {
			return AspectJAfterReturningAdvice.class;
		}
		// 处理<aop:after-throwing>标签
		else if (AFTER_THROWING_ELEMENT.equals(elementName)) {
			return AspectJAfterThrowingAdvice.class;
		}
		// 处理<aop:aroud>标签
		else if (AROUND.equals(elementName)) {
			return AspectJAroundAdvice.class;
		}
		else {
			throw new IllegalArgumentException("Unknown advice kind [" + elementName + "].");
		}
	}

	/**
	 * Parses the supplied {@code <pointcut>} and registers the resulting
	 * Pointcut with the BeanDefinitionRegistry.
	 */
	private AbstractBeanDefinition parsePointcut(Element pointcutElement, ParserContext parserContext) {
		// <aop:pointcut>标签的id属性
		String id = pointcutElement.getAttribute(ID);
		// <aop:pointcut>标签的expression属性
		String expression = pointcutElement.getAttribute(EXPRESSION);

		AbstractBeanDefinition pointcutDefinition = null;

		try {
			this.parseState.push(new PointcutEntry(id));
			// 此处创建一个 AspectJExpressionPointcut 类对应的BeanDefinition对象，处理pointcut
			pointcutDefinition = createPointcutDefinition(expression);
			// 解析源是<aop:pointcut>
			pointcutDefinition.setSource(parserContext.extractSource(pointcutElement));

			String pointcutBeanName = id;
			// 如果配置id属性，则走下面代码
			if (StringUtils.hasText(pointcutBeanName)) {
				parserContext.getRegistry().registerBeanDefinition(pointcutBeanName, pointcutDefinition);
			}
			// 如果没有配置id属性，则走下面代码
			else {
				pointcutBeanName = parserContext.getReaderContext().registerWithGeneratedName(pointcutDefinition);
			}

			parserContext.registerComponent(
					new PointcutComponentDefinition(pointcutBeanName, pointcutDefinition, expression));
		}
		finally {
			this.parseState.pop();
		}

		return pointcutDefinition;
	}

	/**
	 * Parses the {@code pointcut} or {@code pointcut-ref} attributes of the supplied
	 * {@link Element} and add a {@code pointcut} property as appropriate. Generates a
	 * {@link org.springframework.beans.factory.config.BeanDefinition} for the pointcut if  necessary
	 * and returns its bean name, otherwise returns the bean name of the referred pointcut.
	 */
	@Nullable
	private Object parsePointcutProperty(Element element, ParserContext parserContext) {
		if (element.hasAttribute(POINTCUT) && element.hasAttribute(POINTCUT_REF)) {
			parserContext.getReaderContext().error(
					"Cannot define both 'pointcut' and 'pointcut-ref' on <advisor> tag.",
					element, this.parseState.snapshot());
			return null;
		}
		else if (element.hasAttribute(POINTCUT)) { // 解析pointcut属性
			// Create a pointcut for the anonymous pc and register it.
			String expression = element.getAttribute(POINTCUT);
			// 创建切入点类AspectJExpressionPointcut的BeanDefinition对象
			// 注意：AspectJExpressionPointcut是原型的
			AbstractBeanDefinition pointcutDefinition = createPointcutDefinition(expression);
			pointcutDefinition.setSource(parserContext.extractSource(element));
			return pointcutDefinition;
		}
		else if (element.hasAttribute(POINTCUT_REF)) {// 解析pointcut-ref属性
			String pointcutRef = element.getAttribute(POINTCUT_REF);
			if (!StringUtils.hasText(pointcutRef)) {
				parserContext.getReaderContext().error(
						"'pointcut-ref' attribute contains empty value.", element, this.parseState.snapshot());
				return null;
			}
			return pointcutRef;
		}
		else {
			parserContext.getReaderContext().error(
					"Must define one of 'pointcut' or 'pointcut-ref' on <advisor> tag.",
					element, this.parseState.snapshot());
			return null;
		}
	}

	/**
	 * Creates a {@link BeanDefinition} for the {@link AspectJExpressionPointcut} class using
	 * the supplied pointcut expression.
	 */
	protected AbstractBeanDefinition createPointcutDefinition(String expression) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition(AspectJExpressionPointcut.class);
		beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		beanDefinition.setSynthetic(true);
		// 设置切入点表达式
		beanDefinition.getPropertyValues().add(EXPRESSION, expression);
		return beanDefinition;
	}

}
