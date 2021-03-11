package com.am.advancedcalculator

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var newton: NewtonAPI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = Retrofit.Builder()
            .baseUrl("http://156.17.7.48:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newton = retrofit.create(NewtonAPI::class.java)

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Operations.hmap.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sp_operation.adapter = adapter
        hideKeyboardOnEnter()
    }

    private fun hideKeyboardOnEnter() {
        et_expression.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val inputManager: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)

                return@OnKeyListener true
            }
            return@OnKeyListener false
        })
    }


    fun calculate(view: View) {
        tv_result.text = "Calculating..."
        val expression = et_expression.text.toString()
        val operation = Operations.hmap[sp_operation.selectedItem.toString()]!!


        if(expression == ""){
            tv_result.text = "Print expression"
            return
        }

        val callNewton = newton.calculate(operation, expression)


        callNewton.enqueue(object : Callback<NewtonDTO> {
            override fun onResponse(call: Call<NewtonDTO>, response: Response<NewtonDTO>) {
                val body = response.body()
                tv_result.text = body!!.result.toString()
            }

            override fun onFailure(call: Call<NewtonDTO>, t: Throwable) {
                tv_result.text = "Failure"
            }
        })
    }
}
