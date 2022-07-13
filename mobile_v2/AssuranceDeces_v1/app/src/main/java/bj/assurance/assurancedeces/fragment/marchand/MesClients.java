package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.FragmentActivity;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.activity.SearchActivity;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListClientExpandableAdapter;
import bj.assurance.assurancedeces.utils.MakeListClient;
import bj.assurance.assurancedeces.utils.NotificationReader;
import bj.assurance.assurancedeces.utils.OnBackPressedListener;


public class MesClients extends Fragment implements OnBackPressedListener {


    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private EditText editText;
    private TextView textView;



    private List<Client> clients = new ArrayList<>();


    private List<ParentObject> parentObjects;


    public MesClients() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mes_clients, container, false);

        init(view);

        return view;
    }



    private void init(View view) {


        findView(view);
        initValue();
        onClickListener();


    }



    private void findView(View view) {

        floatingActionButton = view.findViewById(R.id.floatingAdd);
        progressBar = view.findViewById(R.id.main_progress);


        recyclerView = view.findViewById(R.id.recycler);
        editText = view.findViewById(R.id.searchView);

        textView = view.findViewById(R.id.client);

    }



    @SuppressLint("WrongConstant")
    private void initValue() {


        new NotificationReader(getContext()).unReadNotifications(MarchandActivity.getUtilisateur().getId());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);




        parentObjects = new ArrayList<>();


        MakeListClient makeListClient = new MakeListClient(getContext(), recyclerView, progressBar);
        makeListClient.makeList(MarchandActivity.getMarchand().getId());

    }



    private void onClickListener() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            getContext().startActivity(
                    new Intent(getContext(), FragmentActivity.class).putExtra("key", "enregistrement")
            );

            }
        });



        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            getContext().startActivity (
                    new Intent(getContext(), SearchActivity.class)
            );

            }
        });

    }






    private void replaceFragment(Fragment fragment, String title) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        MarchandActivity.getFrameTitle().setText(title);

    }









    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        try {

            ((ListClientExpandableAdapter) recyclerView.getAdapter()).onSaveInstanceState(outState);

        } catch (Exception e) {}

    }





    @Override
    public void doBack() {

        replaceFragment(new Accueil(), "Salut " + MarchandActivity.getUtilisateur());

    }



}
