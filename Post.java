package com.example.tiptome.ui.theme;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
data class Post(
        var uid: String? = "",
        var author: String? = "",
        var title: String? = "",
        var body: String? = "",
        var starCount: Int = 0,
        var stars: MutableMap<String, Boolean> =HashMap()
)
