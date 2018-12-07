package com.stackflow.stackoverflow.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Owner {

    @SerializedName("reputation")
    @Expose
    private var reputation: Int? = 0
    @SerializedName("user_id")
    @Expose
    private var userId: Long? = 0
    @SerializedName("user_type")
    @Expose
    private var userType: String? = null
    @SerializedName("profile_image")
    @Expose
    private var profileImage: String? = null
    @SerializedName("display_name")
    @Expose
    private var displayName: String? = null
    @SerializedName("link")
    @Expose
    private var link: String? = null
    @SerializedName("accept_rate")
    @Expose
    private var acceptRate: Int? = 0

}