package com.example.a09_noob_coders_android.presentation.resource_module.modals

data class ResourceModal(
    val title: String,
    val type: String,
    val tags: String,
    val desc: String,
    val url: String,
    val creator: String,
    val upvotes: Long,
    val downvotes: Long
)