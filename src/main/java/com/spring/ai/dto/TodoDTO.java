package com.spring.ai.dto;

import java.util.List;

public class TodoDTO {

    private String title;
    private String subTitle;
    private String listTitle;
    private List<TodoListDTO> todoList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public List<TodoListDTO> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<TodoListDTO> todoList) {
        this.todoList = todoList;
    }
}
