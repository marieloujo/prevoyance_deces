package bj.assurance.assurancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.marchand.AddContratStepthree;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ContenteAddBeneficier extends AppCompatActivity {



    private ViewPagerAdapter adapter;
    int numero = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_add_beneficier);



        init();



    }




    private void init() {

        findView();
        initValue();
        clickListner();

    }




    private void initValue() {



    }



    private void findView() {

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);


        ViewPager viewPager = findViewById(R.id.pager);
        setViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }



    private void clickListner() {

        numero++;

        ((FloatingActionButton) findViewById(R.id.floatingAdd))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.addFragment(new AddContratStepthree(), "Béneficiaire n°" + numero);
                        adapter.notifyDataSetChanged();
                    }
                });

    }









    private void setViewPager(ViewPager viewPager) {
        adapter =  new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddContratStepthree(), "Béneficiaire n°" + numero);
        viewPager.setAdapter(adapter);
    }







    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();




        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }




        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }



        @Override
        public int getCount() {
            return mFragmentList.size();
        }



        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }









}
