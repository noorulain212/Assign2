package com.example.assign2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterRestaurantActivity extends AppCompatActivity {

    private EditText etName, etLocation, etPhone, etDescription, etRatings;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);

        etName = findViewById(R.id.etName);
        etLocation = findViewById(R.id.etLocation);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etRatings = findViewById(R.id.etRatings);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRestaurantData();
            }
        });
    }

    private void saveRestaurantData() {
        String name = etName.getText().toString();
        String location = etLocation.getText().toString();
        String phone = etPhone.getText().toString();
        String description = etDescription.getText().toString();
        String ratings = etRatings.getText().toString();

        if (!name.isEmpty() && !location.isEmpty() && !phone.isEmpty() && !description.isEmpty() && !ratings.isEmpty()) {
            // Save restaurant data using SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("restaurant_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String restaurantKey = name + "_" + location; // Unique key for each restaurant
            editor.putString(restaurantKey, name + "," + location + "," + phone + "," + description + "," + ratings);
            editor.apply();

            //Toast.makeText(this, "Restaurant saved successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close activity after saving
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
