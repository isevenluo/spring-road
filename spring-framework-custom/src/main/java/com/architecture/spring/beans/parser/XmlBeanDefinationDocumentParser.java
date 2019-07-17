package com.architecture.spring.beans.parser;

import com.architecture.spring.beans.config.BeanDefinition;
import com.architecture.spring.beans.config.PropertyValue;
import com.architecture.spring.beans.config.RuntimeBeanReference;
import com.architecture.spring.beans.config.TypedStringValue;
import com.architecture.spring.beans.factory.DefaultListableBeanFactory;
import com.architecture.spring.beans.utils.ReflectUtils;
import org.dom4j.Element;

import java.util.List;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-07-16 22:46
 */
public class XmlBeanDefinationDocumentParser {

    private DefaultListableBeanFactory beanFactory;

    public  XmlBeanDefinationDocumentParser(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     *
     * @param rootElement
     *            beans根标签
     */
    public void loadBeanDefinations(Element rootElement) {
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            // 获取标签名称
            String name = element.getName();
            if (name.equals("bean")) {
                parseDefaultElement(element);
            } else {
                parseCustomElement(element);
            }
        }

        // 2.将bean信息封装到BeanDefinition对象中
        // 对bean标签解析解析
        // class信息
        // id信息
        // init-method信息
        // property标签信息----PropertyValue对象（name和value）
        // name信息
        // value信息
        // value属性---属性值、属性类型（属性赋值的时候，需要进行类型转换）TypedStringValue
        // ref属性---RuntimeBeanReference(bean的名称)---根据bean的名称获取bean的实例，将获取到的实例赋值该对象
        // 3.再将BeanDefinition放入beanDefinations集合对象中

    }

    /**
     *
     * @param beanElement
     *            bean标签
     */
    private void parseDefaultElement(Element beanElement) {
        try {
            if (beanElement == null)
                return;
            // 获取id属性
            String id = beanElement.attributeValue("id");

            // 获取name属性
            String name = beanElement.attributeValue("name");
            // 获取class属性
            String clazz = beanElement.attributeValue("class");
            Class<?> clazzType = Class.forName(clazz);
            // 获取init-method属性
            String initMethod = beanElement.attributeValue("init-method");

            String beanName = id == null ? name : id;
            beanName = beanName == null ? clazzType.getSimpleName() : beanName;
            // 创建BeanDefinition对象
            BeanDefinition beanDefinition = new BeanDefinition(clazz, beanName);
            beanDefinition.setInitMethod(initMethod);
            // 获取property子标签集合
            List<Element> propertyElements = beanElement.elements();
            for (Element propertyElement : propertyElements) {
                parsePropertyElement(beanDefinition, propertyElement);
            }

            // 注册BeanDefinition信息
            registerBeanDefinition(beanName, beanDefinition);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * 解析property子标签
     *
     * @param beanDefinition
     *
     * @param propertyElement
     */
    private void parsePropertyElement(BeanDefinition beanDefinition, Element propertyElement) {
        if (propertyElement == null)
            return;

        // 获取name属性
        String name = propertyElement.attributeValue("name");
        // 获取value属性
        String value = propertyElement.attributeValue("value");
        // 获取ref属性
        String ref = propertyElement.attributeValue("ref");

        // 如果value和ref都有值，则返回
        if (value != null && !value.equals("") && ref != null && !ref.equals("")) {
            return;
        }

        /**
         * PropertyValue就封装着一个property标签的信息
         */
        PropertyValue pv ;

        if (value != null && !value.equals("")) {
            // 因为spring配置文件中的value是String类型，而对象中的属性值是各种各样的，所以需要存储类型
            TypedStringValue typeStringValue = new TypedStringValue(value);

            Class<?> targetType = ReflectUtils.getTypeByFieldName(beanDefinition.getBeanClassName(), name);
            typeStringValue.setTargetType(targetType);

            pv = new PropertyValue(name, typeStringValue);
            beanDefinition.addPropertyValue(pv);
        } else if (ref != null && !ref.equals("")) {

            RuntimeBeanReference reference = new RuntimeBeanReference(ref);
            pv = new PropertyValue(name, reference);
            beanDefinition.addPropertyValue(pv);
        } else {
            return;
        }
    }

    /***
     * 将解析之后的BeanDefinition信息，封装到BeanFactory中
     *
     * @param bd
     * @param beanName
     */
    private void registerBeanDefinition(String beanName, BeanDefinition bd) {
        this.beanFactory.registerBeanDefinition(beanName, bd);
    }

    // 其他标签
    private void parseCustomElement(Element element) {


    }

}
