package ir.sanags.android

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import ir.sanags.android.image_cropper.CropImage
import ir.sanags.android.image_cropper.CropImageView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


const val CAMERA_REQUEST = 121
class MainActivity : AppCompatActivity() {

    private var picturePath: String? = null
    private val REQUEST_IMAGE_CAPTURE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            picturePath = savedInstanceState.getString("safar")
        }
    }

    fun onSelectImageClick(view: View?) {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setActivityTitle("My Crop")
            .setCropShape(CropImageView.CropShape.OVAL)
            .setCropMenuCropButtonTitle("Done")
            .setCropMenuCropButtonIcon(R.drawable.ic_launcher_foreground)
            .setCropBigButtonVisibility(true)
            //.setAspectRatio(1,2)
            .setAspectRatios("1:2, 1:1")
            .start(this)
    }

    fun selectDirectly(view : View?){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    fun onClickCaptureButton(view: View) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
            }
            if (photoFile != null) {
                picturePath = photoFile.getAbsolutePath()
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI_: Uri = FileProvider.getUriForFile(
                    this,
                    "${packageName}.fileprovider", photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        picturePath = image.absolutePath
        return image
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString("safar", picturePath)
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result: CropImage.ActivityResult? = CropImage.getActivityResult(data)
            when (resultCode) {
                RESULT_OK -> {
                    (findViewById<View>(R.id.quick_start_cropped_image) as ImageView).setImageURI(result?.uri)
                    Toast.makeText(
                        this,
                        "Cropping successful, Sample: " + result?.sampleSize.toString(),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> {
                    Toast.makeText(this, "Cropping failed: " + result?.error.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                else -> {
                    Toast.makeText(this, "Cropping failed else: ", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, "get from camera ", Toast.LENGTH_LONG).show()

            val imageBitmap = data?.extras?.get("data") as Bitmap
            (findViewById<View>(R.id.quick_start_cropped_image) as ImageView).setImageBitmap(imageBitmap)

            /*val imageUri: Uri? = data?.data

            if (imageUri != null) {
                (findViewById<View>(R.id.quick_start_cropped_image) as ImageView).setImageURI(imageUri)
            }*/
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                val file = File(picturePath)
                val uri = FileProvider.getUriForFile(
                    this,
                    "${packageName}.fileprovider", file
                )
                (findViewById<View>(R.id.quick_start_cropped_image) as ImageView).setImageURI(uri)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}