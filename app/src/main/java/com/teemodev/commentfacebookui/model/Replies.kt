import com.google.gson.annotations.SerializedName

data class Replies(

    @SerializedName("id") val id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("content_id") val content_id: Int,
    @SerializedName("content_type") val content_type: String,
    @SerializedName("user") val user: User,
    @SerializedName("likes_count") val likes_count: Int,
    @SerializedName("is_liked") val is_liked: Boolean,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)