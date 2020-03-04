package com.app.chat.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.chat.R;

public class playerAdapter extends RecyclerView.Adapter<playerAdapter.HolderPlayer> {

    private Context mContext;

    public playerAdapter(Context mmm){
        this.mContext = mmm;
    }

    @NonNull
    @Override
    public HolderPlayer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(mContext).inflate(R.layout.player_item, parent, false);
        return new HolderPlayer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPlayer holder, int position) {
if(position == 0){
    holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.flix_player_ic));
}else{
    holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_vlc));
}

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    class HolderPlayer extends RecyclerView.ViewHolder{

        private ImageView icon;
        public HolderPlayer(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon_item);
        }
    }
}
