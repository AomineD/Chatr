package com.app.chat.chat;

import android.view.View;
import android.widget.TextView;

import com.app.chat.R;
import com.app.chat.model.MessageReceive;
import com.app.chat.utils.Utils;
import com.wineberryhalley.bclassapp.BottomBaseShet;

public class ClickBottom extends BottomBaseShet {
    @Override
    public int layoutID() {
        return R.layout.bottom_click;
    }

    private TextView banuser, chat_with;
  private   Utils.SelectionListener selectionListener;

  private MessageReceive msg;
  public ClickBottom(Utils.SelectionListener l, MessageReceive messageReceive){
      this.selectionListener = l;
      this.msg = messageReceive;
  }

    @Override
    public void OnStart() {

        ((View)find(R.id.close_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListener.onCancel();
                dismissAllowingStateLoss();
            }
        });

        TextView chatw = find(R.id.chat_with);
        chatw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListener.onConfirm();
                dismissAllowingStateLoss();
            }
        });
        String frchatw = String.format(getString(R.string.chat_with), msg.getName_of());

        chatw.setText(frchatw);

        TextView chatban = find(R.id.ban_user);
        String frchatban = String.format(getString(R.string.ban_user), msg.getName_of());

        chatban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListener.OnBanUser();
                dismissAllowingStateLoss();
            }
        });

        chatban.setText(frchatban);
    }
}
