package com.jg.theappraisallane.model

import com.google.gson.annotations.SerializedName

data class ResponseEmployees (@SerializedName("results") val results: List<Employee>)