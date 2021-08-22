package com.sevenluo.springroad04.dao;

import com.sevenluo.springroad04.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-08-21 14:55
 **/
public interface UserDao extends JpaRepository<User, Long> {
}
