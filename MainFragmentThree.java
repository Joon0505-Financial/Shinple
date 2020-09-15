package com.example.shinple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragmentThree extends Fragment {
    FrameLayout qnatab, my_info;
    View viewqna, viewinfo;
    TextView tvqna, tvinfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_one = inflater.inflate(R.layout.main_menu_fragment3, container, false);
        //INIT VIEWS
        init(fragment_one);
        //SET TABS ONCLICK
        qnatab.setOnClickListener(clik);
        my_info.setOnClickListener(clik);

        //LOAD PAGE FOR FIRST
        loadPage(new QnaTab());
        tvqna.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        return fragment_one;
    }

    public void init(View v) {
        qnatab = v.findViewById(R.id.myqna);
        my_info = v.findViewById(R.id.myinfo);

        viewqna = v.findViewById(R.id.viewqna);
        viewinfo = v.findViewById(R.id.viewinfo);

        tvqna = v.findViewById(R.id.tvqna);
        tvinfo = v.findViewById(R.id.tvinfo);

    }


    //ONCLICK LISTENER
    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.myqna:
                    //ONSELLER CLICK
                    //LOAD Whole FRAGMENT CLASS
                    loadPage(new QnaTab());

                    //눌렀을 때 배경색깔?

                    //WHEN CLICK TEXT COLOR CHANGED
                    tvqna.setTextColor(getActivity().getResources().getColor(R.color.Primary));
                    tvinfo.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

                    //VIEW VISIBILITY WHEN CLICKED
                    viewqna.setVisibility(View.VISIBLE);
                    viewinfo.setVisibility(View.INVISIBLE);

                    break;
                case R.id.myinfo:
                    //ONBUYER CLICK
                    //LOAD Learn FRAGMENT CLASS
                    loadPage(new MyInfo());


                    //WHEN CLICK TEXT COLOR CHANGED
                    tvqna.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tvinfo.setTextColor(getActivity().getResources().getColor(R.color.Primary));

                    //VIEW VISIBILITY WHEN CLICKED
                    viewqna.setVisibility(View.INVISIBLE);
                    viewinfo.setVisibility(View.VISIBLE);

                    break;


            }

        }

    };

    //LOAD PAGE FRAGMENT METHOD
    private boolean loadPage(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerpage, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
