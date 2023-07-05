package com.didaagency.adopteunelivraison.data.network.response

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("header_image")
    val headerImage: String? = null,

    @field:SerializedName("short_description")
    val shortDescription: String? = null,

    @field:SerializedName("contact_phone")
    val contactPhone: String? = null,

    @field:SerializedName("restaurant_slug")
    val restaurantSlug: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("invoice_terms")
    val invoiceTerms: Int? = null,

    @field:SerializedName("merchant_id")
    val merchantId: Int? = null,

    @field:SerializedName("commision_based")
    val commisionBased: String? = null,

    @field:SerializedName("package_id")
    val packageId: Int? = null,

    @field:SerializedName("commision_type")
    val commisionType: String? = null,

    @field:SerializedName("contact_email")
    val contactEmail: String? = null,

    @field:SerializedName("path")
    val path: String? = null,

    @field:SerializedName("is_ready")
    val isReady: Int? = null,

    @field:SerializedName("reviews")
    val reviews: List<String?>? = null,

    @field:SerializedName("free_delivery")
    val freeDelivery: Int? = null,

    @field:SerializedName("logo")
    val logo: String? = null,

    @field:SerializedName("total_rating")
    val totalRating: Int? = null,

    @field:SerializedName("merchant_uuid")
    val merchantUuid: String? = null,

    @field:SerializedName("distance_unit")
    val distanceUnit: String? = null,

    @field:SerializedName("membership_expired")
    val membershipExpired: String? = null,

    @field:SerializedName("merchant_type")
    val merchantType: Int? = null,

    @field:SerializedName("contact_name")
    val contactName: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("restaurant_phone")
    val restaurantPhone: String? = null,

    @field:SerializedName("delivery_distance_covered")
    val deliveryDistanceCovered: Int? = null,

    @field:SerializedName("total_orders")
    val totalOrders: Int? = null,

    @field:SerializedName("restaurant_name")
    val restaurantName: String? = null,

    @field:SerializedName("path2")
    val path2: String? = null,

    @field:SerializedName("pause_ordering")
    val pauseOrdering: Int? = null,

    @field:SerializedName("orders")
    val orders: List<String?>? = null,

    @field:SerializedName("is_featured")
    val isFeatured: Int? = null
)