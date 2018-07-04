package com.nipponit.manojm.smartplanner10;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nipponit.manojm.smartplanner10.Achievement.AreaAchievement_Activity;
import com.nipponit.manojm.smartplanner10.Achievement.CustomerFigure.CustomerAchievementActivity;
import com.nipponit.manojm.smartplanner10.Achievement.CustomerFigure.View_CustomerAchievementActivity;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;
import com.nipponit.manojm.smartplanner10.customers.CustomerManager;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {


    Button btnsettarget,btnmytarget,btnmangecustomer;
    TextView mtxtarea;
    Fonts fonts;
    DB_Class db_class;
    private static final int REQUEST_READ_PHONE_STATE=0;
    private static String SimSerial,sales_code,TargetDate,Month;
    Dialog dialog;
    private  Calendar calendar;
    Spinner spnyear,spnmonth,spnweek,spnarea;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw;
    int year;
    boolean isASM=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            fonts = new Fonts(getApplicationContext());
            db_class = new DB_Class(getApplicationContext());

            /** Set customized toolbar name**/
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setTitle("");
            toolbar.setSubtitle("");
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_main);
            mtxtarea = (TextView) toolbar.findViewById(R.id.toolbar_title_area);
            mtxtarea.setTypeface(fonts.SetType("ubuntu-reg"));
            mTitle.setTypeface(fonts.SetType("ubuntu-reg"));
            mTitle.setText("Smart Planer");

            /** initialize controls**/
            btnmytarget = (Button) findViewById(R.id.btnmytarget);
            btnsettarget = (Button) findViewById(R.id.btnaddtarget);
            btnmangecustomer = (Button) findViewById(R.id.btnamanagecus);



            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            Month = String.format("%02d", month);
            TargetDate = String.valueOf(year) + Month;


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectDate(year, Month);


//                dialog = new Dialog(HomeActivity.this,R.style.FullHeightDialog);
//                dialog.setContentView(R.layout.alert_dialog);
//                Button btncacel = (Button) dialog.findViewById(R.id.btncancel);
//                Button btnyes = (Button) dialog.findViewById(R.id.btnyes);
//                TextView txtheader = (TextView) dialog.findViewById(R.id.textView);
//                TextView txtdetail = (TextView) dialog.findViewById(R.id.txtlocName);
//
//                txtheader.setTypeface(fonts.SetType("ubuntu-reg"));
//                txtdetail.setTypeface(fonts.SetType("ubuntu-reg"));
//                btnyes.setTypeface(fonts.SetType("ubuntu-bold"));
//                btncacel.setTypeface(fonts.SetType("ubuntu-bold"));
//
//
//                btncacel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//
//                btnyes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        //new DownloadCustomer().execute();
//
//                        dialog.dismiss();
//                    }
//                });
//
//
//                dialog.show();


                }
            });

            btnmytarget.setOnClickListener(listener);
            btnmytarget.setTypeface(fonts.SetType("ubuntu-reg"));
            btnsettarget.setOnClickListener(listener_set);
            btnsettarget.setTypeface(fonts.SetType("ubuntu-reg"));
            btnmangecustomer.setOnClickListener(listener_customer);
            btnmangecustomer.setTypeface(fonts.SetType("ubuntu-reg"));

            setArea();
        }catch (Exception e){
            Log.w("error",e.getMessage());
        }
    }


    private void setArea(){
        try {
            getSerial();
            db_class = new DB_Class(getApplicationContext());
            ArrayList<String> myarea = db_class.returnArea();
            if(myarea.size()>0 && myarea.size()<=2) {
                String scode = myarea.get(0);
                String area = myarea.get(1);
                mtxtarea.setText(" ( " + area + " )");
                btnsettarget.setEnabled(true);
            }else if(myarea.size()>2) {

                mtxtarea.setText(" ( ASM )");
                btnsettarget.setEnabled(false);
                isASM =true;
            }
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
    }



    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog = new Dialog(HomeActivity.this,R.style.FullHeightDialog);
            dialog.setContentView(R.layout.select_target);
            Button btnarea = (Button) dialog.findViewById(R.id.btnareatarget);
            Button btncustomer = (Button) dialog.findViewById(R.id.btncustarget);
            TextView txtheader = (TextView) dialog.findViewById(R.id.textView);

            txtheader.setTypeface(fonts.SetType("ubuntu-reg"));
            btnarea.setTypeface(fonts.SetType("ubuntu-reg"));
            btncustomer.setTypeface(fonts.SetType("ubuntu-reg"));


            if(isASM){
                btnarea.setText("REP Achievement");
                btncustomer.setText("rep route");
            }


            btnarea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Intentarea = new Intent(HomeActivity.this,AreaAchievement_Activity.class);

                    //check and download sales code
                    if(sales_code==null || sales_code.equals("")) {
                        db_class = new DB_Class(getApplicationContext());
                        sales_code = db_class.returnScode();
                    }

                    if(!sales_code.equals("")) {
                        Intentarea.putExtra("ccode","");
                        Intentarea.putExtra("cname","");
                        Intentarea.putExtra("scode",sales_code);
                        Intentarea.putExtra("FType", "area");
                        startActivity(Intentarea);
                    }else
                        Toast.makeText(HomeActivity.this,"Please download customer data first.",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            btncustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent customerIntent = new Intent(HomeActivity.this,View_CustomerAchievementActivity.class);
                    customerIntent.putExtra("catID","");
                    customerIntent.putExtra("cattext","");
                    customerIntent.putExtra("date","");
                    customerIntent.putExtra("week","");
                    customerIntent.putExtra("cattar","");
                    customerIntent.putExtra("Day","");
                    customerIntent.putExtra("SimSerial",SimSerial);
                    customerIntent.putExtra("viewType","type0");
                    startActivity(customerIntent);
                    dialog.dismiss();
                }
            });


            dialog.show();
        }
    };


    private View.OnClickListener listener_set = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //check and download sales code
            if(sales_code==null || sales_code.equals("")) {
                db_class = new DB_Class(getApplicationContext());
                sales_code = db_class.returnScode();
                if(sales_code!=null)
                    new DownloadTargetLock().execute();
                else
                    Toast.makeText(HomeActivity.this, "Please download customer data first.", Toast.LENGTH_SHORT).show();
            }else
                new DownloadTargetLock().execute();



        }
    };

    private View.OnClickListener listener_customer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            db_class = new DB_Class(getApplicationContext());
            boolean islock = db_class.chkcusLock(TargetDate);
            if(!islock){
                Intent intcus = new Intent(HomeActivity.this, CustomerManager.class);
                startActivity(intcus);
            }
            else
                Toast.makeText(HomeActivity.this, "Sorry customer manager has been blocked.", Toast.LENGTH_SHORT).show();



        }
    };


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



        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }

    class DownloadTargetLock extends AsyncTask<String,Integer,String>{
        ProgressDialog prgdialog; boolean isok=false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db_class = new DB_Class(getApplicationContext());
            prgdialog= new ProgressDialog(HomeActivity.this);
            prgdialog.setMessage("Configuring Target Lock,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            db_class.Download_TargetLock(sales_code);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

                Toast.makeText(getApplicationContext(), "Configuration Completed.", Toast.LENGTH_SHORT).show();
                Intent setIntent = new Intent(HomeActivity.this,WeekActivity.class);
                setIntent.putExtra("Type","add");
                setIntent.putExtra("SimSerial",SimSerial);
                startActivity(setIntent);

            prgdialog.dismiss();
        }
    }



    private void selectDate(int year_,String month_){
        dialog=new Dialog(HomeActivity.this,R.style.FullHeightDialog);
        dialog.setContentView(R.layout.date_selector_download);


        Button btnadd = (Button)dialog.findViewById(R.id.btnadd);

        TextView lbl = (TextView) dialog.findViewById(R.id.textView);
        TextView lbly = (TextView)dialog.findViewById(R.id.lblyear);
        TextView lblm = (TextView)dialog.findViewById(R.id.lblmonth);
        lbl.setTypeface(fonts.SetType("ubuntu-reg"));
        lbl.setText("Select Date to Download Data");
        btnadd.setTypeface(fonts.SetType("ubuntu-bold"));
        btnadd.setText("Download");
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
                String mmonth = spnmonth.getSelectedItem().toString();
                String myear = spnyear.getSelectedItem().toString();
                String TarDate = myear+mmonth;
                TargetDate = myear+mmonth;
                year = Integer.parseInt(myear);
                Month = mmonth;

                new DownloadCustomer().execute(TargetDate);

                dialog.dismiss();
            }
        });


        dialog.show();
    }


    class DownloadCustomer extends AsyncTask<String,String,String> {

        boolean isok=false,isok_cat=false;
        ProgressDialog prgdialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(HomeActivity.this);
            prgdialog.setMessage("Downloading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try{
//                isok = db_class.Download_Customers("8994029702473540882");
//                isok_cat = db_class.DownloadCategory("8994029702473540882",TargetDate,String.valueOf(year),Month);

                isok = db_class.Download_Customers(SimSerial);
                isok_cat = db_class.DownloadCategory(SimSerial,TargetDate,String.valueOf(year),Month);
                db_class.Download_Set_Target(String.valueOf(year),Month);

            }catch (Exception ex){

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (isok && isok_cat) {
                Toast.makeText(getApplicationContext(), "Download Completed.", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(getApplicationContext(), "Download Not Completed.", Toast.LENGTH_SHORT).show();

            prgdialog.dismiss();
            setArea();







        }
    }
}
