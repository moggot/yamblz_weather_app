package com.acbelter.weatherapp.mvp.view.search.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acbelter.weatherapp.R;
import com.acbelter.weatherapp.domain.model.city.AutocompleteData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    public interface OnItemClickListener {
        void onCityItemClick(AutocompleteData item);
    }

    @NonNull
    private List<AutocompleteData> locations;
    @NonNull
    private final OnItemClickListener itemClickListener;

    public CityAdapter(@NonNull OnItemClickListener clickListener) {
        locations = new ArrayList<>();
        itemClickListener = clickListener;

        setHasStableIds(true);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
        CityViewHolder viewHolder = new CityViewHolder(view);

        view.setOnClickListener(it -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION)
                itemClicked(adapterPosition);
        });
        return viewHolder;
    }

    private void itemClicked(int position) {
        itemClickListener.onCityItemClick(locations.get(position));
    }

    @Override
    public void onBindViewHolder(CityViewHolder viewHolder, int position) {
        AutocompleteData location = locations.get(position);
        viewHolder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void update(@Nullable List<AutocompleteData> list) {
        if (list == null)
            return;
        locations = list;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return locations.get(position).hashCode();
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCity)
        TextView tvCity;

        CityViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bind(final AutocompleteData item) {
            tvCity.setText(item.getCityName());
        }
    }
}
