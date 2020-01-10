package com.auxesis.maxcrowdfund.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.ImageFilePath;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;


public class UploadImageActivity extends AppCompatActivity {
    private static final String TAG = "UploadImageActivity";
    TextView tv_back_arrow, tvHeaderTitle;
    Button btnCancel;
    CircleImageView ivUserProfile, cirIv_ChooseImg;
    BottomSheetDialog dialog_1;
    LinearLayout lLayGallery, lLayCamera, lLayRemove;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final int PERMISSION_REQUEST_CODE_1 = 100;
    public static final int PICK_IMAGE_GALLERY_1 = 1;

    String mConvertedImg1;
    String imgExtension;

    byte[] byteArray_photo_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.change_avatar);
        tv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivUserProfile = findViewById(R.id.ivUserProfile);
        cirIv_ChooseImg = findViewById(R.id.cirIv_ChooseImg);

        cirIv_ChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected(getApplicationContext())) {
                    getBottomSheet();
                } else {
                    showToast(UploadImageActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                }
            }
        });
    }

    //For Camera 1
    private void getBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.camera_bottom_sheet, null);
        dialog_1 = new BottomSheetDialog(this);
        dialog_1.setContentView(view);
        lLayGallery = dialog_1.findViewById(R.id.lLayGallery);
        lLayCamera = dialog_1.findViewById(R.id.lLayCamera);
        lLayRemove = dialog_1.findViewById(R.id.lLayRemove);
        btnCancel = dialog_1.findViewById(R.id.btnCancel);

        lLayGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoFromGallary();
            }
        });


        //click for camera Image 1
        lLayCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkRequestPermiss(getApplicationContext(), UploadImageActivity.this)) {
                    Log.d(TAG, "onClick: " + "permission already granted");
                    //showToast(UploadImageActivity.this, "Comming soon");
                     doPermissionGranted();
                }
            }
        });

        lLayRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_1.dismiss();
               /* if (byteArray_photo_1 == null) {
                    showToast(SavePhotoesActivity.this, "Please choose photo 1");
                } else {
                    ivPhoto_1.setImageDrawable(getResources().getDrawable(R.drawable.file));
                    byteArray_photo_1 = null;
                    showToast(SavePhotoesActivity.this, "Successfully Removed photo 1");
                    dialog_1.dismiss();
                }*/
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_1.dismiss();
            }
        });

        dialog_1.show();
    }

    private void choosePhotoFromGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY_1);
    }

    private void doPermissionGranted() {
        Log.d(TAG, "doPermissionGranted: " + "1 clicked");
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent, PERMISSION_REQUEST_CODE_1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "CAMERA  permission granted");
                        doPermissionGranted();
                    } else {
                        Log.d(TAG, "Camera Permission are not granted ask again ");
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    Utils.checkRequestPermiss(getApplicationContext(), UploadImageActivity.this);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            explain("Go to settings and enable permissions");
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void explain(String msg) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("com.auxesis.maxcrowdfund")));
                        // startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //for gallery Image
        if (requestCode == PICK_IMAGE_GALLERY_1 && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                final Uri imageUri = data.getData();
                String path = ImageFilePath.getPath(UploadImageActivity.this, imageUri);
                Log.d(TAG, "onActivityResult: " + path);
                imgExtension = path.substring(path.lastIndexOf("."));
                if (imgExtension.equalsIgnoreCase(".jpg")) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                    mConvertedImg1 = convertToBase64(resizedBitmap);
                    ivUserProfile.setImageBitmap(bitmap);
                } else {
                    showToast(UploadImageActivity.this, "Please select jpg image only");
                }
                dialog_1.dismiss();
            } else {
                showToast(UploadImageActivity.this, "You haven't picked Image");
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_1 && resultCode == RESULT_OK) {
            //for camera Image 1
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUriImg1 = getImageUri(getApplicationContext(), imageBitmap);
                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFileImg = new File(getRealPathFromURI(tempUriImg1));
                String fileEx = String.valueOf(finalFileImg);
                imgExtension = fileEx.substring(fileEx.lastIndexOf("."));
                Bitmap bitmap = BitmapFactory.decodeFile(fileEx);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                mConvertedImg1 = convertToBase64(resizedBitmap);

                Log.d(TAG, "onActivityResult:" + finalFileImg);
                Log.d(TAG, "onActivityResult:" + imgExtension);
                Log.d(TAG, "onActivityResult:" + tempUriImg1);
                Log.d(TAG, "onActivityResult:" + mConvertedImg1);

                ivUserProfile.setImageBitmap(imageBitmap);
                dialog_1.dismiss();
            }
        }


       /* //for gallery Image 1
        if (requestCode == PERMISSION_REQUEST_CODE_1 && resultCode == RESULT_OK) {
            //for camera Image 1
            if (data != null && data.getExtras() != null) {
                Bitmap photo_Bitmap_1 = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo_Bitmap_1.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray_photo_1 = stream.toByteArray();
                Log.d(TAG, "onActivityResult: "+byteArray_photo_1);
                ivUserProfile.setImageBitmap(photo_Bitmap_1);
                dialog_1.dismiss();

            }
        } */
    }


    //////////
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // For Image path
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    //For Converting Image into Base64
    private String convertToBase64(Bitmap bitmap) {
        String encodedStr = "";
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        String encodedImage = Base64.encodeToString(ba, Base64.NO_WRAP);

        byte[] utf8Bytes = new byte[1024];
        try {
            utf8Bytes = encodedImage.getBytes("UTF-8");
            encodedStr = new String(utf8Bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedStr;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
