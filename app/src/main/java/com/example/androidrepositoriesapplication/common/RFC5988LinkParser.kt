package com.example.androidrepositoriesapplication.common

import com.example.androidrepositoriesapplication.ui.theme.API_URL

data class Link(
    val pageNumber: Int,
    val rel: String? = null,
)

fun parseRFC5988LinkHeader(linkHeader: String): List<Link> {
    return linkHeader.split(",").map { link ->
        val trimmedLink = link.trim()
        val url = trimmedLink.substringBefore(";").trim().removeSurrounding("<", ">")
        val attributes = trimmedLink.substringAfter(";", "").trim().split(";").map { it.trim() }

        val rel = attributes.firstOrNull { it.startsWith("rel=") }?.substringAfter("rel=\"")?.substringBefore("\"")
        val page = url.substringAfter("?").split("&").firstOrNull {
            it.startsWith("page=")
        }?.substringAfter("page=")!!.toInt()
        Link(page, rel)
    }
}
