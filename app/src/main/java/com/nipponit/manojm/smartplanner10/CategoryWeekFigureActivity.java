package com.nipponit.manojm.smartplanner10;

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

import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.customer.CustomerActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CategoryWeekFigureActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int REQUEST_READ_PHONE_STATE=0;
    GridView gridView;
    Fonts fonts;
    TextView lblTarget;
    DB_Class db_class;
    Dialog dialog;
    Double weekTarget=0.00;
    static String scode,ccode,SimSerial,TDate;
    Spinner spnyear,spnmonth,spnweek;
    private  Calendar calendar;
    String TargetDate,year,Month,Week,Date,Day;

    TextView lbly,lblm,lblyear,lblmonth,lblweek,lblw,lbld,lblday,lblAssingTarget,lblDay;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw;



    private ArrayList<CategoryClass> categoryClassArrayList = new ArrayList<>();
    CustomCategoryAdapter customCategoryAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ccode = getIntent().getStringExtra("ccode");
            String cname = getIntent().getStringExtra("cname");
            scode = getIntent().getStringExtra("scode");
            TDate = getIntent().getStringExtra("TDate");
            Day = getIntent().getStringExtra("Day");
            weekTarget = Double.parseDouble(getIntent().getStringExtra("WTarget"));


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
            mTitle.setText("Smart Planner");

            TextView lbl1 = (TextView) findViewById(R.id.labelheader);
            lbl1.setText("Company Day Target");
            lbl1.setTypeface(fonts.SetType("ubuntu-reg"));

            lblTarget = (TextView) findViewById(R.id.lbltottarget);
            lblTarget.setText(ConvertToLKR(weekTarget));
            lblTarget.setTypeface(fonts.SetType("ubuntu-bold"));

            TextView lbl2 = (TextView) findViewById(R.id.labelheader1);
            lbl2.setTypeface(fonts.SetType("ubuntu-reg"));
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

            lblDay = (TextView) findViewById(R.id.lblDay);
            lblDay.setTypeface(fonts.SetType("ubuntu-reg"));


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog();

                }
            });


            //Get gridview object from xml file
            gridView = (GridView) findViewById(R.id.gridView1);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    String catid = categoryClassArrayList.get(position).getCategoryID_();
                    String cattext = categoryClassArrayList.get(position).getCategory_();
                    String date_ = year + Month;
                    String week_ = Week;
                    String cattar = categoryClassArrayList.get(position).getTarget_();

                    Intent customerIntent = new Intent(CategoryWeekFigureActivity.this, CustomerActivity.class);
                    customerIntent.putExtra("catID", catid);
                    customerIntent.putExtra("cattext", cattext);
                    customerIntent.putExtra("date", date_);
                    customerIntent.putExtra("week", week_);
                    customerIntent.putExtra("cattar", cattar);
                    customerIntent.putExtra("Day", Date);
                    customerIntent.putExtra("SimSerial", SimSerial);
                    customerIntent.putExtra("Day_", lblDay.getText());
                    startActivity(customerIntent);

                }
            });



            year = TDate.substring(0, 4);
            Month = TDate.substring(4, 6);
            Week = TDate.substring(6, 7);
            Date = String.format("%02d", Integer.parseInt(TDate.substring(7, 9)));
            lblyear.setText(String.valueOf(year));
            lblmonth.setText(Month);
            lblweek.setText(Week);
            lblday.setText(Date);
            lblDay.setText(Day);
            lblTarget.setText(ConvertToLKR(weekTarget));
            TargetDate = year + Month;
            getSerial();
            new AsyncCategory().execute();
        }catch (NullPointerException e){
            Log.w("error",e.getMessage());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            AlertDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }









    private void AlertDialog(){
        dialog = new Dialog(CategoryWeekFigureActivity.this,R.style.FullHeightDialog);
        dialog.setContentView(R.layout.alert_dialog);
        Button btncacel = (Button) dialog.findViewById(R.id.btncancel);
        Button btnyes = (Button) dialog.findViewById(R.id.btnyes);
        TextView txtheader = (TextView) dialog.findViewById(R.id.textView);
        TextView txtdetail = (TextView) dialog.findViewById(R.id.txtlocName);

        txtheader.setText("Send Customer Target");
        txtdetail.setText("Are you sure to send data.? This may take some time.");

        txtheader.setTypeface(fonts.SetType("ubuntu-reg"));
        txtdetail.setTypeface(fonts.SetType("ubuntu-reg"));
        btnyes.setTypeface(fonts.SetType("ubuntu-bold"));
        btncacel.setTypeface(fonts.SetType("ubuntu-bold"));


        btncacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Send_Target().execute();

//                db_class = new DB_Class(getApplicationContext());
//                boolean chklock = db_class.checkLock(TargetDate,Week,Date);
//                if(chklock==false) {
//
//                }else
//                    Toast.makeText(CategoryWeekFigureActivity.this,"Sorry Target Locked.",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        dialog.show();
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
        ProgressDialog prgdialog;Double AssignTarget=0.00;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            prgdialog= new ProgressDialog(CategoryWeekFigureActivity.this);
//            prgdialog.setMessage("Loading Data,Please wait..!");
//            prgdialog.setCancelable(false);
//            prgdialog.show();

            gridView.setAdapter(null);
            if(customCategoryAdapter!=null){
                customCategoryAdapter.clear();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try{

                Cursor cur = db_class.ReturnWeek_Category_Target(TargetDate,Week,Date);
                if(cur!=null && cur.getCount()>0){
                    while (cur.moveToNext()) {

                        String categoryID = cur.getString(0);
                        String category = cur.getString(1);
                        String target = cur.getString(2);

                        System.out.println("---------------===========------------- "+categoryID+" - "+category+" - "+target+"-------------===============--------------");

                        String assignTarget = "0.00";
                        Cursor cursor_catTarget = db_class.ReturnCat_AssignTarget(TargetDate,Week,Date,categoryID);

                        if(cursor_catTarget != null && cursor_catTarget.getCount()>0){
                            while (cursor_catTarget.moveToNext()){
                                assignTarget = cursor_catTarget.getString(1);
                            }
                        }

                        AssignTarget = AssignTarget + Double.parseDouble(assignTarget);

                        categoryClassArrayList.add(new CategoryClass(categoryID,category,ConvertToLKR(Double.parseDouble(target)),ConvertToLKR(Double.parseDouble(assignTarget))));
                        customCategoryAdapter = new CustomCategoryAdapter(getApplicationContext(),categoryClassArrayList);



                    }

                }
                else {
                    categoryClassArrayList.add(new CategoryClass("0", "N/A", ConvertToLKR(Double.parseDouble("0.00")),ConvertToLKR(Double.parseDouble("0.00"))));
                    customCategoryAdapter = new CustomCategoryAdapter(getApplicationContext(), categoryClassArrayList);
                }
            }catch (Exception ex)
            {
                //Log.w("error",ex.getMessage());
                System.out.println("---------------==== error =======------------- "+ex.getMessage()+"--------------===============--------------");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //prgdialog.dismiss();

            lblTarget.setText(ConvertToLKR(weekTarget));
            lblAssingTarget.setText(ConvertToLKR(AssignTarget));
            customCategoryAdapter.notifyDataSetChanged();
            gridView.setAdapter(customCategoryAdapter);

        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    class Send_Target extends AsyncTask<String,String,String>{
        ProgressDialog prgdialog;
        boolean is_send;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(CategoryWeekFigureActivity.this);
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
                Toast.makeText(CategoryWeekFigureActivity.this,"Sending Completed",Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(CategoryWeekFigureActivity.this,"Sending Not Completed or no changes",Toast.LENGTH_SHORT).show();

            finish();

        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog();
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
