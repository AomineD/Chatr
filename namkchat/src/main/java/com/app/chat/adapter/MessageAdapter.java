package com.app.chat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.chat.NamkoFragment;
import com.app.chat.R;
import com.app.chat.model.MessageReceive;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context mContext;
    private ArrayList<MessageReceive> messageOf = new ArrayList<>();
    private int card_backcolor;
    private int card_main;
    private int actiomtextcolor;
    private boolean card_backcolorAssigned;

    public void setActiomtextcolor(int actiomtextcolor) {
        this.actiomtextcolor = actiomtextcolor;
    }

    public void setTextColorM(int textColorM) {
        this.textColorM = textColorM;
    }

    private int textColorM;

    public void setTextColorMa(int textColorMa) {
        this.textColorMa = textColorMa;
    }

    private int textColorMa;

    public void setCard_backcolor(int card_backcolor) {
        card_backcolorAssigned = true;
        this.card_backcolor = card_backcolor;
    }

    public void setCard_main(int card_main) {
        this.card_main = card_main;
    }

    public void setAnother_cards(int another_cards) {
        this.another_cards = another_cards;
    }

    public void setIdMain(String idMain) {
        this.idMain = idMain;
    }

    private int another_cards;
    private String idMain;

    public void setClickmedia(NamkoFragment.OnClickmedia clickmedia) {
        this.clickmedia = clickmedia;
    }

    private NamkoFragment.OnClickmedia clickmedia;

    public MessageAdapter(Context ss, ArrayList<MessageReceive> messages){
      this.mContext = ss;
      this.messageOf = messages;
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item, viewGroup, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, final int i) {
        messageViewHolder.name.setText(messageOf.get(i).getName_of());
        messageViewHolder.mesn.setText(messageOf.get(i).getMesg());

        Picasso.get().load(Uri.parse(messageOf.get(i).getUrlProfilePic())).placeholder(R.drawable.ic_avatar_default).fit().into(messageViewHolder.prf_pic);

        Long codigoHora = messageOf.get(i).getHora();

        Date d = new Date(codigoHora);

        SimpleDateFormat ss = new SimpleDateFormat("hh:mm:ss a");

        messageViewHolder.time.setText(ss.format(d));


        if(messageOf.get(i).getSnap().equals(idMain) && card_backcolorAssigned){
            messageViewHolder.backing.setCardBackgroundColor(card_main);
            messageViewHolder.mesn.setTextColor(textColorM);
            messageViewHolder.time.setTextColor(textColorM);
            messageViewHolder.name.setTextColor(textColorM);
        }else if(card_backcolorAssigned){
           // Log.e("MAIN", "onBindViewHolder: OTRO COLOR");
            messageViewHolder.backing.setCardBackgroundColor(another_cards);
            messageViewHolder.mesn.setTextColor(textColorMa);
            messageViewHolder.time.setTextColor(textColorMa);
            messageViewHolder.name.setTextColor(textColorMa);
        }

        if(messageOf.get(i).getType_mensaje().equals("1"))
        {

            if(card_backcolorAssigned){
                messageViewHolder.card_back.setCardBackgroundColor(card_backcolor);
            }

            messageViewHolder.mediaText.setVisibility(View.VISIBLE);
            messageViewHolder.mediaPhoto.setVisibility(View.VISIBLE);
            messageViewHolder.actionbtn.setVisibility(View.VISIBLE);
            messageViewHolder.card_back.setVisibility(View.VISIBLE);


            messageViewHolder.mediaText.setText(messageOf.get(i).getDescmedia());
            messageViewHolder.actionbtn.setText(messageOf.get(i).getAction());

            messageViewHolder.mesn.setGravity(Gravity.CENTER);


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10,0,0);
            messageViewHolder.mesn.setLayoutParams(params);

            messageViewHolder.mesn.setTypeface(null, Typeface.BOLD);
            messageViewHolder.mediaText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            messageViewHolder.mesn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            Picasso.get().load(Uri.parse(messageOf.get(i).getUrl_img_media())).into(messageViewHolder.mediaPhoto);

            messageViewHolder.actionbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickmedia.onClickMedia(messageOf.get(i).getDataToClick());
                }
            });

        }else{
            messageViewHolder.mediaText.setVisibility(View.GONE);
            messageViewHolder.mediaPhoto.setVisibility(View.GONE);
            messageViewHolder.actionbtn.setVisibility(View.GONE);
            messageViewHolder.card_back.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messageOf.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView prf_pic;
        private TextView name;
        private TextView time;
        private TextView actionbtn;
        private CardView card_back;
        private TextView mediaText;
        private ImageView mediaPhoto;
        private TextView mesn;
        private CardView backing;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            prf_pic = itemView.findViewById(R.id.prof_ic);
            name = itemView.findViewById(R.id.t_name);
            time = itemView.findViewById(R.id.t_time);
            mesn = itemView.findViewById(R.id.t_messg);
            backing = itemView.findViewById(R.id.bacj);

            actionbtn = itemView.findViewById(R.id.actionmedia);
            card_back = itemView.findViewById(R.id.card_back);
            mediaPhoto = itemView.findViewById(R.id.media_f);
            mediaText = itemView.findViewById(R.id.t_mediainfo);


        }
    }

    public void addMessage(MessageReceive msg){
        messageOf.add(msg);
        notifyItemInserted(messageOf.size() - 1);
    }



}
