package com.group26.todoList.model;

public class Result {

	private boolean status;

    private String message;

    private Object data;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public static Result build(boolean status, String message, Object data) {
        return new Result(status, message, data);
    }
    
	public Result(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
