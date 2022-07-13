package bj.assurance.assurancedeces.fragment.supermarchand;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.FragmentActivity;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.activity.SearchActivity;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListClientExpandableAdapter;
import bj.assurance.assurancedeces.utils.MakeListClient;


@SuppressLint("ValidFragment")
public class ClientsOfMarchands extends Fragment {



    private ProgressBar progressBar;

    private RecyclerView recyclerView;

    private Long id;


    @SuppressLint("ValidFragment")
    public ClientsOfMarchands(Long id) {

        this.id = id;

        System.out
                .println(id + " " + this.id);

        // Required empty public constructor
    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_clients_of_marchands, container, false);


        init(view);


        return view;
    }


















    private void init(View view) {


        findView(view);
        initValue();
        onClickListener();


    }



    private void findView(View view) {

        progressBar = view.findViewById(R.id.main_progress);


        recyclerView = view.findViewById(R.id.recycler);


    }



    @SuppressLint("WrongConstant")
    private void initValue() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        MakeListClient makeListClient = new MakeListClient(getContext(), recyclerView, progressBar);
        makeListClient.makeList(id);

    }



    private void onClickListener() {


    }





    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        try {

            ((ListClientExpandableAdapter) recyclerView.getAdapter()).onSaveInstanceState(outState);

        } catch (Exception e) {}

    }










}
