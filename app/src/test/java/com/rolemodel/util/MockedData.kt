package com.rolemodel.util

import com.rolemodel.data.model.Person

object MockedData {
    val sampleArtist = Person(
            18,
            "Van Beethoven",
            "http://someurl/images/artists.jpg",
            listOf("Composer")
    )
}