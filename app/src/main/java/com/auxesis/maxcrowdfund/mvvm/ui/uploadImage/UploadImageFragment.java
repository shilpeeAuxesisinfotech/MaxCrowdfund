package com.auxesis.maxcrowdfund.mvvm.ui.uploadImage;

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
import com.auxesis.maxcrowdfund.constant.ImageFilePath;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.myprofile.ChangeAvtarResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import static android.app.Activity.RESULT_OK;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;

public class UploadImageFragment extends Fragment {
    private static final String TAG = "UploadImageFragment";
    Button btnCancel, btnUpdated;
    CircleImageView ivUserProfile, cirIv_ChooseImg;
    BottomSheetDialog dialog_1;
    LinearLayout lLayGallery, lLayCamera, lLayRemove;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final int PERMISSION_REQUEST_CODE_1 = 100;
    public static final int PICK_IMAGE_GALLERY_1 = 1;
    String mConvertedImg1;
    String imgExtension;
    ProgressDialog pd;
    byte[] byteArray_photo_1;

    CardView cardView;
    TextView tvNoRecordFound;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upload_image, container, false);

        cardView = root.findViewById(R.id.cardView);
        tvNoRecordFound = root.findViewById(R.id.tvNoRecordFound);

        ivUserProfile = root.findViewById(R.id.ivUserProfile);
        cirIv_ChooseImg = root.findViewById(R.id.cirIv_ChooseImg);
        btnUpdated = root.findViewById(R.id.btnUpdated);

        cirIv_ChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected(getActivity())) {
                    getBottomSheet();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getActivity())) {
                    if (mConvertedImg1!=null) {
                        getUploadImage();
                    }else {
                        Toast.makeText(getActivity(), "Please select image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    private void getUploadImage() {
            try {
                pd = ProgressDialog.show(getActivity(), "Please Wait...");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("avatar",mConvertedImg1);
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                Call<ChangeAvtarResponse> call = git.getChangeAvtar("application/json", jsonObject);
                call.enqueue(new Callback<ChangeAvtarResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ChangeAvtarResponse> call, @NonNull retrofit2.Response<ChangeAvtarResponse> response) {
                        Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                        try {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            if (response != null && response.isSuccessful()) {
                                if (response.body().getResult().equals("success")){
                                    Toast.makeText(getActivity(), "Image upload successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ChangeAvtarResponse> call, @NonNull Throwable t) {
                        Log.e("response", "error " + t.getMessage());
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    //For Camera 1
    private void getBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.camera_bottom_sheet, null);
        dialog_1 = new BottomSheetDialog(getActivity());
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
                if (Utils.checkRequestPermiss(getContext(), getActivity())) {
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
                ivUserProfile.setImageDrawable(getResources().getDrawable(R.drawable.user));
                mConvertedImg1=null;
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
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    Utils.checkRequestPermiss(getContext(), getActivity());
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
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void explain(String msg) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
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
                        //finish();
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
                String path = ImageFilePath.getPath(getContext(), imageUri);
                Log.d(TAG, "onActivityResult: " + path);
                imgExtension = path.substring(path.lastIndexOf("."));
                if (imgExtension.equalsIgnoreCase(".jpg")) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                    mConvertedImg1 = convertToBase64(resizedBitmap);
                    ivUserProfile.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(getActivity(), "Please select jpg image only", Toast.LENGTH_SHORT).show();
                }
                dialog_1.dismiss();
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_1 && resultCode == RESULT_OK) {
            //for camera Image 1
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUriImg1 = getImageUri(getContext(), imageBitmap);
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
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
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


    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.change_avatar));
    }
}
