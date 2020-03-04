package com.app.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.chat.ChangeFragment;
import com.app.chat.R;
import com.app.chat.model.ChanInfo;
import com.google.firebase.database.core.view.Change;

import java.util.ArrayList;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {

    private Context m;
    private String selectedChannel;


    public ChannelAdapter(Context c, ChangeFragment.onChangeChannelListener lls){
this.m = c;
this.listener = lls;
}

private ChangeFragment.onChangeChannelListener listener;

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View v = LayoutInflater.from(m).inflate(R.layout.channel_item, parent, false);

        return new ChannelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
final String needed = ChangeFragment.getListChannels().get(position).identifierChannel;
        final String nan = ChangeFragment.getListChannels().get(position).channelName;
        final ChanInfo inf = ChangeFragment.getListChannels().get(position);

holder.channel_name.setText(nan);

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(!needed.equals(selectedChannel)) {
            listener.onChange(inf);
selectedChannel = needed;
notifyDataSetChanged();
        }
    }
});

if(needed.equals(selectedChannel)){
    holder.relativeLayout.setBackground(m.getResources().getDrawable(R.drawable.border_select));
}else{
    holder.relativeLayout.setBackground(m.getResources().getDrawable(R.drawable.invisibol));
}

    }

    @Override
    public int getItemCount() {
        return ChangeFragment.getListChannels().size();
    }

    public void setSelected(String selectedChannel) {
        this.selectedChannel = selectedChannel;
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder{

private TextView channel_name;
private RelativeLayout relativeLayout;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.selbor);
            channel_name = itemView.findViewById(R.id.text_channel);
        }
    }

}
