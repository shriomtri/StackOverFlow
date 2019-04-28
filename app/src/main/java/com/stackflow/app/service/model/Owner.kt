package com.stackflow.app.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Owner {

    @SerializedName("reputation")
    @Expose
    var reputation: Int? = 0
    @SerializedName("user_id")
    @Expose
    var userId: Long? = 0
    @SerializedName("user_type")
    @Expose
    var userType: String? = null
    @SerializedName("profile_image")
    @Expose
    var profileImage: String? = null
    @SerializedName("display_name")
    @Expose
    var displayName: String? = null
    @SerializedName("link")
    @Expose
    var link: String? = null
    @SerializedName("accept_rate")
    @Expose
    var acceptRate: Int? = 0

}