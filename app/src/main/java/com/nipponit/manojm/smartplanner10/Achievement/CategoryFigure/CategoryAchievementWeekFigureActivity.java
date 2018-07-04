package com.nipponit.manojm.smartplanner10.Achievement.CategoryFigure;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nipponit.manojm.smartplanner10.Achievement.CustomerFigure.CustomerAchievementActivity;
import com.nipponit.manojm.smartplanner10.CategoryClass;
import com.nipponit.manojm.smartplanner10.CustomCategoryAdapter;
import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.customer.CustomerActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CategoryAchievementWeekFigureActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int REQUEST_READ_PHONE_STATE=0;
    GridView gridView;
    Fonts fonts;
    TextView lblTarget;
    DB_Class db_class;
    Dialog dialog;
    Double weekTarget=0.00;
    static String scode,ccode,SimSerial,TDate,MYAREA;
    Spinner spnyear,spnmonth,spnweek;
    private  Calendar calendar;
    String TargetDate,year,Month,Week,Date,Day_;

    TextView lbly,lblm,lblyear,lblmonth,lblweek,lblw,lbld,lblday,lblAssingTarget,lblDay;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw;



    private ArrayList<CategoryAchievementClass> categoryClassArrayList = new ArrayList<>();
    CustomAchievementCategoryAdapter customCategoryAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ccode = getIntent().getStringExtra("ccode");
            String cname = getIntent().getStringExtra("cname");
            scode = getIntent().getStringExtra("scode");
            TDate = getIntent().getStringExtra("TDate");
            weekTarget = Double.parseDouble(getIntent().getStringExtra("WTarget"));
            MYAREA = getIntent().getStringExtra("scode");
            Day_ = getIntent().getStringExtra("Day_");


            db_class = new DB_Class(getApplicationContext());

            fonts = new Fonts(getApplicationContext());
            setContentView(R.layout.activity_category_figure);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setTitle("");
            toolbar.setSubtitle("");

            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setTypeface(fonts.SetType("ubuntu-reg"));
            if (cname != null)
                mTitle.setText("Smart Planner ( " + cname + " )");
            else
                mTitle.setText("Smart Planner ");

            TextView lbl1 = (TextView) findViewById(R.id.labelheader);
            lbl1.setText("My Total Day Target");
            lbl1.setTypeface(fonts.SetType("ubuntu-reg"));

            lblTarget = (TextView) findViewById(R.id.lbltottarget);
            lblTarget.setText(ConvertToLKR(weekTarget));
            lblTarget.setTypeface(fonts.SetType("ubuntu-bold"));

            TextView lbl2 = (TextView) findViewById(R.id.labelheader1);
            lbl2.setTypeface(fonts.SetType("ubuntu-reg"));
            lbl2.setText("My Total Day Achievement");
            lblAssingTarget = (TextView) findViewById(R.id.lbltotsale);
            lblAssingTarget.setTypeface(fonts.SetType("ubuntu-bold"));


            lblyear = (TextView) findViewById(R.id.lblyear);
            lblyear.setTypeface(fonts.SetType("ubuntu-reg"));
            lblmonth = (TextView) findViewById(R.id.lblmonth);
            lblmonth.setTypeface(fonts.SetType("ubuntu-reg"));
            lbly = (TextView) findViewById(R.id.lbly);
            lbly.setTypeface(fonts.SetType("ubuntu-reg"));
            lblm = (TextView) findViewById(R.id.lblm);
            lblm.setTypeface(fonts.SetType("ubuntu-reg"));
            lblweek = (TextView) findViewById(R.id.lblweek);
            lblweek.setTypeface(fonts.SetType("ubuntu-reg"));
            lblw = (TextView) findViewById(R.id.lblw);
            lblw.setTypeface(fonts.SetType("ubuntu-reg"));
            lbld = (TextView) findViewById(R.id.lbld);
            lbld.setTypeface(fonts.SetType("ubuntu-reg"));
            lblday = (TextView) findViewById(R.id.lblday);
            lblday.setTypeface(fonts.SetType("ubuntu-reg"));

            lblDay = (TextView)findViewById(R.id.lblDay);
            lblDay.setTypeface(fonts.SetType("ubuntu-reg"));

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.INVISIBLE);


            //Get gridview object from xml file
            gridView = (GridView) findViewById(R.id.gridView1);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {


                    if (!MYAREA.equals("")) {
                        String catid = categoryClassArrayList.get(position).getCategoryID_();
                        String cattext = categoryClassArrayList.get(position).getCategory_();
                        String date_ = year + Month;
                        String week_ = Week;
                        String cattar = categoryClassArrayList.get(position).getTarget_();

                        Intent customerIntent = new Intent(CategoryAchievementWeekFigureActivity.this, CustomerAchievementActivity.class);
                        customerIntent.putExtra("catID", catid);
                        customerIntent.putExtra("cattext", cattext);
                        customerIntent.putExtra("date", date_);
                        customerIntent.putExtra("week", week_);
                        customerIntent.putExtra("cattar", cattar);
                        customerIntent.putExtra("Day", Date);
                        customerIntent.putExtra("SimSerial", SimSerial);
                        customerIntent.putExtra("viewType", "normal");
                        customerIntent.putExtra("scode", MYAREA);
                        customerIntent.putExtra("Day_",Day_);
                        startActivity(customerIntent);
                    }
                }
            });

            year = TDate.substring(0, 4);
            Month = TDate.substring(4, 6);
            Week = TDate.substring(6, 7);
            Date = String.format("%02d", Integer.parseInt(TDate.substring(7, 9
            )));
            lblyear.setText(String.valueOf(year));
            lblmonth.setText(Month);
            lblweek.setText(Week);
            lblday.setText(Date);
            lblTarget.setText(ConvertToLKR(weekTarget));
            TargetDate = year + Month;
            lblDay.setText(Day_);

            getSerial();

            //new AsyncCategory().execute();
        }catch (Exception e){
            Log.w("error on create",e.getMessage());
        }
    }






    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spnmonth){
//            String month = adapterm.getItem(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void getSerial(){
        try{
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            } else {
                //TODO
            }
            TelephonyManager tpm = (TelephonyManager)getSystemService(getApplicationContext().TELEPHONY_SERVICE);
            SimSerial = tpm.getSimSerialNumber().toString();

        }catch (NullPointerException ex){
            Log.w("error",ex.getMessage());
        }
    }

    class AsyncCategory extends AsyncTask<String,String,String>{
        ProgressDialog prgdialog;Double cmpTARGET=0.00,mySALE=0.00;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(CategoryAchievementWeekFigureActivity.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();

            gridView.setAdapter(null);
            if(customCategoryAdapter!=null){
                customCategoryAdapter.clear();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                Cursor cur = null;
                if(ccode!=null){
                    cur = db_class.Set_CUS_Category_Achievement(year,Month,Date,ccode);
                }else
                    cur = db_class.Set_Category_Achievement(year,Month,Date);
                if(cur!=null && cur.getCount()>0){
                    while (cur.moveToNext()) {

                        double precentage=0.00;String New_precentage="0.00";


                        String categoryID = cur.getString(0);
                        String category = cur.getString(1);
                        double target = cur.getDouble(2);
                        double sale = cur.getDouble(3);

                        cmpTARGET = cmpTARGET+target;
                        mySALE = mySALE+sale;


                        if(target != 0.00) {
                            precentage = (sale / target) * 100;
                            DecimalFormat df = new DecimalFormat("#0.00");
                            New_precentage = df.format(precentage);
                        }


                        categoryClassArrayList.add(new CategoryAchievementClass(categoryID,category,ConvertToLKR(target),ConvertToLKR(sale),New_precentage+" %"));
                        customCategoryAdapter = new CustomAchievementCategoryAdapter(getApplicationContext(),categoryClassArrayList);
                    }

                }
                else {
                    categoryClassArrayList.add(new CategoryAchievementClass("0", "N/A", ConvertToLKR(Double.parseDouble("0.00")),ConvertToLKR(Double.parseDouble("0.00")),"0 %"));
                    customCategoryAdapter = new CustomAchievementCategoryAdapter(getApplicationContext(), categoryClassArrayList);
                }
            }catch (Exception ex){
                Log.w("error",ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lblTarget.setText(ConvertToLKR(cmpTARGET));
            lblAssingTarget.setText(ConvertToLKR(mySALE));
            customCategoryAdapter.notifyDataSetChanged();
            gridView.setAdapter(customCategoryAdapter);
            prgdialog.dismiss();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        new AsyncCategory().execute();
    }

    class Send_Target extends AsyncTask<String,String,String>{
        ProgressDialog prgdialog;
        boolean is_send;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(CategoryAchievementWeekFigureActivity.this);
            prgdialog.setMessage("Sending Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            is_send = db_class.Send_Target(TargetDate,Week,Date,SimSerial);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            prgdialog.dismiss();
            if (is_send)
            {
                Toast.makeText(CategoryAchievementWeekFigureActivity.this,"Download Completed",Toast.LENGTH_SHORT).show();
                finish();
            }else
                Toast.makeText(CategoryAchievementWeekFigureActivity.this,"Download Not Completed",Toast.LENGTH_SHORT).show();

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
