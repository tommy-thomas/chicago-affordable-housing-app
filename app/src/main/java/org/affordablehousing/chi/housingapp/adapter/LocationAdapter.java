package org.affordablehousing.chi.housingapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.affordablehousing.chi.housingapp.R;
import org.affordablehousing.chi.housingapp.databinding.LocationListItemBinding;
import org.affordablehousing.chi.housingapp.model.Location;
import org.affordablehousing.chi.housingapp.ui.LocationListItemCallback;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends RecyclerView.Adapter <LocationAdapter.LocationViewHolder> {

    List<? extends Location> mLocationList;

    @Nullable
    private final LocationListItemCallback mLocationListItemCallback;

    public LocationAdapter(LocationListItemCallback mLocationListItemCallback) {
        this.mLocationListItemCallback = mLocationListItemCallback;
        setHasStableIds(true);
    }

    public void setLocationList(final List<? extends Location> locationList) {
        if (mLocationList == null) {
           mLocationList = locationList;
            notifyItemRangeInserted(0, locationList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mLocationList.size();
                }

                @Override
                public int getNewListSize() {
                    return mLocationList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mLocationList.get(oldItemPosition).getLocationId() ==
                            locationList.get(newItemPosition).getLocationId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Location newLocation = locationList.get(newItemPosition);
                    Location oldLocation = mLocationList.get(oldItemPosition);
                    return newLocation.getLocationId() == oldLocation.getLocationId()
                            && Objects.equals(newLocation.getProperty_name(), oldLocation.getProperty_name());
                }
            });
            mLocationList = locationList;
            result.dispatchUpdatesTo(this);
        }
    }


    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LocationListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.location_list_item,
                        parent, false);
        binding.setCallback(mLocationListItemCallback);
        return new LocationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        holder.binding.setLocation(mLocationList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mLocationList == null ? 0 : mLocationList.size();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {

        final LocationListItemBinding binding;

        public LocationViewHolder(LocationListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}