package com.acbelter.weatherapp.mvp.view.activity.adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acbelter.weatherapp.R;
import com.acbelter.weatherapp.domain.model.city.CityData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

public class FavoritesCitiesAdapter extends RecyclerView.Adapter<FavoritesCitiesAdapter.FavoritesCitiesViewHolder> {

    public interface OnItemClickListener {
        void onCityItemClick(CityData item);

        void deleteCityItem(CityData item);
    }

    @NonNull
    private List<CityData> favoritesCities;
    @NonNull
    private final OnItemClickListener itemClickListener;
    private boolean isShowDeleteBtn;

    public FavoritesCitiesAdapter(@NonNull OnItemClickListener clickListener) {
        this.favoritesCities = new ArrayList<>();
        this.itemClickListener = clickListener;
        this.isShowDeleteBtn = false;

        setHasStableIds(true);
    }

    @Override
    public FavoritesCitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_city_item, parent, false);

        FavoritesCitiesViewHolder viewHolder = new FavoritesCitiesViewHolder(view);
        view.setOnClickListener(it -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                itemClicked(adapterPosition);
            }
        });

        viewHolder.ivDelete.setOnClickListener(delete -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                deleteItem(adapterPosition);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoritesCitiesViewHolder viewHolder, int position) {
        CityData city = favoritesCities.get(position);
        viewHolder.bind(city);
        viewHolder.showDeleteButton(isShowDeleteBtn);
        if (position == 0) {
            viewHolder.setFavoritesVisible(true);
        } else {
            viewHolder.setFavoritesVisible(false);
        }
    }

    @Override
    public int getItemCount() {
        return favoritesCities.size();
    }

    @Override
    public long getItemId(int position) {
        return favoritesCities.get(position).hashCode();
    }

    private void itemClicked(int position) {
        CityData cityData = favoritesCities.get(position);
        itemClickListener.onCityItemClick(cityData);
        favoritesCities.remove(position);
        favoritesCities.add(0, cityData);
        notifyItemRangeChanged(0, position);
    }

    private void deleteItem(int position) {
        itemClickListener.deleteCityItem(favoritesCities.get(position));
        favoritesCities.remove(favoritesCities.get(position));
        notifyItemRemoved(position);
    }

    public void update(@Nullable List<CityData> cities) {
        if (cities == null)
            return;
        favoritesCities = cities;
        notifyDataSetChanged();
    }

    public void showDeleteButton(boolean show) {
        this.isShowDeleteBtn = show;
        notifyDataSetChanged();
    }

    public boolean isShowDeleteButton() {
        return isShowDeleteBtn;
    }

    static class FavoritesCitiesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvFavoriteCity)
        TextView tvCity;
        @BindView(R.id.ivFavoriteFlag)
        ImageView ivFavorite;
        @BindView(R.id.ivDelete)
        ImageView ivDelete;

        FavoritesCitiesViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bind(final CityData item) {
            tvCity.setText(item.getShortName());
        }

        void setFavoritesVisible(boolean visible) {
            if (visible) {
                ivFavorite.setVisibility(VISIBLE);
                tvCity.setTypeface(null, Typeface.BOLD);
            } else {
                ivFavorite.setVisibility(View.INVISIBLE);
                tvCity.setTypeface(null, Typeface.NORMAL);
            }
        }

        void showDeleteButton(boolean isShow) {
            if (isShow)
                ivDelete.setVisibility(VISIBLE);
            else
                ivDelete.setVisibility(View.INVISIBLE);
        }
    }
}
