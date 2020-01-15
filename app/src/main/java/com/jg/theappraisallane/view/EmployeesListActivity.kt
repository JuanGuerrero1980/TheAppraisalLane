package com.jg.theappraisallane.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.size
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jg.theappraisallane.EmployeesContract
import com.jg.theappraisallane.R
import com.jg.theappraisallane.model.Employee
import com.jg.theappraisallane.presenter.EmployeesPresenter

class EmployeesListActivity : AppCompatActivity(), EmployeesContract.EmployeesView {

    private lateinit var progress: ProgressBar
    private lateinit var list: RecyclerView
    private lateinit var presenter: EmployeesPresenter
    private val TAG = EmployeesListActivity::class.java.simpleName
    private lateinit var adapter : EmployeesAdapter
    val EXTRA_EMPLOYEE = "EXTRA_EMPLOYEE"
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = findViewById(R.id.employeesList)
        progress = findViewById(R.id.progressBar)
        presenter = EmployeesPresenter(this)
        presenter.getEmployees()
        adapter = EmployeesAdapter(mutableListOf(), this)
        list.layoutManager = LinearLayoutManager(this)
        list.itemAnimator = DefaultItemAnimator()
        list.setHasFixedSize(true)
        list.adapter = adapter

        list.addOnScrollListener(object : PaginationScrollListener(list.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                presenter.getEmployees(page++)
            }
        })
    }

    override fun showLoading() {
        if(!isFinishing)
            progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        if(!isFinishing)
            progress.visibility = View.GONE
    }

    override fun showEmployessList(list: MutableList<Employee>) {
        isLoading = false
        adapter.populateList(list)
    }

    override fun showError(error: String) {
        Log.d(TAG, "Error: $error")
    }

    override fun onClick(employee: Employee) {
        presenter.employeeSelected(employee)
    }

    override fun navigateToDetail(employee: Employee) {
        val intent = Intent(this, EmployeeDetailActivity::class.java)
        intent.putExtra(EXTRA_EMPLOYEE, employee)
        startActivity(intent)
    }
}
