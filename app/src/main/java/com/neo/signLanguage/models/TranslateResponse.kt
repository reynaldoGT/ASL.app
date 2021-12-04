package com.neo.signLanguage.models

data class TranslateResponse (
    val responseData: ResponseData,
    val quotaFinished: Boolean,
    val mtLangSupported: Any? = null,
    val responseDetails: String,
    val responseStatus: Long,
    val responderID: String,
    val exceptionCode: Any? = null,
    val matches: List<Match>
)

data class Match (
    val id: String,
    val segment: String,
    val translation: String,
    val source: String,
    val target: String,
    val quality: String,
    val reference: Any? = null,
    val usageCount: Long,
    val subject: String,
    val createdBy: String,
    val lastUpdatedBy: String,
    val createDate: String,
    val lastUpdateDate: String,
    val match: Double
)

data class ResponseData (
    val translatedText: String,
    val match: Double
)
