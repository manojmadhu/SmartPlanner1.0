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
import android.widget.Toast;

import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WeekActivity extends AppCompatActivity {

    GridView weekgrid;
    ArrayList<WeekClass> weekClassArrayList = new ArrayList<>();
    WeekAdapter weekAdapter = null;
    private  Calendar calendar;
    DB_Class db_class;
    Fonts fonts;
    String TargetDate,Month,SimSerial;
    int year;
    Double total=0.00,sale=0.00;
    Dialog dialog;
    TextView lblTarget,lblAssingTarget,lblyear,lblmonth,lblweek,lblw,lbld,lblday,lbly,lblm;
    Spinner spnyear,spnmonth,spnweek;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimSerial = getIntent().getStringExtra("SimSerial");
        db_class = new DB_Class(getApplicationContext());
        fonts=new Fonts(getApplicationContext());

        setContentView(R.layout.activity_week);
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


        //Get gridview object from xml file
        weekgrid = (GridView) findViewById(R.id.gridView_week);
        weekgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                db_class = new DB_Class(getApplicationContext());


                String week = weekClassArrayList.get(position).getWeekno_();
                String weekTarget = weekClassArrayList.get(position).getTarget_().split("Rs.")[1].replace(",","");
                String TDate = lblyear.getText().toString().trim()+lblmonth.getText().toString().trim()+week.split(" ")[1].toString().trim();

                boolean islock = db_class.checkLock(TDate.substring(0,6));
                if(!islock) {
                    Intent intent = new Intent(WeekActivity.this, DateActivity.class);
                    intent.putExtra("TDate", TDate);
                    intent.putExtra("WTarget", weekTarget);
                    intent.putExtra("SimSerial", SimSerial);
                    startActivity(intent);
                }else
                    Toast.makeText(WeekActivity.this, "Sorry selected month has been blocked.", Toast.LENGTH_SHORT).show();

            }
        });

        /** initialize the grid view **/
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        Month = String.format("%02d",month);
        TargetDate = String.valueOf(year)+Month;
        lblyear.setText(String.valueOf(year));lblmonth.setText(Month);

    }






    class AsyncWeek extends AsyncTask<String,String,String>{
        ProgressDialog prgdialog;
        Double AreaTarget=0.00,AssignTarget=0.00;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            prgdialog= new ProgressDialog(WeekActivity.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);

            db_class = new DB_Class(getApplicationContext());
            weekgrid.setAdapter(null);
            if(weekAdapter!=null){
                weekAdapter.clear();
            }


            prgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                Cursor cur = db_class.ReturnAreaTarget(TargetDate);

                if (cur != null && cur.getCount() > 0) {
                    while (cur.moveToNext()) {

                        String week_ = cur.getString(0);
                        String target_ = cur.getString(1);

                        String assignTarget = "0.00";

                        Cursor cursor_weekTarget = db_class.ReturnWeek_AssignTarget(TargetDate, week_);

                        if (cursor_weekTarget != null && cursor_weekTarget.getCount() > 0) {
                            while (cursor_weekTarget.moveToNext()) {
                                assignTarget = cursor_weekTarget.getString(1);
                            }
                        }

                        AreaTarget = AreaTarget + Double.parseDouble(target_);
                        AssignTarget = AssignTarget + Double.parseDouble(assignTarget);

                        weekClassArrayList.add(new WeekClass("WEEK " + week_, ConvertToLKR(Double.parseDouble(target_)), ConvertToLKR(Double.parseDouble(assignTarget))));
                        weekAdapter = new WeekAdapter(getApplicationContext(), weekClassArrayList);
                    }
                } else {
                    for (int i = 1; i <= 4; i++) {
                        weekClassArrayList.add(new WeekClass("WEEK " + String.valueOf(i), "Rs.0.00", "Rs.0.00"));
                        weekAdapter = new WeekAdapter(getApplicationContext(), weekClassArrayList);
                    }
                }
            }catch (Exception ex){
                //Log.w("error",ex.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lblTarget.setText(ConvertToLKR(AreaTarget));
            lblAssingTarget.setText(ConvertToLKR(AssignTarget));
            if(weekAdapter!=null) {
                weekAdapter.notifyDataSetChanged();
                weekgrid.setAdapter(weekAdapter);
            }
            prgdialog.dismiss();

        }
    }



    private void selectDate(int year_,String month_){
        dialog=new Dialog(WeekActivity.this,R.style.FullHeightDialog);
        dialog.setContentView(R.layout.date_selector_download);
        Button btnadd = (Button)dialog.findViewById(R.id.btnadd);
        TextView lbl = (TextView) dialog.findViewById(R.id.textView);
        TextView lbly = (TextView)dialog.findViewById(R.id.lblyear);
        TextView lblm = (TextView)dialog.findViewById(R.id.lblmonth);
        lbl.setTypeface(fonts.SetType("ubuntu-reg"));
        btnadd.setTypeface(fonts.SetType("ubuntu-bold"));
        lbly.setTypeface(fonts.SetType("ubuntu-reg"));
        lblm.setTypeface(fonts.SetType("ubuntu-reg"));


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
                String month = spnmonth.getSelectedItem().toString();
                String year = spnyear.getSelectedItem().toString();
                String TarDate = year+month;
                lblyear.setText(year);lblmonth.setText(month);
                TargetDate = year+month;
                new AsyncWeek().execute(TargetDate);
                dialog.dismiss();
            }
        });


        dialog.show();
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
        new AsyncWeek().execute();
    }
}
