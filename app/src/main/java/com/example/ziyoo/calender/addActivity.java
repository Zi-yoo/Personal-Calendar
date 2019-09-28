package com.example.ziyoo.calender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class addActivity extends AppCompatActivity {


    private Button button,button2;
    private EditText id,content,time;
    private Spinner spinner;
    private String grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Bmob.initialize(this, "092b4d092463f2398ef2bec130147397");


        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        id = findViewById(R.id.editText);
        content = findViewById(R.id.editText2);
        spinner = findViewById(R.id.spinner);
        time = findViewById(R.id.editText5);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                grade = spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Intent getintent = getIntent();
        Bundle extras = getintent.getExtras();
        int y = extras.getInt("year");
        int m = extras.getInt("month")+1;
        int d = extras.getInt("day");
        String name = extras.getString("name");

        String timestr = y+"-"+m+"-"+d;
        time.setText(timestr);
        id.setText(name);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                managerdata md = new managerdata();
                md.setId(id.getText().toString());
                md.setContent(content.getText().toString());
                md.setGrade(grade);
                md.setTime(time.getText().toString());

                if(id.getText().toString().equals("")){
                    Toast.makeText(addActivity.this,"id不能为空",Toast.LENGTH_LONG).show();
                }else if(content.getText().toString().equals("")){
                    Toast.makeText(addActivity.this,"内容不能为空",Toast.LENGTH_LONG).show();
                }else{

                    md.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null){
                                Intent intent = new Intent(addActivity.this,MainActivity.class);
                                Toast.makeText(addActivity.this,"添加日程成功",Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }else{
                                Toast.makeText(addActivity.this,"添加日程失败"+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }





            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(addActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    public class managerdata extends BmobObject {

        private String id;
        private String content;
        private String grade;
        private String time;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public String getGrade(){
            return grade;
        }
        public void setGrade(String grade) {
            this.grade = grade;
        }
        public String getTime(){
            return time;
        }
        public void setTime(String time){
            this.time = time;
        }
    }





}
