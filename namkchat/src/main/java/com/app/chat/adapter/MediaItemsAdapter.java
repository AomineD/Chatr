package com.app.chat.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.chat.R;
import com.app.chat.model.MediaItem;
import com.app.chat.utils.DonotTouch;
import com.app.chat.utils.PopUpSelect;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MediaItemsAdapter extends RecyclerView.Adapter<MediaItemsAdapter.HolderMedia> {


    private Context mContext;
    private Activity actividad;
    private ArrayList<MediaItem> Lista;

    private boolean user = false;

    private static int fr = 1;

    public MediaItemsAdapter(Context contexto, ArrayList<MediaItem> lss, boolean issu, Activity mact){
        this.mContext = contexto;
        this.Lista = lss;
        this.user = issu;
        this.actividad = mact;
    }




    @NonNull
    @Override
    public HolderMedia onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            View view = LayoutInflater.from(mContext).inflate(R.layout.media_item, parent, false);

            return new HolderMedia(view);
        }catch (OutOfMemoryError e){
            DonotTouch.ShowErrorLoad(actividad, mContext.getString(R.string.out_memory));
            return new HolderMedia(new View(mContext));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderMedia holder, final int position) {

        if(!Lista.get(position).isAd()){
            String nombre = Lista.get(position).getName();
            Uri urr = Lista.get(position).getUrl_photo();
            holder.title.setText(nombre);
            if(urr != null) {
                Picasso.get().load(urr).into(holder.pic);
            }
                //Log.e("MAIN", "onBindViewHolder: Favorito url: "+Favr.get(position).getUrl()+", Url lista: "+Lista.get(position).getUrl());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Lista.get(position).getUrl().toString().isEmpty()){
                              //  fr++;
                                OpenVideo(Lista.get(position).getUrl().toString(), Lista.get(position).getName());


                }else{
                        DonotTouch.ShowErrorLoad(actividad, mContext.getString(R.string.m3u_empty));
                    }
                }
            });

           // Log.e("MAIN", "onBindViewHolder: NAME = "+nombre);

            if(user){
                holder.report.setVisibility(View.GONE);
            }

            holder.report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String info = Lista.get(position).getName();
                    Report(mContext, info);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }

    class HolderMedia extends RecyclerView.ViewHolder{

        private ImageView pic;
        private TextView title;
        private ImageView report;

        public HolderMedia(View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.picture);
            title = itemView.findViewById(R.id.name_text);
            report = itemView.findViewById(R.id.report_error);


        }
    }

    private void OpenVideo(String url, String nm){
        PopUpSelect pop = new PopUpSelect(mContext, url, nm);

        pop.show();

    }

    public void filterListRefresh(ArrayList<MediaItem> itemsn){
Lista = itemsn;
notifyDataSetChanged();
    }

    private void Report(Context mc, String name){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+DonotTouch.email_developer));

        intent.putExtra(Intent.EXTRA_SUBJECT, mc.getString(R.string.report_part_1)+" "+name+" "+mc.getString(R.string.report_part_2));

        mc.startActivity(intent);
    }
}
