package com.app.chat;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DialogYouWant extends AlertDialog {
    protected DialogYouWant(@NonNull Context context) {
        super(context);
    }

    public interface OnClickYORNOT{
        void OnClickYes();

        void OnClickNO();
    }

    private TextView Title;
    private ImageView d;

    public void setClicking(OnClickYORNOT clicking) {
        this.clicking = clicking;
    }

    private OnClickYORNOT clicking;



    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    private String url_img;
    private String tit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_you_want);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setBackgroundDrawable(ActivityCompat.getDrawable(getContext(), R.color.tran));


        d = findViewById(R.id.img_send);
        Title = findViewById(R.id.titledialog);

        if(!tit.isEmpty()){
            Title.setText(tit);

            Picasso.get().load(Uri.parse(url_img)).into(d);

TextView si = findViewById(R.id.accept_send);

TextView no = findViewById(R.id.cancelButton);


si.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        clicking.OnClickYes();
    }
});

no.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        clicking.OnClickNO();
    }
});


        }else{
            dismiss();
        }

    }
}
