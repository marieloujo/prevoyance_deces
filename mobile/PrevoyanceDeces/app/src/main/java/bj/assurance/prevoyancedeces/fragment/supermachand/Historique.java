package bj.assurance.prevoyancedeces.fragment.supermachand;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import ir.farshid_roohi.linegraph.ChartEntity;
import ir.farshid_roohi.linegraph.LineChart;

public class Historique extends Fragment {

    LinearLayout appBar, nextBar;
    LineChart lineChart;

    float[] graph2 = {0f, 245000f, 1011000f, 1000f, 0f, 0f, 47000f, 20000f, 12000f, 124400f, 160000f};
    String[] legendArr = {"05/21", "05/22", "05/23", "05/24", "05/25", "05/26", "05/27", "05/28", "05/29", "05/30", "05/31"};


    public Historique() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historique_supermarchand, container, false);

        lineChart = view.findViewById(R.id.lineChart);

        ChartEntity secondChartEntity = new  ChartEntity(Color.YELLOW, graph2);

        ArrayList list = new  ArrayList();
        list.add(secondChartEntity);
        lineChart.setLegendArray(legendArr);
        lineChart.setList(list);

        return view;
    }



}
