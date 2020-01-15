package com.jg.theappraisallane.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jg.theappraisallane.R
import com.jg.theappraisallane.model.Employee
import com.jg.theappraisallane.utils.loadImage
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*



class EmployeeDetailActivity : AppCompatActivity() {

    private lateinit var employee: Employee
    private lateinit var imageView: ImageView
    private lateinit var phoneCall: ImageView
    private lateinit var name: TextView
    private lateinit var phone: TextView
    private lateinit var dob: TextView
    private lateinit var mail: TextView
    private lateinit var address: TextView
    private lateinit var userName: TextView
    private lateinit var pass: TextView
    val EXTRA_EMPLOYEE = "EXTRA_EMPLOYEE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)
        employee = intent.getSerializableExtra(EXTRA_EMPLOYEE) as Employee
        imageView = findViewById(R.id.profile)
        name = findViewById(R.id.name)
        phone = findViewById(R.id.mobileNumber)
        dob = findViewById(R.id.dob)
        mail = findViewById(R.id.email)
        phoneCall = findViewById(R.id.phoneCall)
        address = findViewById(R.id.address)

        imageView.loadImage(employee.picture.large)
        name.text = getString(com.jg.theappraisallane.R.string.full_name, employee.name.first, employee.name.last)
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        dob.text = sdf.format(
            Date(
                LocalDate.parse(
                    employee.dob.date,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                ).toEpochDay()
            )
        )
        mail.text = employee.email
        phone.text = employee.phone
        address.text = "${employee.location.street.name} ${employee.location.street.number}, ${employee.location.city}"
        phoneCall.setOnClickListener{checkPermission()}
        address.setOnClickListener{openMap()}
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CALL_PHONE
                )
            ) {
                // dialog para que permita el permiso
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    100
                )
            }
        } else {
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == 100) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                callPhone()
            }
            return
        }
    }

    private fun callPhone() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + employee.phone))
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(intent)
        }
    }

    private fun openMap(){
        val address = "q:${employee.location.street.name} ${employee.location.street.number}, ${employee.location.city}"
        Log.d(EmployeeDetailActivity::class.java.simpleName, "To Address: $address")
        val gmmIntentUri = Uri.parse(address)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        }else{
            Toast.makeText(this, R.string.no_map_view, Toast.LENGTH_LONG).show()
        }
    }
}
