package com.sevenluo.springroad04.controller;

import com.sevenluo.springroad04.entity.JsonUser;
import com.sevenluo.springroad04.entity.User;
import com.sevenluo.springroad04.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.List;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description: spring常用注解学习示例
 * @author: 程序员七哥
 * @create: 2021-08-21 14:45
 **/
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @Autowired(required = false)
    private User user;


    @PostMapping("/id")
    public ResponseEntity<List<User>> getUser(@RequestParam long id) {
        return new ResponseEntity(userService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<User>> findById( @PathVariable @Max(value = 5,message = "超过 id 的范围了") long id) {
        return new ResponseEntity(userService.findById(id),HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        userService.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/getJson")
    public ResponseEntity<User> getJsonUser() {
        JsonUser jsonData = getJsonData();
        return new ResponseEntity(jsonData,HttpStatus.OK);
    }

    @GetMapping("/getConditionalBean")
    public User getConditionalBean() {
        return user;
    }

    public JsonUser getJsonData() {
        JsonUser jsonUser = new JsonUser();
        JsonUser.Address address = new JsonUser.Address();
        address.setHome("西安市长安区");
        address.setLocation("韦曲");
        JsonUser.UserInfo userInfo = new JsonUser.UserInfo();
        userInfo.setUsername("程序员七哥");
        userInfo.setAge("26");
        jsonUser.setUserInfo(userInfo);
        jsonUser.setAddress(address);
        return jsonUser;
    }

}
