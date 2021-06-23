package com.example.demoapp.models

import com.google.gson.annotations.SerializedName

class UserDetailData {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("firstName")
    var firstName: String? = null

    @SerializedName("lastName")
    var lastName: String? = null

    @SerializedName("gender")
    var gender: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("dateOfBirth")
    var dateOfBirth: String? = null

    @SerializedName("phone")
    var phone: String? = null

    @SerializedName("registerDate")
    var registerDate: String? = null

    @SerializedName("picture")
    var picture: String? = null
}