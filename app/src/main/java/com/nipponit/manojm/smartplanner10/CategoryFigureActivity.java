package com.nipponit.manojm.smartplanner10;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class CategoryFigureActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int REQUEST_READ_PHONE_STATE=0;
    GridView gridView;
    Fonts fonts;
    TextView lblTarget;
    DB_Class db_class;
    Dialog dialog;
    Double total=0.00;
    static String scode,ccode,SimSerial,TDate;
    Spinner spnyear,spnmonth,spnweek;
    private  Calendar calendar;
    String TargetDate,Month,Week;
    int year;
    TextView lblyear,lblmonth,lblweek,lblw;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw;


    private ArrayList<CategoryClass> categoryClassArrayList = new ArrayList<>();
    CustomCategoryAdapter customCategoryAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ccode = getIntent().getStringExtra("ccode");
        String cname = getIntent().getStringExtra("cname");
        scode = getIntent().getStringExtra("scode");


        db_class = new DB_Class(getApplicationContext());
        getSerial();
        fonts=new Fonts(getApplicationContext());
        setContentView(R.layout.activity_category_figure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        TextView mTitle = (TextView)toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(fonts.SetType("ubuntu-bold"));
        mTitle.setText(cname);

        TextView lbl1=(TextView)findViewById(R.id.labelheader);
        lbl1.setTypeface(fonts.SetType("ubuntu-reg"));

        lblTarget = (TextView)findViewById(R.id.lbltottarget);
        lblTarget.setTypeface(fonts.SetType("ubuntu-bold"));


        lblyear = (TextView)findViewById(R.id.lblyear);
        lblyear.setTypeface(fonts.SetType("ubuntu-reg"));
        lblmonth = (TextView)findViewById(R.id.lblmonth);
        lblmonth.setTypeface(fonts.SetType("ubuntu-reg"));
        lblweek = (TextView)findViewById(R.id.lblweek);
        lblweek.setTypeface(fonts.SetType("ubuntu-reg"));
        lblw = (TextView)findViewById(R.id.lblw);
        lblw.setTypeface(fonts.SetType("ubuntu-reg"));




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //show date range selector.
                selectDate(year,Month,Week);

            }
        });


        //Get gridview object from xml file
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                ViewDialog(categoryClassArrayList.get(position).getCategory_(),position);
            }
        });


        /** initialize the grid view **/
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        Month = String.format("%02d",month);
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        Week = String.format("%02d",week);
        TargetDate = String.valueOf(year)+Month+Week;

        lblyear.setText(String.valueOf(year));lblmonth.setText(Month);lblweek.setText(Week);
        new AsyncCategory().execute(TargetDate);
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





    private void ViewDialog(final String cat, final int id){
        dialog=new Dialog(CategoryFigureActivity.this,R.style.FullHeightDialog);
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


        txttarget.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER){
                    dialog.dismiss();
                }

                return false;
            }
        });

        txtcategory.setText(cat);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String value = txttarget.getText().toString().trim();
                int c = value.length();

                if(!value.isEmpty() && !value.equals("."))
                {

                    String target = (txttarget.getText().toString());
                    Double target_val = Double.parseDouble(target);

                    total = (total + target_val)-Double.parseDouble(db_class.ReturnTarget(cat,ccode,TargetDate));
                    lblTarget.setText(ConvertToLKR(total));


                    db_class.Add_Target(scode, ccode, cat, target,TargetDate);

                    //categoryClassArrayList.get(id).setTarget_(String.format("%.2f",target_val));
                    categoryClassArrayList.get(id).setTarget_(ConvertToLKR(target_val));
                    customCategoryAdapter.notifyDataSetChanged();
                    dialog.dismiss();

                }else
                    Toast.makeText(CategoryFigureActivity.this, "Please enter correct figures.", Toast.LENGTH_SHORT).show();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void selectDate(int year_,String month_,String week_){
        dialog=new Dialog(CategoryFigureActivity.this,R.style.FullHeightDialog);
        dialog.setContentView(R.layout.date_selector);
        Button btnadd = (Button)dialog.findViewById(R.id.btnadd);
        TextView lbl = (TextView) dialog.findViewById(R.id.textView);
        TextView lbly = (TextView)dialog.findViewById(R.id.lblyear);
        TextView lblm = (TextView)dialog.findViewById(R.id.lblmonth);
        TextView lblw = (TextView)dialog.findViewById(R.id.lblweek);
        lbl.setTypeface(fonts.SetType("ubuntu-reg"));
        btnadd.setTypeface(fonts.SetType("ubuntu-bold"));
        lbly.setTypeface(fonts.SetType("ubuntu-reg"));
        lblm.setTypeface(fonts.SetType("ubuntu-reg"));
        lblw.setTypeface(fonts.SetType("ubuntu-reg"));


        spnyear = (Spinner)dialog.findViewById(R.id.spnyear);
        adapter = ArrayAdapter.createFromResource(this,R.array.year_array, R.layout.date_dialog);
        adapter.setDropDownViewResource(R.layout.date_dialog);
        spnyear.setAdapter(adapter);
        spnyear.setSelection(adapter.getPosition(String.valueOf(year_)));



        spnmonth = (Spinner)dialog.findViewById(R.id.spnmonth);
        adapterm = ArrayAdapter.createFromResource(this,R.array.month_array, R.layout.date_dialog);
        adapterm.setDropDownViewResource(R.layout.date_dialog);
        spnmonth.setAdapter(adapterm);
        spnmonth.setSelection(adapterm.getPosition(month_));
        spnmonth.setOnItemSelectedListener(this);


//        spnweek = (Spinner)dialog.findViewById(R.id.spnweek);
//        adapterw = ArrayAdapter.createFromResource(this,R.array.week_array,R.layout.date_dialog);
//        adapterw.setDropDownViewResource(R.layout.date_dialog);
//        spnweek.setAdapter(adapterw);
//        spnweek.setSelection(adapterw.getPosition(week_));


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = spnmonth.getSelectedItem().toString();
                String year = spnyear.getSelectedItem().toString();
                String week = spnweek.getSelectedItem().toString();
                TargetDate = year+month+week;
                lblyear.setText(year);lblmonth.setText(month);lblweek.setText(week);
                new AsyncCategory().execute(TargetDate);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void AlertDialog(){
        dialog = new Dialog(CategoryFigureActivity.this,R.style.FullHeightDialog);
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

        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
    }

    class AsyncCategory extends AsyncTask<String,String,String>{
        ProgressDialog prgdialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(CategoryFigureActivity.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
            total=0.00;
            gridView.setAdapter(null);
            if(customCategoryAdapter!=null){
                customCategoryAdapter.clear();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                Cursor cur = db_class.LoadCategory();
                if(cur.getCount()>0 && cur!=null){
                    while (cur.moveToNext()){
                        String category = cur.getString(0);
                        String target = db_class.ReturnTarget(category,ccode,params[0]);

                        total = total + Double.parseDouble(target);

                        categoryClassArrayList.add(new CategoryClass("",category,ConvertToLKR(Double.parseDouble(target)),ConvertToLKR(Double.parseDouble("0.00"))));
                        customCategoryAdapter = new CustomCategoryAdapter(getApplicationContext(),categoryClassArrayList);
                    }
                }
            }catch (Exception ex){
                Log.w("error",ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lblTarget.setText(ConvertToLKR(total));
            customCategoryAdapter.notifyDataSetChanged();
            gridView.setAdapter(customCategoryAdapter);
            prgdialog.dismiss();
        }
    }


    class Send_Target extends AsyncTask<String,String,String>{
        ProgressDialog prgdialog;
        boolean is_send;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(CategoryFigureActivity.this);
            prgdialog.setMessage("Sending Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            is_send = db_class.Send_Target(ccode,SimSerial,"","");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            prgdialog.dismiss();
            if (is_send)
            {
                Toast.makeText(CategoryFigureActivity.this,"Download Completed",Toast.LENGTH_SHORT).show();
                finish();
            }else
                Toast.makeText(CategoryFigureActivity.this,"Download Not Completed",Toast.LENGTH_SHORT).show();

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
