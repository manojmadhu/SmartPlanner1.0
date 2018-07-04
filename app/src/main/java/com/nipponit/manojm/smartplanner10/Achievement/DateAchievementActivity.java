package com.nipponit.manojm.smartplanner10.Achievement;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nipponit.manojm.smartplanner10.Achievement.CategoryFigure.CategoryAchievementWeekFigureActivity;
import com.nipponit.manojm.smartplanner10.CategoryWeekFigureActivity;
import com.nipponit.manojm.smartplanner10.DateAdapter;
import com.nipponit.manojm.smartplanner10.DateClass;
import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateAchievementActivity extends AppCompatActivity {

    GridView dategrid;
    ArrayList<DateAchievementClass> dateClassArrayList = new ArrayList<>();
    DateAchievementAdapter dateAdapter = null;
    private  Calendar calendar;
    DB_Class db_class;
    Fonts fonts;
    static String scode,ccode,SimSerial,TDate;
    String TargetDate,year,Month,Week,YEAR,MONTH,WEEK,MYAREA;
    Double total=0.00,sale=0.00,weekTarget=0.00;
    Dialog dialog;
    TextView lblTarget,lblAssingTarget,lblyear,lblmonth,lblweek,lblw,lblday,lbly,lblm;
    Spinner spnyear,spnmonth,spnweek;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        YEAR = getIntent().getStringExtra("YEAR");
        MONTH = getIntent().getStringExtra("MONTH");
        WEEK = getIntent().getStringExtra("WEEK");
        TDate = getIntent().getStringExtra("TDate");
        weekTarget = Double.parseDouble(getIntent().getStringExtra("WTarget"));
        MYAREA = getIntent().getStringExtra("scode");


        db_class = new DB_Class(getApplicationContext());
        fonts=new Fonts(getApplicationContext());

        setContentView(R.layout.activity_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        lbl2.setText("My Week Achievement");

        lblTarget = (TextView)findViewById(R.id.lbltottarget);
        lblTarget.setTypeface(fonts.SetType("ubuntu-bold"));

        lblAssingTarget = (TextView)findViewById(R.id.lbltotsale);
        lblAssingTarget.setTypeface(fonts.SetType("ubuntu-bold"));




        lblyear = (TextView)findViewById(R.id.lblyear);
        lblyear.setTypeface(fonts.SetType("ubuntu-reg"));
        lblmonth = (TextView)findViewById(R.id.lblmonth);
        lblmonth.setTypeface(fonts.SetType("ubuntu-reg"));
        lblweek =(TextView) findViewById(R.id.lblweek);
        lblweek.setTypeface(fonts.SetType("ubuntu-reg"));

        lbly=(TextView)findViewById(R.id.lbly);
        lbly.setTypeface(fonts.SetType("ubuntu-reg"));
        lblm=(TextView)findViewById(R.id.lblm);
        lblm.setTypeface(fonts.SetType("ubuntu-reg"));
        lblw = (TextView)findViewById(R.id.lblw);
        lblw.setTypeface(fonts.SetType("ubuntu-reg"));



        //Get gridview object from xml file
        dategrid = (GridView) findViewById(R.id.gridView_week);
        dategrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String month_date = dateClassArrayList.get(position).getDate_();
                String DateTarget = dateClassArrayList.get(position).getTarget_().split("Rs.")[1].replace(",","");
                String date = String.format("%02d",Integer.parseInt(month_date.split(" ")[1].toString().trim()));
                String TDate = lblyear.getText().toString().trim()+lblmonth.getText().toString().trim()+ lblweek.getText().toString().trim() + date;
                Intent intent = new Intent(DateAchievementActivity.this,CategoryAchievementWeekFigureActivity.class);
                intent.putExtra("TDate",TDate);
                intent.putExtra("WTarget", DateTarget);
                intent.putExtra("scode",MYAREA);
                intent.putExtra("Day_",dateClassArrayList.get(position).getDay_());
                startActivity(intent);

            }
        });

        /** initialize the grid view **/

        year = TDate.substring(0,4); Month = TDate.substring(4,6); Week=TDate.substring(6,7);
        lblyear.setText(String.valueOf(year));lblmonth.setText(Month);lblweek.setText(Week);
        lblTarget.setText(ConvertToLKR(weekTarget));
        TargetDate = year+Month;

    }






    class AsyncDate extends AsyncTask<String,String,String>{
        ProgressDialog prgdialog;
        Double cmpTARGET=0.00,mySALE=0.00;

        String Day="";
        SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(DateAchievementActivity.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
            db_class = new DB_Class(getApplicationContext());
            dategrid.setAdapter(null);
            if(dateAdapter!=null){
                dateAdapter.clear();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<subdayclass> data = db_class.Set_Day_Achievement(YEAR,MONTH,Week);

            if(data!=null && data.size()>0){
                for (int i=0;i<data.size();i++) {

                    double precentage=0.00;String New_precentage="0.00";

                    String day_ = data.get(i).getDay_();
                    Double target_ = Double.parseDouble(data.get(i).getTarget_());
                    Double sale_ =  Double.parseDouble(data.get(i).getSale_());

                    cmpTARGET = cmpTARGET+target_;
                    mySALE = mySALE+sale_;

                    if(target_ != 0.00) {
                        precentage = (sale_ / target_) * 100;
                        DecimalFormat df = new DecimalFormat("#0.00");
                        New_precentage = df.format(precentage);
                    }

                    // android get Day of the date
                    try {
                        Date date = sdate.parse(year+"-"+Month+"-"+String.format("%02d",Integer.parseInt(day_)));
                        Day = (String) android.text.format.DateFormat.format("EEEE",date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    dateClassArrayList.add(new DateAchievementClass("Day "+day_,Day,ConvertToLKR(target_),ConvertToLKR(sale_), New_precentage+" %"));
                    dateAdapter=new DateAchievementAdapter(getApplicationContext(),dateClassArrayList);
                }
            }
            else{
                for(int i=1;i<=5;i++){

                    // android get Day of the date
                    try {
                        Date date = sdate.parse(year+"-"+Month+"-"+String.format("%02d",i));
                        Day = (String) android.text.format.DateFormat.format("EEEE",date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    dateClassArrayList.add(new DateAchievementClass("Day "+String.valueOf(i),Day,"Rs.0.00","Rs.0.00","0 %"));
                    dateAdapter=new DateAchievementAdapter(getApplicationContext(),dateClassArrayList);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lblTarget.setText(ConvertToLKR(cmpTARGET));
            lblAssingTarget.setText(ConvertToLKR(mySALE));
            dateAdapter.notifyDataSetChanged();
            dategrid.setAdapter(dateAdapter);
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

    @Override
    protected void onResume() {
        super.onResume();
        new AsyncDate().execute();
    }
}
