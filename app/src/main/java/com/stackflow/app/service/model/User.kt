package com.stackflow.app.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class User : Serializable {

    @SerializedName("badge_counts")
    @Expose
    var badgeCounts: BadgeCounts? = null
    @SerializedName("account_id")
    @Expose
    var accountId: Int? = null
    @SerializedName("is_employee")
    @Expose
    var isEmployee: Boolean? = false
    @SerializedName("last_modified_date")
    @Expose
    var lastModifiedDate: Long? = 0
    @SerializedName("last_access_date")
    @Expose
    var lastAccessDate: Long? = 0
    @SerializedName("reputation_change_year")
    @Expose
    var reputationChangeYear: Int? = 0
    @SerializedName("reputation_change_quarter")
    @Expose
    var reputationChangeQuarter: Int? = 0
    @SerializedName("reputation_change_month")
    @Expose
    var reputationChangeMonth: Int? = 0
    @SerializedName("reputation_change_week")
    @Expose
    var reputationChangeWeek: Int? = 0
    @SerializedName("reputation_change_day")
    @Expose
    var reputationChangeDay: Int? = 0
    @SerializedName("reputation")
    @Expose
    var reputation: Int? = 0
    @SerializedName("creation_date")
    @Expose
    var creationDate: Long? = 0
    @SerializedName("user_type")
    @Expose
    var userType: String? = null
    @SerializedName("user_id")
    @Expose
    var userId: Int? = 0
    @SerializedName("location")
    @Expose
    var location: String? = null
    @SerializedName("website_url")
    @Expose
    var websiteUrl: String? = null
    @SerializedName("link")
    @Expose
    var link: String? = null
    @SerializedName("profile_image")
    @Expose
    var profileImage: String? = null
    @SerializedName("display_name")
    @Expose
    var displayName: String? = "no_name"
}