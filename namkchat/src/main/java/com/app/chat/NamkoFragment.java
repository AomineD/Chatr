package com.app.chat;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.app.chat.adapter.MessageAdapter;
import com.app.chat.model.BanModel;
import com.app.chat.model.Message;
import com.app.chat.model.MessageReceive;
import com.app.chat.model.MessageSend;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.labo.kaji.fragmentanimations.PushPullAnimation;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NamkoFragment extends Fragment {

    public void setWithAds(boolean withAds, String adbanner) {
        this.withAds = withAds;
        this.id_banner = adbanner;
    }

    private boolean withAds;
    private String id_banner;
    private long diasMax = 5;
    private int saizMax = 1000;

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

    private String identifier;

    public void setIdentifier(String j){
        identifier = j;
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

        if(database != null){
            database = null;
            messageArrayList.clear();
            adapter.notifyDataSetChanged();
        }

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(lang_chat);
        referenceBan = database.getReference("bans");

        if(adapter == null){
            adapter = new MessageAdapter(getActivity(), messageArrayList, isAdminSender);
        }
        SetupRef();

        if(lang_c != null){
            lang_c.setText(lang_chat);
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


    private DatabaseReference referenceBan;
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

    }

    public void setNameP(String n, onBanUser onBanUser){
        this.nameF = n;
        this.OnBanUserList = onBanUser;
    }


    public boolean isAdminSender = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_namko, container, false);


lang_c = v.findViewById(R.id.channel);
                list_mesg = v.findViewById(R.id.rec_chat);
        message = v.findViewById(R.id.message_edt);
        profile_name = v.findViewById(R.id.profile_nam);
        profile_pic = v.findViewById(R.id.image_prf);

        profile_name.setText(nameF);
        Picasso.get().load(Uri.parse(urrPhoto)).placeholder(R.drawable.ic_avatar_default).fit().into(profile_pic);

        send_msg = v.findViewById(R.id.send_btn);



        if(isDebug)
        Log.e("MAIN", "onCreateView: CHAT => "+lang_chat+" BASE DE DATOS => "+databaseReference.getRef().toString());
        adapter = new MessageAdapter(getContext(), messageArrayList, isAdminSender);

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
                        referenceBan.push().setValue(model);
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

        lang_c.setText(lang_chat);

getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);




SetupadapteR();


if(getContext() != null && withAds) {
    LinearLayout d = v.findViewById(R.id.containads);

    AdView ad = new AdView(getContext(), id_banner, AdSize.BANNER_HEIGHT_50);

    ad.loadAd();

    d.addView(ad);
}


        return v;
    }

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



    private onBanUser OnBanUserList;
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
    private EditText message;
    private CircleImageView profile_pic;
    private TextView profile_name;
    private FloatingActionButton send_msg;
    private MessageAdapter adapter;


    private boolean isDebug = false;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<MessageReceive> messageArrayList = new ArrayList<>();

public void setDebg (boolean ss){
    isDebug = ss;
}
public String keyactual;
private int fr;
public static final int mansi = 3;


    // =========================== CONFIG REFERENCE ========================== //
    private void SetupRef() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MessageReceive m = dataSnapshot.getValue(MessageReceive.class);


                if (m != null && isRudness(m.getMesg())) {

                   m.setMesg(transform(m.getMesg()));

                }


                if(m == null){
                    return;
                }else {
                    if(getTimeInInteger(m.getHora())){
                        dataSnapshot.getRef().removeValue();
                    }
                }



                if(dataSnapshot.getKey().equals(keyactual)){
                    return;
                }

                keyactual = dataSnapshot.getKey();


                if(isDebug){
                    Log.e("MAIN", "onChildAdded: m es null = "+dataSnapshot.getKey()+(" mensaje  = ") + (m!=null ? m.getMesg(): "ES NULL PAPA"));
                }


                if(withAds) {
                    if (fr >= mansi) {
                        MessageReceive mw = new MessageReceive();
                        mw.isAd = true;
                        adapter.addMessage(mw);
                        fr = 0;
                    } else {
                        fr++;
                    }
                }



                if(!getTimeInInteger(m.getHora()) && adapter != null) {
                    Log.e("MAIN", "onChildAdded: "+adapter.getItemCount());
                    adapter.addMessage(m);
                }else if(adapter == null){
                    Log.e("MAIN", "onChildAdded: 0pp" );
                    messageArrayList.add(m);
                }

                if(messageArrayList.size() >= saizMax){
                    if(isLongTo(m.getHora(), 1)){
                        dataSnapshot.getRef().removeValue();
                    }
                }
   // Log.e("MAIN", "onChildAdded: "+messageArrayList.size());


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if(adapter != null)
adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


referenceBan.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        BanModel model = dataSnapshot.getValue(BanModel.class);

        if(model != null){
            nameBanneds.add(model.name);
        }

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }


    private ArrayList<String> nameBanneds = new ArrayList<>();
    private void SendMessage(boolean isMedia) {
        if (database != null && (!message.getText().toString().isEmpty() || isMedia)) {
            if(nameBanneds.contains(nameF)){
                Toast.makeText(getContext(), "Has sido baneado por algun admin", Toast.LENGTH_SHORT).show();
                return;
            }
            MessageSend mss = new MessageSend();

            mss.setIsAdmin(isAdminSender ? "true" : "false");
            mss.setHora(ServerValue.TIMESTAMP);
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
            mss.setName_of(nameF);
            mss.setUrlProfilePic(urrPhoto);
            message.setText("");
            databaseReference.push().setValue(mss);
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


    private boolean isLongTo(Long pastvalue, long max) {

        // long pastvalue = Long.parseLong(codigoHora);
        // Default time zone.
        DateTime zulu = DateTime.now(DateTimeZone.UTC);


        long dias = TimeUnit.MILLISECONDS.toDays(zulu.toDate().getTime() - pastvalue);


        return dias > max;




    }


}
