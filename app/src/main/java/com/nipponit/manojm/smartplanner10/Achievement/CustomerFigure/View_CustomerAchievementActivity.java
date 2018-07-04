package com.nipponit.manojm.smartplanner10.Achievement.CustomerFigure;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nipponit.manojm.smartplanner10.Achievement.CategoryFigure.CategoryAchievementWeekFigureActivity;
import com.nipponit.manojm.smartplanner10.CategoryFigureActivity;
import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;

import java.lang.annotation.Target;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class View_CustomerAchievementActivity extends AppCompatActivity {


    private static final int REQUEST_READ_PHONE_STATE=0;
    private static String Type,sales_code;
    private ArrayList<View_CustomerAchievementClass_target> customerClass_targetArrayList=new ArrayList<>();
    View_CustomCustomerAchievementAdapter_target customerClassArrayAdapter_target = null;

    static int filteroption = 0;
    ListView customerList;
    EditText txtCustomerName;
    DB_Class db_class;

    private Calendar calendar;

    TextView lblTarget,lblCurrent,lblyear,lblmonth,lblweek,lblw,lbld,lblday,lbly,lblm,mArea,lblDay;
    String Year,Month,Week,Day;
    int year;
    Double WCatTarget = 0.00,cmp_target = 0.00,cus_achiev=0.00;
    String SimSerial,TargetDate;
    Dialog dialog;
    Fonts fonts;

    Spinner spnyear,spnmonth,spnweek,spnday,spnareas;
    ArrayAdapter<CharSequence> adapter,adapterm,adapterw,adapterd,adapterareas;
    List<String> dayArray=null;


    String Day_="";
    SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fonts=new Fonts(getApplicationContext());
        setContentView(R.layout.view_activity_customer);

        SimSerial = getIntent().getStringExtra("SimSerial");


        /** set toolbar style **/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        TextView mTitle = (TextView)toolbar.findViewById(R.id.toolbar_title_main);
        mTitle.setTypeface(fonts.SetType("ubuntu-reg"));
        mTitle.setText("Smart Planner");

        mArea = (TextView)toolbar.findViewById(R.id.toolbar_area);
        mArea.setTypeface(fonts.SetType("ubuntu-reg"));
        mArea.setText("");

        TextView lblheader = (TextView)findViewById(R.id.lblCusname);
        lblheader.setTypeface(fonts.SetType("ubuntu-bold"));

        TextView lbl1=(TextView)findViewById(R.id.labelheader);
        lbl1.setTypeface(fonts.SetType("ubuntu-reg"));
        lbl1.setText("My Day Target");

        TextView lbl2=(TextView)findViewById(R.id.labelheader1);
        lbl2.setTypeface(fonts.SetType("ubuntu-reg"));
        lbl2.setText("My Day Achievement");

        lblTarget = (TextView)findViewById(R.id.lbltottarget);
        lblTarget.setTypeface(fonts.SetType("ubuntu-bold"));
        lblTarget.setText("Rs.0.00");

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

        lblDay = (TextView)findViewById(R.id.lblDay);
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

        customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intcategory = new Intent(View_CustomerAchievementActivity.this, CategoryAchievementWeekFigureActivity.class);
                intcategory.putExtra("ccode",customerClassArrayAdapter_target.getItem(position).getCcode_());
                intcategory.putExtra("cname",customerClassArrayAdapter_target.getItem(position).getCname_());
                intcategory.putExtra("scode","");
                intcategory.putExtra("TDate",lblyear.getText().toString()+lblmonth.getText().toString()+  lblweek.getText().toString() +String.format("%02d",Integer.parseInt(lblday.getText().toString())));
                intcategory.putExtra("WTarget",cmp_target.toString());
                intcategory.putExtra("Day_",Day_);
                startActivity(intcategory);

            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              selectDate(year,Month);

            }
        });



        /** initialize the calendar view **/
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        Month = String.format("%02d",month);
        TargetDate = String.valueOf(year)+Month;
        lblyear.setText(String.valueOf(year));lblmonth.setText(Month);
        Week = "1";
        Day = "01";

        lblyear.setText(String.valueOf(year));lblmonth.setText(Month);lblweek.setText(Week);lblday.setText(Day);

        // android get Day of the date
        try {
            Date date = sdate.parse(year+"-"+Month+"-"+String.format("%02d",Integer.parseInt(Day)));
            Day_ = (String) android.text.format.DateFormat.format("EEEE",date);
            lblDay.setText(Day_);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        new AsyncCustomer().execute("");

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
        String cus_target="0.00",cus_achievement="0.00";
        Cursor cursor = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db_class=new DB_Class(getApplicationContext());
            prgdialog= new ProgressDialog(View_CustomerAchievementActivity.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
           if(customerClassArrayAdapter_target!=null)
                customerClassArrayAdapter_target.clear();
        }

        @Override
        protected String doInBackground(String... params) {

            try{

                db_class.Download_Achievement(TargetDate.substring(0,4),TargetDate.substring(4,6),params[0]);


                if(!params[0].toString().equals(""))
                    cursor = db_class.LoadCustomers_Area(params[0]);
                else
                    cursor = db_class.LoadCustomers();

                if(cursor != null && cursor.getCount()>0){
                    while (cursor.moveToNext()) {
                        String ccode = cursor.getString(0);
                        String cname = cursor.getString(1);
                        String ccity = cursor.getString(2);
                        sales_code = cursor.getString(3);

                        Cursor cus_targetAssign = db_class.Load_TargetAssigned(ccode,TargetDate,Week,Day);
                        Cursor cus_targetAchievement = db_class.Load_TargetAchievement(ccode,TargetDate,Day);
                        if(cus_targetAchievement!=null){
                            while (cus_targetAchievement.moveToNext()){
                                cus_achievement=cus_targetAchievement.getString(0);
                                if(cus_achievement==null)
                                    cus_achievement="0.00";
                            }
                        }


                        if(cus_targetAssign!=null){
                            while(cus_targetAssign.moveToNext()){
                                cus_target = cus_targetAssign.getString(0);

                                if (cus_target != null && cus_achievement !=null ) {

                                    cmp_target = cmp_target + Double.parseDouble(cus_target);
                                    cus_achiev = cus_achiev + Double.parseDouble(cus_achievement);
                                }
                                else if(cus_target==null)
                                    cus_target = "0.00";
                            }
                        }


                        customerClass_targetArrayList.add(new View_CustomerAchievementClass_target(ccode, cname, ccity, ConvertToLKR(Double.parseDouble(cus_target)),ConvertToLKR(Double.parseDouble(cus_achievement))));
                        customerClassArrayAdapter_target = new View_CustomCustomerAchievementAdapter_target(getApplicationContext(), customerClass_targetArrayList);
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
        try {
        customerList.setAdapter(customerClassArrayAdapter_target);
        //customerClassArrayAdapter_target.notifyDataSetChanged();
        lblTarget.setText(ConvertToLKR(cmp_target));
        lblCurrent.setText(ConvertToLKR(cus_achiev));
            cursor.close();
            //mArea.setText(" ( "+db_class.returnScode(sales_code)+" ) ");
        prgdialog.dismiss();


        } catch (NullPointerException ee){

        } catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        }
    }



    private void selectDate(int year_,String month_){
        dialog=new Dialog(View_CustomerAchievementActivity.this,R.style.FullHeightDialog);
        dialog.setContentView(R.layout.date_selector);
        Button btnadd = (Button)dialog.findViewById(R.id.btnadd);
        TextView lbla = (TextView)dialog.findViewById(R.id.lblarea);
        TextView lbl = (TextView) dialog.findViewById(R.id.textView);
        TextView lbly = (TextView)dialog.findViewById(R.id.lblyear);
        TextView lblm = (TextView)dialog.findViewById(R.id.lblmonth);
        TextView lblw = (TextView)dialog.findViewById(R.id.lblweek);
        TextView lbld = (TextView)dialog.findViewById(R.id.lblday);

        lbl.setTypeface(fonts.SetType("ubuntu-reg"));
        lbla.setTypeface(fonts.SetType("ubuntu-bold"));
        btnadd.setTypeface(fonts.SetType("ubuntu-bold"));
        lbly.setTypeface(fonts.SetType("ubuntu-reg"));
        lblm.setTypeface(fonts.SetType("ubuntu-reg"));
        lblw.setTypeface(fonts.SetType("ubuntu-reg"));
        lbld.setTypeface(fonts.SetType("ubuntu-reg"));

        spnareas = (Spinner)dialog.findViewById(R.id.spnarea);
        db_class = new DB_Class(getApplicationContext());
        ArrayList<String> areaArray = new ArrayList<>();
        areaArray.addAll(db_class.returnArea_ASM());
        ArrayAdapter<String> spinareaAdapter = new ArrayAdapter<String>(this,R.layout.date_dialog,areaArray);
        spinareaAdapter.setDropDownViewResource(R.layout.date_dialog);
        spnareas.setAdapter(spinareaAdapter);
        spnareas.setSelection(0);

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


        spnweek = (Spinner)dialog.findViewById(R.id.spnweek);
        adapterw = ArrayAdapter.createFromResource(this,R.array.week_array, R.layout.date_dialog);
        adapterw.setDropDownViewResource(R.layout.date_dialog);
        spnweek.setAdapter(adapterw);
        spnweek.setSelection(adapterw.getPosition("1"));



        spnday = (Spinner)dialog.findViewById(R.id.spnday);
        dayArray = new ArrayList<String>();

        db_class=new DB_Class(getApplicationContext());
        dayArray.addAll(db_class.ReturnWeek_days("1"));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, R.layout.date_dialog,
                        dayArray);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.date_dialog);
        spnday.setAdapter(spinnerArrayAdapter);
        spnday.setSelection(spinnerArrayAdapter.getPosition("1"));


        spnweek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayArray.clear();

                dayArray.addAll(db_class.ReturnWeek_days(spnweek.getSelectedItem().toString()));
                spinnerArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String area =  spnareas.getSelectedItem().toString().split("-")[0];
                String month = spnmonth.getSelectedItem().toString();
                String year = spnyear.getSelectedItem().toString();
                Week = spnweek.getSelectedItem().toString();
                Day = spnday.getSelectedItem().toString();

                cmp_target=0.00;cus_achiev=0.00;

                String TarDate = year+month;
                lblyear.setText(year);lblmonth.setText(month);lblweek.setText(Week);lblday.setText(Day);
                TargetDate = year+month;

                // android get Day of the date
                try {
                    Date date = sdate.parse(year+"-"+Month+"-"+String.format("%02d",Integer.parseInt(Day)));
                    Day_ = (String) android.text.format.DateFormat.format("EEEE",date);
                    lblDay.setText(Day_);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new AsyncCustomer().execute(area);
                dialog.dismiss();
            }
        });


        dialog.show();
    }







}
