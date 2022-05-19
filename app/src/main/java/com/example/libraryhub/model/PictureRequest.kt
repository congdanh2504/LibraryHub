package com.example.libraryhub.model

import okhttp3.MultipartBody

data class PictureRequest(val file: MultipartBody.Part)