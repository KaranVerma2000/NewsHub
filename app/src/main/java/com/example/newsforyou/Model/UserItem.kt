package com.example.newsforyou.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserItem : Serializable {

    @SerializedName("image")
    var image: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("number")
    var number: String = ""
}