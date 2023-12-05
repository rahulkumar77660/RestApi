package com.example.restapirecyclerview

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class UpdateActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val getId : EditText = findViewById(R.id.getId)
        val getUserId : EditText = findViewById(R.id.getName)
        val getTitle : EditText = findViewById(R.id.gettTitle)

        val updateBtn : Button = findViewById(R.id.updateBtn)

        val intentTitle = intent.getStringExtra("title")
        val intentId = intent.getStringExtra("id")
        val intentUserId = intent.getStringExtra("userId")

        getId.setText(intentId.toString())
        getUserId.setText(intentUserId.toString())
        getTitle.setText(intentTitle.toString())

        updateBtn.setOnClickListener {


            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val updateApi = retrofit.create(PostInterface::class.java)



            val postId = intentId // Replace this with the ID of the post you want to update.
            val updatedPostDataItem = PostDataItem(
                getUserId.text.toString().toInt(),
                getTitle.text.toString(),
                getId.text.toString().toInt()
            )

            val updateCall = updateApi.updatePost(postId!!.toInt(), updatedPostDataItem)
            updateCall.enqueue(object : Callback<PostDataItem?> {
                override fun onResponse(
                    call: Call<PostDataItem?>,
                    response: Response<PostDataItem?>
                ) {
                    startActivity(Intent(this@UpdateActivity, MainActivity::class.java))
                    Toast.makeText(this@UpdateActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<PostDataItem?>, t: Throwable) {

                }

            })

        }



    }
}