package com.example.shinple;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shinple.Adapter.RecyclerAdapter;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent loadintent = new Intent(getApplicationContext(), LoadingActivity.class);
        startActivity(loadintent);
        finish();
//        init();

//        getData();

    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("[전략적사고] 전략적 사고와 기획 실무과정", "[커뮤니케이션] 고객 중심 Communication", "[준법/윤리] 기업 윤리 청탁금지법", "[전략적사고] 타이틀2", "[전략적사고] 타이틀3", "[전략적사고] 타이틀4",
                "[커뮤니케이션] 타이틀1", "[커뮤니케이션] 타이틀2", "[커뮤니케이션] 타이틀3", "[준법/윤리] 타이틀1", "[준법/윤리] 타이틀2", "[준법/윤리] 타이틀3", "[준법/윤리] 타이틀4", "[준법/윤리] 타이틀5");
        List<String> listContent = Arrays.asList(
                "[전략적사고] 전략적 사고와 기획 실무과정에 대한 내용",
                "[커뮤니케이션] 고객 중심 Communication에 대한 내용",
                "[준법/윤리] 기업 윤리 청탁금지법에 대한 내용",
                "[전략적사고] 타이틀2에 대한 내용",
                "[전략적사고] 타이틀3에 대한 내용",
                "[전략적사고] 타이틀4에 대한 내용",
                "[커뮤니케이션] 타이틀1에 대한 내용",
                "[커뮤니케이션] 타이틀2에 대한 내용",
                "[커뮤니케이션] 타이틀3에 대한 내용",
                "[준법/윤리] 타이틀1에 대한 내용",
                "[준법/윤리] 타이틀2에 대한 내용",
                "[준법/윤리] 타이틀3에 대한 내용",
                "[준법/윤리] 타이틀4에 대한 내용",
                "[준법/윤리] 타이틀5에 대한 내용"
        );

        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 mainRecyclerData 객체에 set 해줍니다.
            MainRecyclerData mainRecyclerData = new MainRecyclerData();
            mainRecyclerData.setTitle(listTitle.get(i));
            mainRecyclerData.setContent(listContent.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(mainRecyclerData);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}