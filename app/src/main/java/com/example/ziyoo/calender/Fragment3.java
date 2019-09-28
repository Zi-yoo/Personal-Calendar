package com.example.ziyoo.calender;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class Fragment3 extends Fragment {

    private EditText name,pwd;
    private TextView userlog,allnumlog,jnumlog,znumlog,pnumlog;
    private CheckBox checkBox;
    private Button login,reg,exit;
    private String namestr,pwdstr,user;
    private int allnum,jnum,znum,pnum;
    private boolean remember = false,online = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view1 =  inflater.inflate(R.layout.fragment_fragment3, container, false);
        View view2 =  inflater.inflate(R.layout.fragment_fragment3_1, container, false);

        SharedPreferences sp1 = getContext().getSharedPreferences("User",Context.MODE_MULTI_PROCESS);
        online = sp1.getBoolean("online",false);
        user = sp1.getString("user",null);

        name = view1.findViewById(R.id.editText3);
        pwd = view1.findViewById(R.id.editText4);
        checkBox = view1.findViewById(R.id.checkBox);
        login  =view1.findViewById(R.id.button3);
        reg = view1.findViewById(R.id.button4);

        userlog = view2.findViewById(R.id.textView9);
        allnumlog = view2.findViewById(R.id.textView11);
        jnumlog = view2.findViewById(R.id.textView13);
        znumlog = view2.findViewById(R.id.textView15);
        pnumlog = view2.findViewById(R.id.textView17);

        exit = view2.findViewById(R.id.button7);


        //SharedPreferences sp = getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
        SharedPreferences sp = getContext().getSharedPreferences("User",Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor ed = sp.edit();

        pwdstr = sp.getString("password",null);
        remember = sp.getBoolean("remember",false);
        namestr = sp.getString("user",null);





        checkBox.setChecked(remember);
        name.setText(namestr);

        if(remember){
            pwd.setText(pwdstr);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namestr  =name.getText().toString();
                pwdstr = pwd.getText().toString();

                BmobQuery<manage> query = new BmobQuery<manage>();
                query.addWhereEqualTo("name",namestr);
                query.findObjects(new FindListener<manage>() {
                    @Override
                    public void done(List<manage> list, BmobException e) {
                        if(e == null){
                            for (manage ma : list){
                                String pwd = ma.getPwd().toString();
                                if(!pwdstr.equals(pwd)){
                                    Toast.makeText(getActivity(),"请检查用户名和密码是否正确",Toast.LENGTH_LONG).show();
                                }else{
                                    SharedPreferences sp = getContext().getSharedPreferences("User",Context.MODE_MULTI_PROCESS);
                                    SharedPreferences.Editor editor = sp.edit();

                                    if (checkBox.isChecked()){
                                        editor.putString("password",pwdstr);
                                    }else{
                                        editor.putString("password",null);
                                    }

                                    remember = checkBox.isChecked();
                                    editor.putBoolean("remember",remember);
                                    editor.putBoolean("online",true);
                                    editor.putString("user",namestr);
                                    editor.commit();

                                    Toast.makeText(getActivity(),"欢迎回来:"+namestr,Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(),MainActivity.class);
                                    startActivity(intent);
                                }

                            }
                        }else{
                            Toast.makeText(getActivity(),"error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),registerActivity.class);
                startActivity(intent);
            }
        });


        if (online){

            BmobQuery<managerdata> queryall = new BmobQuery<managerdata>();
            queryall.addWhereEqualTo("id",user);
            queryall.findObjects(new FindListener<managerdata>() {
                @Override
                public void done(List<managerdata> list, BmobException e) {
                    allnumlog.setText(list.size()+"");
                }
            });

            BmobQuery<managerdata> queryj = new BmobQuery<managerdata>();
            queryj.addWhereEqualTo("id",user);
            queryj.addWhereEqualTo("grade","紧急");
            queryj.findObjects(new FindListener<managerdata>() {
                @Override
                public void done(List<managerdata> list, BmobException e) {
                    jnumlog.setText(list.size()+"");
                }
            });

            BmobQuery<managerdata> queryz = new BmobQuery<managerdata>();
            queryz.addWhereEqualTo("id",user);
            queryz.addWhereEqualTo("grade","重要");
            queryz.findObjects(new FindListener<managerdata>() {
                @Override
                public void done(List<managerdata> list, BmobException e) {
                    znumlog.setText(list.size()+"");
                }
            });

            BmobQuery<managerdata> queryp = new BmobQuery<managerdata>();
            queryp.addWhereEqualTo("id",user);
            queryp.addWhereEqualTo("grade","普通");
            queryp.findObjects(new FindListener<managerdata>() {
                @Override
                public void done(List<managerdata> list, BmobException e) {
                    pnumlog.setText(list.size()+"");
                }
            });

            userlog.setText(user);

            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = getContext().getSharedPreferences("User",Context.MODE_MULTI_PROCESS);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putBoolean("online",false);
                    ed.commit();
                    Toast.makeText(getActivity(),"期待您的下次使用",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                }
            });


        }





        if(!online){
            return view1;
        }else{
            return view2;
        }

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

    //end
}