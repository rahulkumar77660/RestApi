package com.example.restapirecyclerview

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostInterface {
    @GET("posts")
    fun getPostData() : Call<List<PostDataItem>>

    @PUT("posts/{postId}")
    fun updatePost(@Path("postId") postId: Int, @Body updatedPost: PostDataItem): Call<PostDataItem>

    @DELETE("posts/{postId}")
    fun deletePost(@Path("postId") postId: Int): Call<Void>

}