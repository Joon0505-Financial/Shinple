package com.example.shinple;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shinple.App_DB.RestClient;
import com.example.shinple.Functions.MyInfo_SslFunc;

public class MyInfo extends Fragment {

    TextView[] textResponse = new TextView[6];  //원하는 TextView 크기 지정
    String[] text_column = new String[6];
    RestClient restClient; //RestClient class를 이용해야함 필요 시 만들어서 사용

    private Handler mHandler = new Handler(Looper.getMainLooper());




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.my_info_tab, container, false);

    init(view);

      return view;




    }

    // SSL 통신 시 필요 : DB 추가 부분 ------------------------------------------------------------------------------------------------------------------------


    private void init(View view) {
        getViews(view);
        restClient = RestClient.getInstance();
        restClient.parseDB("https://shinple.kr/app_db/get_info.php");  // 불러오고 싶은 DB 테이블 설정!!
        setListeners();
    }

        private void getViews(View view) {
            textResponse[0] = (TextView) view.findViewById(R.id.nameinfo);
            textResponse[1] = (TextView) view.findViewById(R.id.group2info);
            textResponse[2] = (TextView) view.findViewById(R.id.team2info);
            textResponse[3] = (TextView) view.findViewById(R.id.comp_num2info);
            textResponse[4] = (TextView) view.findViewById(R.id.email2info);
            textResponse[5] = (TextView) view.findViewById(R.id.contact2info);
            text_column[0] = "name";
            text_column[1] = "company_name";
            text_column[2] = "team";
            text_column[3] = "cp_id";
            text_column[4] = "email";
            text_column[5] = "birthday";
    }

    private void setListeners() {                                   // request에 따른 response 함수
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = restClient.postRequest(); // response 내용 저장
                setText(response, textResponse); //파씽 함수 고고
                System.out.println(response);
            }
        }).start();
    }
//.runOnUiThread
    private void setText(final String response, final TextView[] textRes) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                MyInfo_SslFunc.ParseJSON(response, textRes, text_column);
            }
        });
    }


    // SSL 통신 시 필요  -------------------------------------------------------------------------------------------------------------------------------------
/*
    public void onClick_find(View v) { //test 검색을 누르면 화면전환(이 부분은 검색 목록이 떠야함)
        Intent findIntent = new Intent(getApplicationContext(), FindActivity.class);
        startActivity(findIntent);  //find activity 실행
        finish();
    }

    public void onClick_notice_str(View v) { //공지사항을 누르면 공지사항 home으로 이동!!
        Intent notice_strIntent = new Intent(getApplicationContext(), NoticeActivity.class);
        startActivity(notice_strIntent); //notice activity 실행
        finish();
    }
*/



}

















