package bj.assurance.prevoyancedeces.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;


public class ListeProduit extends Fragment {

    CardView cardView;

    public ListeProduit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste_produit, container, false);

        init(view);

        return view;
    }

    public void init(View view) {
        cardView = view.findViewById(R.id.item_product);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DetailProduits(), "");
            }
        });
    }

    public void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        //title.setText(titre);

    }

}
