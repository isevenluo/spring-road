package com.sevenluo.springroad04.service;

import com.sevenluo.springroad04.dao.UserDao;
import com.sevenluo.springroad04.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-08-21 14:47
 **/
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> findAll() {
        List<User> userList = userDao.findAll();
        return userList;
    }

    public void save(User user) {
        userDao.save(user);
    }

    public User findById(long id) {
        return userDao.findById(id).get();
    }
}
