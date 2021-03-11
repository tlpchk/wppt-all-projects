package com.appteam.catchup.ui.createEvent


import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_create_event.view.*
import java.util.*
import android.text.Spanned
import android.text.InputFilter
import androidx.navigation.fragment.findNavController
import com.appteam.catchup.MapFragment
import com.appteam.catchup.R
import com.appteam.catchup.model.Event
import com.appteam.catchup.model.EventMember
import com.appteam.catchup.notification.NotificationService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream


class CreateEventFragment : Fragment(), View.OnClickListener {

    private val PICK_IMAGE_REQUEST_CODE = 100

    private lateinit var eventNameInput : EditText
    private lateinit var eventStartDateInput : EditText
    private lateinit var eventEndDateInput : EditText
    private lateinit var eventDescriptionInput : EditText
    private lateinit var eventImage : ImageView
    private lateinit var eventCapacity : EditText

    private lateinit var eventStartDate : Timestamp
    private lateinit var eventEndDate : Timestamp

    var globalUri : Uri = Uri.EMPTY


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_create_event, container, false)

        eventNameInput = view.event_name
        eventStartDateInput = view.event_start_date
        eventEndDateInput = view.event_end_date
        eventDescriptionInput = view.event_description
        eventImage = view.event_image
        eventCapacity = view.event_capacity

        eventCapacity.filters = arrayOf(InputFilterMinMax(1,1000))

        view.selectPlaceButton.setOnClickListener(this)
        eventStartDateInput.setOnClickListener(this)
        eventEndDateInput.setOnClickListener(this)
        eventImage.setOnClickListener(this)
        view.checkbox_meat.setOnClickListener(this)
        eventCapacity.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {

        val c : Calendar = Calendar.getInstance()

        val actualDay = c.get(Calendar.DAY_OF_MONTH)
        val actualMonth = c.get(Calendar.MONTH)
        val actualYear = c.get(Calendar.YEAR)
        val actualHour = c.get(Calendar.HOUR_OF_DAY)
        val actualMinute = c.get(Calendar.MINUTE)

        var selectedYear = 0
        var selectedMonth = 0
        var selectedDay = 0

        lateinit var timePickerListener : TimePickerDialog.OnTimeSetListener

        val datePickerListener : DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                selectedYear = year
                selectedMonth = month //because Calendar#MONTH goes like 0-11
                selectedDay = dayOfMonth

                val timePickerDialog = TimePickerDialog(this.context, timePickerListener, actualHour, actualMinute, true)
                timePickerDialog.show()
            }


        when(v?.id){
            R.id.event_start_date -> {

                timePickerListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    eventStartDateInput.setText(
                        getProperDateDisplayFormat(
                            hourOfDay, minute, selectedDay, selectedMonth, selectedYear
                        )
                    )

                    c.set(selectedYear, selectedMonth, selectedDay, hourOfDay, minute, 0)
                    eventStartDate = Timestamp(c.time)
                }

                val datePickerDialog = DatePickerDialog(this.context,  datePickerListener, actualYear, actualMonth, actualDay)
                datePickerDialog.datePicker.minDate=System.currentTimeMillis()
                datePickerDialog.show()
            }
            R.id.event_end_date -> {

                timePickerListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    eventEndDateInput.setText(
                        getProperDateDisplayFormat(
                            hourOfDay, minute, selectedDay, selectedMonth, selectedYear
                        )
                    )

                    c.set(selectedYear, selectedMonth, selectedDay, hourOfDay, minute, 0)
                    eventEndDate = Timestamp(c.time)
                }

                val datePickerDialog = DatePickerDialog(this.context,  datePickerListener, actualYear, actualMonth, actualDay)
                datePickerDialog.datePicker.minDate=System.currentTimeMillis()
                datePickerDialog.show()
            }
            R.id.selectPlaceButton -> {

                val capacity : String? = if(eventCapacity.visibility==View.VISIBLE){
                        eventCapacity.text.toString()
                } else {
                    null
                }

                val photoID = UUID.randomUUID().toString()
                val owner : EventMember = EventMember(FirebaseAuth.getInstance().currentUser?.displayName)

                println(isInputDataCorrect(eventNameInput.text.toString(), globalUri, capacity))
                if(isInputDataCorrect(eventNameInput.text.toString(), globalUri, capacity)) {
                    val event = Event(
                        eventNameInput.text.toString(),
                        eventDescriptionInput.text.toString(),
                        photoID,
                        eventStartDate,
                        eventEndDate,
                        Timestamp.now(),
                        capacity?.toLong(),
                        arguments!!.getParcelable<LatLng>(MapFragment.NEW_EVENT_POSITION),
                        owner,
                        arrayListOf(owner)
                    )

                    storageFileAndPutEventToDatabase(photoID, event)

                }
            }
            R.id.event_image -> {
                openGallery()
            }
            R.id.checkbox_meat -> {
                addEditText(v)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode==PICK_IMAGE_REQUEST_CODE){
            globalUri = data!!.data
            val imageUri = data.data
            Picasso.get().load(imageUri).fit().centerCrop().into(eventImage)
        }
    }

    private fun storageFileAndPutEventToDatabase(fileID : String, event : Event){

        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()

        val file = compressImageToFileInBytes()

        val storage : FirebaseStorage = FirebaseStorage.getInstance()
        val storageReference : StorageReference = storage.reference
        val ref : StorageReference = storageReference.child("images/$fileID")

        ref.putBytes(file)
            .addOnSuccessListener {
                val database = FirebaseFirestore.getInstance()
                database.collection("events").add(event).addOnSuccessListener {
                    val bundle = Bundle()
                    bundle.putParcelable(MapFragment.EVENT_POSITION, event.location)
                    findNavController().navigate(R.id.action_createEvent_to_mapScreen, bundle)
                    NotificationService.scheduleNotification(context!!, 314, event)
                    NotificationService.scheduleNotification(context!!, 315, 2000, "Utworzyłeś wydarzenie!", "Powodzenia!")
                }
                progressDialog.dismiss()
            }
            .addOnFailureListener {progressDialog.dismiss()}
    }

    private fun compressImageToFileInBytes() : ByteArray {

        val bmp: Bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, globalUri)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)

        return baos.toByteArray()
    }

    private fun openGallery(){

        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE_REQUEST_CODE)
    }

    private fun getProperDateDisplayFormat(hour:Int, minute:Int, day:Int, month:Int, year :Int) : String {
        return String.format("%s:%s %s-%s-%d",
            concatenateWithZeroIfItsSmallerThan10(hour),
            concatenateWithZeroIfItsSmallerThan10(minute),
            concatenateWithZeroIfItsSmallerThan10(day),
            concatenateWithZeroIfItsSmallerThan10(month),
            year
        )
    }

    private fun concatenateWithZeroIfItsSmallerThan10(number :Int) : String {

        if(number<10){
            return "0$number"
        }
        return number.toString()
    }

    private fun isInputDataCorrect(name : String, globalUri : Uri, capacity : String?) : Boolean{

        var errorMsg = ""
        if(name.isEmpty())
            errorMsg="Event name cannot be empty"
        else if(name.length>25)
            errorMsg="Your title is too long"
        if(globalUri.equals(Uri.EMPTY))
            errorMsg="Upload a photo"
        if(!::eventStartDate.isInitialized)
            errorMsg="Set up start date"
        else if(!::eventEndDate.isInitialized)
            errorMsg="Set up end date"
        else if(eventStartDate>eventEndDate)
            errorMsg="Event end date should be later than start date..."
        if(capacity!=null && capacity.isNotEmpty() && capacity.toInt()<2 || capacity!=null && capacity.isEmpty())
            errorMsg="Event capacity should be equal at least 2"
        if(errorMsg.isNotEmpty())
            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()

        return errorMsg.isEmpty()
    }

    private fun addEditText(v : View){
        if (v is CheckBox) {
            if(v.isChecked)
                eventCapacity.visibility=View.VISIBLE
            else
                eventCapacity.visibility=View.GONE
        }
    }


    inner class InputFilterMinMax(private var min: Int, private var max: Int) : InputFilter {

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            try {
                val input = Integer.parseInt(dest.toString() + source.toString())
                if (isInRange(min, max, input))
                    return null
            } catch (nfe: NumberFormatException) {
            }

            return ""
        }

        private fun isInRange(a: Int, b: Int, c: Int): Boolean {
            return if (b > a) c in a..b else c in b..a
        }
    }



}
