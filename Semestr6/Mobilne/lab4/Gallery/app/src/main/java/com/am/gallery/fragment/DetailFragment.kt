package com.am.gallery.fragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.am.gallery.R
import com.am.gallery.activity.DetailActivity
import com.am.gallery.model.Photo
import com.am.gallery.model.SharedViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_grid.*
import java.io.File

class DetailFragment : Fragment() {
    private var photoId: Int? = null
    private var photo: Photo? = null
    private lateinit var model: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }


    override fun onStart() {
        super.onStart()
        photo = arguments?.getParcelable(DetailActivity.EXTRA_PHOTO)
        photoId = arguments?.getInt(DetailActivity.EXTRA_ID)

        load(photo,iv_image)
        if(photo == null){
            throw Exception("No photo to display")
        }
        rb_rating.rating = photo!!.rating


        rb_rating.setOnRatingBarChangeListener { _, rating, _ ->
            if (activity?.detail_fl == null) {
                val intent = Intent()

                intent.putExtra(DetailActivity.EXTRA_RATING, rating)
                intent.putExtra(DetailActivity.EXTRA_ID, photoId)

                activity?.setResult(DetailActivity.REQUEST_CODE, intent)
                activity?.finish()
            } else {
                model.changeRating(photoId, rating)
                fragmentManager?.findFragmentById(R.id.grid_frag)?.rv_images?.adapter?.notifyDataSetChanged()
            }
        }
        tv_description.text = photo!!.description
    }


    fun setArguments(_photoId: Int, _photo: Photo){
        val bundle = Bundle(2)
        bundle.putParcelable(DetailActivity.EXTRA_PHOTO, _photo)
        bundle.putInt(DetailActivity.EXTRA_ID,_photoId)
        this.arguments = bundle
    }

    private fun loadFromUrl(imageView: ImageView, url: String) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(imageView)
    }

    private fun loadFromFile(imageView: ImageView, filePath: String?) {
        Picasso.get()
            .load(File(filePath))
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(imageView)
    }

    fun load(photo: Photo?, destImageView: ImageView){
        if(photo == null){
            loadFromFile(destImageView,null)
            return
        }
        if(photo.filePath != null){
            loadFromFile(destImageView, photo.filePath)
        }else if(photo.url != null){
            loadFromUrl(destImageView,photo.url)
        }
    }

}
