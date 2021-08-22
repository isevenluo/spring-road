package com.sevenluo.springroad04.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-08-21 20:54
 **/
@Getter
@Setter
@ToString
public class JsonUser {

    private Address address;

    @JsonUnwrapped
    private UserInfo userInfo;

    @Getter
    @Setter
    @ToString
    public static class Address {

        private String home;

        private String location;
    }

    @Getter
    @Setter
    @ToString
    public static class UserInfo {
        private String username;

        private String age;
    }

}
