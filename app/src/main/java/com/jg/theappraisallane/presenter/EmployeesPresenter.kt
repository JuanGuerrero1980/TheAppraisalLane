package com.jg.theappraisallane.presenter

import com.jg.theappraisallane.EmployeesContract
import com.jg.theappraisallane.model.Employee
import com.jg.theappraisallane.model.EmployeesRepository.Repository

class EmployeesPresenter(private val view: EmployeesContract.EmployeesView): EmployeesContract.EmployeesPresenter, Repository.EmployeesListener {

    private val repository = Repository()

    override fun getEmployees() {
        view.showLoading()
        repository.getEmployees(this)
    }

    override fun onDestroy() {

    }

    override fun onSuccess(list: MutableList<Employee>) {
        view.hideLoading()
        view.showEmployessList(list)
    }

    override fun onError(error: String) {
        view.hideLoading()
        view.showError(error)
    }
}