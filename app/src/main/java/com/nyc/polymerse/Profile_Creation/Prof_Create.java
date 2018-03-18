package com.nyc.polymerse.Profile_Creation;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nyc.polymerse.R;

import java.util.ArrayList;
import java.util.List;

public class Prof_Create extends AppCompatActivity {
    //TODO:Give Language and Location Fragments spinners instead of EditTexts


    TabLayout createUserTab;
    ViewPager frag_Pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof__create);
        if (savedInstanceState == null){
            Name_Fragment name_fragment = new Name_Fragment();
            name_fragment.getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, name_fragment, Name_Fragment.TAG)
                    .commit();
        }
        createUserTab = (TabLayout) findViewById(R.id.create_tabs);
        frag_Pager = (ViewPager) findViewById(R.id.create_page);

        createUserTab.setupWithViewPager(frag_Pager);
    }

    public static class CreateProf_Adapter extends FragmentPagerAdapter{
        private List<Fragment> createFragments = new ArrayList<>();
        private List<String> frag_Titles = new ArrayList<>();

        public CreateProf_Adapter(FragmentManager manager) {
            super(manager);
        }
        public void AddFragmentPage(Fragment frag, String title){
            createFragments.add(frag);
            frag_Titles.add(title);
        }
        @Override
        public Fragment getItem(int position) {

            return createFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return frag_Titles.get(position);
        }
        @Override
        public int getCount() {
            return 3;
        }
    }
}
