package com.app.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.app.chat.adapter.MediaItemsAdapter;
import com.app.chat.model.MediaItem;
import com.app.chat.utils.ApiM3uGet;
import com.app.chat.utils.DonotTouch;

import java.util.ArrayList;

/**
 * @author Diego Garcia
 */
public class ViewListActivity extends AppCompatActivity implements ApiM3uGet.M3uResponse {


    public static void openM3uView(Context u, String urr, String name){
        url_getter = urr;
        name_list = name;
        u.startActivity(new Intent(u, ViewListActivity.class));
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


    private Toolbar tolbar;


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
SetupSearchs();
   //     MainActivity.opened = false;

       // AdView banner = findViewById(R.id.banner_container);


     //   banner.loadAd(new AdRequest.Builder().build());
        relative_error = findViewById(R.id.error_connect);


        ImageView mm = findViewById(R.id.back);
        ChangeBackground(DonotTouch.GetRandom(), mm);
        refreshLayout = findViewById(R.id.refresh_ly);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromZero();
            }
        });

        tolbar = findViewById(R.id.toolbar);
list_recy = findViewById(R.id.list_m3u);


GetDataFromUrl();
SetupActionBar();
SetupList();
    }

    private void SetupSearchs() {
        Search = findViewById(R.id.btn_search);
        buscador = findViewById(R.id.searchb);

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
        tolbar.setTitle("");
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

        DonotTouch.showKeyboard(ViewListActivity.this);

        isSearching = true;
    }

    private void CloseSearch() {
        SetupActionBar();
        buscador.setVisibility(View.GONE);
        buscador.setText("");
        DonotTouch.hideKeyboard(ViewListActivity.this);
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


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        list_recy.setLayoutManager(gridLayoutManager);
        list_recy.setAdapter(adapter);

    }


    private void GetDataFromUrl() {
        if(!url_getter.isEmpty()) {
        //    Log.e("MAIN", "GetDataFromUrl: Cargo");
            adapter = new MediaItemsAdapter(this, mediaItems, isUser,this);
            ApiM3uGet Api = new ApiM3uGet(this, url_getter, this);
            Api.Execute();
        }
    }





    private void SetupActionBar(){
        if(name_list.length() > 17 && name_list.length() < 25){
            Log.e("MAIN", "SetupActionBar: LARGO");
         tolbar.setTitleTextAppearance(this, R.style.Toolbarsito);
        }else if(name_list.length() >= 25){
            tolbar.setTitleTextAppearance(this, R.style.Toolbarsito2);
        }

        tolbar.setTitle(name_list);
        tolbar.setTitleTextColor(getResources().getColor(R.color.white));


       tolbar.setNavigationIcon(R.drawable.arrow_back);
       tolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!isSearching) {
                   onBackPressed();
               }else{
                   CloseSearch();
               }
           }
       });

       if(getSupportActionBar() != null){
           setSupportActionBar(tolbar);


       }
    }

    @Override
    public void OnLoad(ArrayList<MediaItem> items) {

        if(reload_success){
            DonotTouch.ShowSuccess(this, "");
        }

        relative_error.setVisibility(View.GONE);
        mediaItems.addAll(items);
        list_recy.getAdapter().notifyDataSetChanged();
        refreshLayout.setRefreshing(false);

      //  Log.e("MAIN", "OnLoad: ITEM COUNT = "+list_recy.getAdapter().getItemCount() );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater ff = getMenuInflater();

        ff.inflate(R.menu.menu_search, menu);
       // Log.e("MAIN", "onCreateOptionsMenu: MENU CREADO");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void FailedLoad(String error) {

        refreshLayout.setRefreshing(false);
     //   String[] fit = error.split(":");

        reload_success = true;
        relative_error.setVisibility(View.VISIBLE);

        DonotTouch.ShowErrorLoad(this, getString(R.string.error_connect));
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
}
