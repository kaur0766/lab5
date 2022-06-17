package com.cst2335.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "PROFILE_ACTIVITY";

    private ActivityResultLauncher<Intent> myPictureTakerLauncher;
    private ImageButton imgView;
    private EditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "In function: onCreate()");
        setContentView(R.layout.activity_profile);

        edtEmail = findViewById(R.id.edtEmailAdress);
        Intent fromMain = getIntent();
        edtEmail.setText(fromMain.getStringExtra("EMAIL"));


        myPictureTakerLauncher =
                registerForActivityResult( new ActivityResultContracts.StartActivityForResult()
                        ,new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {

                                Log.i(TAG, "In function: onActivityResult()");

                                if (result.getResultCode() == Activity.RESULT_OK)
                                { Intent data = result.getData();
                                    Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                                    imgView.setImageBitmap(imgbitmap); // the imageButton
                                }
                                else if(result.getResultCode() == Activity.RESULT_CANCELED)
                                    Log.i(TAG, "User refused to capture a picture.");
                            }
                        } );

        imgView = findViewById(R.id.btnImage);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            myPictureTakerLauncher.launch(takePictureIntent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "In function: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "In function: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "In function: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "In function: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "In function: onDestroy()");
    }

}