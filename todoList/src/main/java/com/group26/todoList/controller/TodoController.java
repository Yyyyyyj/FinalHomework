package com.group26.todoList.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group26.todoList.model.Result;
import com.group26.todoList.model.Task;
import com.group26.todoList.utils.FileDateUtil;

@RestController
@RequestMapping(value = "/api/tasks")
public class TodoController {
	
	/**
	 * 添加一个新的todo任务
	 * 
	 * @param task
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/", consumes = "application/json")
	public Result createTask(@RequestBody Map<String, Object> map) throws Exception {

		//封装新任务的数据
		String content = (String) map.get("content");
		Task newtask = new Task();
		String id = UUID.randomUUID().toString();
		newtask.setId(id);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		String time = dateFormat.format(new Date());
		newtask.setCreatedTime(time);
		newtask.setContent(content);
		
		//从文件中读取现有任务
		FileDateUtil fileDateUtil = new FileDateUtil();
		String data = fileDateUtil.readDate();
		List<Task> taskList = new ArrayList<>();
		if (!data.isEmpty()) {
			//判断是否已存在相同任务内容，若存在，则返回提示
			taskList = JSONObject.parseArray(data, Task.class);
			for (Task task : taskList) {
				if (task.getContent().equals(content)) {
					return Result.build(false, "任务已存在", null);
				}
			}
		}
		
		taskList.add(newtask);
		String newData = JSON.toJSONString(taskList);
		//将新的任务列表写入文件
		fileDateUtil.writeDate(newData);

		return Result.build(true, "添加任务成功", newtask);
	}


}
