package com.example.shinple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shinple.MyClass.Complete;
import com.example.shinple.MyClass.Expired;
import com.example.shinple.MyClass.Learn;
import com.example.shinple.MyClass.Whole;

public class MainFragmentTwo extends Fragment {
    FrameLayout whole, learned, complete, expired;
    View viewwh, viewlea, viewcom, viewex;
    TextView tvwhole, tvlearned, tvcomplete, tvexpired;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_fragment2, container, false);
        //INIT VIEWS
        init(view);
        //SET TABS ONCLICK
        whole.setOnClickListener(clik);
        learned.setOnClickListener(clik);
        complete.setOnClickListener(clik);
        expired.setOnClickListener(clik);
        //LOAD PAGE FOR FIRST
        loadPage(new Whole());
        tvwhole.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        return view;
    }

    public void init(View v) {
        whole = v.findViewById(R.id.whole1);
        learned = v.findViewById(R.id.learn1);
        complete = v.findViewById(R.id.complete1);
        expired = v.findViewById(R.id.expired1);
        viewwh = v.findViewById(R.id.viewwh1);
        viewlea = v.findViewById(R.id.viewlea1);
        viewcom = v.findViewById(R.id.viewcom1);
        viewex = v.findViewById(R.id.viewex1);
        tvwhole = v.findViewById(R.id.tvwhole1);
        tvlearned = v.findViewById(R.id.tvlearn1);
        tvcomplete = v.findViewById(R.id.tvcom1);
        tvexpired = v.findViewById(R.id.tvex1);
    }


    //ONCLICK LISTENER
    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.whole1:
                    //ONSELLER CLICK
                    //LOAD Whole FRAGMENT CLASS
                    loadPage(new Whole());

                    //눌렀을 때 배경색깔?
                    tvwhole.setBackgroundColor(getActivity().getResources().getColor(R.color.colorNull));
                    tvlearned.setBackgroundColor(getActivity().getResources().getColor(R.color.colorNull));
                    tvcomplete.setBackgroundColor(getActivity().getResources().getColor(R.color.colorNull));
                    tvexpired.setBackgroundColor(getActivity().getResources().getColor(R.color.colorNull));
                    //WHEN CLICK TEXT COLOR CHANGED
                    tvwhole.setTextColor(getActivity().getResources().getColor(R.color.Primary));
                    tvlearned.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tvcomplete.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tvexpired.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    //VIEW VISIBILITY WHEN CLICKED
                    viewwh.setVisibility(View.VISIBLE);
                    viewlea.setVisibility(View.INVISIBLE);
                    viewcom.setVisibility(View.INVISIBLE);
                    viewex.setVisibility(View.INVISIBLE);
                    break;
                case R.id.learn1:
                    //ONBUYER CLICK
                    //LOAD Learn FRAGMENT CLASS
                    loadPage(new Learn());


                    //WHEN CLICK TEXT COLOR CHANGED
                    tvwhole.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tvlearned.setTextColor(getActivity().getResources().getColor(R.color.Primary));
                    tvcomplete.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tvexpired.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    //VIEW VISIBILITY WHEN CLICKED
                    viewwh.setVisibility(View.INVISIBLE);
                    viewlea.setVisibility(View.VISIBLE);
                    viewcom.setVisibility(View.INVISIBLE);
                    viewex.setVisibility(View.INVISIBLE);
                    break;

                case R.id.complete1:
                    //ONBUYER CLICK
                    //LOAD Complete FRAGMENT CLASS
                    loadPage(new Complete());


                    //WHEN CLICK TEXT COLOR CHANGED
                    tvwhole.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tvlearned.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tvcomplete.setTextColor(getActivity().getResources().getColor(R.color.Primary));
                    tvexpired.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    //VIEW VISIBILITY WHEN CLICKED
                    viewwh.setVisibility(View.INVISIBLE);
                    viewlea.setVisibility(View.INVISIBLE);
                    viewcom.setVisibility(View.VISIBLE);
                    viewex.setVisibility(View.INVISIBLE);
                    break;

                case R.id.expired1:
                    //ONBUYER CLICK
                    //LOAD Expired FRAGMENT CLASS
                    loadPage(new Expired());

                    //WHEN CLICK TEXT COLOR CHANGED
                    tvwhole.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tvlearned.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tvcomplete.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tvexpired.setTextColor(getActivity().getResources().getColor(R.color.Primary));
                    //VIEW VISIBILITY WHEN CLICKED
                    viewwh.setVisibility(View.INVISIBLE);
                    viewlea.setVisibility(View.INVISIBLE);
                    viewcom.setVisibility(View.INVISIBLE);
                    viewex.setVisibility(View.VISIBLE);
                    break;
            }

        }

    };

    //LOAD PAGE FRAGMENT METHOD
    private boolean loadPage(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerpage1, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}