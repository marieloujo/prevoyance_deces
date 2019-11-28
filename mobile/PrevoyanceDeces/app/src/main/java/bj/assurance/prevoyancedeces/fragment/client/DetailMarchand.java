package bj.assurance.prevoyancedeces.fragment.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.Main2Activity;


public class DetailMarchand extends Fragment {

    public DetailMarchand() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_marchand, container, false);

        Main2Activity.getTextTitle().setVisibility(View.INVISIBLE);
        Main2Activity.getBackTitle().setVisibility(View.VISIBLE);
        Main2Activity.getBackTitle().setText(getActivity().getResources().getString(R.string.nom_prenom));

        Main2Activity.getBackTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new Marchands()).commit();
            }
        });

        return view;
    }

}
