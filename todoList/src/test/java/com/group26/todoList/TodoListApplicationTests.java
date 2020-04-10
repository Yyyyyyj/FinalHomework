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
	public void testAddTask() throws Exception {
		RequestBuilder request;

		/* 新增任务测试 */
		request = post("/api/tasks/").contentType(MediaType.APPLICATION_JSON)
				.content("{\"content\":\"addTodo test2\"}");
		mvc.perform(request).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("添加任务成功"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("addTodo test2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.createdTime").isNotEmpty());

		/* 新增相同任务测试 */
		request = post("/api/tasks/").contentType(MediaType.APPLICATION_JSON)
				.content("{\"content\":\"addTodo test2\"}");
		mvc.perform(request).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(false))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("任务已存在"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
	}
	
	
	@Test
	public void testDeleteTask() throws Exception {
		RequestBuilder request;
		
		/* 删除任务存在时的测试 */
		request = delete("/api/tasks/8da476eb-6e9f-40ff-9120-fd10080616c4");
		mvc.perform(request).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("删除成功"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
	}

	
	/* 对应任务ID不在任务列表中时的测试 */
	@Test
	public void testErrorId() throws Exception {
		RequestBuilder request;
		
		/* 对应任务ID不在任务列表中时的删除测试 */
		request = delete("/api/tasks/1b6f7dac-2425-474d-835b-7746cb154229");
		mvc.perform(request).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(false))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("id对应任务不存在"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
	}
	
	
	/*空列表测试 */
	@Test
	public void testEmpty() throws Exception {
		RequestBuilder request;
		
		/* 任务列表中为空时的删除测试 */
		request = delete("/api/tasks/d5c1c9ee-d657-476d-8fa1-ae432d3e8e73");
		mvc.perform(request).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(false))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("当前待办事项为空"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
	}
}
