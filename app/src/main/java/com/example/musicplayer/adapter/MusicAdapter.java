package com.example.musicplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.model.ItemMusic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    private List<ItemMusic> itemMusicList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtIso)
        TextView txtIso;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPhone)
        TextView txtPhone;
        @BindView(R.id.imgRemove)
        ImageView imgRemove;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public MusicAdapter(List<ItemMusic> itemMusicList) {
        this.itemMusicList = itemMusicList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_music, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ItemMusic itemMusic = itemMusicList.get(position);
        holder.txtIso.setText(itemMusic.getIso());
        holder.txtName.setText(itemMusic.getName());
        holder.txtPhone.setText(itemMusic.getPhone());

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemMusicList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, itemMusicList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemMusicList.size();
    }
}

