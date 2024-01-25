    package com.example.core_utils

    import android.Manifest
    object Constants {

        const val TAG = "cameraX"
        const val FILE_NAME_FROMAT = "yy-MM-dd-HH-mm-ss-SSS"
        const val REQUEST_CODE_PERMISSIONS = 123
        const val REQUEST_CODE_PERMISSIONS_GALLERY = 124
        const val REQUEST_CODE_SETTINGS = 125
        const val SHOWED_KEY = "seen.key"
        const val TOKEN_KEY = "token.key"
        const val REFRESH_TOKEN_KEY = "refresh.key"


        val CAMERA_REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val GALLERY_REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }


