package com.stackflow.app.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class BadgeCounts {
    @SerializedName("bronze")
    @Expose
    var bronze: Int? = 0
    @SerializedName("silver")
    @Expose
    var silver: Int? = 0
    @SerializedName("gold")
    @Expose
    var gold: Int? = 0
}
