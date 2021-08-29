package com.sevenluo.springroad04.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.tomcat.jni.Address;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-08-21 14:49
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @JsonProperty(value = "id")
    private Long id;

    @NotNull(message = "name 不能为空")
    @NotEmpty
    @JsonProperty(value = "name")
    private String name;
}
