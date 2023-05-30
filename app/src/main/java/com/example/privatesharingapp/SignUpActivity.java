package com.example.privatesharingapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.privatesharingapp.sqlitehelper.DatabaseHelper;
import com.example.privatesharingapp.sqlitehelper.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    ImageView profilePict;
    EditText name, username, phone, password, confirmPassword;
    Button signUp;
    DatabaseHelper databaseHelper;

    // Permissions
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    private String[] cameraPermissions; // Camera and Storage
    private String[] storagePermissions; // Only Storage
    private Uri imageUrl;
    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // findViewById
        profilePict = findViewById(R.id.imageView);
        name = findViewById(R.id.editTextText2);
        username = findViewById(R.id.editTextText3);
        phone = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextTextPassword2);
        confirmPassword = findViewById(R.id.editTextTextPassword3);
        signUp = findViewById(R.id.button3);

        // Permissions
        cameraPermissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        // DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Add image
        profilePict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Shows image pick dialog
                imagePickDialog();
            }
        });

        // Submit to database
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Makes sure EditText is not empty
                if (name.getText().toString().trim().length() != 0 ||
                    username.getText().toString().trim().length() != 0 ||
                    password.getText().toString().trim().length() != 0 ||
                    confirmPassword.getText().toString().trim().length() != 0 ||
                    phone.getText().toString().trim().length() != 0)
                {
                    // Password has to be equal to ConfirmPassword
                    if (password.getText().toString().equals(confirmPassword.getText().toString()))
                    {
                        long result = databaseHelper.insertUser(new User(
                                name.getText().toString(),
                                username.getText().toString(),
                                password.getText().toString(),
                                phone.getText().toString(),
                                byteArray
                                ));
                        if(result > 0)
                        {
                            SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                            SharedPreferences.Editor myEditor = sharedPreferences.edit();
                            myEditor.putString("username", username.getText().toString());
                            myEditor.apply();
                            Toast.makeText(SignUpActivity.this, "User registered successfully!", Toast.LENGTH_LONG).show();
                            Intent intent3 = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent3);
                        }
                        else Toast.makeText(SignUpActivity.this, "Unsuccessful!", Toast.LENGTH_LONG).show();
                    }
                    else Toast.makeText(SignUpActivity.this, "Password and Confirm Password is different!", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(SignUpActivity.this, "Please enter all fields!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void imagePickDialog() {
        // Option to display dialog
        String[] options = { "Camera", "Gallery" };
        // Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Title
        builder.setTitle("Pick an image from");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) // Handle clicks
                {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    }
                    else {
                        // Permission granted already
                        pickFromCamera();
                    }
                }
                else if (i == 1)
                {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    }
                    else {
                        // Permission granted already
                        pickFromGallery();
                    }
                }
            }
        });

        // Create / show dialog
        builder.create().show();
    }
    private void pickFromGallery() {
        // Intent to pick image from camera
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }
    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image description");
        imageUrl = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // Intent to open camera for image
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }
    private boolean checkStoragePermission() {
        // Checks if storage permission is enabled or not
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermission() {
        // Checks if camera permission is enabled or not
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result1 && result2;
    }
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Result of permission allowed / denied
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0)
                {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        // Both permission allowed
                        pickFromCamera();
                    }
                    else Toast.makeText(this, "Camera and Storage permissions are required", Toast.LENGTH_LONG).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0)
                {
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickFromGallery();
                    }
                    else Toast.makeText(this, "Storage permission is required", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == IMAGE_PICK_GALLERY_CODE)
            {
                imageUrl = data.getData();
                Bitmap theImage = null;
                try {
                    theImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUrl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                byteArray = getBytes(theImage);
                profilePict.setImageBitmap(theImage);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE)
            {
                Bitmap theImage = null;
                try {
                    theImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUrl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                byteArray = getBytes(theImage);
                profilePict.setImageBitmap(theImage);
            }
        }
    }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}