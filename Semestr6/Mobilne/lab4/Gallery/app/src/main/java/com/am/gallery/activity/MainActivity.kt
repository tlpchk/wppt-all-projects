package com.am.gallery.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.am.gallery.R
import com.am.gallery.model.SharedViewModel


class MainActivity : AppCompatActivity() {
    lateinit var model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DetailActivity.REQUEST_CODE && data != null) {
            val rating = data.getFloatExtra(DetailActivity.EXTRA_RATING, 0f)
            val id = data.getIntExtra(DetailActivity.EXTRA_ID, 0)
            model.changeRating(id, rating)
        }
    }


}
