package com.app.chat;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.app.chat.adapter.ChannelAdapter;
import com.app.chat.model.ChanInfo;
import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeFragment extends Fragment {


    public static boolean checkAvailibity(String snap) {
        for(int i=0; i < getListChannels().size(); i++) {
            ChanInfo chanInfo = getListChannels().get(i);
            String[] parts = chanInfo.identifierChannel.split("-chat-");
            for (int o = 0; o < parts.length; o++) {

                if (parts[o].equals(snap)) {
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean checkIsSameChan(String snap) {

            ChanInfo chanInfo = NamkoFragment.chanInfo;
            String[] parts = chanInfo.identifierChannel.split("-chat-");
            for (int o = 0; o < parts.length; o++) {

                if (parts[o].equals(snap)) {
                //    Log.e("MAIN", "checkIsSameChan: "+chanInfo.identifierChannel );
                    return true;
                }
            }

        return false;
    }

    public static ChanInfo getChanInf(String snap)
    {
        for(int i=0; i < getListChannels().size(); i++) {
            ChanInfo chanInfo = getListChannels().get(i);
            String[] parts = chanInfo.identifierChannel.split("-chat-");
            for (int o = 0; o < parts.length; o++) {

                if (parts[o].equals(snap)) {
                    return chanInfo;
                }
            }
        }
        return null;
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.LEFT, enter, 1100);
        } else {
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 1100);
        }
    }

    public void setNamkoFragment(NamkoFragment namkoFragment) {
        this.namkoFragment = namkoFragment;
    }

    private NamkoFragment namkoFragment;



    public ChangeFragment() {
        // Required empty public constructor
    }

    public static void processNewChan(ChanInfo chanInfo){
        if(getListChannels().size() < 1){
            getListChannels().add(chanInfo);
        }else {
boolean haveChan = false;
            for (int i = 0; i < getListChannels().size(); i++) {
               //  Log.e("MAIN", "processNewChan: "+getListChannels().get(i).identifierChannel );
                if (getListChannels().get(i).identifierChannel.equals(chanInfo.identifierChannel)) {
                   haveChan = true;
                }
            }
          //  Log.e("MAIN", "processNewChan: "+chanInfo.identifierChannel + " "+haveChan  + " size "+getListChannels().size());
            if(!haveChan){
                getListChannels().add(chanInfo);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change, container, false);
    }

    public static ArrayList<ChanInfo> getListChannels() {
        return listChannels;
    }

    public static void setListChannels(ArrayList<ChanInfo> listChannels) {
        ChangeFragment.listChannels = listChannels;
    }

    private static ArrayList<ChanInfo> listChannels = new ArrayList<>();



    private RecyclerView recyclerView;
    public String selectedChannel = "";
    private ChannelAdapter channelAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rec_channels);

        channelAdapter = new ChannelAdapter(getActivity(), new onChangeChannelListener() {
            @Override
            public void onChange(ChanInfo id) {
                //FragmentTransaction transaction = getFragmentManager().beginTransaction();

                NamkoFragment.chanInfo = id;
                namkoFragment.setLang_chat(id.identifierChannel);

                getFragmentManager().popBackStack();
                // transaction.replace(namkoFragment.ContainerId, namkoFragment).commitAllowingStateLoss();

            }
        });

        channelAdapter.setSelected(selectedChannel);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(channelAdapter);

    }



    public interface onChangeChannelListener{
        void onChange(ChanInfo id);
    }
}
