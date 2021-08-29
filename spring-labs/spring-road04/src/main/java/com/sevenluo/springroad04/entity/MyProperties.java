package com.sevenluo.springroad04.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.io.Serializable;
import java.util.List;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-08-25 22:40
 **/
@Component
@ConfigurationProperties(prefix = "my", ignoreUnknownFields = false)
@Data
public class MyProperties implements Serializable {

    private String name;

    private String username;

    private List<Book> books;

    @Data
    static class Book implements Serializable{
        private int id;
        private String bookName;
    }

}


