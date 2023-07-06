package com.example.movusandroidapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movusandroidapp.Activities.MainActivity;
import com.example.movusandroidapp.Activities.MapsActivity;
import com.example.movusandroidapp.Api.GetLocationResponse;
import com.example.movusandroidapp.Api.GetUsedCarsResponse;
import com.example.movusandroidapp.R;
import com.example.movusandroidapp.Repository.MainRepository;

import java.util.List;

public class UsedCarsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GetUsedCarsResponse> carsList;
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    public UsedCarsAdapter(List<GetUsedCarsResponse> carsList) {
        this.carsList = carsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_HEADER) {
            View headerView = inflater.inflate(R.layout.car_list_header, parent, false);
            return new HeaderViewHolder(headerView);
        } else {
            View itemView = inflater.inflate(R.layout.item_used_cars, parent, false);

            return new ItemViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            GetUsedCarsResponse car = carsList.get(position - 1); // Subtract 1 for the header view
            itemViewHolder.bind(car);

            int backgroundColor = (position % 2 == 0) ? Color.WHITE : Color.LTGRAY;
            itemViewHolder.itemView.setBackgroundColor(backgroundColor);
        }
    }

    @Override
    public int getItemCount() {
        return carsList.size() + 1; // Add 1 for the header view
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView plateTextView;
        private TextView user;
        private TextView destination;
        private TextView time;
        private Button btnViewOnMap;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            plateTextView = itemView.findViewById(R.id.tvPlate);
            user = itemView.findViewById(R.id.tvUsername);
            destination = itemView.findViewById(R.id.tvDestination);
            time = itemView.findViewById(R.id.tvTime);
            btnViewOnMap = itemView.findViewById(R.id.btnViewOnMap);

            btnViewOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        GetUsedCarsResponse car = carsList.get(position - 1); // Subtract 1 for the header view

                        // Start the MapsActivity and pass the plate data
                        Context context = itemView.getContext();
                        Intent intent = new Intent(context, MapsActivity.class);
                        intent.putExtra("plate", car.getPlate());
                        context.startActivity(intent);
                    }
                }
            });

        }

        public void bind(GetUsedCarsResponse car) {
            plateTextView.setText(car.getPlate().trim());
            user.setText(car.getUsername().trim());
            destination.setText(car.getDestination().trim());
            time.setText(car.getCarTakenTime().substring(5, 22).trim());
        }
    }
}

