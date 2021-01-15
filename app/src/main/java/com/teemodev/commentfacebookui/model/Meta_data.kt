import com.google.gson.annotations.SerializedName

data class Meta_data (

	@SerializedName("current_page") val current_page : Int,
	@SerializedName("from") val from : Int,
	@SerializedName("last_page") val last_page : Int,
	@SerializedName("next_page_url") val next_page_url : String,
	@SerializedName("per_page") val per_page : Int,
	@SerializedName("prev_page_url") val prev_page_url : String,
	@SerializedName("to") val to : Int,
	@SerializedName("total") val total : Int
)