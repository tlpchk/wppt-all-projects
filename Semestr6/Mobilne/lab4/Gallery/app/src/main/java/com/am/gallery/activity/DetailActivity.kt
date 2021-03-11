package com.am.gallery.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.am.gallery.R
import com.am.gallery.fragment.DetailFragment

class DetailActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE = 100
        const val EXTRA_PHOTO = "EXTRA_PHOTO"
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_RATING = "EXTRA_RATING"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val fm = supportFragmentManager
        val detailFragment : DetailFragment = fm.findFragmentById(R.id.detail_frag) as DetailFragment
        detailFragment.setArguments(
            _photo = intent.getParcelableExtra(EXTRA_PHOTO),
            _photoId = intent.getIntExtra(EXTRA_ID,0))
    }

}
