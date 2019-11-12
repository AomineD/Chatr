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
import com.facebook.ads.Ad;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final String TAG = "MAIN";
    private Context mContext;
    private ArrayList<MessageReceive> messageOf = new ArrayList<>();
    private int card_backcolor;
    private int card_main;
    private int actiomtextcolor;

    public void setIdBanner(String id_intersticial) {
        this.id_intersticial = id_intersticial;
    }

    private String id_intersticial;
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

    public interface onClickListener{
        void clickingMessage(String name);
    }

    private int another_cards;
    private String idMain;

    public void setClickmedia(NamkoFragment.OnClickmedia clickmedia) {
        this.clickmedia = clickmedia;
    }

    private NamkoFragment.OnClickmedia clickmedia;

    public MessageAdapter(Context ss, ArrayList<MessageReceive> messages, boolean Admin){
      this.mContext = ss;
      this.messageOf = messages;
      this.isAdminUser = Admin;
    }


    public void setOnClickMessage(onClickListener onClickMessage){
        this.listener = onClickMessage;
    }

    private onClickListener listener;
    private boolean isAdminUser = false;
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item, viewGroup, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, final int i) {
messageViewHolder.img_admin.setVisibility(View.GONE);




        if(messageOf.get(i).isAd && messageOf.get(i).getIsAdmin().equals("false")){
            if(!messageViewHolder.isAdLoaded) {
                messageViewHolder.adView2 = new AdView(mContext, id_intersticial, AdSize.BANNER_HEIGHT_50);
                messageViewHolder.adView2.loadAd();
                messageViewHolder.adView.addView(messageViewHolder.adView2);
messageViewHolder.isAdLoaded = true;
            }
            messageViewHolder.adView.setVisibility(View.VISIBLE);
            messageViewHolder.adve.setVisibility(View.VISIBLE);
            messageViewHolder.base.setVisibility(View.GONE);
        }else if(messageOf.get(i).getIsAdmin().equals("false")){
final MessageReceive mf = messageOf.get(i);
            if(isAdminUser) {
             //   Log.e(TAG, "onBindViewHolder: si es admin");
                messageViewHolder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
listener.clickingMessage(mf.getName_of());
                    }
                });
            }else{
                messageViewHolder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            messageViewHolder.adView.setVisibility(View.GONE);
            messageViewHolder.adve.setVisibility(View.GONE);
            messageViewHolder.base.setVisibility(View.VISIBLE);

            messageViewHolder.name.setText(messageOf.get(i).getName_of());
            messageViewHolder.mesn.setText(messageOf.get(i).getMesg());

            Picasso.get().load(Uri.parse(messageOf.get(i).getUrlProfilePic())).placeholder(R.drawable.ic_avatar_default).fit().into(messageViewHolder.prf_pic);

            Long codigoHora = messageOf.get(i).getHora();


            try {

              String text =  getTimingt(codigoHora);
                messageViewHolder.time.setText(text);
            } catch (Exception e) {
                e.printStackTrace();
                messageViewHolder.time.setText("Incalculate");
                Log.e(TAG, "onBindViewHolder: "+e.getMessage());
            }

          /*  Date d = new Date(codigoHora);

            SimpleDateFormat ss = new SimpleDateFormat("hh:mm:ss a");
*/



            if (messageOf.get(i).getSnap().equals(idMain) && card_backcolorAssigned) {
                messageViewHolder.backing.setCardBackgroundColor(card_main);
                messageViewHolder.mesn.setTextColor(textColorM);
                messageViewHolder.time.setTextColor(textColorM);
                messageViewHolder.name.setTextColor(textColorM);
            } else if (card_backcolorAssigned) {
                // Log.e("MAIN", "onBindViewHolder: OTRO COLOR");
                messageViewHolder.backing.setCardBackgroundColor(another_cards);
                messageViewHolder.mesn.setTextColor(textColorMa);
                messageViewHolder.time.setTextColor(textColorMa);
                messageViewHolder.name.setTextColor(textColorMa);
            }

            if (messageOf.get(i).getType_mensaje().equals("1")) {

                if (card_backcolorAssigned) {
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
                params.setMargins(0, 10, 0, 0);
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

            }
            else {
                messageViewHolder.mediaText.setVisibility(View.GONE);
                messageViewHolder.mediaPhoto.setVisibility(View.GONE);
                messageViewHolder.actionbtn.setVisibility(View.GONE);
                messageViewHolder.card_back.setVisibility(View.GONE);
            }
        } else if(messageOf.get(i).getIsAdmin().equals("true")){

            SetupMessageAdmin(messageOf.get(i), messageViewHolder);

        }
    }

    private void SetupMessageAdmin(final MessageReceive messageReceive, MessageViewHolder messageViewHolder) {

        messageViewHolder.img_admin.setVisibility(View.VISIBLE);
        messageViewHolder.adView.setVisibility(View.GONE);
        messageViewHolder.adve.setVisibility(View.GONE);
        messageViewHolder.base.setVisibility(View.VISIBLE);

        messageViewHolder.name.setText(messageReceive.getName_of());
        messageViewHolder.mesn.setText(messageReceive.getMesg());

        Picasso.get().load(Uri.parse(messageReceive.getUrlProfilePic())).placeholder(R.drawable.ic_avatar_default).fit().into(messageViewHolder.prf_pic);

        Long codigoHora = messageReceive.getHora();


        try {

            String text =  getTimingt(codigoHora);
            messageViewHolder.time.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
            messageViewHolder.time.setText("Incalculate");
            Log.e(TAG, "onBindViewHolder: "+e.getMessage());
        }

          /*  Date d = new Date(codigoHora);

            SimpleDateFormat ss = new SimpleDateFormat("hh:mm:ss a");
*/



            messageViewHolder.backing.setCardBackgroundColor(mContext.getResources().getColor(R.color.admin_texts));
            messageViewHolder.mesn.setTextColor(mContext.getResources().getColor(R.color.graylight));
            messageViewHolder.time.setTextColor(mContext.getResources().getColor(R.color.graylight));
            messageViewHolder.name.setTextColor(mContext.getResources().getColor(R.color.yellow));


        Log.e(TAG, "SetupMessageAdmin: "+messageReceive.getType_mensaje() );
        if (messageReceive.getType_mensaje().equals("1")) {

            if (card_backcolorAssigned) {
                messageViewHolder.card_back.setCardBackgroundColor(card_backcolor);
            }

            messageViewHolder.mediaText.setVisibility(View.VISIBLE);
            messageViewHolder.mediaPhoto.setVisibility(View.VISIBLE);
            messageViewHolder.actionbtn.setVisibility(View.VISIBLE);
            messageViewHolder.card_back.setVisibility(View.VISIBLE);


            messageViewHolder.mediaText.setText(messageReceive.getDescmedia());
            messageViewHolder.actionbtn.setText(messageReceive.getAction());

            messageViewHolder.mesn.setGravity(Gravity.CENTER);


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);
            messageViewHolder.mesn.setLayoutParams(params);

            messageViewHolder.mesn.setTypeface(null, Typeface.BOLD);
            messageViewHolder.mediaText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            messageViewHolder.mesn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            Picasso.get().load(Uri.parse(messageReceive.getUrl_img_media())).into(messageViewHolder.mediaPhoto);

            messageViewHolder.actionbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickmedia.onClickMedia(messageReceive.getDataToClick());
                }
            });

        }
        else {
            messageViewHolder.mediaText.setVisibility(View.GONE);
            messageViewHolder.mediaPhoto.setVisibility(View.GONE);
            messageViewHolder.actionbtn.setVisibility(View.GONE);
            messageViewHolder.card_back.setVisibility(View.GONE);
        }

        }

    private String getTimingt(Long pastvalue) {

       // long pastvalue = Long.parseLong(codigoHora);
        // Default time zone.
        DateTime zulu = DateTime.now(DateTimeZone.UTC);


        //   Log.e(TAG, "getTimingt: MENSAJE "+pastvalue);

        //     Log.e(TAG, "getTimingt: AHORA "+zulu.getMillis() + " NOW ES "+pastvalue);
        long mint = TimeUnit.MILLISECONDS.toMinutes(zulu.toInstant().getMillis() - pastvalue);

        if (mint < 60) {
            if (mint > 1)
                return "Hace " + mint + " minutos";
            else
                return "Hace " + mint + " minuto";
        } else {
            long horas = TimeUnit.MILLISECONDS.toHours(zulu.toDate().getTime() - pastvalue);

            if (horas < 24) {
                if (horas > 1)
                    return "Hace " + horas + " horas";
                else
                    return "Hace " + horas + " hora";
            } else {
                long dias = TimeUnit.MILLISECONDS.toDays(zulu.toDate().getTime() - pastvalue);

                if (dias > 1) {
                    return "Hace " + dias + " dias";
                } else
                    return "Hace " + dias + " dia";
            }


        }
    }

    @Override
    public int getItemCount() {
        return messageOf.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private boolean isAdLoaded;
        private CircleImageView prf_pic;
        private TextView name;
        private TextView time;
        private TextView actionbtn;
        private CardView card_back;
        private TextView mediaText;
        private ImageView mediaPhoto;
        private TextView mesn;
        private CardView backing;
        private LinearLayout adView;
        private AdView adView2;
        private View base;
        private ImageView img_admin;
        private View adve;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            base = itemView.findViewById(R.id.baselay);
            img_admin = itemView.findViewById(R.id.admin_insigne);
            prf_pic = itemView.findViewById(R.id.prof_ic);
            name = itemView.findViewById(R.id.t_name);
            time = itemView.findViewById(R.id.t_time);
            mesn = itemView.findViewById(R.id.t_messg);
            backing = itemView.findViewById(R.id.bacj);
adView = itemView.findViewById(R.id.layad);
adve = itemView.findViewById(R.id.adlay);
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
