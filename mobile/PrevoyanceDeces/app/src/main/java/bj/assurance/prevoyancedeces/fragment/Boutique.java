package bj.assurance.prevoyancedeces.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.fragment.client.Accueil;


public class Boutique extends Fragment {

    ImageSlider imageSlider;
    private ViewPager viewPager;
    private SmartTabLayout viewPagerTab;

    public Boutique() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_boutique, container, false);

        init(view);
        makeSilder();
        makeTabs();

        return view;
    }

    public void init(View view) {
        imageSlider = view.findViewById(R.id.image_slider);
        viewPager =  view.findViewById(R.id.viewpager);
        viewPagerTab =  view.findViewById(R.id.viewpagertab);

    }

    public void makeSilder() {
       List<SlideModel> imageList = new ArrayList();

        imageList.add(new  SlideModel(R.mipmap.gestion_imobilier, "Gestion immobilier"));
        imageList.add(new  SlideModel(R.mipmap.organisation_funeraille, "Organisation des funérailles"));

        imageSlider.setImageList(imageList, false);
    }
    
    public void makeTabs() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(getContext())
                .add("Tous", ListeProduit.class)
                .add("Immobilier", ListeProduit.class)
                .add("Cerceuil", ListeProduit.class)
                .add("Service traiteur", ListeProduit.class)

                .create());

        viewPager.setAdapter(adapter);

        viewPagerTab.setViewPager(viewPager);
        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
