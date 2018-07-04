package com.nipponit.manojm.smartplanner10.Achievement;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nipponit.manojm.smartplanner10.Category_ach_class;
import com.nipponit.manojm.smartplanner10.CustomCategoryAchivementAdapter;
import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewCategoryFigure extends AppCompatActivity  {
    GridView gridView;
    Fonts fonts;
    TextView lblTarget;
    DB_Class db_class;
    Dialog dialog;
    Double total=0.00;
    static String scode,ccode,Ftype;
    Spinner spnyear,spnmonth;
    private Calendar calendar;
    String TargetDate,Month;
    int year;
    TextView lblyear,lblmonth,lblweek,lblw;
    ArrayAdapter<CharSequence> adapter,adapterm;

    private ArrayList<Category_ach_class> category_ach_classes = new ArrayList<>();
    CustomCategoryAchivementAdapter customCategoryAchivementAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category_figure);
        ccode = getIntent().getStringExtra("ccode");
        String cname = getIntent().getStringExtra("cname");
        scode = getIntent().getStringExtra("scode");
        Ftype = getIntent().getStringExtra("FType");

        db_class = new DB_Class(getApplicationContext());
        fonts=new Fonts(getApplicationContext());

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

        TextView lbl2=(TextView)findViewById(R.id.labelheader1);
        lbl2.setTypeface(fonts.SetType("ubuntu-reg"));

        TextView lblSale = (TextView)findViewById(R.id.lbltotsale);
        lblSale.setTypeface(fonts.SetType("ubuntu-bold"));

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
                selectDate(year,Month);
            }
        });

        gridView = (GridView) findViewById(R.id.gridView1);

        /** initialize the grid view **/
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        Month = String.format("%02d",month);
        TargetDate = String.valueOf(year)+Month;
        lblyear.setText(String.valueOf(year));lblmonth.setText(Month);


        if(Ftype.equals("area")){
            mTitle.setText("My Achievement");
            lbl1.setText("Total Target");
            lbl2.setText("Total Achievement");

        }else
        {
            new AsyncCategory().execute(TargetDate);
        }
    }



    private void selectDate(int year_,String month_){
        dialog=new Dialog(ViewCategoryFigure.this,R.style.FullHeightDialog);
        dialog.setContentView(R.layout.date_selector);
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
                new AsyncCategory().execute(TarDate);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    class AsyncCategory_area extends AsyncTask<String,String,String>{
        ProgressDialog prgdialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }



    class AsyncCategory extends AsyncTask<String,String,String> {
        ProgressDialog prgdialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgdialog= new ProgressDialog(ViewCategoryFigure.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
            total=0.00;
            gridView.setAdapter(null);
            if(customCategoryAchivementAdapter!=null){
                customCategoryAchivementAdapter.clear();
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

                        category_ach_classes.add(new Category_ach_class(category,ConvertToLKR(Double.parseDouble(target)),ConvertToLKR(0.00)));
                        customCategoryAchivementAdapter = new CustomCategoryAchivementAdapter(getApplicationContext(),category_ach_classes);
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
            customCategoryAchivementAdapter.notifyDataSetChanged();
            gridView.setAdapter(customCategoryAchivementAdapter);
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

}
