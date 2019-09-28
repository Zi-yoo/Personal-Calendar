package com.example.ziyoo.calender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class registerActivity extends AppCompatActivity {

    private EditText name,pwd1,pwd2;
    private Button submit,cancel;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.editText6);
        pwd1 = findViewById(R.id.editText7);
        pwd2 = findViewById(R.id.editText8);
        submit = findViewById(R.id.button5);
        cancel = findViewById(R.id.button6);





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str1 = pwd1.getText().toString();
                String str2 = pwd2.getText().toString();
                String str3 = name.getText().toString();



                BmobQuery<manage> query = new BmobQuery<manage>();
                query.addWhereEqualTo("name",str3);
                query.findObjects(new FindListener<manage>() {
                    @Override
                    public void done(List<manage> list, BmobException e) {
                        if(e == null){

                            if(list.size()==0){
                                flag = false;
                            }else{
                                flag = true;
                            }

                        }else{
                            Toast.makeText(registerActivity.this,"error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });




                if(str1.length()<6||str1.length()>12){
                    Toast.makeText(registerActivity.this,"密码长度6-12",Toast.LENGTH_LONG).show();
                }else if(!str1.equals(str2)){
                    Toast.makeText(registerActivity.this,"两次密码不一致",Toast.LENGTH_LONG).show();
                }else if (str3.equals("")){
                    Toast.makeText(registerActivity.this,"用户名不能为空",Toast.LENGTH_LONG).show();
                }else if(flag){
                    Toast.makeText(registerActivity.this,"用户已经存在",Toast.LENGTH_LONG).show();

                }else{



                    manage manage = new manage();
                    manage.setName(str3);
                    manage.setPwd(str1);

                    manage.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null){
                                Toast.makeText(registerActivity.this,"用户注册成功",Toast.LENGTH_LONG).show();

                                Intent intent1 = new Intent(registerActivity.this,MainActivity.class);
                                intent1.putExtra("id","3");
                                startActivity(intent1);


                            }else{
                                Toast.makeText(registerActivity.this,"error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(registerActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });




    }

    public class manage extends BmobObject {

        private String name,pwd;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }


    }
}
