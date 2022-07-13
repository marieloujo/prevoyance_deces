package bj.assurance.assurancedeces.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.SearchActivity;
import bj.assurance.assurancedeces.model.customModel.Search;
import bj.assurance.assurancedeces.utils.sqliteDbHelper.DBHelper;


public class HistoriqueSearch extends Fragment {


    private RecyclerView searchRecycler;
    private DBHelper dbHelper;

    public HistoriqueSearch() {
        // Required empty public constructor
    }








    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_historique_search, container, false);


        init(view);


        return view;
    }








    private void init(View view) {


        findView(view);
        initValue();

    }





    private void findView(View view) {

        searchRecycler = view.findViewById(R.id.search_list);

    }




    @SuppressLint("WrongConstant")
    private void initValue() {

        dbHelper = new DBHelper(getContext());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchRecycler.setLayoutManager(layoutManager);


        SearchAdapter searchAdapter = new SearchAdapter(getContext(), dbHelper.getHistoriqueSearch());
        searchRecycler.setAdapter(searchAdapter);

    }














    private class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {



        private Context context;
        private List<Search> searches = new ArrayList<>();




        public SearchAdapter(Context context, List<Search> searches) {
            this.context = context;
            this.searches = searches;
        }




        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new SearchViewHolder(LayoutInflater.from(context).inflate(
                    R.layout.item_search_list, parent, false
            ));

        }




        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, final int position) {

            holder.getChip().setText(searches.get(position).getContenue());



            holder.getChip().setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int code = dbHelper.deleteHistorique(Integer.valueOf(String.valueOf(searches.get(position).getId())));

                    if (code != -1) {

                        searches.remove(position);

                        SearchAdapter.this.notifyDataSetChanged();

                    } else Toast.makeText(context, "Cette recherche n'a pas pu être supprimé", Toast.LENGTH_LONG).show();

                }
            });


            holder.getChip().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   SearchActivity.getSearchView().setQuery(searches.get(position).getContenue(), true);

                }
            });


        }




        @Override
        public int getItemCount() {
            return searches.size();
        }


    }









    private class SearchViewHolder extends RecyclerView.ViewHolder {


        private Chip chip;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            chip = itemView.findViewById(R.id.search_texte);

        }


        public Chip getChip() {
            return chip;
        }
    }







}
