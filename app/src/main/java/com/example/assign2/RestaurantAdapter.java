package com.example.assign2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private ArrayList<Restaurant> restaurantList;

    public RestaurantAdapter(ArrayList<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public void updateData(ArrayList<Restaurant> updatedList) {
        restaurantList.clear();
        restaurantList.addAll(updatedList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.tvName.setText(restaurant.getName());
        holder.tvLocation.setText(restaurant.getLocation());
        holder.tvPhone.setText(restaurant.getPhone());
        holder.tvDescription.setText(restaurant.getDescription());
        holder.tvRatings.setText(restaurant.getRatings());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvLocation, tvPhone, tvDescription, tvRatings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameTextView);
            tvLocation = itemView.findViewById(R.id.locationTextView);
            tvPhone = itemView.findViewById(R.id.phoneTextView);
            tvDescription = itemView.findViewById(R.id.descriptionTextView);
            tvRatings = itemView.findViewById(R.id.ratingsTextView);
        }
    }
}
