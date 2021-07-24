package com.architecture.spring.beans.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-07-16 22:43
 */
public class DocumentReader {

    /**
     * 使用dom4j创建Document对象
     * @param inputStream
     * @return
     */
    public static Document createDocument(InputStream inputStream) {
        Document document;

        try {
            SAXReader reader = new SAXReader();
            document = reader.read(inputStream);
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
