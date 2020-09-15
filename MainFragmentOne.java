package com.example.shinple;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shinple.Adapter.RecyclerAdapter;
import com.example.shinple.App_DB.RestClient;
import com.example.shinple.App_DB.URLConnection;
import com.example.shinple.Course.CourseContents;
import com.example.shinple.Course.CourseDetailActivity;
import com.example.shinple.Course.VideoActivity;
import com.example.shinple.Functions.MyInfo_SslFunc;
import com.example.shinple.MyClass.VideoListActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainFragmentOne extends Fragment {
    ProgressBar pb1,pb2,pb3;
    RecyclerAdapter adapter;

    TextView[] textResponse = new TextView[6];  //원하는 TextView 크기 지정
    String[] text_column = new String[6];
    RestClient restClient; //RestClient class를 이용해야함 필요 시 만들어서 사용

    private Handler mHandler = new Handler(Looper.getMainLooper());

    TextView course_title;
    TextView course_instructor;
    TextView course_view_count;
    ImageView course_image_view;

    View view;

    TextView user_course_name;
    TextView learning_period;

    //fragment 안에서 인텐트 넘기기
    String u_id;
    List<String> course_id_array;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_menu_fragmnet1, container, false);

//        u_id = getActivity().getIntent().getStringExtra("u_id");
        Intent intent = getActivity().getIntent();
        u_id = intent.getStringExtra("u_id");

        pb1 = view.findViewById(R.id.progressBar);
        pb2 = view.findViewById(R.id.progressBar2);
        pb3 = view.findViewById(R.id.progressBar3);

        pb1.setProgress(50);
        pb2.setProgress(70);
        pb3.setProgress(90);

        //메인 나의 학습 현황
        user_course_name = (TextView) view.findViewById(R.id.user_course_name);
        learning_period = (TextView) view.findViewById(R.id.learning_period);

        restClient = RestClient.getInstance();
        restClient.parseDB("https://shinple.kr/app_db/user_course_tbl.php?u_id="+u_id);
        setListeners_top();

        ///end of 나의 학습 현황

        //하단 리사이클러뷰
//        init(view);
        //end of 하단 리사이클러뷰
        return view;

    }
    // SSL 통신 시 필요 : DB 추가 부분 ------------------------------------------------------------------------------------------------------------------------


    private void init(View view) {
        getViews(view);
        restClient = RestClient.getInstance();
        restClient.parseDB("https://shinple.kr/app_db/course_tbl.php");  // 불러오고 싶은 DB 테이블 설정!!
        setListeners();
    }
    private void getViews(View view) {
        textResponse[0] = (TextView) view.findViewById(R.id.main_bottom_course_title);
        textResponse[1] = (TextView) view.findViewById(R.id.main_bottom_instructor);
        textResponse[2] = (TextView) view.findViewById(R.id.main_bottom_view_count);
        course_image_view = (ImageView) view.findViewById(R.id.main_bottom_image_view);

        text_column[0] = "c_name";
        text_column[1] = "instructor";
        text_column[2] = "view_count";
        text_column[3] = "thumbnail";
        text_column[4] = "c_id";
    }
    private void setListeners() {
        // request에 따른 response 함수
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = restClient.postRequest(); // response 내용 저장
                setText(response, textResponse); //파씽 함수 고고
                System.out.println(response);
            }
        }).start();
    }
    private void setListeners_top() {
        // request에 따른 response 함수
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = restClient.postRequest(); // response 내용 저장
//                여기 바꾸기
                try {
                    JSONObject json = new JSONObject(response);
                    JSONArray arr = json.getJSONArray("user_data");
                    JSONObject json2;

                    for(int i = 0; i < arr.length(); i++){
                        json2 = arr.getJSONObject(i);
                        user_course_name.setText(json2.getString("c_name"));
                        String start_date = json2.getString("start_date");
                        String end_date = json2.getString("end_date");
                        String learning_period_text ="학습기간: "+start_date+" ~ "+end_date;
                        learning_period.setText(learning_period_text);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println(response);
            }
        }).start();
    }
    private void setListeners_chk_course_takes(final int p) {
        // request에 따른 response 함수
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = restClient.postRequest(); // response 내용 저장
//                여기 바꾸기
                try {
                    JSONObject json = new JSONObject(response);
                    JSONArray arr = json.getJSONArray("result");
                    JSONObject json2;

                    for(int i = 0; i < arr.length(); i++){
                        json2 = arr.getJSONObject(i);
                        if(json2.getString("c_id").equals("none")){
                            Intent intent = new Intent(getActivity(), CourseContents.class);
                            intent.putExtra("courseId", course_id_array.get(p));
                            intent.putExtra("courseId", json2.getString("sub_id"));
                            intent.putExtra("courseId", json2.getString("c_name"));
                            startActivity(intent);
                            break;
                        }else{
                            Intent intent = new Intent(getActivity(), VideoListActivity.class);
                            intent.putExtra("courseId", course_id_array.get(p));
                            intent.putExtra("subCategory", json2.getString("sub_id"));
                            intent.putExtra("title", json2.getString("c_name"));
//                            System.out.println("this: "+course_id_array.get(p)+json2.getString("sub_id")+json2.getString("c_name"));

                            startActivity(intent);
                            break;
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println(response);
            }
        }).start();
    }
    //.runOnUiThread
    private void setText(final String response, final TextView[] textRes) {
        mHandler.post(new Runnable() {
            @Override


            public void run() {

                RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);

                adapter = new RecyclerAdapter();
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        // TODO : 아이템 클릭 이벤트를 MainActivity에서 처리.
                        restClient = RestClient.getInstance();
                        restClient.parseDB("https://shinple.kr/app_db/chk_takes_tbl.php?u_id="+u_id+"&"+"c_id="+course_id_array.get(position));
                        setListeners_chk_course_takes(position);


//                        Toast.makeText(getActivity(), course_id_array.get(position)+"", Toast.LENGTH_SHORT).show();
                    }
                });

                try {
                    JSONObject json = new JSONObject(response);
                    JSONArray arr = json.getJSONArray("result");

                    List<String> title = new ArrayList<String>();
                    List<String> instructor = new ArrayList<String>();
                    List<String> view_count = new ArrayList<String>();
                    List<String> course_thumb_array = new ArrayList<String>();
                    course_id_array = new ArrayList<>();

                    JSONObject json2;

                    System.out.println("array_length: "+arr.length());
                    for(int i = 0; i < arr.length(); i++){
                        json2 = arr.getJSONObject(i);
                        String course_title = json2.getString(text_column[0]);
                        String course_instructor = json2.getString(text_column[1]);
                        String course_view_count = json2.getString(text_column[2]);
                        String course_image_thumb = json2.getString(text_column[3]);
                        String course_id = json2.getString(text_column[4]);

                        title.add(course_title);
                        instructor.add(course_instructor);
                        view_count.add(course_view_count);
                        course_thumb_array.add(course_image_thumb);
                        course_id_array.add(course_id);
                    }

                    for (int i = 0; i < arr.length(); i++) {
                        // 각 List의 값들을 mainRecyclerData 객체에 set 해줍니다.
                        MainRecyclerData mainRecyclerData = new MainRecyclerData();
                        mainRecyclerData.setTitle(title.get(i));
                        mainRecyclerData.setContent(instructor.get(i));
                        mainRecyclerData.setImage_thumb(course_thumb_array.get(i), course_image_view);

                        // 각 값이 들어간 data를 adapter에 추가합니다.
                        adapter.addItem(mainRecyclerData);
                    }
                    // adapter의 값이 변경되었다는 것을 알려줍니다.
                    adapter.notifyDataSetChanged();

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }
}
