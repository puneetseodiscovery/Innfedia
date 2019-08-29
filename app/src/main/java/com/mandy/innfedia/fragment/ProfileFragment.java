package com.mandy.innfedia.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.Retrofit.ApiInterface;
import com.mandy.innfedia.ApiModel.ProfileApi;
import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Retrofit.ServiceGenerator;
import com.mandy.innfedia.Utils.SharedToken;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private final int RESULT_LOAD_IMAGE = 12;
    Button button, btnEdit;
    View view;
    Context context;
    EditText edtName, edtEmail, edtPhone;
    RoundedImageView roundedImageView;
    ArrayList<String> image_uris = new ArrayList<>();
    String token;
    MultipartBody.Part part;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        init();
        MainActivity.textView.setText("My Profile");
        Fresco.initialize(getActivity());

        if (CheckInternet.isInternetAvailable(context)) {
            getProfileDetails();
        } else {
            context.startActivity(new Intent(context, NoInternetActivity.class));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEmail.setFocusable(false);
                edtEmail.setClickable(false);
                edtEmail.setFocusableInTouchMode(false);

                edtName.setFocusable(false);
                edtName.setClickable(false);
                edtName.setFocusableInTouchMode(false);
                edtPhone.setFocusable(false);

                btnEdit.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                if (CheckInternet.isInternetAvailable(context)) {
                    getProfile();
                } else {
                    context.startActivity(new Intent(context, NoInternetActivity.class));
                }

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEmail.setFocusable(true);
                edtEmail.setClickable(true);
                edtEmail.setFocusableInTouchMode(true);
                edtName.setFocusable(true);
                edtName.setClickable(true);
                edtName.setFocusableInTouchMode(true);

                button.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);

                edtName.requestFocus();

            }
        });


        roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start multiple photos selector
                Intent intent = new Intent(getContext(), ImagesSelectorActivity.class);
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 1);
                //  intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);

                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, image_uris);
                // start the selector
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        return view;
    }

    private void init() {
        button = (Button) view.findViewById(R.id.btnProfile);
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPhone = (EditText) view.findViewById(R.id.edtPhone);
        btnEdit = (Button) view.findViewById(R.id.btnEdit);
        roundedImageView = (RoundedImageView) view.findViewById(R.id.imageView);

        SharedToken sharedToken = new SharedToken(context);
        token = "Bearer " + sharedToken.getShared();
    }

    private void getData(ProfileApi.Data data) {
        Glide.with(context).load(ServiceGenerator.BASE_API_PROFILE_IMAGE + data.getImage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(roundedImageView);
        edtPhone.setText(data.getPhone());
        edtEmail.setText(data.getEmail());
        edtName.setText(data.getName());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE) {
            image_uris.clear();
            if (resultCode == RESULT_OK) {
                image_uris = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert image_uris != null;
                for (int i = 0; i < image_uris.size(); i++) {
                    roundedImageView.setImageURI(Uri.parse(image_uris.get(i)));
                    File file = new File(compressImage(image_uris.get(i)));
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part filepart = MultipartBody.Part.createFormData("userimage", file.getName(), requestFile);
                    part = filepart;
                }
                setProfile();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }


    private void getProfile() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<ProfileApi> call = apiInterface.getProfileUpdate(token, edtEmail.getText().toString(), edtName.getText().toString());
        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();

        call.enqueue(new Callback<ProfileApi>() {
            @Override
            public void onResponse(Call<ProfileApi> call, Response<ProfileApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "" + response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                        getProfileDetails();
                    } else
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "" + response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }


    // method for uploading the new image
    private void setProfile() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<ProfileApi> call = apiInterface.getProfileImage(token, part);

        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();

        call.enqueue(new Callback<ProfileApi>() {
            @Override
            public void onResponse(Call<ProfileApi> call, Response<ProfileApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "" + response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                        getProfileDetails();
                    } else
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "" + response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "" + response.message(), Snackbar.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(getActivity().findViewById(android.R.id.content), "" + t.getMessage(), Snackbar.LENGTH_LONG).show();

            }
        });

    }


    //get profile details
    public void getProfileDetails() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<ProfileApi> call = apiInterface.getProfile(token);
        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();
        call.enqueue(new Callback<ProfileApi>() {
            @Override
            public void onResponse(Call<ProfileApi> call, Response<ProfileApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {

                        getData(response.body().getData());
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "" + response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileApi> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }


    // method for comprase image size
    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContext().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

}
