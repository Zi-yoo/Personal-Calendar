package com.example.ziyoo.calender;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.http.AsyncHttpClientMiddleware;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.b.name;
import cn.bmob.v3.datatype.BmobReturn;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class Fragment2 extends Fragment {

    private TextView textView,textView2,content,time;
    private ListView listView;
    private Spinner spinner;
    private String viewgrade,name,strflag1 = "普通",strflag2 = "重要",strflag3 = "紧急",strflag4 = "创建时间";
    private BmobQuery<managerdata> query = new BmobQuery<managerdata>();
    private SimpleAdapter adapter;
    private android.support.constraint.ConstraintLayout mlayout;
    private boolean online = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view1 = inflater.inflate(R.layout.fragment_fragment2,container,false);
        View view2 = inflater.inflate(R.layout.fragment_fragment2_1,container,false);

        textView = view1.findViewById(R.id.textView5);
        textView2 = view2.findViewById(R.id.textView4);
        spinner = view2.findViewById(R.id.spinner2);
        listView = view2.findViewById(R.id.listview);
        content = view2.findViewById(R.id.textView6);
        time = view2.findViewById(R.id.textView7);
        mlayout = view2.findViewById(R.id.listviewlayout);



        SharedPreferences sp = getContext().getSharedPreferences("User",Context.MODE_MULTI_PROCESS);
        online = sp.getBoolean("online",false);
        name = sp.getString("user",null);

        if(online){
            textView2.setText("欢迎回来： "+name);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    viewgrade = spinner.getSelectedItem().toString();
                    switch (viewgrade){
                        case "普通":
                            query.addWhereEqualTo("id",name);
                            query.addWhereEqualTo("grade",viewgrade);
                            query.findObjects(new FindListener<managerdata>() {
                                @Override
                                public void done(final List<managerdata> list, BmobException e) {
                                    if (e == null){
                                        Toast.makeText(getActivity(),"查找完成",Toast.LENGTH_LONG).show();
                                        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                                        for (managerdata md : list){
                                            HashMap<String, Object> map = new HashMap<String, Object>();
                                            map.put("content",md.getContent());
                                            map.put("time",md.getTime());
                                            map.put("objid",md.getObjectId().toString());
                                            data.add(map);
                                        }
                                        adapter = new SimpleAdapter(getActivity(),data,R.layout.listview,new String[]{"content","time"},new int[]{R.id.textView6,R.id.textView7});
                                        listView.setAdapter(adapter);
                                    }else {
                                        Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                                    HashMap<String,Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
                                    String objid = map.get("objid").toString();
                                    managerdata mdata = new managerdata();
                                    mdata.setObjectId(objid);
                                    mdata.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null){
                                                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(getActivity(),"error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                    query.addWhereEqualTo("id",name);
                                    query.addWhereEqualTo("grade",viewgrade);
                                    query.findObjects(new FindListener<managerdata>() {
                                        @Override
                                        public void done(final List<managerdata> list, BmobException e) {
                                            if (e == null){
                                                Toast.makeText(getActivity(),"查找完成",Toast.LENGTH_LONG).show();
                                                List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                                                for (managerdata md : list){
                                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                                    map.put("content",md.getContent());
                                                    map.put("time",md.getTime());
                                                    map.put("objid",md.getObjectId().toString());
                                                    data.add(map);
                                                }
                                                adapter = new SimpleAdapter(getActivity(),data,R.layout.listview,new String[]{"content","time"},new int[]{R.id.textView6,R.id.textView7});
                                                listView.setAdapter(adapter);
                                            }else {
                                                Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });




                                }
                            });
                            break;

                        case "重要":
                            query.addWhereEqualTo("id",name);
                            query.addWhereEqualTo("grade",viewgrade);
                            query.findObjects(new FindListener<managerdata>() {
                                @Override
                                public void done(List<managerdata> list, BmobException e) {
                                    if (e == null){
                                        Toast.makeText(getActivity(),"查找完成",Toast.LENGTH_LONG).show();
                                        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                                        for (managerdata md : list){
                                            HashMap<String, Object> map = new HashMap<String, Object>();
                                            map.put("content",md.getContent());
                                            map.put("time",md.getTime());
                                            map.put("objid",md.getObjectId().toString());
                                            data.add(map);
                                        }
                                        adapter = new SimpleAdapter(getActivity(),data,R.layout.listview,new String[]{"content","time"},new int[]{R.id.textView6,R.id.textView7});
                                        listView.setAdapter(adapter);
                                    }else {
                                        Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                                    HashMap<String,Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
                                    String objid = map.get("objid").toString();
                                    managerdata mdata = new managerdata();
                                    mdata.setObjectId(objid);
                                    mdata.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null){
                                                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(getActivity(),"error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                    query.addWhereEqualTo("id",name);
                                    query.addWhereEqualTo("grade",viewgrade);
                                    query.findObjects(new FindListener<managerdata>() {
                                        @Override
                                        public void done(final List<managerdata> list, BmobException e) {
                                            if (e == null){
                                                Toast.makeText(getActivity(),"查找完成",Toast.LENGTH_LONG).show();
                                                List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                                                for (managerdata md : list){
                                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                                    map.put("content",md.getContent());
                                                    map.put("time",md.getTime());
                                                    map.put("objid",md.getObjectId().toString());
                                                    data.add(map);
                                                }
                                                adapter = new SimpleAdapter(getActivity(),data,R.layout.listview,new String[]{"content","time"},new int[]{R.id.textView6,R.id.textView7});
                                                listView.setAdapter(adapter);
                                            }else {
                                                Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });




                                }
                            });
                            break;

                        case "紧急":
                            query.addWhereEqualTo("id",name);
                            query.addWhereEqualTo("grade",viewgrade);
                            query.findObjects(new FindListener<managerdata>() {
                                @Override
                                public void done(List<managerdata> list, BmobException e) {
                                    if (e == null){
                                        Toast.makeText(getActivity(),"查找完成",Toast.LENGTH_LONG).show();
                                        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                                        for (managerdata md : list){
                                            HashMap<String, Object> map = new HashMap<String, Object>();
                                            map.put("content",md.getContent());
                                            map.put("time",md.getTime());
                                            map.put("objid",md.getObjectId().toString());
                                            data.add(map);
                                        }
                                        adapter = new SimpleAdapter(getActivity(),data,R.layout.listview,new String[]{"content","time"},new int[]{R.id.textView6,R.id.textView7});
                                        listView.setAdapter(adapter);
                                    }else {
                                        Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                                    HashMap<String,Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
                                    String objid = map.get("objid").toString();
                                    managerdata mdata = new managerdata();
                                    mdata.setObjectId(objid);
                                    mdata.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null){
                                                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(getActivity(),"error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                    query.addWhereEqualTo("id",name);
                                    query.addWhereEqualTo("grade",viewgrade);
                                    query.findObjects(new FindListener<managerdata>() {
                                        @Override
                                        public void done(final List<managerdata> list, BmobException e) {
                                            if (e == null){
                                                Toast.makeText(getActivity(),"查找完成",Toast.LENGTH_LONG).show();
                                                List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                                                for (managerdata md : list){
                                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                                    map.put("content",md.getContent());
                                                    map.put("time",md.getTime());
                                                    map.put("objid",md.getObjectId().toString());
                                                    data.add(map);
                                                }
                                                adapter = new SimpleAdapter(getActivity(),data,R.layout.listview,new String[]{"content","time"},new int[]{R.id.textView6,R.id.textView7});
                                                listView.setAdapter(adapter);
                                            }else {
                                                Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });




                                }
                            });
                            break;

                        case "全部":
                            BmobQuery<managerdata> querr = new BmobQuery<managerdata>();
                            querr.addWhereEqualTo("id",name);
                            querr.findObjects(new FindListener<managerdata>() {
                                @Override
                                public void done(List<managerdata> list, BmobException e) {
                                    if (e == null){
                                        Toast.makeText(getActivity(),"查找完成",Toast.LENGTH_LONG).show();
                                        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                                        for (managerdata md : list){
                                            HashMap<String, Object> map = new HashMap<String, Object>();
                                            map.put("content",md.getContent());
                                            map.put("time",md.getTime());
                                            map.put("objid",md.getObjectId().toString());
                                            data.add(map);
                                        }
                                        adapter = new SimpleAdapter(getActivity(),data,R.layout.listview,new String[]{"content","time"},new int[]{R.id.textView6,R.id.textView7});
                                        listView.setAdapter(adapter);
                                    }else{
                                        Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                                    HashMap<String,Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
                                    String objid = map.get("objid").toString();
                                    managerdata mdata = new managerdata();
                                    mdata.setObjectId(objid);
                                    mdata.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null){
                                                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(getActivity(),"error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                    query.addWhereEqualTo("id",name);
                                    query.addWhereEqualTo("grade",viewgrade);
                                    query.findObjects(new FindListener<managerdata>() {
                                        @Override
                                        public void done(final List<managerdata> list, BmobException e) {
                                            if (e == null){
                                                Toast.makeText(getActivity(),"查找完成",Toast.LENGTH_LONG).show();
                                                List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                                                for (managerdata md : list){
                                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                                    map.put("content",md.getContent());
                                                    map.put("time",md.getTime());
                                                    map.put("objid",md.getObjectId().toString());
                                                    data.add(map);
                                                }
                                                adapter = new SimpleAdapter(getActivity(),data,R.layout.listview,new String[]{"content","time"},new int[]{R.id.textView6,R.id.textView7});
                                                listView.setAdapter(adapter);
                                            }else {
                                                Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });




                                }
                            });
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(online){
            return view2;
        }else{
            return view1;
        }

    }



    public class querydata{


        public List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        public querydata(String name){
            BmobQuery<managerdata> query = new BmobQuery<managerdata>();
            query.addWhereEqualTo("id",name);
            query.findObjects(new FindListener<managerdata>() {
                @Override
                public void done(List<managerdata> list, BmobException e) {
                    if (e == null){

                        for (managerdata md : list){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("content",md.getContent());
                            map.put("time",md.getTime());
                            data.add(map);
                        }
                    }else{
                        Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        public List<HashMap<String, Object>> getData(){
            return data;
        }




    }


    private void querr(String name){



        BmobQuery<managerdata> query = new BmobQuery<managerdata>();
        query.addWhereEqualTo("id",name);
        query.findObjects(new FindListener<managerdata>() {
            @Override
            public void done(List<managerdata> list, BmobException e) {

                if (e == null){
                    List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

                    for (managerdata md : list){
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("content",md.getContent());
                        map.put("time",md.getTime());
                        data.add(map);
                    }


                }else{
                    Toast.makeText(getActivity(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                }




            }
        });




    }

   /* private List<HashMap<String, Object>> refreshdata(List<HashMap<String, Object>> data){

        data = null;
        return data;

    }*/

    private SimpleAdapter setadapter(SimpleAdapter adapter,List<HashMap<String, Object>> data){

        adapter = new SimpleAdapter(getActivity(),data,R.layout.listview,new String[]{"content","time"},new int[]{R.id.textView6,R.id.textView7});

        return adapter;
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



                            /*querydata qd = new querydata(name);
                           data = qd.getData();
                           if (data.size()==0){
                               Toast.makeText(getActivity(),"null",Toast.LENGTH_LONG).show();
                           }else{
                               Toast.makeText(getActivity(),"have",Toast.LENGTH_LONG).show();
                           }
                           SimpleAdapter adapter = new SimpleAdapter(getActivity(),data,R.layout.listview,new String[]{"content","time"},new int[]{R.id.textView6,R.id.textView7});
                           listView.setAdapter(adapter);*/

    //end
}
