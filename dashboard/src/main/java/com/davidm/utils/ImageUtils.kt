package com.davidm.utils

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class ImageUtils {
    companion object {
        private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(angle)

            return Bitmap.createBitmap(
                source, 0, 0, source.width, source.height,
                matrix, true
            )
        }


        fun checkAndFixPhotoOrientation(bitmap: Bitmap, file: File): Bitmap {
            val ei = ExifInterface(file)
            val orientation: Int = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            val bitmap = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
                ExifInterface.ORIENTATION_NORMAL -> bitmap
                else -> rotateImage(bitmap, 270f)
            }

            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()

            return bitmap
        }

        private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
            val bytes = ByteArrayOutputStream()
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val outImage = Bitmap.createScaledBitmap(inImage, 1000, 1000, true)
            val path =
                MediaStore.Images.Media.insertImage(
                    inContext.contentResolver,
                    outImage,
                    "Title",
                    null
                )
            return Uri.parse(path)
        }

        fun getRealPathFromURI(inContext: Context, inImage: Bitmap): String {
            var path = ""
            val uri = getImageUri(inContext, inImage)

            if (inContext.contentResolver != null) {
                val cursor: Cursor? =
                    inContext.contentResolver.query(uri, null, null, null, null)
                if (cursor != null) {
                    cursor.moveToFirst()
                    val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    path = cursor.getString(idx)
                    cursor.close()
                }
            }
            return path
        }
    }
}