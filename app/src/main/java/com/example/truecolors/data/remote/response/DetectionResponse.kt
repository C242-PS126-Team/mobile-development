package com.example.truecolors.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetectionResponse(

	@field:SerializedName("status_code")
	val statusCode: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("predictions")
	val predictions: Predictions,

	@field:SerializedName("status")
	val status: String
)

data class Predictions(

	@field:SerializedName("saturation")
	val saturation: String? = null,

	@field:SerializedName("predicted_class")
	val predictedClass: String? = null,

	@field:SerializedName("lightness")
	val lightness: Any? = null,

	@field:SerializedName("saturation_hsv")
	val saturationHsv: String? = null,

	@field:SerializedName("confidence")
	val confidence: Double? = null,

	@field:SerializedName("hue")
	val hue: String? = null,

	@field:SerializedName("rgb")
	val rgb: String? = null,

	@field:SerializedName("value_hsv")
	val valueHsv: String? = null
)
