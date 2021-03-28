package com.example.repository

import com.example.database.DatabaseManager
import com.example.entities.ToDo
import com.example.entities.ToDoDraft

class MySQLTodoRepository : ToDoRepository {

    private val databse = DatabaseManager()

    override fun getAllToDos(): List<ToDo> {
       return  databse.getAllTodos().map { ToDo(it.id,it.title,it.done) }
    }

    override fun getToDo(id: Int): ToDo? {
        return  databse.getTodo(id) ?.let { ToDo(it.id, it.title ,it.done) }
    }

    override fun addToDo(draft: ToDoDraft): ToDo {
        return  databse.addTod(draft)
    }

    override fun removeTodo(id: Int): Boolean {
       return databse.removeTodo(id)
    }

    override fun updatetodo(id: Int, draft: ToDoDraft) : Boolean {
         return databse.updateTodo(id , draft)
    }
}