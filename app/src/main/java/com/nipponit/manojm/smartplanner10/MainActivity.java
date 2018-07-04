package com.nipponit.manojm.smartplanner10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.nipponit.manojm.smartplanner10.Achievement.ViewCategoryFigure;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_READ_PHONE_STATE=0;
    private static String Type,sales_code;
    private ArrayList<CustomerClass> customerClassList=new ArrayList<>();
    CustomCustomerAdapter customerClassArrayAdapter = null;

    ListView customerList;
    EditText txtCustomerName;
    DB_Class db_class;

    Fonts fonts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fonts=new Fonts(getApplicationContext());
        setContentView(R.layout.activity_main);


        /** set toolbar style **/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        TextView mTitle = (TextView)toolbar.findViewById(R.id.toolbar_title_main);
        mTitle.setTypeface(fonts.SetType("ubuntu-reg"));
        mTitle.setText("Smart Planer");

        Type=getIntent().getStringExtra("Type");



        TextView lblheader = (TextView)findViewById(R.id.lblCusname);
        lblheader.setTypeface(fonts.SetType("ubuntu-bold"));

        txtCustomerName = (EditText)findViewById(R.id.txtCusname);
        txtCustomerName.setTypeface(fonts.SetType("ubuntu-reg"));
        txtCustomerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    customerClassArrayAdapter.getFilter().filter(s.toString());}
                catch (Exception ex){
                    Log.w("error","error on text change");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customerList = (ListView)findViewById(R.id.lstcustomers);

        new AsyncCustomer().execute();

        customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ccode = customerClassArrayAdapter.getItem(position).getCcode_();
                String cname = customerClassArrayAdapter.getItem(position).getCname_();

                if(Type.equals("add")) {
                    Intent intentfigure = new Intent(MainActivity.this, CategoryFigureActivity.class);
                    intentfigure.putExtra("ccode", ccode);
                    intentfigure.putExtra("cname", cname);
                    intentfigure.putExtra("scode", sales_code);
                    startActivity(intentfigure);
                }
                else if(Type.equals("view")){
                    Intent intentview_ach = new Intent(MainActivity.this,ViewCategoryFigure.class);
                    intentview_ach.putExtra("ccode", ccode);
                    intentview_ach.putExtra("cname", cname);
                    intentview_ach.putExtra("scode", sales_code);
                    intentview_ach.putExtra("FType","cus");
                    startActivity(intentview_ach);
                }
            }
        });

    }



    class AsyncCustomer extends AsyncTask<String,String,String>{

        ProgressDialog prgdialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db_class=new DB_Class(getApplicationContext());
            prgdialog= new ProgressDialog(MainActivity.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                Cursor cursor = db_class.LoadCustomers();
                if(cursor != null && cursor.getCount()>0){
                    while (cursor.moveToNext()) {
                        String ccode = cursor.getString(0);
                        String cname = cursor.getString(1);
                        String ccity = cursor.getString(2);
                        sales_code = cursor.getString(3);

                        customerClassList.add(new CustomerClass(ccode,cname,ccity));
                        customerClassArrayAdapter = new CustomCustomerAdapter(getApplicationContext(),customerClassList);
                    }

                }
            }catch (Exception ex){

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            customerList.setAdapter(customerClassArrayAdapter);
            prgdialog.dismiss();
        }
    }







}
