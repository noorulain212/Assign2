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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
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

            }
        });

        loadRestaurantData();
    }

    private void searchRestaurant(String query) {
        if(query == null || query.isEmpty()){
            loadRestaurantData();
        }
        else{
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

    }

    private void loadRestaurantData() {
        SharedPreferences sharedPreferences = getSharedPreferences("restaurant_prefs", MODE_PRIVATE);
        // Get all keys stored in SharedPreferences
        Map<String, ?> allRestaurantData = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allRestaurantData.entrySet()) {
            String restaurantDataStr = entry.getValue().toString();
            String[] restaurantData = restaurantDataStr.split(",");
            if (restaurantData.length == 5) {
                // Create a new Restaurant object and add it to your list
                Restaurant restaurant = new Restaurant(
                        restaurantData[0],
                        restaurantData[1],
                        restaurantData[2],
                        restaurantData[3],
                        restaurantData[4]
                );
                restaurantList.add(restaurant);

            }
        }
        sortRestaurantListByRatings();
        // Notify the adapter after loading the data

    }


    // Inside your MainActivity class

    // Method to sort the restaurantList by ratings in descending order
    private void sortRestaurantListByRatings() {
        Collections.sort(restaurantList, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                // Convert ratings to float for comparison
                float rating1 = Float.parseFloat(r1.getRatings());
                float rating2 = Float.parseFloat(r2.getRatings());
                // Compare in descending order
                return Float.compare(rating2, rating1);
            }
        });
        adapter.notifyDataSetChanged(); // Notify adapter after sorting
    }




    @Override
    protected void onResume() {
        super.onResume();
        // Reload data onResume to handle cases where data might have changed
        restaurantList.clear(); // Clear list before reloading to avoid duplicates
        loadRestaurantData();
        adapter.notifyDataSetChanged(); // Notify adapter after reloading data
    }



}
