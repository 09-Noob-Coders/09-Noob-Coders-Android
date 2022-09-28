package com.example.a09_noob_coders_android.presentation.resource_module

import androidx.compose.runtime.mutableStateOf

class ResourceItemClass{
    companion object{

        val upvoted =
            mutableStateOf(false)


        val downvoted =
            mutableStateOf(false)

    }
}