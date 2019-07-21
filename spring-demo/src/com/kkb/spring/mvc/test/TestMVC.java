package com.kkb.spring.mvc.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.annotation.Resource;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-mvc.xml")
@WebAppConfiguration
public class TestMVC {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test() throws Exception {
		this.mockMvc.perform(post("/demo/test").param("id", "11").param("name", "jerry")).andExpect(view().name("test")).andDo(print())
				.andReturn();
	}
	@Test
	public void test2() throws Exception {
		MvcResult result = this.mockMvc.perform(post("/demo/test2").param("id", "11").param("name", "jerry")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
}
