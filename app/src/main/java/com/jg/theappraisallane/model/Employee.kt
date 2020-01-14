package com.jg.theappraisallane.model

import com.google.gson.annotations.SerializedName

data class Employee (@SerializedName("name") val name: Name,
                     @SerializedName("dob") val dob: Dob,
                     @SerializedName("picture") val picture: Picture,
                     @SerializedName("email") val email: String,
                     @SerializedName("login") val login: Login)




data class Name(@SerializedName("title") val title: String,
                @SerializedName("first") val first: String,
                @SerializedName("last") val last: String)


data class Picture(@SerializedName("large") val large: String,
                @SerializedName("medium") val medium: String,
                @SerializedName("thumbnail") val thumbnail: String)


data class Login(@SerializedName("username") val username: String,
                   @SerializedName("password") val password: String,
                   @SerializedName("thumbnail") val thumbnail: String)

data class Dob(@SerializedName("date") val date: String,
                 @SerializedName("age") val age: Int)
