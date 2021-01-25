package com.davidm.utils

import android.Manifest.permission.*

sealed class Permission(vararg val permissions: String) {
    // Individual permissions
    object Camera : Permission(CAMERA)
    object Storage : Permission(WRITE_EXTERNAL_STORAGE)


    companion object {
        fun from(permission: String) = when (permission) {
            CAMERA -> Camera
            WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE -> Storage
            else -> throw IllegalArgumentException("Unknown permission: $permission")
        }
    }
}