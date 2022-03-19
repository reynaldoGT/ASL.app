package com.neo.signLanguage.data.models

import com.neo.signLanguage.data.database.entities.SignEntity

data class SingDbModel(var id: Int, var sing: String)

fun SingDbModel.toDomain() = SingDbModel(id, sing)
fun SignEntity.toDomain() = SingDbModel(id, sing)