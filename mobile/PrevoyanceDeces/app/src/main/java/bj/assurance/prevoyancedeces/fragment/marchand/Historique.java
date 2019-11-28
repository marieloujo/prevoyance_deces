package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import ir.farshid_roohi.linegraph.ChartEntity;
import ir.farshid_roohi.linegraph.LineChart;

public class Historique extends Fragment {

    LinearLayout appBar, nextBar;
    LineChart lineChart;

    float[] graph1 = {113000f, 183000f, 188000f, 695000f, 324000f, 230000f, 188000f, 15000f, 126000f, 5000f, 33000f};
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

        View view = inflater.inflate(R.layout.fragment_historique, container, false);

        MarchandMainActivity.getFloatingActionButton().setVisibility(View.VISIBLE);
        lineChart = view.findViewById(R.id.lineChart);

        ChartEntity firstChartEntity = new  ChartEntity(Color.WHITE, graph1);
        ChartEntity secondChartEntity = new  ChartEntity(Color.YELLOW, graph2);

        ArrayList<ChartEntity> list = new  ArrayList();
        list.add(firstChartEntity);
        list.add(secondChartEntity);
        lineChart.setLegendArray(legendArr);
        lineChart.setList(list);

        return view;
    }



}
