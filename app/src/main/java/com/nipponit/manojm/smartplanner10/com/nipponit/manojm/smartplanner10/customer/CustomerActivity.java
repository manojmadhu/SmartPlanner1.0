package com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.customer;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nipponit.manojm.smartplanner10.CategoryFigureActivity;
import com.nipponit.manojm.smartplanner10.CategoryWeekFigureActivity;
import com.nipponit.manojm.smartplanner10.CustomCustomerAdapter;
import com.nipponit.manojm.smartplanner10.CustomerClass;
import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {


    private static final int REQUEST_READ_PHONE_STATE=0;
    private static String Type,sales_code;
    private ArrayList<CustomerClass_target> customerClass_targetArrayList=new ArrayList<>();
    CustomCustomerAdapter_target customerClassArrayAdapter_target = null;

    static int filteroption = 0;
    ListView customerList;
    EditText txtCustomerName;
    DB_Class db_class;

    TextView lblTarget,lblCurrent,lblyear,lblmonth,lblweek,lblw,lbld,lblday,lbly,lblm,lblDay;
    String year,Month,Week;
    Double WCatTarget = 0.00;
    String TargetDate,catid,catText,date,week,target,Day,Day_,Date,SimSerial;
    Dialog dialog;
    Fonts fonts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fonts=new Fonts(getApplicationContext());
        setContentView(R.layout.activity_customer);

        catid = getIntent().getStringExtra("catID");
        catText = getIntent().getStringExtra("cattext");
        date = getIntent().getStringExtra("date");
        Week = getIntent().getStringExtra("week");
        target = getIntent().getStringExtra("cattar");
        Day = getIntent().getStringExtra("Day");
        SimSerial = getIntent().getStringExtra("SimSerial");
        Day_ = getIntent().getStringExtra("Day_");


        /** set toolbar style **/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        TextView mTitle = (TextView)toolbar.findViewById(R.id.toolbar_title_main);
        mTitle.setTypeface(fonts.SetType("ubuntu-reg"));
        mTitle.setText(catText);



        TextView lblheader = (TextView)findViewById(R.id.lblCusname);
        lblheader.setTypeface(fonts.SetType("ubuntu-bold"));

        TextView lbl1=(TextView)findViewById(R.id.labelheader);
        lbl1.setTypeface(fonts.SetType("ubuntu-reg"));

        TextView lbl2=(TextView)findViewById(R.id.labelheader1);
        lbl2.setTypeface(fonts.SetType("ubuntu-reg"));

        lblTarget = (TextView)findViewById(R.id.lbltottarget);
        lblTarget.setTypeface(fonts.SetType("ubuntu-bold"));
        lblTarget.setText(target);

        lblCurrent = (TextView)findViewById(R.id.lblassigntarget);
        lblCurrent.setTypeface(fonts.SetType("ubuntu-bold"));


        lblyear = (TextView)findViewById(R.id.lblyear);
        lblyear.setTypeface(fonts.SetType("ubuntu-reg"));
        lblmonth = (TextView)findViewById(R.id.lblmonth);
        lblmonth.setTypeface(fonts.SetType("ubuntu-reg"));
        lblweek = (TextView)findViewById(R.id.lblweek);
        lblweek.setTypeface(fonts.SetType("ubuntu-reg"));

        lbly=(TextView)findViewById(R.id.lbly);
        lbly.setTypeface(fonts.SetType("ubuntu-reg"));
        lblm=(TextView)findViewById(R.id.lblm);
        lblm.setTypeface(fonts.SetType("ubuntu-reg"));
        lblw = (TextView)findViewById(R.id.lblw);
        lblw.setTypeface(fonts.SetType("ubuntu-reg"));

        lbld =(TextView) findViewById(R.id.lbld);
        lbld.setTypeface(fonts.SetType("ubuntu-reg"));
        lblday =(TextView) findViewById(R.id.lblday);
        lblday.setTypeface(fonts.SetType("ubuntu-reg"));

        lblDay=(TextView)findViewById(R.id.lblDay);
        lblDay.setTypeface(fonts.SetType("ubuntu-reg"));


        txtCustomerName = (EditText)findViewById(R.id.txtCusname);
        txtCustomerName.setTypeface(fonts.SetType("ubuntu-reg"));
        txtCustomerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    customerClassArrayAdapter_target.getFilter().filter(s.toString());}
                catch (Exception ex){
                    Log.w("error","error on text change");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customerList = (ListView)findViewById(R.id.lstcustomers);
        filteroption=1;
        new AsyncCustomer().execute(Day_.substring(0,3));

        customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ccode = customerClassArrayAdapter_target.getItem(position).getCcode_();
                String cname = customerClassArrayAdapter_target.getItem(position).getCname_();
                String prv_val = customerClassArrayAdapter_target.getItem(position).getTarget_();
                ShowDialog(position, cname, ccode, prv_val);

//                db_class = new DB_Class(getApplicationContext());
//                boolean chklock = db_class.checkLock(date,week,Day);
//                if(chklock==false) {
//
//                }else
//                    Toast.makeText(CustomerActivity.this,"Sorry Target Locked.",Toast.LENGTH_SHORT).show();

            }
        });

        year = date.substring(0,4); Month = date.substring(4,6); week=Week;
        lblyear.setText(String.valueOf(year));lblmonth.setText(Month);lblweek.setText(Week);lblday.setText(Day);lblDay.setText(Day_);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filteroption==0) {
                    customerClassArrayAdapter_target.getFilter_target().filter("");
                    filteroption=1;
                }else if(filteroption==1){
                    customerClassArrayAdapter_target.getFilter_target().filter("Rs.0.00");
                    filteroption=0;
                }

            }
        });
    }


    private void ShowDialog(final int id, String customer, final String ccode, final String prvVal){
        try{
            dialog=new Dialog(CustomerActivity.this,R.style.FullHeightDialog);
            dialog.setContentView(R.layout.category_data);
            Button btnok = (Button)dialog.findViewById(R.id.btnok);
            Button btncancel = (Button)dialog.findViewById(R.id.btncancel);
            final EditText txttarget = (EditText) dialog.findViewById(R.id.textttarget);
            final TextView txtcategory = (TextView) dialog.findViewById(R.id.textcategory);
            TextView lbl = (TextView) dialog.findViewById(R.id.textView2);

            txtcategory.setTypeface(fonts.SetType("ubuntu-bold"));
            txttarget.setTypeface(fonts.SetType("ubuntu-reg"));
            lbl.setTypeface(fonts.SetType("ubuntu-reg"));
            btnok.setTypeface(fonts.SetType("ubuntu-bold"));
            btncancel.setTypeface(fonts.SetType("ubuntu-bold"));
            txttarget.setHint(prvVal);


            txttarget.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(event.getAction()==KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER){
                        dialog.dismiss();
                    }

                    return false;
                }
            });

            txtcategory.setText(customer);

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String value = txttarget.getText().toString().trim();
                    int c = value.length();

                    if(!value.isEmpty() && !value.equals("."))
                    {

                        String Target = (txttarget.getText().toString());
                        Double target_val = Double.parseDouble(Target);

                        Double MyTarget = Double.parseDouble(target.split("Rs.")[1].replace(",",""));
                        Double MyPrvVal = Double.parseDouble(prvVal.split("Rs.")[1].replace(",",""));
                        WCatTarget = (WCatTarget + target_val)-MyPrvVal;
                        //if(MyTarget >= WCatTarget )
                        if(MyTarget >= 0 ) {

                            db_class.Add_Week_Target(sales_code, ccode, catid, Target, date, week,Day);

                            customerClass_targetArrayList.get(id).setTarget_(ConvertToLKR(target_val));
                            customerClassArrayAdapter_target.notifyDataSetChanged();

                            lblCurrent.setText(ConvertToLKR(WCatTarget));

                            dialog.dismiss();
                        }else{
                            WCatTarget = (WCatTarget - target_val)+MyPrvVal;
                            Toast.makeText(CustomerActivity.this, "Error: Category Target Exceeded.", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(CustomerActivity.this, "Please enter correct figures.", Toast.LENGTH_SHORT).show();
                }
            });

            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
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
        //new Send_Target().execute();
    }


    class Send_Target extends AsyncTask<String,String,String>{
        ProgressDialog prgdialog;
        boolean is_send;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(CustomerActivity.this);
            prgdialog.setMessage("Sending Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            is_send = db_class.Send_Target_category(date,week,Day,catid,SimSerial);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            prgdialog.dismiss();
            if (is_send)
            {
                Toast.makeText(CustomerActivity.this,"Sending Completed",Toast.LENGTH_SHORT).show();
                finish();
            }else
                Toast.makeText(CustomerActivity.this,"Sending Not Completed or no changes.",Toast.LENGTH_SHORT).show();

            finish();

        }
    }

    class AsyncCustomer extends AsyncTask<String,String,String>{

        ProgressDialog prgdialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db_class=new DB_Class(getApplicationContext());
            prgdialog= new ProgressDialog(CustomerActivity.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                String day = params[0].toString().toLowerCase();
                Cursor cursor = db_class.Retrivew_customers(day);
                if(cursor != null && cursor.getCount()>0){
                    while (cursor.moveToNext()) {
                        String ccode = cursor.getString(0);
                        String cname = cursor.getString(1);
                        String ccity = cursor.getString(2);
                        sales_code = cursor.getString(3);

                        String CatTarget = db_class.ReturnCus_Cat_Target(ccode,catid,date,week,Day);
                        String ctarget = ConvertToLKR(Double.parseDouble(CatTarget));
                        WCatTarget = WCatTarget + Double.parseDouble(CatTarget);

                        customerClass_targetArrayList.add(new CustomerClass_target(ccode,cname,ccity,ctarget));
                        customerClassArrayAdapter_target = new CustomCustomerAdapter_target(getApplicationContext(),customerClass_targetArrayList);
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







}
