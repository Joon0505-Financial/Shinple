package com.example.shinple;

import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shinple.Course.CourseActivity;
import com.example.shinple.Functions.BackPressCloseHandler;
import com.example.shinple.MyClass.MyClassRoom;
import com.example.shinple.MyClass.VideoListActivity;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;



public class MainMenuActivity extends AppCompatActivity implements View.OnDragListener{
    BoomMenuButton bmb;
    ImageButton cr_btr;
    ImageButton mypage_btr;
    private final int START_DRAG = 0;
    private final int END_DRAG = 1;
    private int isMoving;
    private float offset_x, offset_y;
    private boolean start_yn = true;
    private BackPressCloseHandler backPressCloseHandler;
    ImageButton course_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        settingBoommenu();  // 붐메뉴 버튼 세팅 함수

        // 나의 강의실 넘어가는 함수 구현
        cr_btr = findViewById(R.id.classroom_btn);
        cr_btr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View  view) {
                Intent intent = new Intent(MainMenuActivity.this, MyClassRoom.class);
                startActivity(intent);
            }
        });

        //나의 학습 현황 넘어가는 버튼 구현
        ImageButton mypage_btr = findViewById(R.id.learning_status_btn);
        mypage_btr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent videoList_intent = new Intent(MainMenuActivity.this, VideoListActivity.class);
                startActivity(videoList_intent);
            }

        });
        mypage_btr.getBackground().setAlpha(90); // 나의 학습 현황 이미지 배경화면 투명도 조절
        bmb.setDraggable(true);   // 붐 메뉴 버튼 드래그 가능하도록 설정
        bmb.setOnDragListener(this); // dragListener를 이용해 드래그 시도시 수행할 함수 정의 OnTouchListener와 유사
        backPressCloseHandler = new BackPressCloseHandler(this);
        course_btn = (ImageButton)findViewById(R.id.total_lecture_btn);
        course_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent course_intent = new Intent(MainMenuActivity.this, CourseActivity.class);
                startActivity(course_intent);
            }
        });

    }




    //붐메뉴 버튼 셋팅 함수
    public void settingBoommenu(){
        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setNormalColor(getResources().getColor(R.color.colorPrimaryP));
        //마이 페이지 버튼 구현
        TextOutsideCircleButton.Builder home_builder = new TextOutsideCircleButton.Builder().normalImageRes(R.drawable.mypage1);
        home_builder.normalText("마이페이지");
        home_builder.normalColorRes(R.color.white);
        home_builder.normalTextColorRes(R.color.colorNull);
        bmb.addBuilder(home_builder);
        //전체 강좌 버튼 구현
        TextOutsideCircleButton.Builder lecture_builder = new TextOutsideCircleButton.Builder().normalImageRes(R.drawable.lists);
        lecture_builder.normalText("전체강좌");
        lecture_builder.normalColorRes(R.color.white);
        lecture_builder.normalTextColorRes(R.color.colorNull);
        bmb.addBuilder(lecture_builder);
        //공지 사항 버튼 구현
        TextOutsideCircleButton.Builder notice_builder = new TextOutsideCircleButton.Builder().normalImageRes(R.drawable.notice);
        notice_builder.normalText("공지사항");
        notice_builder.normalColorRes(R.color.white);
        notice_builder.normalTextColorRes(R.color.colorNull);
        bmb.addBuilder(notice_builder);
        //강의노트 게시판 버튼 구현
        TextOutsideCircleButton.Builder note_builder = new TextOutsideCircleButton.Builder().normalImageRes(R.drawable.note);
        note_builder.normalText("강의노트");
        note_builder.normalColorRes(R.color.white);
        note_builder.normalTextColorRes(R.color.colorNull);
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
            Toast.makeText(MainMenuActivity.this,"Drag Start",Toast.LENGTH_SHORT).show();
            isMoving=START_DRAG;
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            Toast.makeText(MainMenuActivity.this, "Drag End",Toast.LENGTH_SHORT).show();
            isMoving=END_DRAG;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(isMoving == START_DRAG){
                v.setX((int)event.getX()-offset_x);
                v.setY((int)event.getY()-offset_y);
            }
        }
        return false;
    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }


}


