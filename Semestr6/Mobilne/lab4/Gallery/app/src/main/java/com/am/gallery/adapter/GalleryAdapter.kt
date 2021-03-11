package com.am.gallery.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.am.gallery.R
import com.am.gallery.activity.DetailActivity
import com.am.gallery.fragment.DetailFragment
import com.am.gallery.model.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_grid.view.*
import java.io.File


class GalleryAdapter(
    val context: Context,
    val photos: ArrayList<Photo>
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_grid, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]
        val imageView = holder.photoImageView
        load(photo,imageView)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    private fun loadFromUrl(imageView: ImageView, url: String) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .fit()
            .centerCrop()
            .into(imageView)
    }

    private fun loadFromFile(imageView: ImageView, filePath: String) {
        Picasso.get()
            .load(File(filePath))
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .fit()
            .centerCrop()
            .into(imageView)
    }

    fun load(photo: Photo, destImageView: ImageView){
        if(photo.filePath != null){
            loadFromFile(destImageView, photo.filePath)
        }else if(photo.url != null){
            loadFromUrl(destImageView,photo.url)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var photoImageView: ImageView = itemView.iv_photo

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val clickedPhoto = photos[adapterPosition]

            if (position == RecyclerView.NO_POSITION) {
                return
            }

            if (detailContainer() == null) {
                startDetailActivity(clickedPhoto)
            } else {
                setDetailFragment(clickedPhoto)
            }
        }

        private fun setDetailFragment(photo: Photo) {
            val detailFragment = DetailFragment()
            val ft = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            detailFragment.setArguments(
                _photo = photo,
                _photoId = photo.getId()
            )
            detailContainer()!!.visibility = View.VISIBLE
            ft.replace(R.id.detail_fl, detailFragment)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
        }

        private fun startDetailActivity(photo: Photo) {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_ID, photo.getId())
                putExtra(DetailActivity.EXTRA_PHOTO, photo)
            }
            (context as Activity).startActivityForResult(intent, DetailActivity.REQUEST_CODE)
        }

        private fun detailContainer(): FrameLayout? {
            return (context as Activity).detail_fl
        }
    }
}



