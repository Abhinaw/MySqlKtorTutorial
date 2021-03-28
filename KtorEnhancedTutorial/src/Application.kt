package com.example

import com.example.entities.ToDoDraft
import com.example.repository.InMemoryToDoRepository
import com.example.repository.MySQLTodoRepository
import com.example.repository.ToDoRepository
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CallLogging)
    install(ContentNegotiation)
    {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        val repository: ToDoRepository = MySQLTodoRepository()
        get("/todos")
        {
            call.respond(repository.getAllToDos())
        }
        get("/todos/{id}")
        {
            val todoId = call.parameters["id"]?.toIntOrNull()
            call.respond(todoId?.let { it1 -> repository.getToDo(it1) }!!)
        }
        post("/todos")
        {
            val todoDraft = call.receive<ToDoDraft>()
            val todo = repository.addToDo(todoDraft)
            call.respond(todo)
        }
        put("/todos/{id}")
        {
            val todoDraft = call.receive<ToDoDraft>()
            val todoId = call.parameters["id"]?.toIntOrNull()
            if (todoId == null) {
                call.respond(HttpStatusCode.BadRequest, "id  parameter has to be a number!")
                return@put
            }
            val update = repository.updatetodo(todoId, todoDraft)
            if (update) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "found no todo with the id $todoId")
            }
        }
        delete("/todos/{id}")
        {
            val todoDraft = call.receive<ToDoDraft>()
            val todoId = call.parameters["id"]?.toIntOrNull()
            if (todoId == null) {
                call.respond(HttpStatusCode.BadRequest, "id  parameter has to be a number!")
                return@delete
            }
            val removed = repository.removeTodo(todoId)
            if (removed) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "removed no todo with the id $todoId")
            }
        }
    }
}

