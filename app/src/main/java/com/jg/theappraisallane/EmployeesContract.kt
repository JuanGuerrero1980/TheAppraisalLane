package com.jg.theappraisallane

import com.jg.theappraisallane.model.Employee

interface EmployeesContract {

    interface EmployeesView{
        fun showLoading()
        fun hideLoading()
        fun showEmployessList(list: MutableList<Employee>)
        fun showError(error: String)
        fun onClick(employee: Employee)
        fun navigateToDetail(employee: Employee)
    }

    interface EmployeesPresenter{
        fun getEmployees(page: Int = 1)
        fun onDestroy()
        fun employeeSelected(employee: Employee)
    }
}