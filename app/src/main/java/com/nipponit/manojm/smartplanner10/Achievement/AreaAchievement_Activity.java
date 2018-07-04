package com.nipponit.manojm.smartplanner10.Achievement;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class AreaAchievement_Activity extends AppCompatActivity {

    Fonts fonts;
    TextView lblTarget,lblAssingTarget,lblyear,lblmonth,lblweek,lblw,lbld,lblday,lbly,lblm,lbl_achievement,mArea;
    GridView weekgrid;
    private Calendar calendar;
    int year;String TargetDate,Month,SimSerial,myarea,myareaName;
    Dialog dialog;
    Spinner spnyear,spnmonth,spnweek,spnarea;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw,adapterarea;
    DB_Class db_class;
    private CustomGauge gauge_area;
    double precentage=0.00;int i=0,New_Precentage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_achievement);
        fonts=new Fonts(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        TextView mTitle = (TextView)toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(fonts.SetType("ubuntu-reg"));
        mTitle.setText("Smart Planner");

        mArea = (TextView)toolbar.findViewById(R.id.toolbar_area);
        mArea.setTypeface(fonts.SetType("ubuntu-reg"));
        mArea.setText("");


        gauge_area = (CustomGauge)findViewById(R.id.gauge2);
        lbl_achievement = (TextView)findViewById(R.id.txt_achievement);
        lbl_achievement.setTypeface(fonts.SetType("ubuntu-bold"));

        TextView lbl1=(TextView)findViewById(R.id.labelheader);
        lbl1.setTypeface(fonts.SetType("ubuntu-reg"));
        lbl1.setText("My Target");

        TextView lbl2=(TextView)findViewById(R.id.labelheader1);
        lbl2.setTypeface(fonts.SetType("ubuntu-reg"));

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectDate(year,Month);

            }
        });


        lbl_achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intach_week = new Intent(AreaAchievement_Activity.this,WeekAchivementActivity.class);
                Intach_week.putExtra("YEAR",lblyear.getText().toString());
                Intach_week.putExtra("MONTH",lblmonth.getText().toString());
                Intach_week.putExtra("scode",myarea);
                startActivity(Intach_week);

            }
        });



        /** initialize the calendar view **/
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        Month = String.format("%02d",month);
        TargetDate = String.valueOf(year)+Month;
        lblyear.setText(String.valueOf(year));lblmonth.setText(Month);

        db_class = new DB_Class(getApplicationContext());
        ArrayList<String> area = db_class.returnArea();
        myarea = area.get(0);
        myareaName = area.get(1);
        mArea.setText(" ("+myareaName+")");
        new AsyncAchievement().execute("","",myarea);


        //LoadPrecentage(Integer.toString(year),Month);
    }

    private void LoadPrecentage(String year,String month){
        try{
            db_class = new DB_Class(getApplicationContext());
            String scode = db_class.returnScode();
            Cursor cursor = db_class.Get_Area_Achievement(year+month,scode);
            double target = 1.00,sale=0.00,temptarget=1.00;
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    target = cursor.getDouble(0);
                    sale = cursor.getDouble(1);
                    if(target==0.00)
                        temptarget = 1.00;
                    else
                        temptarget = target;

                    precentage = (sale/temptarget)*100;

                    DecimalFormat df = new DecimalFormat("#0");
                    New_Precentage = Integer.parseInt(df.format(precentage));

                }
            }

            lblTarget.setText(ConvertToLKR(target));
            lblAssingTarget.setText(ConvertToLKR(sale));
            lbl_achievement.setText("0 %");

            gauge_area.setValue(New_Precentage);
            lbl_achievement.setText(Integer.toString(gauge_area.getValue())+" %");

//            new Thread(){
//                public void run(){
//                    for(i=0;i<New_Precentage;i++){
//                        try{
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    gauge_area.setValue(New_Precentage);
//                                    lbl_achievement.setText(Integer.toString(gauge_area.getValue())+" %");
//                                }
//                            });
//                            Thread.sleep(10);
//                        }catch (InterruptedException e){
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }.start();


        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
    }


    private void selectDate(int year_,String month_){
        dialog=new Dialog(AreaAchievement_Activity.this,R.style.FullHeightDialog);
        dialog.setContentView(R.layout.date_selector_2);
        Button btnadd = (Button)dialog.findViewById(R.id.btnadd);

        TextView lblarea= (TextView)dialog.findViewById(R.id.lblarea);
        TextView lbl = (TextView)dialog.findViewById(R.id.textView);
        TextView lbly = (TextView)dialog.findViewById(R.id.lblyear);
        TextView lblm = (TextView)dialog.findViewById(R.id.lblmonth);
        lbl.setTypeface(fonts.SetType("ubuntu-reg"));
        btnadd.setTypeface(fonts.SetType("ubuntu-bold"));
        lblarea.setTypeface(fonts.SetType("ubuntu-bold"));
        lbly.setTypeface(fonts.SetType("ubuntu-reg"));
        lblm.setTypeface(fonts.SetType("ubuntu-reg"));


        spnarea = (Spinner)dialog.findViewById(R.id.spnarea);
        db_class = new DB_Class(getApplicationContext());
        ArrayList<String> areaArray = new ArrayList<>();
        areaArray.addAll(db_class.returnArea_ASM());
        ArrayAdapter<String> spinareaAdapter = new ArrayAdapter<String>(this,R.layout.date_dialog,areaArray);
        spinareaAdapter.setDropDownViewResource(R.layout.date_dialog);
        spnarea.setAdapter(spinareaAdapter);
        spnarea.setSelection(0);


        spnyear = (Spinner)dialog.findViewById(R.id.spnyear);
        adapter = ArrayAdapter.createFromResource(this,R.array.year_array, R.layout.date_dialog);
        adapter.setDropDownViewResource(R.layout.date_dialog);
        spnyear.setAdapter(adapter);
        spnyear.setSelection(adapter.getPosition(String.valueOf(year_)));



        spnmonth = (Spinner)dialog.findViewById(R.id.spnmonth);
        adapterm = ArrayAdapter.createFromResource(this,R.array.month_array, R.layout.date_dialog);
        adapter.setDropDownViewResource(R.layout.date_dialog);
        spnmonth.setAdapter(adapterm);
        spnmonth.setSelection(adapterm.getPosition(month_));



        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myarea =  spnarea.getSelectedItem().toString().split("-")[0];
                String month = spnmonth.getSelectedItem().toString();
                String year = spnyear.getSelectedItem().toString();
                String TarDate = year+month;
                lblyear.setText(year);lblmonth.setText(month);
                TargetDate = year+month;
                mArea.setText(" ("+spnarea.getSelectedItem().toString().split("-")[1]+")");
                new AsyncAchievement().execute(year,month,myarea);
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    class AsyncAchievement extends AsyncTask<String,String,String> {
        ProgressDialog prgdialog;
        Double AreaTarget=0.00,AssignTarget=0.00; String year,month;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(AreaAchievement_Activity.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
            db_class = new DB_Class(getApplicationContext());
            year=lblyear.getText().toString();
            month=lblmonth.getText().toString();
        }

        @Override
        protected String doInBackground(String... params) {

            db_class.Download_Achievement(year,month,params[2]);
            //db_class.Download_Month(year,month);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            prgdialog.dismiss();
            LoadPrecentage(year,month);

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
