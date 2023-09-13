package com.snuzj.bai4;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private TextView nameTextView, addressTextView, course1TextView, course2TextView, course3TextView;
    private ImageView englishButton, vietnameseButton;
    private JSONObject jsonData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        nameTextView = findViewById(R.id.nameTextView);
        addressTextView = findViewById(R.id.addressTextView);
        course1TextView = findViewById(R.id.course1TextView);
        course2TextView = findViewById(R.id.course2TextView);
        course3TextView = findViewById(R.id.course3TextView);
        englishButton = findViewById(R.id.switchToEnglishButton);
        vietnameseButton = findViewById(R.id.switchToVietnameseButton);

        // Load JSON data from assets
        try {
            String json = loadJSONFromAsset(this, "data.json");
            jsonData = new JSONObject(json);

            // Display data in default language (Vietnamese)
            displayData("vn");

            // Set click listeners for language buttons
            englishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayData("en");
                }
            });

            vietnameseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayData("vn");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void displayData(String language) {
        try {
            JSONObject languageData = jsonData.getJSONObject("language").getJSONObject(language);
            nameTextView.setText(languageData.getString("name"));
            addressTextView.setText(languageData.getString("address"));
            course1TextView.setText(languageData.getString("course1"));
            course2TextView.setText(languageData.getString("course2"));
            course3TextView.setText(languageData.getString("course3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
