package com.khoahoang183.data.base

object DateTimeCons {
    const val DATE_SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val HOUR_MINUTE_SECOND_FORMAT = "HH:mm:ss"
    const val HOUR_MINUTE_FORMAT = "HH:mm"
}

enum class EnumUserType(val value: String) {
    TRUCKER("Trucker"),
    USER("User")
}

enum class EnumUserStatus(val value: String) {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    UNVERIFIED("Unverified"),
    CREATED("Created")
}

enum class EnumAccountLevel(val value: String) {
    BASIC("BASIC"),
    STANDARD("STANDARD"),
    PREMIUM("PREMIUM"),
}

enum class EnumAnswerType(val id: Long, val answerName: String, val answerType: String) {
    TARGET_AUDIENCE(1, "Target Audience", "Influencer"),
    NUMBER_FOLLOWERS(2, "Number of followers", "Influencer"),
    COMPANY_CATEGORIES(3, "Company categories", "Business"),
}

enum class EnumHomeMainFilter(val id: Long, val value: String) {
    YESTERDAY(1, "Yesterday"),
    LAST_WEEK(2, "Last week"),
    LAST_MONTH(3, "Last month"),
}

enum class EnumHomeCampaignFilter(val id: Long, val value: String, val orderFieldName: String) {
    NAME(1, "Name (A-Z)", "name"),
    LAST_MODIFIED(2, "Last modified", "desc"),
    HIGHER_VIEWS(3, "Higher views", "views"),
    RATING(4, "Rating", "rate"),
}

enum class EnumDeliveryStatus(val id: Long, val value: String,val displayName:String) {
    NA(1, "NA","Waiting"),
    APPLIED(2, "Applied","Applied"),
    FAILED(5, "Failed","Failed"),
    DONE(6, "Done","Done"),
}

enum class EnumOrderDirection(val id: Long, val value: String) {
    ASC(1, "asc"),
    DESC(2, "desc")
}

enum class EnumSearchCase(val id: Long,val displayText:String,val displayTextShort:String) {
    LATEST_CREATED(1, "Latest creation date","Lastest"),
    OLDEST_CREATED(2, "Oldest creation date","Oldest"),
    BY_STATUS(3, "Order by status","Status"),
    ONLY_MINE(4, "Only my delivery","My"),
}

enum class ChooseLocationFragmentType(val value:String) {
    PICKUP_LOCATION("PICKUP_LOCATION"),
    DROPOFF_LOCATION("DROPOFF_LOCATION"),
}

enum class EnumPositionWhenBackToHome {
    TAB_1,
    TAB_2_FULL_TRUCK,
    TAB_2_PARTIAL_TRUCK,
    TAB_3,
    TAB_4,
    TAB_5,
}