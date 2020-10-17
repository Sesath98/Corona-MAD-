package com.example.corona_mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddProductActivity extends AppCompatActivity {

    EditText prodName, unitName;
    Spinner status;
    Button btnAdd, chooseImageBtn;

    //Firebase Connection
    FirebaseDatabase database;
    DatabaseReference myRef;

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog ;

    Uri FilePathUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ProductDetail");

        // Assign From UI
        prodName = findViewById(R.id.addProductName);
        unitName = findViewById(R.id.addUnitPriceInput);
        status = (Spinner) findViewById(R.id.addProductspinner);
        chooseImageBtn = (Button)findViewById(R.id.chooseImageBtn);
        btnAdd = findViewById(R.id.addProductBtn);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(AddProductActivity.this);

        // Adding click listener to Choose image button.
        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });

    }

    private void insertData(){

        final String prod_name = prodName.getText().toString().trim();
        final String prod_unit_price = unitName.getText().toString();
        final String prod_status = status.getSelectedItem().toString();

        if(prod_name.matches("") || prod_unit_price.matches("")){

            if(prod_name.matches(""))
                prodName.setError("Please Enter Product Name.!");

            if(prod_unit_price.matches(""))
                    unitName.setError("Please Enter Product Unit Price.!");

        }else{

            // Checking whether FilePathUri Is empty or not.
            if (FilePathUri != null) {

                // Setting progressDialog Title.
                progressDialog.setTitle("Image is Uploading...");

                // Showing progressDialog.
                progressDialog.show();

                // Creating second StorageReference.
                final StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

                // Adding addOnSuccessListener to second StorageReference.
                storageReference2nd.putFile(FilePathUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override

                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                // Hiding the progressDialog after done uploading.
                                progressDialog.dismiss();

                                // Showing toast message after done uploading.
                                Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                                // Getting image upload ID.
                                String ImageUploadId = myRef.push().getKey();

                                // Getting image name from EditText and store into string variable.
                                String TempImageName = ImageUploadId;

                                String id = myRef.push().getKey();

                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful());
                                Uri downloadUrl = urlTask.getResult();

                                final String sdownload_url = String.valueOf(downloadUrl);

                                ProductItem productItem = new ProductItem(id, prod_name, prod_unit_price, prod_status,TempImageName, sdownload_url);

                                myRef.child(id).setValue(productItem);

                                ((EditText) findViewById(R.id.addProductName)).setText("");
                                ((EditText) findViewById(R.id.addUnitPriceInput)).setText("");

                                Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
                                startActivity(intent);

                            }
                        })
                        // If something goes wrong .
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {

                                // Hiding the progressDialog.
                                progressDialog.dismiss();

                                // Showing exception erro message.
                                Toast.makeText(AddProductActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })

                        // On progress change upload time.
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                // Setting progressDialog Title.
                                progressDialog.setTitle("Image is Uploading...");

                            }
                        });
            }
            else {

                Toast.makeText(AddProductActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

            }

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // After selecting image change choose button above text.
                chooseImageBtn.setText("Image Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

}