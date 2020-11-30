package com.sabihamumcu.kelimekutusu.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
        var word: String? = null,
        var description: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}