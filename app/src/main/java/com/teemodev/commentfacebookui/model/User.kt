import com.google.gson.annotations.SerializedName

data class User (

	@SerializedName("id") val id : Int,
	@SerializedName("username") val username : String,
	@SerializedName("fullname") val fullname : String,
	@SerializedName("avatar_url") val avatar_url : String
)