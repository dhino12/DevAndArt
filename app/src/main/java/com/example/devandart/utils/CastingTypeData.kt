package com.example.devandart.utils

import java.net.URL

fun Boolean.toInt() = if (this) 1 else 0

fun String.toOriginalImg(): String =
    if (!this.contains("img")) ""
    else {
        val url = URL(this)
        val path = url.path
        val segments = path.split("/")
        val result = "/" + segments.takeLast(8).joinToString("/")
        "https://i.pximg.net/img-original${result}".replace("_square1200.jpg", ".png")
    }

fun String.toRegularImg(): String =
    if (!this.contains("img")) ""
    else {
        val url = URL(this)
        val path = url.path
        val segments = path.split("/")
        val result = "/" + segments.takeLast(8).joinToString("/")
        "https://i.pximg.net/img-master${result}".replace("_master1200.jpg", ".jpg")
    }

fun String.toThumbImg(): String =
    if (!this.contains("img")) ""
    else {
        val url = URL(this)
        val path = url.path
        val segments = path.split("/")
        val result = "/" + segments.takeLast(8).joinToString("/")
        "https://i.pximg.net/c/250x250_80_a2/img-master${result}"
    }