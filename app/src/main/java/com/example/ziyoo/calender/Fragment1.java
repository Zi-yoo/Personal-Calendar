package com.example.ziyoo.calender;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;


public class Fragment1 extends Fragment {


    private CalendarView cv;
    private boolean online = false;
    private String name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment1_layout, container, false);

        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);


        cv = view.findViewById(R.id.calendarView);


        SharedPreferences sp = getContext().getSharedPreferences("User",Context.MODE_MULTI_PROCESS);
        online = sp.getBoolean("online",false);
        name = sp.getString("user",null);




        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {

                if (!online){
                    Toast.makeText(getActivity(),"请在登录后添加日程",Toast.LENGTH_LONG).show();
                }else{

                    SharedPreferences sp = getContext().getSharedPreferences("User",Context.MODE_MULTI_PROCESS);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("online",true);
                    editor.putString("user",name);
                    editor.commit();


                    Intent intent = new Intent(getActivity(),addActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("year",y);
                    bundle.putInt("month",m);
                    bundle.putInt("day",d);
                    bundle.putString("name",name);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }







            }
        });





        return view;


    }


}
