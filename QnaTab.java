package com.example.shinple;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.shinple.Adapter.Qna;
import com.example.shinple.Adapter.QnaListAdapter;
import com.example.shinple.App_DB.RestClient;
import com.example.shinple.MyClass.Qna_view;
import com.example.shinple.MyClass.qna_write;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QnaTab extends Fragment {
    //    private ListView qna_listView;
//    private QnaListAdapter madapter;
//    private List<Qna> qnaList;
//    ImageView enterqna;
    private final static int REQUEST_QNA_WRITE = 100;

    QnaListAdapter adpapter;
    ListView qna_listView;


    TextView[] textResponse = new TextView[3];  //원하는 TextView 크기 지정
    String[] text_column = new String[3];
    RestClient restClient; //RestClient class를 이용해야함 필요 시 만들어서 사용

    TextView qna_text; //내용
    TextView qnacategory; //제목
    TextView qnadate;

    View view;

    private Handler mHandler = new Handler(Looper.getMainLooper());


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_qna_tab, container, false);


        final Button btn = (Button) view.findViewById(R.id.qna_write);
//        btn.setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(),qna_write.class);
//                        getActivity().startActivity(intent);
//                    }
//                }
//        );
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), qna_write.class);
                startActivityForResult(intent1, REQUEST_QNA_WRITE);

            }
        });

//        List<String> categories = new ArrayList<String>();
//
//        categories.add("전체");
//        categories.add("강의");
//        categories.add("기간");
//        categories.add("고객");
//        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
//        spinner.setSelection(0);
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, categories);
//
//
//        spinner.setAdapter(adapter);


//          qna_listView = (ListView) view.findViewById(R.id.qnaListView1);
//        qnaList = new ArrayList<Qna>();
//        /*
//        qnaList.add(new Qna("계정의 비밀번호를 잊어버렸습니다..", "[이용문의]", "2019-08-22"));
//        qnaList.add(new Qna("나의 질문 내용", "[강의]", "2019-08-22"));
//        qnaList.add(new Qna("나의 질문 내용", "[강의]", "2019-08-22"));
//        qnaList.add(new Qna("나의 질문 내용", "[강의]", "2019-08-22"));
//        qnaList.add(new Qna("나의 질문 내용", "[강의]", "2019-08-22"));
//        qnaList.add(new Qna("나의 질문 내용", "[강의]", "2019-08-22"));
//*/
//        madapter = new QnaListAdapter(this.getActivity().getApplicationContext(), qnaList);
//        qna_listView.setAdapter(madapter);


//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.search_tool);

//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        toolbar.getTitle();

//        ((AppCompatActivity)getActivity()).setTitle("My Q\u0026A");


//        ((AppCompatActivity)getActivity()).setTitle("");

        setHasOptionsMenu(true);

                /*

        enterqna = (ImageView)view.findViewById(R.id.enterqna);
        enterqna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(this, com.example.shinple.MyClassRoom.class);
                startActivity(intent);
            }
        });
        */


        init(view);

        //기본 Adapter로 view를 return 하기 때문에 Q&A 어댑터와 충돌이 날 수도 있습니다.
        //onViewCreated를 만들어주어서 따로 동작하게. Created 다음에 바로 보여주는 것이기 때문에 충돌방지할 수 있는 것 같습니다
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }


    // SSL 통신 시 필요 : DB 추가 부분 -----------
    // //-------------------------------------------------------------------------------------------------------------

    private void init(View view) {
        getViews(view);
        restClient = RestClient.getInstance();
        restClient.parseDB("https://shinple.kr/app_db/qna_tbl_1.php");  // 불러오고 싶은 DB 테이블 설정!!
        setListeners();
    }

    private void getViews(View view) {

        textResponse[0] = (TextView) view.findViewById(R.id.qna_text);
        textResponse[1] = (TextView) view.findViewById(R.id.qnacategory);
        textResponse[2] = (TextView) view.findViewById(R.id.qnadate);
        text_column[0] = "title";
        text_column[1] = "q_type";
        text_column[2] = "created_date";
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
//                MyQna_SslFunc.ParseJSON(response, textRes, text_column);

                qna_listView = (ListView) view.findViewById(R.id.qnaListView1);
                ArrayList<Qna> qnaList = new ArrayList<Qna>();
                adpapter = new QnaListAdapter(getActivity().getApplicationContext(), qnaList);

                //listView의 item클릭시 해당 Q&A 뷰잉
                qna_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Qna qna = (Qna) adpapter.getItem(i);
                        Intent intent2 = new Intent(getActivity(), Qna_view.class);
                        intent2.putExtra("detail", i);
                        startActivity(intent2);
                    }
                });

                try {

                    JSONObject json = new JSONObject(response);
                    JSONArray arr = json.getJSONArray("result");
                    //임의의 리스트를 지정하여 값을 넣어주는 틀을 만듭니다.
                    List<String> title = new ArrayList<String>();
                    List<String> category = new ArrayList<String>();
                    List<String> date = new ArrayList<String>();

                    JSONObject json2;

                    for (int i = 0; i < arr.length(); i++) {
                        json2 = arr.getJSONObject(i);
                        String qna_title = json2.getString(text_column[0]);
                        String qna_category = json2.getString(text_column[1]);
                        String qna_date = json2.getString(text_column[2]);
                        //Qna_SslFunc에서 가져온 것들. json 이용.

                        String cnum = qna_category;

                        switch(cnum){
                            case "0" :
                                qna_category = "[강좌]";
                                break;
                            case "1" :
                                qna_category = "[영상]";
                                break;
                            case "2" :
                                qna_category = "[노트]";
                                break;
                            case "3" :
                                qna_category = "[기타]";
                                break;
                            default:
                                break;
                        }

                        title.add(qna_title);
                        category.add(qna_category);
                        date.add(qna_date);
                    }

                    for (int i = 0; i < arr.length(); i++) {
                        qnaList.add(new Qna(title.get(i), category.get(i), date.get(i)));
                        qna_listView.setAdapter(adpapter); //adapter에 추가해줍니다.
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //refresh
        if (requestCode == REQUEST_QNA_WRITE) {
            synchronized (adpapter){
                adpapter.notifyAll();
            }
            init(view);
        }
    }
}