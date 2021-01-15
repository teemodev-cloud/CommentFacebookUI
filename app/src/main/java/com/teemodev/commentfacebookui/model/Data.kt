import com.google.gson.annotations.SerializedName

data class Data (

	@SerializedName("comments") val comments : List<Comments>,
	@SerializedName("meta_data") val meta_data : Meta_data
)