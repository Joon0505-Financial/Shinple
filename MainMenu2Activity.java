package com.example.shinple;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.shinple.Course.CourseDetailActivity;
import com.example.shinple.Notice.NoticeActivity;
import com.example.shinple.Note.NoteActivity;
import com.google.android.material.tabs.TabLayout;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;


public class MainMenu2Activity extends AppCompatActivity implements View.OnDragListener {
    BoomMenuButton bmb;
    ViewPager viewPager;
    TabLayout tabLayout;
    private final int START_DRAG = 0;
    private final int END_DRAG = 1;
    private int isMoving;
    private float offset_x, offset_y;
    private boolean start_yn = true;
//    String u_id = getIntent().getStringExtra("u_id");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu2);

        Intent intent = getIntent();
        intent.getStringExtra("u_id");

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);


        tabLayout.addTab(tabLayout.newTab().setText("홈"));
        tabLayout.addTab(tabLayout.newTab().setText("강의실"));
        tabLayout.addTab(tabLayout.newTab().setText("내정보"));

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));

        com.example.shinple.Adapter.MainPagerAdapter pagerAdapter = new com.example.shinple.Adapter.MainPagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        settingBoommenu();
        bmb.setDraggable(true);   // 붐 메뉴 버튼 드래그 가능하도록 설정
        bmb.setOnDragListener(this); // dragListener를 이용해 드래그 시도시 수행할 함수 정의 OnTouchListener와 유사

    }

    //붐메뉴 버튼 셋팅 함수
    public void settingBoommenu(){
        bmb = (BoomMenuButton) findViewById(R.id.bmb1);
        bmb.setNormalColor(getResources().getColor(R.color.colorPrimaryP));
        //마이 페이지 버튼 구현
        TextOutsideCircleButton.Builder home_builder = new TextOutsideCircleButton.Builder().normalImageRes(R.drawable.mypage1);
        home_builder.normalText("마이페이지");
        home_builder.normalColorRes(R.color.white);
        home_builder.normalTextColorRes(R.color.colorNull);
        home_builder.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {

            }
        });
        bmb.addBuilder(home_builder);

        //전체 강좌 버튼 구현
        TextOutsideCircleButton.Builder lecture_builder = new TextOutsideCircleButton.Builder().normalImageRes(R.drawable.lists);
        lecture_builder.normalText("전체강좌");
        lecture_builder.normalColorRes(R.color.white);
        lecture_builder.normalTextColorRes(R.color.colorNull);
        lecture_builder.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Intent course_intent = new Intent(MainMenu2Activity.this, CourseDetailActivity.class);
                startActivity(course_intent);
            }
        });
        bmb.addBuilder(lecture_builder);
        //공지 사항 버튼 구현
        TextOutsideCircleButton.Builder notice_builder = new TextOutsideCircleButton.Builder().normalImageRes(R.drawable.notice);
        notice_builder.normalText("공지사항");
        notice_builder.normalColorRes(R.color.white);
        notice_builder.normalTextColorRes(R.color.colorNull);
        notice_builder.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Intent notice_intent = new Intent(MainMenu2Activity.this, NoticeActivity.class);
                startActivity(notice_intent);
            }
        });
        bmb.addBuilder(notice_builder);
        //강의노트 게시판 버튼 구현
        TextOutsideCircleButton.Builder note_builder = new TextOutsideCircleButton.Builder().normalImageRes(R.drawable.note);
        note_builder.normalText("강의노트");
        note_builder.normalColorRes(R.color.white);
        note_builder.normalTextColorRes(R.color.colorNull);
        note_builder.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Intent note_intent = new Intent(MainMenu2Activity.this, NoteActivity.class);
                startActivity(note_intent);
            }
        });
        bmb.addBuilder(note_builder);



    }





    // OnTouch 함수와 유사함, 붐버튼이 터치 되었을때 어떻게 기능할지 정의
    @Override
    public boolean onDrag(View v, DragEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(start_yn){
                offset_x = event.getX();
                offset_y = event.getY();
                start_yn = false;
            }
            isMoving=START_DRAG;
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            isMoving=END_DRAG;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(isMoving == START_DRAG){
                v.setX((int)event.getX()-offset_x);
                v.setY((int)event.getY()-offset_y);
            }
        }
        return false;
    }

    public void side_bar_boom(){
        bmb = findViewById(R.id.bmb1);
        bmb.boom();
    }










}