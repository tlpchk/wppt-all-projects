package com.am.gallery.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.gallery.R
import com.am.gallery.adapter.GalleryAdapter
import com.am.gallery.model.SharedViewModel
import kotlinx.android.synthetic.main.fragment_grid.*


class GridFragment : Fragment() {
    lateinit var model: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid, container, false)
    }

    override fun onStart() {
        super.onStart()
        val layoutManager = GridLayoutManager(activity, 2)

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        rv_images.setHasFixedSize(true)
        rv_images.layoutManager = layoutManager
        rv_images.adapter = GalleryAdapter(activity!!, model.getPhotos())
    }
}
