package com.sanags.android_image_cropper

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sanags.lib.android_image_cropper.CropImage
import com.sanags.lib.android_image_cropper.CropImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onSelectImageClick(view: View?) {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setActivityTitle("My Crop")
            .setCropShape(CropImageView.CropShape.OVAL)
            .setCropMenuCropButtonTitle("Done")
            .setRequestedSize(400, 400)
            .setCropMenuCropButtonIcon(R.drawable.ic_launcher_foreground)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result: CropImage.ActivityResult? = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                (findViewById<View>(R.id.quick_start_cropped_image) as ImageView).setImageURI(result?.uri)
                Toast.makeText(
                    this,
                    "Cropping successful, Sample: " + result?.sampleSize.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result?.error.toString(), Toast.LENGTH_LONG)
                    .show()
            }else{
                Toast.makeText(this, "Cropping failed else: ", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}