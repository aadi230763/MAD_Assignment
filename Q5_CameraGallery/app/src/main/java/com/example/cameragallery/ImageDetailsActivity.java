package com.example.cameragallery;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.bumptech.glide.Glide;

import java.util.Date;

public class ImageDetailsActivity extends AppCompatActivity {
    private ImageView ivImage;
    private TextView tvName, tvPath, tvSize, tvDate;
    private Button btnDelete;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        ivImage = findViewById(R.id.ivImage);
        tvName = findViewById(R.id.tvName);
        tvPath = findViewById(R.id.tvPath);
        tvSize = findViewById(R.id.tvSize);
        tvDate = findViewById(R.id.tvDate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        if (intent != null) {
            imageUri = Uri.parse(intent.getStringExtra("imageUri"));
            String name = intent.getStringExtra("imageName");
            String path = intent.getStringExtra("imagePath");
            long size = intent.getLongExtra("imageSize", 0);
            long date = intent.getLongExtra("imageDate", 0);

            displayImageDetails(name, path, size, date);
            setupDeleteButton();
        }
    }

    private void displayImageDetails(String name, String path, long size, long date) {
        Glide.with(this).load(imageUri).into(ivImage);
        tvName.setText(String.format("Name: %s", name));
        tvPath.setText(String.format("Path: %s", path));
        tvSize.setText(String.format("Size: %s", Formatter.formatFileSize(this, size)));
        tvDate.setText(String.format("Date: %s", DateFormat.format("dd/MM/yyyy hh:mm:ss a", new Date(date))));
    }

    private void setupDeleteButton() {
        btnDelete.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Image")
                .setMessage("Are you sure you want to delete this image?")
                .setPositiveButton("Delete", (d, w) -> deleteImage())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteImage() {
        try {
            DocumentFile file = DocumentFile.fromSingleUri(this, imageUri);
            if (file != null && file.exists() && file.delete()) {
                Toast.makeText(this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Failed to delete image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error deleting image", Toast.LENGTH_SHORT).show();
        }
    }
}