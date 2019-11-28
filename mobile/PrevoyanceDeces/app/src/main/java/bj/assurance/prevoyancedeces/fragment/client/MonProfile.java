package bj.assurance.prevoyancedeces.fragment.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.Main2Activity;


public class MonProfile extends AppCompatActivity {


    public MonProfile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mon_profile);
    }


    public void init() {
        Main2Activity.getTextTitle().setVisibility(View.VISIBLE);
        Main2Activity.getBackTitle().setVisibility(View.INVISIBLE);

        Main2Activity.getBackTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }

}
