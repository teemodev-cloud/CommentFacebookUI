import com.google.gson.annotations.SerializedName

data class CommentResponse (

	@SerializedName("success") val success : Boolean,
	@SerializedName("message") val message : String,
	@SerializedName("data") val data : Data
)