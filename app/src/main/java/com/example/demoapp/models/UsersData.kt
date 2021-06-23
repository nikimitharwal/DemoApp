package com.example.demoapp.models

import com.google.gson.annotations.SerializedName

class UsersData {
    @JvmField
    @SerializedName("id")
    var id: String? = null

    @JvmField
    @SerializedName("title")
    var title: String? = null

    @JvmField
    @SerializedName("firstName")
    var firstName: String? = null

    @JvmField
    @SerializedName("lastName")
    var lastName: String? = null

    @JvmField
    @SerializedName("email")
    var email: String? = null

    @JvmField
    @SerializedName("picture")
    var picture: String? = null
}