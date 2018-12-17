package com.stackflow.app.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PopularTag {

    @SerializedName("has_synonyms")
    @Expose
    var hasSynonyms: Boolean? = false
    @SerializedName("is_moderator_only")
    @Expose
    var isModeratorOnly: Boolean? = false
    @SerializedName("is_required")
    @Expose
    var isRequired: Boolean? = false
    @SerializedName("count")
    @Expose
    var count: Long? = 0
    @SerializedName("name")
    @Expose
    var name: String? = null

}