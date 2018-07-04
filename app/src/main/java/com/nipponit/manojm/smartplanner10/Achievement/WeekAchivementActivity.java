package com.nipponit.manojm.smartplanner10.Achievement;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;
import com.nipponit.manojm.smartplanner10.WeekActivity;
import com.nipponit.manojm.smartplanner10.WeekAdapter;
import com.nipponit.manojm.smartplanner10.WeekClass;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class WeekAchivementActivity extends AppCompatActivity {

    Fonts fonts;
    TextView lblTarget,lblAssingTarget,lblyear,lblmonth,lblweek,lblw,lbld,lblday,lbly,lblm;
    GridView weekgrid;

    ArrayList<WeekAchievementClass> weekClassArrayList = new ArrayList<>();
    WeekAchievementAdapter weekAdapter = null;

    private  Calendar calendar;
    int year;String TargetDate,Month,SimSerial,YEAR,MONTH,MYAREA;
    Dialog dialog;
    DB_Class db_class;
    Spinner spnyear,spnmonth,spnweek;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_achivement);
        fonts=new Fonts(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        YEAR = getIntent().getStringExtra("YEAR");
        MONTH = getIntent().getStringExtra("MONTH");
        MYAREA=getIntent().getStringExtra("scode");

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        TextView mTitle = (TextView)toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(fonts.SetType("ubuntu-reg"));
        mTitle.setText("Smart Planner");

        TextView lbl1=(TextView)findViewById(R.id.labelheader);
        lbl1.setTypeface(fonts.SetType("ubuntu-reg"));
        lbl1.setText("My Target");

        TextView lbl2=(TextView)findViewById(R.id.labelheader1);
        lbl2.setTypeface(fonts.SetType("ubuntu-reg"));
        lbl2.setText("My Achievement");

        lblTarget = (TextView)findViewById(R.id.lbltottarget);
        lblTarget.setTypeface(fonts.SetType("ubuntu-bold"));

        lblAssingTarget = (TextView)findViewById(R.id.lbltotsale);
        lblAssingTarget.setTypeface(fonts.SetType("ubuntu-bold"));


        lbly=(TextView)findViewById(R.id.lbly);
        lbly.setTypeface(fonts.SetType("ubuntu-reg"));
        lblm=(TextView)findViewById(R.id.lblm);
        lblm.setTypeface(fonts.SetType("ubuntu-reg"));

        lblyear = (TextView)findViewById(R.id.lblyear);
        lblyear.setTypeface(fonts.SetType("ubuntu-reg"));
        lblmonth = (TextView)findViewById(R.id.lblmonth);
        lblmonth.setTypeface(fonts.SetType("ubuntu-reg"));



        //Get gridview object from xml file
        weekgrid = (GridView) findViewById(R.id.gridView_week);
        weekgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String week = weekClassArrayList.get(position).getWeekno_();
                String weekTarget = weekClassArrayList.get(position).getTarget_().split("Rs.")[1].replace(",","");
                String TDate = lblyear.getText().toString().trim()+lblmonth.getText().toString().trim()+week.split(" ")[1].toString().trim();

                Intent int_day = new Intent(WeekAchivementActivity.this,DateAchievementActivity.class);
                int_day.putExtra("YEAR",YEAR);
                int_day.putExtra("MONTH",MONTH);
                int_day.putExtra("WEEK",week);
                int_day.putExtra("TDate",TDate);
                int_day.putExtra("WTarget",weekTarget);
                int_day.putExtra("scode",MYAREA);
                startActivity(int_day);
            }
        });

        new AsyncWeek().execute();


        /** initialize the grid view **/
//        calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        Month = String.format("%02d",month);
//        TargetDate = String.valueOf(year)+Month;
        lblyear.setText(YEAR);lblmonth.setText(MONTH);


    }






    class AsyncWeek extends AsyncTask<String,String,String> {
        ProgressDialog prgdialog;
        Double cmpTARGET=0.00,mySALE=0.00;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(WeekAchivementActivity.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
            db_class = new DB_Class(getApplicationContext());
            weekgrid.setAdapter(null);
            if(weekAdapter!=null){
                weekAdapter.clear();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<subweekclass> data = db_class.Set_Week_Achievement(YEAR,MONTH);

            if(data!=null && data.size()>0){
                for (int i=0;i<data.size();i++) {

                    double precentage=0.00; String New_precentage="0.00";

                    String week_ = data.get(i).getWeek_();
                    Double TARGET = Double.parseDouble(data.get(i).getTarget_());
                    Double SALE = Double.parseDouble(data.get(i).getSale_());

                    cmpTARGET = cmpTARGET + TARGET;
                    mySALE = mySALE + SALE;

                    if(TARGET != 0.00) {
                        precentage = (SALE / TARGET) * 100;
                        DecimalFormat df = new DecimalFormat("#0.00");
                        New_precentage = df.format(precentage);


                    }

                    weekClassArrayList.add(new WeekAchievementClass("WEEK "+week_,ConvertToLKR(TARGET),ConvertToLKR(SALE),New_precentage+" %"));
                    weekAdapter=new WeekAchievementAdapter(getApplicationContext(),weekClassArrayList);

                }
            }
            else{
                for(int i=1;i<=4;i++){
                    weekClassArrayList.add(new WeekAchievementClass("WEEK "+String.valueOf(i),"Rs.0.00","Rs.0.00","0 %"));
                    weekAdapter=new WeekAchievementAdapter(getApplicationContext(),weekClassArrayList);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lblTarget.setText(ConvertToLKR(cmpTARGET));
            lblAssingTarget.setText(ConvertToLKR(mySALE));
            weekAdapter.notifyDataSetChanged();
            weekgrid.setAdapter(weekAdapter);
            prgdialog.dismiss();

        }
    }



    private String ConvertToLKR(Double target){
        String currency="Rs.0.00";
        try{
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("Rs.");
            ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
            currency = nf.format(target).trim();
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return currency;
    }

}
