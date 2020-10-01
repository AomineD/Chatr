package com.app.chat;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.chat.model.ChanInfo;
import com.app.chat.utils.Utils;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.dagf.admobnativeloader.EasyFAN;
import com.facebook.ads.AdSettings;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.app.chat.adapter.MessageAdapter;
import com.app.chat.model.BanModel;
import com.app.chat.model.MessageReceive;
import com.app.chat.model.MessageSend;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.emoji.Emojicon;
import okhttp3.internal.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class NamkoFragment extends Fragment {

    private boolean isAutoUser;

    public void setWithAds(boolean withAds, boolean isnv, String adbanner) {
        this.withAds = withAds;
        this.id_banner = adbanner;
        this.isNativeAd = isnv;
    }


    private boolean isNativeAd;
    private boolean withAds;
    private String id_banner;

    private void checkMyPrivateChats(){
FirebaseFirestore.getInstance().collection(key_chan).addSnapshotListener(new EventListener<QuerySnapshot>() {
    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

        if(value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {

                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    String thiskey = documentChange.getDocument().getId();
                    String[] parts = documentChange.getDocument().getId().split("-chat-");
                    for (int i = 0; i < parts.length; i++) {

                        if (parts[i].equals(getIdentifier())) {
                            configData();
                        }
                    }
                }
            }
        }
    }
});

    }

    private void configData() {

FirebaseFirestore.getInstance().collection(key_chan).addSnapshotListener(new EventListener<QuerySnapshot>() {
    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        if(value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChanInfo  chanInfo = documentChange.getDocument().toObject(ChanInfo.class);
                    ChangeFragment.processNewChan(chanInfo);
                }
            }
        }

    }
});

    }

    public void setId_native_banner(ArrayList<String> id_native_banner) {
        this.id_native_banner = id_native_banner;
        sizeIds = id_native_banner.size();
    }

    private ArrayList<String> id_native_banner = new ArrayList<>();
    public static int sizeIds = 0;

    private long diasMax = 4;
    private int saizMax = 50;

    /**
     Si pasa de estos días sera borrado el mensaje
      **/
public void setDiasMaximos(long diasmax){
    this.diasMax = diasmax;
}


public void setMaxMessage(int max){
    this.saizMax = max;
}

    public NamkoFragment() {
        // Required empty public constructor
    }

    public int[] cc = new int[6];

    public void setColors(int cardMain, int cardanother, int cardBackaction, int actioncolor, int textcolor, int textcoloran){

            cc[0] = cardBackaction;
            cc[1] = cardanother;
            cc[2] = cardMain;
            cc[3] = actioncolor;
            cc[4] = textcolor;
            cc[5] = textcoloran;
            colorAssigned = true;

    }

    public String identifier;
    public int ContainerId;

    public String getIdentifier(){
        return identifier;
    }
    public String getNamef(){
        return nameF;
    }

    public void setIdentifier(String j, int container_id){
        identifier = j;
    this.ContainerId = container_id;
    }

    public void setIdentifier(boolean ist, int container_id){
        isAutoUser = ist;
        this.ContainerId = container_id;
    }
    public interface OnClickingSend{
        void OnSend();
    }

    public void setListenerMedia(OnClickmedia listenerMedia) {
        ListenerMedia = listenerMedia;
    }

    private OnClickmedia ListenerMedia;



    private OnClickingSend clikS;

    public void setClickSendListener(OnClickingSend sendListener){
        clikS = sendListener;
    }

    private String nameF = "NO NAME";


    public void setLang_chat(String lang_chat) {
        this.lang_chat = lang_chat;

        if(adapter != null) {
            messageArrayList.clear();
            adapter.notifyDataSetChanged();
        }

        checkMyPrivateChats();


        if(adapter == null){
            adapter = new MessageAdapter(getActivity(), messageArrayList, isAdminSender);
        }

        adapter.isNativeAd = isNativeAd;

        SetupRef();

        if(lang_c != null && chanInfo == null){
            lang_c.setText(lang_chat);
        }else if(lang_c != null){
            lang_c.setText(chanInfo.channelName);
        }
    }

    private String url_background = "";

    public void setUrl_background(String url_background) {
        this.url_background = url_background;
    }

    public void setBackgdrawable(int backgdrawable) {
        this.backgdrawable = backgdrawable;
        if(getContext() != null && bk != null){
            SetupBackground();
        }
    }

    private int backgdrawable;

    private String lang_chat = "latino";
    private TextView lang_c;
    private ArrayList<String> blockeds = new ArrayList<>();

    private boolean isRudness(String mensj){

        String[] mpart = mensj.split(" ");

        for(int s=0; s < mpart.length; s++) {


        for(int i=0; i < blockeds.size(); i++){
            if (mpart[s].toLowerCase().equals(blockeds.get(i).toLowerCase())){
                return true;
            }
        }

        }

        return false;
    }

    private String transform(String mdef){
        String mensDef = "";
        String[] mpart = mdef.split(" ");
        for(int s=0; s < mpart.length; s++) {
            for (int i = 0; i < blockeds.size(); i++) {
              //  Log.e("MAIN", "transform: "+mdef+" es groseria: "+mpart[s].toLowerCase().equals(blockeds.get(i).toLowerCase()));
                if (mpart[s].toLowerCase().equals(blockeds.get(i).toLowerCase())){
                  //  Log.e("MAIN", "transform: "+mdef+" es groseria: "+mpart[s].toLowerCase().equals(blockeds.get(i).toLowerCase()));
                   int lenght = mpart[s].length();
                    for(int sc=0; sc < lenght; sc++){
                        if(sc == 0){
                            mpart[s] = "*";
                        }else{
                            mpart[s] = mpart[s]+"*";
                        }

                    }


                }
            }
            if(s > 0) {
                mensDef = mensDef + " " + mpart[s];
            }else{
                mensDef = mensDef+ mpart[s];
            }
        }

return mensDef;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new Shutup(getContext(), new Shutup.onLoadComplete() {
            @Override
            public void ReadyToGo(ArrayList<String> groseriasBlock) {
                blockeds.addAll(groseriasBlock);
                Log.e("MAIN", "ReadyToGo: "+blockeds.size());
            }

            @Override
            public void Failed(String erno) {
                Log.e("MAIN", "Failed: "+erno);
            }
        });
        if(getContext() != null && isAutoUser) {
            SharedPreferences preferences = getContext().getSharedPreferences("user_chat", Context.MODE_PRIVATE);
            Utils.setPreferences(preferences);
            if(Utils.isUserSaved()){
                String id = Utils.getDataUser()[1];
                String usern = Utils.getDataUser()[0];
                nameF = usern;
                identifier = id;
            }else{
UserActivity.namkoFragment = this;
startActivity(new Intent(getContext(), UserActivity.class));
                Animatoo.animateZoom(getContext());
            }
        }

        if(getContext() != null)
       FirebaseApp.initializeApp(getContext());

    }

    public void setNameP(String n, onBanUser onBanUser, onClickBackListener olikc) {
        this.nameF = n;
        if (profile_name != null)
            profile_name.setText(nameF);
        this.OnBanUserList = onBanUser;
    this.clickBackListener = olikc;
    }


    public boolean isAdminSender = false;
    public interface onClickBackListener{
        void onClicked();
    }
    public onClickBackListener clickBackListener;
    public void shownow(){
        if(getActivity() != null)
        Utils.showCase(getActivity(), openEmoji, vs.findViewById(R.id.change_channel));
    }
    private View vs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_namko, container, false);

vs = v;
        v.findViewById(R.id.change_channel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getFragmentManager() != null && getFragmentManager().getFragments().size() < 2) {
                    ChangeFragment changeFragment = new ChangeFragment();

                    changeFragment.selectedChannel = lang_chat;
                    changeFragment.setNamkoFragment(NamkoFragment.this);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();


                    transaction.add(ContainerId, changeFragment).addToBackStack(null).commitAllowingStateLoss();
                }
            }
        });



lang_c = v.findViewById(R.id.channel);
                list_mesg = v.findViewById(R.id.rec_chat);
        message = v.findViewById(R.id.message_edt);
        profile_name = v.findViewById(R.id.profile_nam);
        profile_pic = v.findViewById(R.id.image_prf);
        openEmoji = v.findViewById(R.id.emoji_btn);
        back_btn = v.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickBackListener != null)
             clickBackListener.onClicked();
            }
        });

        if(getActivity()!=null && !isAutoUser)
        Utils.showCase(getActivity(), openEmoji, v.findViewById(R.id.change_channel));
else if(getActivity() != null && isAutoUser && Utils.isUserSaved()){
            Utils.showCase(getActivity(), openEmoji, v.findViewById(R.id.change_channel));
        }
        final EmojIconActions emojicon = new EmojIconActions(getActivity(), v, message, openEmoji);

        emojicon.setUseSystemEmoji(false);

      //


        emojicon.ShowEmojIcon();

        profile_name.setText(nameF);
        Picasso.get().load(Uri.parse(urrPhoto)).placeholder(R.drawable.ic_avatar_default).fit().into(profile_pic);

        send_msg = v.findViewById(R.id.send_btn);

//        AdSettings.setDebugBuild(true);


       // Log.e("MAIN", "onCreateView: CHAT => "+lang_chat+" BASE DE DATOS => "+databaseReference.getRef().toString());
        adapter = new MessageAdapter(getActivity(), messageArrayList, isAdminSender);

        adapter.setFragmentInfo(this,getFragmentManager());

        adapter.isNativeAd = isNativeAd;

        adapter.setOnClickMessage(new MessageAdapter.onClickListener() {
            @Override
            public void clickingMessage(final String name) {
                final BanModel model = new BanModel();

                model.name = name;
              //  Toast.makeText(getContext(), "si "+name, Toast.LENGTH_SHORT).show();

                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle("Banear usuario").setMessage("¿Seguro que quieres banear a "+name+" del chat/sorteos?").setCancelable(false).create();

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si, banear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore.getInstance().collection("bans").add(model);
                        Toast.makeText(getContext(), "Baneado con éxito", Toast.LENGTH_SHORT).show();
                        OnBanUserList.whenBanUser(name);
                    }
                });

                alertDialog.show();
            }
        });


        LinearLayoutManager ll = new LinearLayoutManager(getContext());

        list_mesg.setLayoutManager(ll);
        list_mesg.setAdapter(adapter);

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clikS != null){
                    clikS.OnSend();
                }
                SendMessage(false);
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                list_mesg.scrollToPosition(adapter.getItemCount() - 1);
            }
        });



        bk = v.findViewById(R.id.backgr);

        SetupBackground();

        if(lang_c != null && chanInfo == null){
            lang_c.setText(lang_chat);
        }else if(lang_c != null){
            lang_c.setText(chanInfo.channelName);
        }

getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);




SetupadapteR();


if(getContext() != null && withAds) {
    LinearLayout d = v.findViewById(R.id.containads);

    AdView ad = new AdView(getContext(), id_banner, AdSize.BANNER_HEIGHT_50);

    ad.loadAd();

    d.addView(ad);


    //Log.e("MAIN", "onCreateView: "+id_native_banner.size() );
    if(id_native_banner != null && id_native_banner.size() > 0) {
       // AdSettings.setDebugBuild(true);
        fanfic = new EasyFAN(getActivity(), id_native_banner);

        fanfic.setInterface(new EasyFAN.OnNativeLoadInterface() {
            @Override
            public void OnSuccess(int pos) {
                Log.e("MAIN", "OnSuccess: cargado" );
            }

            @Override
            public void OnFail(String ss, int pos) {
                Log.e("MAIN", "OnFail: "+ss );
            }
        });

        fanfic.loadBannerAds();


    }

}


        return v;
    }

    public static EasyFAN fanfic;

    private boolean colorAssigned;
    private void SetupadapteR() {

        if(ListenerMedia != null)
            adapter.setClickmedia(ListenerMedia);

        if(colorAssigned) {
            //Log.e("MAIN", "SetupadapteR: "+cc[1]);
            adapter.setAnother_cards(cc[1]);
            adapter.setCard_main(cc[2]);

            adapter.setCard_backcolor(cc[0]);

            adapter.setActiomtextcolor(cc[3]);
            adapter.setTextColorM(cc[4]);
            adapter.setTextColorMa(cc[5]);
        }

        if(withAds){
            adapter.setIdBanner(id_banner);
        }

        adapter.setIdMain(identifier);
    }



    public onBanUser OnBanUserList;
    public interface onBanUser{
        void whenBanUser(String username);
    }
    ImageView bk;

    private void SetupBackground() {
        if(backgdrawable != 0){
            Picasso.get().load(backgdrawable).fit().into(bk);
        }else if(!url_background.isEmpty()){
            Picasso.get().load(Uri.parse(url_background)).fit().into(bk);
        }
    }


    private RecyclerView list_mesg;
    private EmojiconEditText message;
    private ImageView openEmoji;
    private CircleImageView profile_pic;
    private TextView profile_name;
    public ImageView back_btn;
    private FloatingActionButton send_msg;
    private MessageAdapter adapter;


    private boolean isDebug = false;
    private ArrayList<MessageReceive> messageArrayList = new ArrayList<>();

public void setDebg (boolean ss){
    isDebug = ss;
}
public String keyactual;
private int fr;
public static final int mansi = 3;


    // =========================== CONFIG REFERENCE ========================== //
    private String key_chan = "channelInfo";
    private void SetupRef() {

        FirebaseFirestore.getInstance().collection(key_chan).document(lang_chat).collection("messages").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null) {
                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            MessageReceive m = documentChange.getDocument().toObject(MessageReceive.class);

                             m.setMesg(transform(m.getMesg()));

                                if (getTimeInInteger(m.getHora())) {
                                    FirebaseFirestore.getInstance().collection(key_chan).document(lang_chat).collection("messages").document(documentChange.getDocument().getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.e("MAIN", "onSuccess: borrado " );
                                        }
                                    });
                                }



                            if (documentChange.getDocument().getId().equals(keyactual)) {
                                return;
                            }

                            keyactual = documentChange.getDocument().getId();


                            if (withAds) {
                                if (fr >= mansi) {
                                    MessageReceive mw = new MessageReceive();
                                    mw.isAd = true;
                                    adapter.addMessage(mw);
                                    fr = 0;
                                } else {
                                    fr++;
                                }
                            }


                            if (!getTimeInInteger(m.getHora()) && adapter != null) {
                                adapter.addMessage(m);
                            } else if (adapter == null) {
                                messageArrayList.add(m);
                            }

                            if (messageArrayList.size() >= saizMax) {
                                if (isLongTo(m.getHora())) {
                                    FirebaseFirestore.getInstance().collection(key_chan).document(lang_chat).collection("messages").document(documentChange.getDocument().getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.e("MAIN", "onSuccess: borrado " );
                                        }
                                    });
                                }
                            }
                        }else if(documentChange.getType() == DocumentChange.Type.REMOVED){
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                // Log.e("MAIN", "onChildAdded: "+messageArrayList.size());
            }
        });

      FirebaseFirestore.getInstance().collection(key_chan).addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

              if(value != null){
                  for(DocumentChange documentChange : value.getDocumentChanges()){

                      if(documentChange.getType() == DocumentChange.Type.ADDED){

                          ChanInfo chanInfo = documentChange.getDocument().toObject(ChanInfo.class);
                          ChangeFragment.processNewChan(chanInfo);
                          if(chanInfo.identifierChannel.equals(lang_chat) && chanInfo != NamkoFragment.chanInfo){

                              NamkoFragment.chanInfo = chanInfo;

                          }
                      }

                  }

              }

          }
      });



FirebaseFirestore.getInstance().collection("bans").addSnapshotListener(new EventListener<QuerySnapshot>() {
    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        if(value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {

                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    BanModel model = documentChange.getDocument().toObject(BanModel.class);

                        nameBanneds.add(model.name);
                }
            }
        }
    }
});

    }

    public static ChanInfo chanInfo;


    private ArrayList<String> nameBanneds = new ArrayList<>();
    private void SendMessage(boolean isMedia) {
        if ((!message.getText().toString().isEmpty() || isMedia)) {
            if(nameBanneds.contains(nameF)){
                Toast.makeText(getContext(), "Has sido baneado por algun admin", Toast.LENGTH_SHORT).show();
                return;
            }
            MessageSend mss = new MessageSend();

            mss.setIsAdmin(isAdminSender ? "true" : "false");

            mss.setHora(Timestamp.now().toDate().getTime());
if(!isMedia) {
    String men = message.getText().toString();
/*
if(isRudness(men)){
    men = transform(men);
}
*/
    mss.setType_mensaje("0");
    mss.setMesg(men);
}else{

    mss.setMesg(mediamess);
    mss.setDescmedia(mediainfo);
    mss.setUrl_img_media(url_media);
    mss.setAction(actionn);
    mss.setDataToClick(cl);
    mss.setType_mensaje("1");
}
mss.setSnap(identifier);
if(messageArrayList.size() < 1 && chanInfo == null){
    chanInfo = new ChanInfo();
    chanInfo.channelName = lang_chat;
    chanInfo.identifierChannel = lang_chat;
    FirebaseFirestore.getInstance().collection(key_chan).document(lang_chat).set(chanInfo);
    ChangeFragment.processNewChan(chanInfo);
}else if(messageArrayList.size() < 1){
    FirebaseFirestore.getInstance().collection(key_chan).document(lang_chat).set(chanInfo);
   ChangeFragment.processNewChan(chanInfo);
}
            mss.setName_of(nameF);
            mss.setUrlProfilePic(urrPhoto);
            message.setText("");
            FirebaseFirestore.getInstance().collection(key_chan).document(lang_chat).collection("messages").add(mss);
            if (isDebug)
                Log.e("MAIN", "SendMessage: MENSAJE ENVIADO = " + mss);
            //adapter.addMessage(mss);
        }else if(message.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Mensaje vacío? oh no", Toast.LENGTH_SHORT).show();
        }
    }


    public void setUrrPhoto(String urrPhoto) {
        this.urrPhoto = urrPhoto;
    }

    private String urrPhoto = "";


public interface OnClickmedia{
    void onClickMedia(String data);
}

public void SendPersonalizedMessage(String titlediua, final String message, final String mediainfo, final String urlmedia, final String act,final String clickmedia){

    if(getActivity() != null) {
        final DialogYouWant dialogYouWant = new DialogYouWant(getActivity());

        dialogYouWant.setTit(titlediua);

        dialogYouWant.setUrl_img(urlmedia);
        dialogYouWant.setClicking(new DialogYouWant.OnClickYORNOT() {
            @Override
            public void OnClickYes() {
                mediamess = message;
                url_media = urlmedia;
                actionn = act;
                NamkoFragment.this.mediainfo = mediainfo;
                cl = clickmedia;

                SendMessage(true);
                dialogYouWant.dismiss();
            }

            @Override
            public void OnClickNO() {
                dialogYouWant.dismiss();
            }
        });

        dialogYouWant.show();
    }
}

private String mediamess;
private String url_media;
private String mediainfo;
private String actionn;
private String cl;




    private boolean getTimeInInteger(Long pastvalue) {

        // long pastvalue = Long.parseLong(codigoHora);
        // Default time zone.
        DateTime zulu = DateTime.now(DateTimeZone.UTC);


                long dias = TimeUnit.MILLISECONDS.toDays(zulu.toDate().getTime() - pastvalue);


                return dias >= diasMax;




    }


    private boolean isLongTo(Long pastvalue) {

        // long pastvalue = Long.parseLong(codigoHora);
        // Default time zone.
        DateTime zulu = DateTime.now(DateTimeZone.UTC);


        long dias = TimeUnit.MILLISECONDS.toDays(zulu.toDate().getTime() - pastvalue);


        return dias > 1;




    }


}
