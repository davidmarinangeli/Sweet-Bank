package com.davidm.utils

import android.Manifest.permission.*

sealed class Permission(vararg val permissions: String) {
    // Individual permissions
    object Camera : Permission(CAMERA)


    companion object {
        fun from(permission: String) = when (permission) {
            CAMERA -> Camera
            else -> throw IllegalArgumentException("Unknown permission: $permission")
        }
    }
}