package com.neo.signLanguage.data.models

data class GiphyResponse (
    val data: List<Datum>,
    val pagination: Pagination,
    val meta: Meta
)

data class Datum (
    val type: String,
    val id: String,
    val url: String,
    val slug: String,
    val bitlyGIFURL: String,
    val bitlyURL: String,
    val embedURL: String,
    val username: String,
    val source: String,
    val title: String,
    val rating: String,
    val contentURL: String,
    val sourceTLD: String,
    val sourcePostURL: String,
    val isSticker: Long,
    val importDatetime: String,
    val trendingDatetime: String,
    val images: Images,
    val user: User,
    val analyticsResponsePayload: String,
    val analytics: Analytics
)

data class Analytics (
    val onload: Onclick,
    val onclick: Onclick,
    val onsent: Onclick
)

data class Onclick (
    val url: String
)

data class Images (
    val original: FixedHeight,
    val downsized: The480_WStill,
    val downsizedLarge: The480_WStill,
    val downsizedMedium: The480_WStill,
    val downsizedSmall: DownsizedSmall,
    val downsizedStill: The480_WStill,
    val fixedHeight: FixedHeight,
    val fixedHeightDownsampled: FixedHeight,
    val fixedHeightSmall: FixedHeight,
    val fixedHeightSmallStill: The480_WStill,
    val fixedHeightStill: The480_WStill,
    val fixedWidth: FixedHeight,
    val fixedWidthDownsampled: FixedHeight,
    val fixedWidthSmall: FixedHeight,
    val fixedWidthSmallStill: The480_WStill,
    val fixedWidthStill: The480_WStill,
    val looping: Looping,
    val originalStill: The480_WStill,
    val originalMp4: DownsizedSmall,
    val preview: DownsizedSmall,
    val previewGIF: The480_WStill,
    val previewWebp: The480_WStill,
    val the480WStill: The480_WStill
)

data class The480_WStill (
    val height: String,
    val width: String,
    val size: String,
    val url: String
)

data class DownsizedSmall (
    val height: String,
    val width: String,
    val mp4Size: String,
    val mp4: String
)

data class FixedHeight (
    val height: String,
    val width: String,
    val size: String,
    val url: String,
    val mp4Size: String? = null,
    val mp4: String? = null,
    val webpSize: String,
    val webp: String,
    val frames: String? = null,
    val hash: String? = null
)

data class Looping (
    val mp4Size: String,
    val mp4: String
)

data class User (
    val avatarURL: String,
    val bannerImage: String,
    val bannerURL: String,
    val profileURL: String,
    val username: String,
    val displayName: String,
    val description: String,
    val instagramURL: String,
    val websiteURL: String,
    val isVerified: Boolean
)

data class Meta (
    val status: Long,
    val msg: String,
    val responseID: String
)

data class Pagination (
    val totalCount: Long,
    val count: Long,
    val offset: Long
)
