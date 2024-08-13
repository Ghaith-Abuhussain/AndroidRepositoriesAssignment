package com.example.androidrepositoriesapplication.common

// Enum for loading types
// Append Loading: When we call the api and load the next page when the end of the List is reached
// Refresh: to call the api and load page 0 from the remote server
enum class LoadingType {
    APPEND,
    REFRESH
}