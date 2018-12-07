package com.stackflow.stackoverflow.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Question {

    @SerializedName("tags")
    @Expose
    var tags: List<String>? = null
    @SerializedName("owner")
    @Expose
    var owner: Owner? = null
    @SerializedName("is_answered")
    @Expose
    var isAnswered: Boolean? = false
    @SerializedName("view_count")
    @Expose
    var viewCount: Int? = null
    @SerializedName("answer_count")
    @Expose
    var answerCount: Int? = null
    @SerializedName("score")
    @Expose
    var score: Int? = null
    @SerializedName("last_activity_date")
    @Expose
    var lastActivityDate: Long? = 0
    @SerializedName("creation_date")
    @Expose
    var creationDate: Long? = 0
    @SerializedName("last_edit_date")
    @Expose
    var lastEditDate: Long? = 0
    @SerializedName("question_id")
    @Expose
    var questionId: Long? = 0
    @SerializedName("link")
    @Expose
    var link: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("bounty_amount")
    @Expose
    var bountyAmount: Int? = 0
    @SerializedName("bounty_closes_date")
    @Expose
    var bountyClosesDate: Long? = -1

}