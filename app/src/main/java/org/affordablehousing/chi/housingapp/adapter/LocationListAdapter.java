package org.affordablehousing.chi.housingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.affordablehousing.chi.housingapp.R;
import org.affordablehousing.chi.housingapp.model.LocationEntity;
import org.affordablehousing.chi.housingapp.viewmodel.LocationListViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class LocationListAdapter extends RecyclerView.Adapter <LocationListAdapter.ViewHolder> {

    Context mContext;
    ArrayList <LocationEntity> mLocationEntityList;
    List <LocationEntity> mLocationEntityListMaster;
    String mCurrrentCommunity;
    ArrayList <String> mLocationTypeFilter;
    LocationListViewModel mLocationListViewModel;

    private void copyMasterList() {
        if (mLocationEntityList != null)
            mLocationEntityList.clear();
        for (LocationEntity locationEntity : mLocationEntityListMaster) {
            mLocationEntityList.add(locationEntity);
        }
    }

    private void filterList() {

        Iterator <LocationEntity> iter = this.mLocationEntityList.iterator();
        while (iter.hasNext()) {
            LocationEntity locationEntity = iter.next();
            if ( mLocationTypeFilter.size() > 0 && !mLocationTypeFilter.contains(locationEntity.getProperty_type()) ) {
                iter.remove();
            } else if ( !mCurrrentCommunity.equals("Community") && !locationEntity.getCommunity_area().equals(mCurrrentCommunity) ){
                iter.remove();
            } else if( locationEntity.getAddress().isEmpty()){
                iter.remove();
            }
        }

    }

    @NonNull
    @Override
    public LocationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder != null) {

            holder.mHeader.setText(mLocationEntityList.get(position).getProperty_name());
            holder.mAddress.setText(mLocationEntityList.get(position).getAddress());

            //holder.mToggleButtonFavorite.setChecked(false);
            if( mLocationEntityList.get(position).getIs_favorite() ){
                holder.mToggleButtonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_outline_favorite_24px));
                holder.mToggleButtonFavorite.setChecked(true);
            } else {
                holder.mToggleButtonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_round_favorite_border_24px));
                holder.mToggleButtonFavorite.setChecked(false);
            }

            holder.mToggleButtonFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if ( isChecked ) {
                        holder.mToggleButtonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_outline_favorite_24px));
                        Toast toast = Toast.makeText(mContext,
                                mLocationEntityList.get(position).getProperty_name() + " added to favorites.",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        holder.mToggleButtonFavorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_round_favorite_border_24px));
                        Toast toast = Toast.makeText(mContext,
                                mLocationEntityList.get(position).getProperty_name() + " removed from favorites.",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    mLocationListViewModel.setFavorite(mLocationEntityList.get(position).getLocationId(), isChecked);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // mLocationClickListener.onLocationSelected( mLocationEntityList.get(position).getLocationId());
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return mLocationEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mHeader;
        TextView mAddress;
        ToggleButton mToggleButtonFavorite;

        public ViewHolder(View view) {
            super(view);
            mHeader = view.findViewById(R.id.tv_location_list_header);
            mAddress = view.findViewById(R.id.tv_location_list_address);
            mToggleButtonFavorite = view.findViewById(R.id.tb_favorite);
        }
    }

}