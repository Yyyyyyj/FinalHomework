package com.group26.todoList.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/", consumes = "application/json")
	public Result createTask(@RequestBody Map<String, Object> map) throws Exception {

		// 封装新任务的数据
		String content = (String) map.get("content");
		Task newtask = new Task();
		String id = UUID.randomUUID().toString();
		newtask.setId(id);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		String time = dateFormat.format(new Date());
		newtask.setCreatedTime(time);
		newtask.setContent(content);

		// 从文件中读取现有任务
		FileDateUtil fileDateUtil = new FileDateUtil();
		String data = fileDateUtil.readDate();
		List<Task> taskList = new ArrayList<>();
		if (!data.isEmpty()) {
			// 判断是否已存在相同任务内容,若存在,则返回提示
			taskList = JSONObject.parseArray(data, Task.class);
			for (Task task : taskList) {
				if (task.getContent().equals(content)) {
					return Result.build(false, "任务已存在", null);
				}
			}
		}

		// 像任务列表中添加新的任务
		taskList.add(newtask);
		String newData = JSON.toJSONString(taskList);
		// 将新的任务列表写入文件
		fileDateUtil.writeDate(newData);

		return Result.build(true, "添加任务成功", newtask);
	}

	/**
	 * 根据id删除一个task
	 * 
	 * @param id 任务id
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/{id}")
	public Result delectTaskById(@PathVariable String id) throws Exception {

		FileDateUtil fileDateUtil = new FileDateUtil();
		String data = fileDateUtil.readDate();
		List<Task> taskList = JSONObject.parseArray(data, Task.class);

		// 判断文件是否为空
		if (taskList == null || taskList.size() == 0) {
			return Result.build(false, "当前待办事项为空", null);
		}

		// 判断文件中是否有当前任务
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getId().equals(id)) {
				// 删除当前任务
				taskList.remove(i);
				String newData = JSON.toJSONString(taskList);
				// 将删除后的任务列表重新写入覆盖原数据
				fileDateUtil.writeDate(newData);
				return Result.build(true, "删除成功", null);
			}
		}

		return Result.build(false, "id对应任务不存在", null);
	}

	/**
	 * 修改task
	 * 
	 * @param id,content
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/", consumes = "application/json")
	public Result updateTask(@RequestBody Map<String, Object> map) throws Exception {

		String newContent = (String) map.get("content");
		String id = (String) map.get("id");

		FileDateUtil fileDateUtil = new FileDateUtil();
		String data = fileDateUtil.readDate();
		List<Task> taskList = JSONObject.parseArray(data, Task.class);

		// 判断文件是否为空
		if (taskList == null || taskList.size() == 0) {
			return Result.build(false, "当前待办事项为空", null);
		}

		HashSet<String> set = new HashSet<>();
		for(Task task: taskList) {
			set.add(task.getContent());
		}
		for (int i = 0; i < taskList.size(); i++) {
			// 判断文件中是否有当前任务id
			if (taskList.get(i).getId().equals(id)) {
				//判断是否已存在相同任务
				if(set.contains(newContent)) {
					return Result.build(false, "已存在相同任务", null);
				}
				else {
					// 修改当前任务
					taskList.get(i).setContent(newContent);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
					String newTime = dateFormat.format(new Date());
					taskList.get(i).setCreatedTime(newTime);
					// 将删除后的任务列表重新写入覆盖原数据
					String newData = JSON.toJSONString(taskList);
					fileDateUtil.writeDate(newData);
					return Result.build(true, "修改成功", null);
				}
				
			}
		}

		return Result.build(false, "id对应任务不存在", null);
	}

	/**
	 * 返回所有todo任务
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/")
	public Result getAllTask() throws Exception {

		// 读取文件中的数据
		FileDateUtil fileDateUtil = new FileDateUtil();
		String data = fileDateUtil.readDate();
		List<Task> taskList = JSONObject.parseArray(data, Task.class);
		// 判断文件是否为空
		if (taskList == null || taskList.size() == 0) {
			return Result.build(false, "暂无待办任务", null);
		} else {
			// 若任务列表不为空，将数据转化为对象，返回任务列表数据
			taskList = JSONObject.parseArray(data, Task.class);
		}

		return Result.build(true, "success", taskList);
	}
}
