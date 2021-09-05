package com.sevenluo.springroad03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description: 使用嵌入式的容器启动应用程序进行测试
 * @author: 程序员七哥
 * @create: 2021-09-05 12:46
 **/
@ExtendWith(SpringExtension.class) // 开启Spring集成测试支持
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadingListWebContainerTests {



    @Test
    @LocalServerPort
    @WithMockUser(username = "sevenluo",password = "123")
    public void pageNotFound() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject("http://localhost:{port}}/viewerList/sevenluo",String.class);
            fail("请求路径不存在404了");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }

    }
}
