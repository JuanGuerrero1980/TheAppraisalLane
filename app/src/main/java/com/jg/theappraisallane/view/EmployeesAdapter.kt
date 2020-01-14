package com.jg.theappraisallane.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jg.theappraisallane.EmployeesContract

import com.jg.theappraisallane.model.Employee
import com.jg.theappraisallane.utils.loadImage
import kotlinx.android.synthetic.main.employee_item.view.*

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*




class EmployeesAdapter(private val list: MutableList<Employee>, private val viewContract: EmployeesContract.EmployeesView) : RecyclerView.Adapter<EmployeesAdapter.EmployeeHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.jg.theappraisallane.R.layout.employee_item, null, false)
        return EmployeeHolder(view, viewContract)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        holder.bind(list[position])
    }

    fun populateList( list: MutableList<Employee>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class EmployeeHolder(private val view: View, private val viewContract: EmployeesContract.EmployeesView): RecyclerView.ViewHolder(view){

        private val image = view.imageView
        private val name = view.textViewFullName
        private val dob = view.textViewDob

        fun bind(employee: Employee){
            image.loadImage(employee.picture.medium)
            val fullName = employee.name.first + " " + employee.name.last
            name.text =  fullName
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            dob.text = sdf.format(Date(LocalDate.parse(employee.dob.date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toEpochDay()))

            view.setOnClickListener{ viewContract.onClick(employee) }
        }

    }
}