package com.nipponit.manojm.smartplanner10.Achievement.CustomerFigure;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nipponit.manojm.smartplanner10.Achievement.AreaAchievement_Activity;
import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.customer.CustomCustomerAdapter_target;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.customer.CustomerClass_target;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CustomerAchievementActivity extends AppCompatActivity {


    private static final int REQUEST_READ_PHONE_STATE=0;
    private static String Type,sales_code,MYAREA;
    private ArrayList<CustomerAchievementClass_target> customerClass_targetArrayList=new ArrayList<>();
    CustomCustomerAchievementAdapter_target customerClassArrayAdapter_target = null;

    static int filteroption = 0;
    ListView customerList;
    EditText txtCustomerName;
    DB_Class db_class;

    TextView lblTarget,lblCurrent,lblyear,lblmonth,lblweek,lblw,lbld,lblday,lbly,lblm,lblDay;
    String year,Month,Week;
    Double WCatTarget = 0.00;
    String TargetDate,catid,catText,date,week,target,Day,Date,SimSerial,viewType,Day_;
    Dialog dialog;
    Fonts fonts;

    Spinner spnyear,spnmonth,spnweek,spnday;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw,adapterd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            fonts = new Fonts(getApplicationContext());
            setContentView(R.layout.activity_customer);


            catid = getIntent().getStringExtra("catID");
            catText = getIntent().getStringExtra("cattext");
            date = getIntent().getStringExtra("date");
            Week = getIntent().getStringExtra("week");
            target = getIntent().getStringExtra("cattar");
            Day = getIntent().getStringExtra("Day");
            SimSerial = getIntent().getStringExtra("SimSerial");
            MYAREA = getIntent().getStringExtra("scode");
            Day_ = getIntent().getStringExtra("Day_");


            /** set toolbar style **/
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setTitle("");
            toolbar.setSubtitle("");
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_main);
            mTitle.setTypeface(fonts.SetType("ubuntu-reg"));
            mTitle.setText(catText);


            TextView lblheader = (TextView) findViewById(R.id.lblCusname);
            lblheader.setTypeface(fonts.SetType("ubuntu-bold"));

            TextView lbl1 = (TextView) findViewById(R.id.labelheader);
            lbl1.setTypeface(fonts.SetType("ubuntu-reg"));

            TextView lbl2 = (TextView) findViewById(R.id.labelheader1);
            lbl2.setTypeface(fonts.SetType("ubuntu-reg"));
            lbl2.setText("My Category Achievement");

            lblTarget = (TextView) findViewById(R.id.lbltottarget);
            lblTarget.setTypeface(fonts.SetType("ubuntu-bold"));
            lblTarget.setText(target);

            lblCurrent = (TextView) findViewById(R.id.lblassigntarget);
            lblCurrent.setTypeface(fonts.SetType("ubuntu-bold"));


            lblyear = (TextView) findViewById(R.id.lblyear);
            lblyear.setTypeface(fonts.SetType("ubuntu-reg"));
            lblmonth = (TextView) findViewById(R.id.lblmonth);
            lblmonth.setTypeface(fonts.SetType("ubuntu-reg"));
            lblweek = (TextView) findViewById(R.id.lblweek);
            lblweek.setTypeface(fonts.SetType("ubuntu-reg"));

            lbly = (TextView) findViewById(R.id.lbly);
            lbly.setTypeface(fonts.SetType("ubuntu-reg"));
            lblm = (TextView) findViewById(R.id.lblm);
            lblm.setTypeface(fonts.SetType("ubuntu-reg"));
            lblw = (TextView) findViewById(R.id.lblw);
            lblw.setTypeface(fonts.SetType("ubuntu-reg"));

            lbld = (TextView) findViewById(R.id.lbld);
            lbld.setTypeface(fonts.SetType("ubuntu-reg"));
            lblday = (TextView) findViewById(R.id.lblday);
            lblday.setTypeface(fonts.SetType("ubuntu-reg"));

            lblDay = (TextView)findViewById(R.id.lblDay);
            lblDay.setTypeface(fonts.SetType("ubuntu-reg"));


            txtCustomerName = (EditText) findViewById(R.id.txtCusname);
            txtCustomerName.setTypeface(fonts.SetType("ubuntu-reg"));
            txtCustomerName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        customerClassArrayAdapter_target.getFilter().filter(s.toString());
                    } catch (Exception ex) {
                        Log.w("error", "error on text change");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            customerList = (ListView) findViewById(R.id.lstcustomers);
            filteroption = 1;


            new AsyncCustomer().execute();


            customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

            year = date.substring(0, 4);
            Month = date.substring(4, 6);
            week = Week;
            lblyear.setText(String.valueOf(year));
            lblmonth.setText(Month);
            lblweek.setText(Week);
            lblday.setText(Day);
            lblDay.setText(Day_);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (filteroption == 0) {
                        customerClassArrayAdapter_target.getFilter_target().filter("");
                        filteroption = 1;
                    } else if (filteroption == 1) {
                        customerClassArrayAdapter_target.getFilter_target().filter("Rs.0.00");
                        filteroption = 0;
                    }

                }
            });
        }catch (Exception e){
            Log.w("Error oncrate",e.getMessage());
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
    public void onBackPressed() {
        super.onBackPressed();

    }

    class AsyncCustomer extends AsyncTask<String,String,String>{

        ProgressDialog prgdialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db_class=new DB_Class(getApplicationContext());
            prgdialog= new ProgressDialog(CustomerAchievementActivity.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                Cursor cursor = db_class.LoadCustomers_Area(MYAREA);
                if(cursor != null && cursor.getCount()>0){
                    while (cursor.moveToNext()) {
                        String ccode = cursor.getString(0);
                        String cname = cursor.getString(1);
                        String ccity = cursor.getString(2);
                        sales_code = cursor.getString(3);

                        String CatTarget = db_class.ReturnCus_Cat_Achievement(ccode, catid, date, Day);
                        String ctarget = ConvertToLKR(Double.parseDouble(CatTarget));
                        WCatTarget = WCatTarget + Double.parseDouble(CatTarget);

                        if (Double.parseDouble(CatTarget) != 0.00) {
                            customerClass_targetArrayList.add(new CustomerAchievementClass_target(ccode, cname, ccity, ctarget));
                            customerClassArrayAdapter_target = new CustomCustomerAchievementAdapter_target(getApplicationContext(), customerClass_targetArrayList);
                        }
                    }

                }
            }catch (Exception ex){

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            customerList.setAdapter(customerClassArrayAdapter_target);
            lblCurrent.setText(ConvertToLKR(WCatTarget));
            prgdialog.dismiss();
        }
    }



    private void selectDate(int year_,String month_){
        dialog=new Dialog(CustomerAchievementActivity.this,R.style.FullHeightDialog);
        dialog.setContentView(R.layout.date_selector_2);
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
                dialog.dismiss();
            }
        });


        dialog.show();
    }







}
