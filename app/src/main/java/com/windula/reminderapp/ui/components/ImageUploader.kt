package com.windula.reminderapp.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.windula.reminderapp.ui.reminder.ReminderViewModel
import com.windula.reminderapp.util.APP_TAG
import com.windula.reminderapp.util.getTimestamp
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

@Composable
fun PickImageFromGallery(    viewModel: ReminderViewModel = hiltViewModel(),imgUri:String?=null) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val photoFileName = imgUri ?: (getTimestamp() + "_img.jpg")
    Log.i(APP_TAG,photoFileName)


    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        imageUri?.let {

            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->

                val photoFile = getPhotoFileUri(photoFileName,context)
                println(photoFile)
                viewModel.imageUri = photoFileName
                convertBitmapToFile(photoFile,btm)
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { launcher.launch("image/*")
        println(imageUri)
        }) {
            Text(text = "Upload Image")
        }
    }
}

fun getPhotoFileUri(fileName: String,context: Context): File {

    // Get safe storage directory for photos
    // Use `getExternalFilesDir` on Context to access package-specific directories.
    // This way, we don't need to request external read/write runtime permissions.
    val mediaStorageDir =
        File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG)

    // Create the storage directory if it does not exist
    if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
        Log.e( APP_TAG,"failed to create directory")
    }

    // Return the file target for the photo based on filename
    return File(mediaStorageDir.path + File.separator + fileName)
}

fun convertBitmapToFile(destinationFile: File, bitmap: Bitmap) {
    //create a file to write bitmap data
    destinationFile.createNewFile()
    //Convert bitmap to byte array
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
    val bitmapData = bos.toByteArray()
    //write the bytes in file
    val fos = FileOutputStream(destinationFile)
    fos.write(bitmapData)
    fos.flush()
    fos.close()
}