package com.neo.signLanguage.models

class Sign(letter: String, image: Int, type: String) {
    var letter = ""
    var image = 0
    var type = ""

    init {
        this.letter = letter
        this.image = image
        this.type = type
    }
}