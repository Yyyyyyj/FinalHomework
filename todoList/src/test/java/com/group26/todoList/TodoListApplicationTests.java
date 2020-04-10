package com.group26.todoList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.group26.todoList.controller.TodoController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoListApplicationTests {

	private MockMvc mvc;

	@Autowired
	private TodoController todoController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mvc = MockMvcBuilders.standaloneSetup(todoController).build();
	}

	@Test
	public void testTaskController() throws Exception {
		RequestBuilder request;

		/* 新增任务测试 */
		request = post("/api/tasks/").contentType(MediaType.APPLICATION_JSON)
				.content("{\"content\":\"addTodo test\"}");
		mvc.perform(request).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("添加任务成功"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("addTodo test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.createdTime").isNotEmpty());

		/* 新增相同任务测试 */
		request = post("/api/tasks/").contentType(MediaType.APPLICATION_JSON)
				.content("{\"content\":\"addTodo test\"}");
		mvc.perform(request).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(false))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("任务已存在"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());

	}

}
