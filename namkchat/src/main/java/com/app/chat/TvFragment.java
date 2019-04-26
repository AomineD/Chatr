package com.app.chat;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.chat.adapter.MediaItemsAdapter;
import com.app.chat.model.MediaItem;
import com.app.chat.utils.ApiM3uGet;
import com.app.chat.utils.DonotTouch;
import com.app.chat.utils.PopUpSelect;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment implements ApiM3uGet.M3uResponse {


    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_tv, container, false);


        setView(v);

        Config();

        return v;
    }



    // ===================================== DEL TV =================================================== //




    public void openM3uView(String urr, String name){
        url_getter = urr;
        name_list = name;

      //  GetDataFromUrl();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static String key_b = "sdasdfw";
    private RecyclerView list_recy;
    private static String url_getter;
    private static String name_list;
    private int page = 1;
    private boolean keeploading = true;
    private ArrayList<MediaItem> mediaItems = new ArrayList<>();
    private MediaItemsAdapter adapter;
    private boolean reload_success;
    private RelativeLayout relative_error;


    private ImageView Search;
    private EditText buscador;

    private boolean isSearching;

    private SwipeRefreshLayout refreshLayout;
    private boolean isUser = true;


    private void setView(View view){

        relative_error = view.findViewById(R.id.error_connect);


        ImageView mm = view.findViewById(R.id.back);
        ChangeBackground(DonotTouch.GetRandom(), mm);
        refreshLayout = view.findViewById(R.id.refresh_ly);


        list_recy = view.findViewById(R.id.list_m3u);

        Search = view.findViewById(R.id.btn_search);
        buscador = view.findViewById(R.id.searchb);
    }


    public static boolean beginStart = false;


    private void Config() {
     //   setContentView(R.layout.activity_view_list);
        SetupSearchs();
        //     MainActivity.opened = false;

        // AdView banner = findViewById(R.id.banner_container);


        //   banner.loadAd(new AdRequest.Builder().build());


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(beginStart)
                getDataFromZero();
else
    refreshLayout.setRefreshing(false);
            }
        });




        SetupList();
if(beginStart)
        GetDataFromUrl();
else {
 refreshLayout.setVisibility(View.GONE);
    relative_error.setVisibility(View.VISIBLE);
}
    }

    private void SetupSearchs() {


        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSearching) {
                    OpenSearch();
                }else{
                    CloseSearch();
                }
            }
        });
    }

    private void getDataFromZero() {
        CloseSearch();
        //  Log.e("MAIN", "getDataFromZero: CARGO DESDE ZERO");
        keeploading = true;
        // actual = Configs.FRECUENCIA_NATIVO_BANNER - 1;
        mediaItems.clear();

        GetDataFromUrl();
    }


    // ================================ Busqueda ===================================== //

    private void OpenSearch() {
        buscador.setVisibility(View.VISIBLE);
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filterList(s.toString());


            }
        });
        Search.setImageDrawable(getResources().getDrawable(R.drawable.cancel_icon));
        buscador.performClick();
        buscador.callOnClick();

        DonotTouch.showKeyboard(getActivity());

        isSearching = true;
    }

    private void CloseSearch() {
        buscador.setVisibility(View.GONE);
        buscador.setText("");
        DonotTouch.hideKeyboard(getActivity());
        if(mediaItems.size() > 0)
        filterList("");
        isSearching = false;
        Search.setImageDrawable(getResources().getDrawable(R.drawable.search_icon));
    }

    private void filterList(String search) {
        ArrayList<MediaItem> items = new ArrayList<>();

        for(int i=0; i < mediaItems.size(); i++){
            if(mediaItems.get(i).getName().toLowerCase().contains(search.toLowerCase())){
                items.add(mediaItems.get(i));
            }
        }

        adapter.filterListRefresh(items);

    }

    // ===================================================================================== //

    private void SetupList() {


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new MediaItemsAdapter(getContext(), mediaItems, isUser,getActivity());
        list_recy.setLayoutManager(gridLayoutManager);
        list_recy.setAdapter(adapter);

    }


    private void GetDataFromUrl() {
        if(beginStart && !url_getter.isEmpty()) {
            //    Log.e("MAIN", "GetDataFromUrl: Cargo");
         //   adapter = new MediaItemsAdapter(getContext(), mediaItems, isUser,getActivity());
            ApiM3uGet Api = new ApiM3uGet(getContext(), url_getter, this);
            Api.Execute();
        }else{
            buscador.setVisibility(View.GONE);
        }
    }






    @Override
    public void OnLoad(ArrayList<MediaItem> items) {

        if(reload_success){
            DonotTouch.ShowSuccess(getActivity(), "");
        }

        relative_error.setVisibility(View.GONE);
        mediaItems.addAll(items);
        list_recy.getAdapter().notifyDataSetChanged();
        refreshLayout.setRefreshing(false);

        //  Log.e("MAIN", "OnLoad: ITEM COUNT = "+list_recy.getAdapter().getItemCount() );
    }


    @Override
    public void FailedLoad(String error) {

        refreshLayout.setRefreshing(false);
        //   String[] fit = error.split(":");

        reload_success = true;
        relative_error.setVisibility(View.VISIBLE);

        DonotTouch.ShowErrorLoad(getActivity(), getString(R.string.error_connect));
    }

    private void ChangeBackground(int id, View cardView){
        switch (id){
            case 0:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_1));
                break;
            case 1:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_2));
                break;
            case 2:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_3));
                break;
            case 3:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_4));
                break;
            case 4:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_5));
                break;
            case 5:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_6));
                break;
            case 6:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_7));
                break;
            case 7:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_8));
                break;
            case 8:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_9));
                break;
            case 9:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_10));
                break;
            case 10:
                cardView.setBackground(getResources().getDrawable(R.drawable.bg_item_11));
                break;
        }

    }


    public void setAd(PopUpSelect.ClickAd a){
PopUpSelect.clicking = a;

    }

    public void playMedia(){
        if(PopUpSelect.intr != null)
        PopUpSelect.intr.Now();
    }

}
