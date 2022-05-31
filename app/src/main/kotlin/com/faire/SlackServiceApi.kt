package com.faire

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface SlackServiceApi {
    /**
     * Legacy signature for the OAuth token used by "Notification Bot".
     * This signature is incompatible with the newer apps, which use OAuth2.
     */
    @FormUrlEncoded
    @POST("chat.postMessage")
    fun postMessageFormUrlEncoded(
        @Field("token") token: String,
        @Field("channel") channel: String,
        @Field("text") text: String,
        @Field("as_user") asUser: Boolean,
    ): Call<PostMessageResponse>

    /**
     * OAuth2 signature.
     */
    @POST("chat.postMessage")
    @Headers("content-type: application/json")
    fun postMessageJSON(
        @Header("Authorization") authorization: String,
        @Body request: PostMessageRequest,
    ): Call<PostMessageResponse>

    @FormUrlEncoded
    @POST("users.lookupByEmail")
    fun getUserByEmail(
        @Field("token") token: String,
        @Field("email") email: String,
    ): Call<GetUserByEmailResponse>

    @FormUrlEncoded
    @POST("users.profile.get")
    fun getUserProfileByEmail(
        @Field("token") token: String,
        @Field("include_labels") includeLabels: Boolean,
        @Field("user") userId: String,
    ): Call<GetUserProfileByEmailResponse>
}

data class PostMessageRequestAttachment(
    val text: String,
    val color: String?,
)

data class PostMessageRequest(
    val channel: String,
    val text: String,
    val attachments: List<PostMessageRequestAttachment>? = null,
)

data class PostMessageResponse(val ok: Boolean, val error: String)

data class GetUserByEmailResponse(val ok: Boolean, val user: SlackUser, val error: String)

data class GetUserProfileByEmailResponse(val ok: Boolean, val profile: SlackUserProfile, val error: String)

data class SlackUser(val id: String, val profile: SlackUserProfile)

data class SlackUserProfile(
    val statusEmoji: String,
    val statusText: String,
    val displayName: String,
    val imageOriginal: String,
)

