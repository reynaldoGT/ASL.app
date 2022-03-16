package com.neo.signLanguage.data.models

data class SingDbModel(var id: Int, var sing: String)

fun SingDbModel.toDomain() = SingDbModel(id, sing)