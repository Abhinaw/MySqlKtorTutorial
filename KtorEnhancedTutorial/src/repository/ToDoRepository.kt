package com.example.repository

import com.example.entities.ToDo
import com.example.entities.ToDoDraft

interface ToDoRepository
{
    fun getAllToDos():List<ToDo>
    fun getToDo(id : Int) : ToDo?
    fun addToDo(draft: ToDoDraft) : ToDo
    fun removeTodo(id: Int) : Boolean
    fun updatetodo(id:Int , draft: ToDoDraft) : Boolean
}