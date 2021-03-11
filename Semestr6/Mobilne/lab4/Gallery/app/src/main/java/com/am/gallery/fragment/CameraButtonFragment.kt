package com.am.gallery.fragment


import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.am.gallery.R
import com.am.gallery.activity.MainActivity
import com.am.gallery.model.Photo
import com.am.gallery.model.SharedViewModel
import kotlinx.android.synthetic.main.fragment_camera_button.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CameraButtonFragment : Fragment(), View.OnClickListener {

    private lateinit var model: SharedViewModel
    private lateinit var takenPhoto: Photo

    companion object {
        const val CAMERA_REQUEST = 99
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera_button, container, false)
        val button = view.findViewById<ImageButton>(R.id.btn_camera)
        button.setOnClickListener(this)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = getModel()
    }

    private fun getModel(): SharedViewModel {
        if (activity != null) {
            return ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        } else {
            // if we don't have activity
            return ViewModelProviders.of(this).get(SharedViewModel::class.java)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            showDescriptionAlertDialog()
        }
    }

    private fun showDescriptionAlertDialog() {
        val alertDialog = AlertDialog.Builder(context!!).create()
        val editTextDialog = EditText(context)
        alertDialog.setTitle("Write description")
        alertDialog.setView(editTextDialog)
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE") { _, _ ->
            takenPhoto.description = editTextDialog.text.toString()
            model.addPhoto(takenPhoto)
        }
        alertDialog.show()
    }

    override fun onClick(v: View?) {
        val photoFile: File? = createImageFile()

        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                activity!!,
                "com.am.gallery.fileprovider",
                it
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(intent, CAMERA_REQUEST)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            takenPhoto = Photo(filePath = absolutePath)
        }
    }

}
