package com.example.assign2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_KEY_RESTAURANTS = "restaurants";
    private static final String PREF_NAME = "restaurant_prefs";

    private RecyclerView recyclerView;
    private ArrayList<Restaurant> restaurantList;
    private RestaurantAdapter adapter;
    private SearchView searchView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        restaurantList = new ArrayList<>();
        adapter = new RestaurantAdapter(restaurantList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchView);
        addButton = findViewById(R.id.addButton);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRestaurant(newText);
                return true;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterRestaurantActivity.class));
                // Add code here to save restaurant data after adding a new restaurant
                saveRestaurantData();
            }
        });

        loadRestaurantData();
    }

    private void searchRestaurant(String query) {
        ArrayList<Restaurant> filteredList = new ArrayList<>();
        for (Restaurant restaurant : restaurantList) {
            if (restaurant.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(restaurant);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }

        adapter.updateData(filteredList);
    }

    private void loadRestaurantData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Set<String> restaurantSet = sharedPreferences.getStringSet(PREF_KEY_RESTAURANTS, null);
        if (restaurantSet != null) {
            for (String restaurantStr : restaurantSet) {
                String[] restaurantData = restaurantStr.split(",");
                if (restaurantData.length == 5) {
                    Restaurant restaurant = new Restaurant(
                            restaurantData[0],
                            restaurantData[1],
                            restaurantData[2],
                            restaurantData[3],
                            restaurantData[4]
                    );
                    Toast.makeText(this, restaurantData[0], Toast.LENGTH_SHORT).show();
                    restaurantList.add(restaurant);
                }
            }
            adapter.notifyDataSetChanged(); // Adapter initialized after loading data
        } else {
            // Handle case where no data is found in SharedPreferences
        }
    }



    private void saveRestaurantData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> restaurantSet = new HashSet<>();
        for (Restaurant restaurant : restaurantList) {
            String restaurantStr = restaurant.getName() + "," +
                    restaurant.getLocation() + "," +
                    restaurant.getPhone() + "," +
                    restaurant.getDescription() + "," +
                    restaurant.getRatings();
            restaurantSet.add(restaurantStr);
        }
        editor.putStringSet(PREF_KEY_RESTAURANTS, restaurantSet);
        editor.apply();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Reload data onResume to handle cases where data might have changed
        restaurantList.clear(); // Clear list before reloading to avoid duplicates
        loadRestaurantData();
        adapter.notifyDataSetChanged(); // Notify adapter after reloading data
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveRestaurantData(); // Save data onPause to ensure changes are persisted
    }

}
