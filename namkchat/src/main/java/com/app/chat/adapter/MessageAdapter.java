package com.app.chat.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.chat.ChangeFragment;
import com.app.chat.utils.NamkoFragment;
import com.app.chat.R;
import com.app.chat.model.ChanInfo;
import com.app.chat.model.MessageReceive;
import com.app.chat.utils.EmojiUtils;
import com.app.chat.utils.Utils;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

import static com.app.chat.utils.Utils.isBanned;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final String TAG = "MAIN";
    private Activity mContext;
    private ArrayList<MessageReceive> messageOf = new ArrayList<>();
    private int card_backcolor;
    private int card_main;
    private int actiomtextcolor;
    private NamkoFragment nk;
    private FragmentManager fragmentManager;

    public void setFragmentInfo(NamkoFragment fragment, FragmentManager manager){
        this.nk = fragment;
        this.fragmentManager = manager;
    }

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

    public interface onClickListener {
        void clickingMessage(String name);
    }

    private int another_cards;
    private String idMain;

    public void setClickmedia(NamkoFragment.OnClickmedia clickmedia) {
        this.clickmedia = clickmedia;
    }

    private NamkoFragment.OnClickmedia clickmedia;

    public MessageAdapter(Activity ss, ArrayList<MessageReceive> messages, boolean Admin) {
        this.mContext = ss;
        this.messageOf = messages;
        this.isAdminUser = Admin;
    }


    public boolean isNativeAd = false;

    public void setOnClickMessage(onClickListener onClickMessage) {
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

    private int indiceAd = 0;

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, final int i) {
        messageViewHolder.img_admin.setVisibility(View.GONE);

      //  Log.e(TAG, "onBindViewHolder CHAT: "+getItemCount() );
        if (messageOf.get(i).isAd && messageOf.get(i).getIsAdmin().equals("false")) {

            if (!messageViewHolder.isAdLoaded && !isNativeAd) {
                messageViewHolder.adView2 = new AdView(mContext, id_intersticial, AdSize.BANNER_HEIGHT_50);
                messageViewHolder.adView2.loadAd();
                messageViewHolder.adView.addView(messageViewHolder.adView2);
                messageViewHolder.isAdLoaded = true;
                hideNativeAd(messageViewHolder, i);
                showBannerAd(messageViewHolder);

            } else {
                hideBannerAd(messageViewHolder);
                showNativeAd(messageViewHolder);


//messageViewHolder.native_view.setVisibility(View.VISIBLE);
                NamkoFragment.fanfic.setupNativeView(messageViewHolder.native_view, indiceAd, mContext.getResources().getColor(R.color.white), mContext.getResources().getColor(R.color.black));
              //  Log.e(TAG, "onBindViewHolder: nativo "+i );
                indiceAd++;

                if (indiceAd >= NamkoFragment.sizeIds) {
                    indiceAd = 0;
                }


            }


            messageViewHolder.base.setVisibility(View.GONE);
        } else if (messageOf.get(i).getIsAdmin().equals("false")) {

setupNormalMessage(messageViewHolder, i);
        } else if (messageOf.get(i).getIsAdmin().equals("true")) {
            YoYo.with(Techniques.SlideInDown)
                    .duration(1500)
                    .playOn(messageViewHolder.base);
            SetupMessageAdmin(messageOf.get(i), messageViewHolder);

        }
    }

    private void goChatPrivate(final MessageReceive mf, final int i) {

        Utils.createPopUpContact(mContext, mf,new Utils.SelectionListener() {
            @Override
            public void onConfirm() {
             ChanInfo chanInfo = ChangeFragment.getChanInf(mf.getSnap());
                // nk.setLang_chat();
                NamkoFragment.chanInfo = chanInfo;
                nk.setLang_chat(chanInfo.identifierChannel);

                ArrayList<ChanInfo> chanInfos = ChangeFragment.getListChannels();

                fragmentManager.beginTransaction().replace(nk.ContainerId, nk).commitAllowingStateLoss();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void OnBanUser() {
                super.OnBanUser();
                Utils.banThis(mf.getSnap());
                removeMessage(i);
            }
        });

    }

    private void SetupMessageAdmin(final MessageReceive messageReceive, MessageViewHolder messageViewHolder) {

        messageViewHolder.img_admin.setVisibility(View.VISIBLE);
        hideBannerAd(messageViewHolder);
        hideNativeAd(messageViewHolder, 0);
        messageViewHolder.base.setVisibility(View.VISIBLE);

        messageViewHolder.name.setText(messageReceive.getName_of());
        messageViewHolder.mesn.setText(messageReceive.getMesg());
        messageViewHolder.isOnlyEmoji(messageReceive.getMesg());

        Picasso.get().load(Uri.parse(messageReceive.getUrlProfilePic())).placeholder(R.drawable.ic_avatar_default).fit().into(messageViewHolder.prf_pic);

        Long codigoHora = messageReceive.getHora();


        try {

            String text = getTimingt(codigoHora);
            messageViewHolder.time.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
            messageViewHolder.time.setText("Incalculate");
            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
        }

          /*  Date d = new Date(codigoHora);

            SimpleDateFormat ss = new SimpleDateFormat("hh:mm:ss a");
*/


        messageViewHolder.backing.setCardBackgroundColor(mContext.getResources().getColor(R.color.admin_texts));
        messageViewHolder.mesn.setTextColor(mContext.getResources().getColor(R.color.graylight));
        messageViewHolder.time.setTextColor(mContext.getResources().getColor(R.color.graylight));
        messageViewHolder.name.setTextColor(mContext.getResources().getColor(R.color.yellow));


        Log.e(TAG, "SetupMessageAdmin: " + messageReceive.getType_mensaje());
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

        } else {
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
     //   DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss am");
        Date date = new Date(pastvalue);
// formattter
        SimpleDateFormat formatter= new SimpleDateFormat("hh:mm a");
        formatter.setTimeZone(TimeZone.getDefault());
// Pass date object
        return formatter.format(date );
        /*
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


        }*/
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
        private EmojiconTextView mesn;
        private EmojiconTextView mesnAnother;
        private CardView backing;
        private LinearLayout adView;
        private AdView adView2;
        private View base;
        private ImageView img_admin;
        private View adve;


        private View native_view;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            base = itemView.findViewById(R.id.baselay);
            img_admin = itemView.findViewById(R.id.admin_insigne);
            mesnAnother = itemView.findViewById(R.id.t_messg_another);
            prf_pic = itemView.findViewById(R.id.prof_ic);
            name = itemView.findViewById(R.id.t_name);
            time = itemView.findViewById(R.id.t_time);
            mesn = itemView.findViewById(R.id.t_messg);
            mesn.setUseSystemDefault(false);
            backing = itemView.findViewById(R.id.bacj);
            native_view = itemView.findViewById(R.id.native_banner_ad);
            adView = itemView.findViewById(R.id.layad);
            adve = itemView.findViewById(R.id.adlay);
            actionbtn = itemView.findViewById(R.id.actionmedia);
            card_back = itemView.findViewById(R.id.card_back);
            mediaPhoto = itemView.findViewById(R.id.media_f);
            mediaText = itemView.findViewById(R.id.t_mediainfo);


        }


        public void isOnlyEmoji(String texto) {
            mesn.setEmojiconSize(65);
            if (!isAlphanumeric(texto) && EmojiUtils.containsEmoji(texto)) {
                Log.e(TAG, "tiene emoji: " + texto);
                mesn.setTextSize(TypedValue.COMPLEX_UNIT_SP,68);
            }else if(EmojiUtils.containsEmoji(texto)){
             //   Log.e(TAG, "tiene emoji: " + texto);

                mesn.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

            }else{
                mesn.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            }

        }



        boolean isAlphanumeric(String str) {
            if (str.matches(".*[A-Za-z].*") || str.matches(".*[0-9].*") || str.matches("[A-Za-z0-9]*")) {
                //  System.out.println("Its Alphanumeric");
                return true;
            } else {
                //   System.out.println("Its NOT Alphanumeric");

                return false;
            }
        }


    }

    public void addMessage(MessageReceive msg) {
        messageOf.add(msg);
        notifyItemInserted(messageOf.size() - 1);
    }

    public void removeMessage(int pos) {
        messageOf.remove(pos);
        notifyItemRemoved(pos);
    }

    private void showBannerAd(MessageViewHolder messageViewHolder) {
        messageViewHolder.adView.setVisibility(View.VISIBLE);
        messageViewHolder.adve.setVisibility(View.VISIBLE);
    }

    private void hideBannerAd(MessageViewHolder messageViewHolder) {
        messageViewHolder.adView.setVisibility(View.GONE);
        messageViewHolder.adve.setVisibility(View.GONE);
    }

    private void hideNativeAd(MessageViewHolder messageViewHolder, int pos) {
        messageViewHolder.native_view.setVisibility(View.GONE);
        messageViewHolder.adve.setVisibility(View.GONE);
    }

    private void showNativeAd(MessageViewHolder messageViewHolder) {
        messageViewHolder.native_view.setVisibility(View.VISIBLE);
        messageViewHolder.adve.setVisibility(View.VISIBLE);
    }

    private void isMainUser(MessageViewHolder messageViewHolder){
        messageViewHolder.mesnAnother.setVisibility(View.GONE);

        LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) messageViewHolder.backing.getLayoutParams();

        l.gravity = Gravity.END;

        messageViewHolder.backing.setLayoutParams(l);
        messageViewHolder.mesn.setVisibility(View.GONE);
        messageViewHolder.prf_pic.setVisibility(View.GONE);
        messageViewHolder.name.setVisibility(View.GONE);
        messageViewHolder.mesn.setVisibility(View.VISIBLE);
    }

    private void NoMain(MessageViewHolder messageViewHolder){
        messageViewHolder.mesnAnother.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) messageViewHolder.backing.getLayoutParams();

        l.gravity = Gravity.START;

        messageViewHolder.backing.setLayoutParams(l);

        messageViewHolder.name.setVisibility(View.VISIBLE);
        messageViewHolder.mesn.setVisibility(View.VISIBLE);
        messageViewHolder.prf_pic.setVisibility(View.VISIBLE);
        messageViewHolder.mesn.setVisibility(View.GONE);
    }

    private void setupNormalMessage(MessageViewHolder messageViewHolder, final int i){



        final MessageReceive mf = messageOf.get(i);
        if (isAdminUser) {
            //   Log.e(TAG, "onBindViewHolder: si es admin");
            messageViewHolder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickingMessage(mf.getName_of());
                }
            });
        } else {
            messageViewHolder.backing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Log.e(TAG, "onClick: clicked" );
                    if(!mf.getSnap().equals(nk.getIdentifier())) {
                        if (ChangeFragment.checkAvailibity(mf.getSnap())) {
                            Utils.createPopUpContact(mContext, mf,new Utils.SelectionListener() {
                                @Override
                                public void onConfirm() {
                                    ChanInfo chanInfo = new ChanInfo();

                                    chanInfo.identifierChannel = nk.getIdentifier() + "-chat-" + mf.getSnap();
                                    chanInfo.channelName = "Chat " + nk.getNamef() + " & " + mf.getName_of();
                                    // nk.setLang_chat();
                                    NamkoFragment.chanInfo = chanInfo;
                                    nk.setLang_chat(chanInfo.identifierChannel);

                                    ChangeFragment.processNewChan(chanInfo);

                                    fragmentManager.beginTransaction().replace(nk.ContainerId, nk).commitAllowingStateLoss();


                                }

                                @Override
                                public void onCancel() {

                                }

                                @Override
                                public void OnBanUser() {
                                    super.OnBanUser();
                                    Utils.banThis(mf.getSnap());
                                   removeMessage(i);
                                }
                            });

                        } else {

                            if (!ChangeFragment.checkIsSameChan(nk.getIdentifier())) {
                                goChatPrivate(mf, i);
                            }



                        }
                    }
                }
            });
        }

        if(isBanned(mf.getSnap())){
            messageViewHolder.itemView.setVisibility(View.GONE);
        }


        hideBannerAd(messageViewHolder);
        hideNativeAd(messageViewHolder, i);
        messageViewHolder.base.setVisibility(View.VISIBLE);



        messageViewHolder.isOnlyEmoji(messageOf.get(i).getMesg());



        Long codigoHora = messageOf.get(i).getHora();


        try {

            String text = getTimingt(codigoHora);
            messageViewHolder.time.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
            messageViewHolder.time.setText("Incalculate");
            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
        }

        if(isMain(i)){
            messageViewHolder.mesn.setText(messageOf.get(i).getMesg());
            isMainUser(messageViewHolder);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(1000)
                    .playOn(messageViewHolder.base);
        }else{
            YoYo.with(Techniques.SlideInRight)
                    .duration(1000)
                    .playOn(messageViewHolder.base);
            NoMain(messageViewHolder);
            messageViewHolder.mesnAnother.setText(messageOf.get(i).getMesg());
            Picasso.get().load(Uri.parse(messageOf.get(i).getUrlProfilePic())).placeholder(R.drawable.ic_avatar_default).fit().into(messageViewHolder.prf_pic);
            messageViewHolder.name.setText(messageOf.get(i).getName_of());
        }

          /*  Date d = new Date(codigoHora);

            SimpleDateFormat ss = new SimpleDateFormat("hh:mm:ss a");
*/


        if (isMain(i) && card_backcolorAssigned) {
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

        } else {
            messageViewHolder.mediaText.setVisibility(View.GONE);
            messageViewHolder.mediaPhoto.setVisibility(View.GONE);
            messageViewHolder.actionbtn.setVisibility(View.GONE);
            messageViewHolder.card_back.setVisibility(View.GONE);
        }
    }

    public boolean isMain(int pos){

        Log.e(TAG, "isMain: "+messageOf.get(pos).getSnap()+" VERDADERO: "+idMain );

       return messageOf.get(pos).getSnap().equals(idMain);
    }
}
