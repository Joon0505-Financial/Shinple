package com.example.shinple;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    EditText emp_num ;
    String shared = "file";
    CheckBox log_check ;
    Boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emp_num = (EditText)findViewById(R.id.emp_num);
        log_check = (CheckBox)findViewById(R.id.log_check);
        SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        String value = sharedPreferences.getString("id","");
        if(value.length()!=0){
            emp_num.setText(value);
            log_check.setChecked(true);
        }
    }

    //회원가입 버튼 함수
    public void onClick_join(View v){
        Intent joinintent = new Intent(getApplicationContext(),JoinActivity.class); // 원래는 JoinActivity.class
        startActivity(joinintent);
    }


    // 로그인 버튼 함수
    public void onClick_login(View v){
        //------------------아이디 패스워드 값 받기 -----------
        EditText idtext = (EditText)findViewById(R.id.emp_num);
        EditText pwtext = (EditText)findViewById(R.id.emp_pw);
        String [] personalInfo = new String[2];
        personalInfo[0] = idtext.getText().toString();
        personalInfo[1] = pwtext.getText().toString();
        //------------------아이디 패스워드 공백 제거 --------------
        personalInfo[0] = personalInfo[0].trim();
        personalInfo[1] = personalInfo[1].trim();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success){ // 로그인 성공시
                        String u_id = jsonResponse.getString("u_id");
                        Intent intent = new Intent(LoginActivity.this,MainMenu2Activity.class);
                        intent.putExtra("u_id",u_id);               //putExtra 가 다음 액티비티로 값같은거 보내주는 건가봄
                        LoginActivity.this.startActivity(intent);
                        finish();
                    }else{//로그인 실패시
                        Toast.makeText(getApplicationContext(),"잘못된 사번 또는 비밀번호입니다.",Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(personalInfo[0], personalInfo[1], responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
        log_check = (CheckBox)findViewById(R.id.log_check);
        if(log_check.isChecked())                         // 로그인 버튼을 눌렀을때 아이디 저장하기가 체크 되어 있다면
            isChecked = true;                            //isChecked 변수를 true로 바꿈 , 기본적으로는 false임

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();         // Editor를 연결해줌 이 Editor로 값을 저장함
        if(isChecked){                                      //로그인시 아이디 저장하기를 선택해서 체크박스 체크가 되었다면
            String value = emp_num.getText().toString()   ;  //editText에 입력한 값을 String형태로 형변환을 시킨뒤 value에 저장
            editor.putString("id",value);             //id 라는 이름으로 value값을 editor에 저장
            editor.commit();                    //save를 완료 해라 즉, editor.putString()을 완료하라는 의미
        }else{
            String value = "";                              // 체크 되어있지 않았다면 빈값을 넣음
            editor.putString("id",value);
            editor.commit();
        }
    }
}
