package com.sevenluo.springroad03;

import com.sevenluo.springroad03.entity.Video;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description: 使用 Mock MVC 模式请求处理
 * @author: 程序员七哥
 * @create: 2021-08-29 20:57
 **/
@ExtendWith(SpringExtension.class) // 开启Spring集成测试支持
@SpringBootTest(classes = SpringRoad03Application.class)  //1.4版本之前用的是 @SpringApplicationConfiguration(classes = SpringRoad03Application.class)
@WebAppConfiguration // 声明创建的应用上下文是一个 WebApplicationContext
public class ReadlingListMockMvcWebTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/viewerList/sevenluo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("readingList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("videos"))
                .andExpect(MockMvcResultMatchers.model().attribute("videos",
                        Matchers.is(Matchers.empty())));
    }

    @Test
    public void postVideo() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/viewerList/sevenluo").contentType(
                MediaType.APPLICATION_FORM_URLENCODED)
                .param("title","视频标题")
                .param("author","创作者姓名")
                .param("isbn","123")
                .param("description","视频描述")
                .param("reader","读者姓名")
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location","/viewerList/sevenluo"));
        Video expectVideo = new Video();
        expectVideo.setId(1L);
        expectVideo.setReader("sevenluo");
        expectVideo.setTitle("视频标题");
        expectVideo.setAuthor("创作者姓名");
        expectVideo.setIsbn("123");
        expectVideo.setDescription("视频描述");

        mockMvc.perform(MockMvcRequestBuilders.get("/viewerList/sevenluo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("readingList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("videos"))
                .andExpect(MockMvcResultMatchers.model().attribute("videos",hasSize(1)))
                .andExpect(MockMvcResultMatchers.model().attribute("videos",
                        contains(samePropertyValuesAs(expectVideo))));

    }

    @Test
    public void homePage_unauthenticatedUser() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/viewerList/sevenluo"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }




}
