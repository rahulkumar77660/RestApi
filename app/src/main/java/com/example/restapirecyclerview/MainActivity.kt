package com.example.restapirecyclerview

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),getItemId {
    @SuppressLint("MissingInflatedId")
    lateinit var myAdapter: MyAdapter
    lateinit var listItem : List<PostDataItem>
    lateinit var linearLayoutManager: LinearLayoutManager
    var id: Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        listItem = listOf()
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()

        val postApi = retrofit.create(PostInterface::class.java)
        val myCall = postApi.getPostData()
        myCall.enqueue(object :Callback<List<PostDataItem>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<PostDataItem>>, response: Response<List<PostDataItem>>) {
                val postDatas = response.body()!!
                myAdapter = MyAdapter(baseContext,postDatas,this@MainActivity)
                myAdapter.notifyDataSetChanged()
                recyclerView.adapter=myAdapter
            }

            override fun onFailure(call: Call<List<PostDataItem>>, t: Throwable) {
                Log.e("ERROR",t.message.toString())
            }
        })

    }

    override fun getItemPosition(position: Int, id: Int, title: String, userId: Int) {
        val intent = Intent(this@MainActivity,UpdateActivity::class.java)
        intent.putExtra("id",id.toString())
        intent.putExtra("userId",userId.toString())
        intent.putExtra("title",title.toString())
        startActivity(intent)
    }

    override fun deleteItem(position: Int, id: Int) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()

        val postApi = retrofit.create(PostInterface::class.java)
        val myCall = postApi.deletePost(id)
        myCall.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                Toast.makeText(this@MainActivity, response.code().toString() + " delete Succssfully..", Toast.LENGTH_SHORT).show()
                myAdapter.notifyItemChanged(position)
                myAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message.toString() + " delete operatoin failed..", Toast.LENGTH_SHORT).show()
            }
        })
    }

}