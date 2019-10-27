package com.example.todoapp.api

import com.example.todoapp.data.entity.TodoItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TodoApiService {

    @GET("/items")
    fun getItemList(): Call<WrappedResponse<List<TodoItem>>>

    @GET("/item")
    fun getItem(@Query("id") id: Int): Call<WrappedResponse<TodoItem>>

    @POST("/item")
    fun postItem(@Body item: TodoItem): Call<WrappedResponse<Unit>> // post는 리턴하는 바디 값이 없기 때문에 Unit으로 넣어주었음. 오류 생긴다면 수정

    // 더 추
}