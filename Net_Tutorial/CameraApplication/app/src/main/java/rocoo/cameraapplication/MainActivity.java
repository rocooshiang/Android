package rocoo.cameraapplication;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // For permissions request
    private final int REQUEST_CAMERA_STORAGE = 1;

    private final String[] CAMERA_STORAGE_PERMISSION = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private ImageView fullImageView, thumbnailImageView;
    private Button takePictureButton;

    private final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullImageView = (ImageView)findViewById(R.id.imageView);
        thumbnailImageView = (ImageView)findViewById(R.id.imageView2);
        takePictureButton = (Button)findViewById(R.id.button);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  確認裝置是否有相機
                if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) == false){
                    Toast.makeText(MainActivity.this,"此裝置沒有相機",Toast.LENGTH_SHORT).show();
                    return;
                }

                checkPermission();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            addPhotoToGallery();

            // Show the full sized image.
            setFullImageFromFilePath(mCurrentPhotoPath, fullImageView);
            setFullImageFromFilePath(mCurrentPhotoPath, thumbnailImageView);
        } else {
            Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CAMERA_STORAGE:
                // 判斷所有權限是否都被使用者所允許
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(this, "沒有權限，無法使用相機", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    private void dispatchTakePictureIntent(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){

            // Create the File where the photo should go.
            // If you don't do this, you may get a crash in some devices.
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "在儲存照片時發生問題", Toast.LENGTH_SHORT).show();

            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoUri = Uri.fromFile(photoFile);
                // /storage/emulated/0/Pictures/JPEG_20160629_154946_1992137745.jpg
                mCurrentPhotoPath = photoUri.getPath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }

    private File createImageFile() throws IOException {


        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        // file:/storage/emulated/0/Pictures/JPEG_20160629_154946_1992137745.jpg
        mCurrentPhotoPath =  "file:" + image.getAbsolutePath();

        return image;
    }

    private void addPhotoToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setFullImageFromFilePath(String imagePath, ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private void checkPermission() {

        // 判斷所有權限是否都被允許
        if (ActivityCompat.checkSelfPermission(this, CAMERA_STORAGE_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, CAMERA_STORAGE_PERMISSION[1]) != PackageManager.PERMISSION_GRANTED ) {

            /** 沒有權限則顯示視窗告知使用者 **/

            // 自訂視窗來解釋為何要有此權限，對使用者有什麼好處
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    CAMERA_STORAGE_PERMISSION[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    CAMERA_STORAGE_PERMISSION[1])) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("權限通知")
                        .setMessage("1. 掃描QRCode及拍照，需要取得您相機的權限 \n2. 照片儲存至相簿，需要取得您儲存的權限")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermission(MainActivity.this, CAMERA_STORAGE_PERMISSION, REQUEST_CAMERA_STORAGE);
                            }
                        }).show();

            } else {

                // 顯示Android預設視窗 (安裝後第一次使用會跑這裡，使用者如果去關掉這個權限，那會跑上面)
                requestPermission(MainActivity.this, CAMERA_STORAGE_PERMISSION, REQUEST_CAMERA_STORAGE);
            }

        } else {

            /** The permissions are agree. **/

            dispatchTakePictureIntent();
        }
    }

    private void requestPermission(Activity activity, String[] permissions, int requestFrom) {

        ActivityCompat.requestPermissions(activity, permissions, requestFrom);
    }
}

