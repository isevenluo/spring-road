package com.architecture.spring.beans.parser;

import com.architecture.spring.beans.config.Resource;
import com.architecture.spring.beans.factory.DefaultListableBeanFactory;
import com.architecture.spring.beans.utils.DocumentReader;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-07-16 22:40
 */
public class XmlBeanDefinationParser {

    public void loadBeanDefinations(DefaultListableBeanFactory beanFactory, Resource resource) {

        /**
         * 读取配置文件
         */
        InputStream inputStream = resource.getInputStream();
        Document document = DocumentReader.createDocument(inputStream);
        XmlBeanDefinationDocumentParser xmlBeanDefinationDocumentParser = new XmlBeanDefinationDocumentParser(beanFactory);
        xmlBeanDefinationDocumentParser.loadBeanDefinations(document.getRootElement());

    }

}
