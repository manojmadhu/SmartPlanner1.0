package com.nipponit.manojm.smartplanner10;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.wifi.p2p.WifiP2pManager;
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

import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateActivity extends AppCompatActivity {

    GridView dategrid;
    ArrayList<DateClass> dateClassArrayList = new ArrayList<>();
    DateAdapter dateAdapter = null;
    private  Calendar calendar;
    DB_Class db_class;
    Fonts fonts;
    static String scode,ccode,SimSerial,TDate;
    String TargetDate,year,Month,Week;
    Double total=0.00,sale=0.00,weekTarget=0.00;
    Dialog dialog;
    TextView lblTarget,lblAssingTarget,lblyear,lblmonth,lblweek,lblw,lblday,lbly,lblm;
    Spinner spnyear,spnmonth,spnweek;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimSerial = getIntent().getStringExtra("SimSerial");
        TDate = getIntent().getStringExtra("TDate");

        weekTarget = Double.parseDouble(getIntent().getStringExtra("WTarget"));
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

        TextView lbl2=(TextView)findViewById(R.id.labelheader1);
        lbl2.setTypeface(fonts.SetType("ubuntu-reg"));

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
                Intent intent = new Intent(DateActivity.this,CategoryWeekFigureActivity.class);
                intent.putExtra("TDate",TDate);
                intent.putExtra("WTarget", DateTarget);
                intent.putExtra("ccode","");
                intent.putExtra("cname","");
                intent.putExtra("scode","");
                intent.putExtra("Day",dateClassArrayList.get(position).getDay_());
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
        Double AreaTarget=0.00,AssignTarget=0.00;
        String Day="";
        SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(DateActivity.this);
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
            Cursor cur = db_class.ReturnWeekTarget(TargetDate,Week);


            if(cur!=null && cur.getCount()>0){
                while (cur.moveToNext()) {

                    String day_ = cur.getString(0);
                    String target_ = cur.getString(1);

                    String assignTarget = "0.00";

                    Cursor cursor_dayTarget = db_class.ReturnDay_AssignTarget(TargetDate,Week,String.format("%02d",Integer.parseInt(day_)));
                    if(cursor_dayTarget!=null && cursor_dayTarget.getCount()>0){
                        while(cursor_dayTarget.moveToNext()){
                            assignTarget = cursor_dayTarget.getString(1);
                        }
                    }

                    AreaTarget = AreaTarget + Double.parseDouble(target_);
                    AssignTarget = AssignTarget + Double.parseDouble(assignTarget);

                    // android get Day of the date
                    try {
                        Date date = sdate.parse(year+"-"+Month+"-"+String.format("%02d",Integer.parseInt(day_)));
                        Day = (String) android.text.format.DateFormat.format("EEEE",date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                    dateClassArrayList.add(new DateClass("Day "+day_,Day,ConvertToLKR(Double.parseDouble(target_)),ConvertToLKR(Double.parseDouble(assignTarget))));
                    dateAdapter=new DateAdapter(getApplicationContext(),dateClassArrayList);
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

                    dateClassArrayList.add(new DateClass("Day "+String.valueOf(i),Day,"Rs.0.00","Rs.0.00"));
                    dateAdapter=new DateAdapter(getApplicationContext(),dateClassArrayList);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lblTarget.setText(ConvertToLKR(AreaTarget));
            lblAssingTarget.setText(ConvertToLKR(AssignTarget));
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
