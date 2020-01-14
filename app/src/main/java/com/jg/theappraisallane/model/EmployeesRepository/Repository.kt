package com.jg.theappraisallane.model.EmployeesRepository

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.jg.theappraisallane.model.Employee
import com.jg.theappraisallane.model.ResponseEmployees

class Repository {

    companion object {
        val TAG = Repository::class.java.simpleName
    }

    fun getEmployees(listener: EmployeesListener){
        AndroidNetworking.get("https://randomuser.me/api/?results=30")
            .setTag(TAG)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(ResponseEmployees::class.java, object :
                ParsedRequestListener<ResponseEmployees> {
                override fun onResponse(newsResponseNullable: ResponseEmployees) {
                    val employeesItemList: MutableList<Employee>? = mutableListOf()

                    newsResponseNullable
                        .results?.asSequence()?.filterNotNull()
                        ?.map { Employee(it.name, it.dob, it.picture, it.email, it.login) }?.toList()
                        ?.forEach { employeesItemList?.add(it) }

                    if (employeesItemList != null && !employeesItemList.isEmpty()) {
                        listener.onSuccess(employeesItemList)
                    } else {
                        listener.onError("Nothing to show")
                    }
                }

                override fun onError(anError: ANError) {
                    // handle error
                    listener.onError(anError.errorDetail.toString())
                }
            })
    }

    interface EmployeesListener{
        fun onSuccess(list: MutableList<Employee>)
        fun onError(error: String)
    }
}