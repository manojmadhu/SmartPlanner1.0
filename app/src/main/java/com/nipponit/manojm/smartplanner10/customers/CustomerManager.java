package com.nipponit.manojm.smartplanner10.customers;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nipponit.manojm.smartplanner10.CustomerClass;
import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database.DB_Class;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.customer.CustomCustomerAdapter_target;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.customer.CustomerActivity;
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.customer.CustomerClass_target;

import java.util.ArrayList;
import java.util.Objects;

public class CustomerManager extends AppCompatActivity {

    DB_Class db_class;
    private static String Type,sales_code;
    private ArrayList<CustomerModel> customerArrayList=new ArrayList<>();
    private ArrayList<CustomerModel> customerArrayList_select=new ArrayList<>();
    CustomerAdapter customerClassAdapter = null;
    CustomerAdapter customerClassAdapter_select = null;
    ListView customerList,temp_customerlist;
    Fonts fonts;
    TextView lbltitle;
    EditText txtCusname;
    Dialog viewDialog;
    Button btnsun,btnmon,btntue,btnwed,btnthu,btnfri,btnsat,btnunassign;
    ImageButton btnclear;
    static int filteroption = 0;
    static String FONT="ubuntu-reg";


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_manager);
        fonts=new Fonts(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        btnclear = (ImageButton)findViewById(R.id.btnclear);

        TextView mTitle = (TextView)toolbar.findViewById(R.id.toolbar_title_main);
        mTitle.setTypeface(fonts.SetType("ubuntu-reg"));

        TextView mTitle2 = (TextView)toolbar.findViewById(R.id.toolbar_title_2);
        mTitle2.setTypeface(fonts.SetType("ubuntu-reg"));

        lbltitle=(TextView)findViewById(R.id.lblCusname);
        lbltitle.setTypeface(fonts.SetType("ubuntu-reg"));

        txtCusname = (EditText) findViewById(R.id.txtCusname);
        txtCusname.setTypeface(fonts.SetType("ubuntu-reg"));

        customerList = (ListView)findViewById(R.id.lstcustomers);

        filteroption=1;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialog();

            }
        });

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customerClassAdapter.getSelectedCount()==0) {
                    txtCusname.setEnabled(true);
                    txtCusname.setText("");
                    txtCusname.setFocusable(true);
                }
            }
        });


        new AsyncCustomer().execute("");


        customerList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        customerList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = customerList.getCheckedItemCount();

                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                customerClassAdapter.toggleSelection(position);
                int selects = customerClassAdapter.getSelectedIds().size();
                if(selects==0)
                    txtCusname.setEnabled(true);
                else
                    txtCusname.setEnabled(false);



            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.customer_manager,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                try {
                switch (item.getItemId()) {
                    case R.id.action_save:
                            viewDialog = new Dialog(CustomerManager.this, R.style.FullHeightDialog);
                            viewDialog.setContentView(R.layout.layout_view_customer_manager);

                            final ListView lstcustomer = (ListView) viewDialog.findViewById(R.id.lstcustomers);
                            lbltitle = (TextView) viewDialog.findViewById(R.id.lblCusname);
                            lbltitle.setTypeface(fonts.SetType(FONT));
                            btnsun = (Button) viewDialog.findViewById(R.id.btnsun);
                            btnmon = (Button) viewDialog.findViewById(R.id.btnmon);
                            btntue = (Button) viewDialog.findViewById(R.id.btntue);
                            btnwed = (Button) viewDialog.findViewById(R.id.btnwed);
                            btnthu = (Button) viewDialog.findViewById(R.id.btnthu);
                            btnfri = (Button) viewDialog.findViewById(R.id.btnfri);
                            btnsat = (Button) viewDialog.findViewById(R.id.btnsat);
                        btnunassign = (Button) viewDialog.findViewById(R.id.btnunassign);
                        btnunassign.setVisibility(View.VISIBLE);

                            btnsun.setTypeface(fonts.SetType(FONT));
                            btnmon.setTypeface(fonts.SetType(FONT));
                            btntue.setTypeface(fonts.SetType(FONT));
                            btnwed.setTypeface(fonts.SetType(FONT));
                            btnthu.setTypeface(fonts.SetType(FONT));
                            btnfri.setTypeface(fonts.SetType(FONT));
                            btnsat.setTypeface(fonts.SetType(FONT));
                        btnunassign.setTypeface(fonts.SetType(FONT));

                            if (customerArrayList_select != null)
                                customerArrayList_select.clear();

                            try {

                                SparseBooleanArray selected = customerClassAdapter.getSelectedIds();
                                for (int i = selected.size() - 1; i >= 0; i--) {
                                    if (selected.valueAt(i)) {

                                        int id = customerClassAdapter.getItem(selected.keyAt(i)).getId_();
                                        String ccode = customerClassAdapter.getItem(selected.keyAt(i)).getCcode_();
                                        String cname = customerClassAdapter.getItem(selected.keyAt(i)).getCname_();
                                        String carea = customerClassAdapter.getItem(selected.keyAt(i)).getCcity_();

                                        customerArrayList_select.add(new CustomerModel(id, true, ccode, cname, carea));
                                        customerClassAdapter_select = new CustomerAdapter(getApplicationContext(), customerArrayList_select);
                                    }
                                }
                                lstcustomer.setAdapter(customerClassAdapter_select);

                            } catch (IndexOutOfBoundsException ex) {
                                Log.w("error", ex.getMessage());
                            }


                            btnsun.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    for (Object data : customerArrayList_select) {
                                        String ccode = ((CustomerModel) data).getCcode_();
                                        db_class.Update_customer_day(ccode, "sun");
                                    }

                                    mode.finish();
                                    txtCusname.setEnabled(true);
                                    viewDialog.dismiss();
                                }
                            });
                            btnmon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    for (Object data : customerArrayList_select) {
                                        String ccode = ((CustomerModel) data).getCcode_();
                                        db_class.Update_customer_day(ccode, "mon");
                                    }

                                    mode.finish();
                                    txtCusname.setEnabled(true);
                                    viewDialog.dismiss();
                                }
                            });
                            btntue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    for (Object data : customerArrayList_select) {
                                        String ccode = ((CustomerModel) data).getCcode_();
                                        db_class.Update_customer_day(ccode, "tue");
                                    }

                                    mode.finish();
                                    txtCusname.setEnabled(true);
                                    viewDialog.dismiss();
                                }
                            });
                            btnwed.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    for (Object data : customerArrayList_select) {
                                        String ccode = ((CustomerModel) data).getCcode_();
                                        db_class.Update_customer_day(ccode, "wed");
                                    }

                                    mode.finish();
                                    txtCusname.setEnabled(true);
                                    viewDialog.dismiss();
                                }
                            });
                            btnthu.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    for (Object data : customerArrayList_select) {
                                        String ccode = ((CustomerModel) data).getCcode_();
                                        db_class.Update_customer_day(ccode, "thu");
                                    }

                                    mode.finish();
                                    txtCusname.setEnabled(true);
                                    viewDialog.dismiss();
                                }
                            });
                            btnfri.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    for (Object data : customerArrayList_select) {
                                        String ccode = ((CustomerModel) data).getCcode_();
                                        db_class.Update_customer_day(ccode, "fri");
                                    }

                                    mode.finish();
                                    txtCusname.setEnabled(true);
                                    viewDialog.dismiss();
                                }
                            });
                            btnsat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    for (Object data : customerArrayList_select) {
                                        String ccode = ((CustomerModel) data).getCcode_();
                                        db_class.Update_customer_day(ccode, "sat");
                                    }

                                    mode.finish();
                                    txtCusname.setEnabled(true);
                                    viewDialog.dismiss();
                                }
                            });
                        btnunassign.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                for (Object data : customerArrayList_select) {
                                    String ccode = ((CustomerModel) data).getCcode_();
                                    db_class.Update_customer_day(ccode, null);
                                }

                                mode.finish();
                                txtCusname.setEnabled(true);
                                viewDialog.dismiss();
                            }
                        });

                            viewDialog.show();

                            return true;
                            default:
                                return false;
                        }
                }catch (Exception ex){
                    Log.w("error",ex.getMessage());
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        customerClassAdapter.clear();
                        new AsyncCustomer().execute("");

                    }
                });

            }
        });




        txtCusname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

//                ListView listView = customerList;
//                SparseBooleanArray checked = listView.getCheckedItemPositions();
//                for(int i=0;i<customerArrayList.size();i++){
//                    if(checked.get(i)==true){
//                        Object Obj = customerClassAdapter.getItem(i).getCcode_();
//                        String ccode = Obj.toString();
//                        if(customerClassAdapter.getCustomers().contains(ccode)){
//
//                        }else{
//                           Log.w("true",ccode);
//                        }
//                    }
//                }


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    customerClassAdapter.getFilter().filter(s.toString());
                }catch (Exception ex){
                    Log.w("error",ex.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

//                ListView listView = customerList;
//                listView.setSelected(false);

//                customerClassAdapter.getFilter().filter(s, new Filter.FilterListener() {
//                    @Override
//                    public void onFilterComplete(int count) {
//                        customerClassAdapter.notifyDataSetChanged();
//                        ListView listView = customerList;
//                        for(int i=0;i<customerClassAdapter.getCount();i++){
//                            Object obj = customerClassAdapter.getItem(i).getCcode_();
//                            String ccode = obj.toString();
//                            if(customerClassAdapter.getCustomers().contains(ccode)){
//                                Log.w("true",ccode);
//                            }else{
//
//                            }
//                        }
//                    }
//                });

            }
        });
    }


    @Override
    public void onBackPressed() {

       new AsyncSendCustomers().execute();
    }


    class AsyncSendCustomers extends AsyncTask<String,String,String>{
        ProgressDialog prgdialog;
        boolean is_send = false;
        int i=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db_class=new DB_Class(getApplicationContext());
            prgdialog= new ProgressDialog(CustomerManager.this);
            prgdialog.setMessage("Sending Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            db_class.SendToHost_Customer();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            prgdialog.dismiss();
            if (is_send)
            {
                Toast.makeText(CustomerManager.this,"Sending Completed",Toast.LENGTH_SHORT).show();
                finish();
            }else
                Toast.makeText(CustomerManager.this,"Sending Not Completed or no updates.",Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    class AsyncCustomer extends AsyncTask<String,String,String> {

        ProgressDialog prgdialog;
        int i=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db_class=new DB_Class(getApplicationContext());
            prgdialog= new ProgressDialog(CustomerManager.this);
            prgdialog.setMessage("Loading Data,Please wait..!");
            prgdialog.setCancelable(false);
            prgdialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try{
                String day = params[0].toString();
                Cursor cursor = db_class.Retrivew_customers(day);
                if(cursor != null && cursor.getCount()>0){
                    while (cursor.moveToNext()) {
                        String ccode = cursor.getString(0);
                        String cname = cursor.getString(1);
                        String ccity = cursor.getString(2);
                        sales_code = cursor.getString(3);

                        customerArrayList.add(new CustomerModel(i,false,ccode,cname,ccity));
                        customerClassAdapter = new CustomerAdapter(getApplicationContext(),customerArrayList);
                        i++;
                    }

                }
            }catch (Exception ex){

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            customerList.setAdapter(customerClassAdapter);
            prgdialog.dismiss();
        }
    }

    private void ViewDialog(){
        try{
            viewDialog = new Dialog(CustomerManager.this, R.style.FullHeightDialog);
            viewDialog.setContentView(R.layout.layout_view_customer_manager);

            final ListView lstcustomer = (ListView) viewDialog.findViewById(R.id.lstcustomers);
            lbltitle = (TextView) viewDialog.findViewById(R.id.lblCusname);
            lbltitle.setTypeface(fonts.SetType(FONT));
            btnsun = (Button) viewDialog.findViewById(R.id.btnsun);
            btnmon = (Button) viewDialog.findViewById(R.id.btnmon);
            btntue = (Button) viewDialog.findViewById(R.id.btntue);
            btnwed = (Button) viewDialog.findViewById(R.id.btnwed);
            btnthu = (Button) viewDialog.findViewById(R.id.btnthu);
            btnfri = (Button) viewDialog.findViewById(R.id.btnfri);
            btnsat = (Button) viewDialog.findViewById(R.id.btnsat);
            btnunassign = (Button) viewDialog.findViewById(R.id.btnunassign);
            btnunassign.setVisibility(View.VISIBLE);

            btnsun.setTypeface(fonts.SetType(FONT));
            btnmon.setTypeface(fonts.SetType(FONT));
            btntue.setTypeface(fonts.SetType(FONT));
            btnwed.setTypeface(fonts.SetType(FONT));
            btnthu.setTypeface(fonts.SetType(FONT));
            btnfri.setTypeface(fonts.SetType(FONT));
            btnsat.setTypeface(fonts.SetType(FONT));
            btnunassign.setTypeface(fonts.SetType(FONT));

            btnsun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(customerClassAdapter!=null) {

                        customerClassAdapter.clear();
                        new AsyncCustomer().execute("sun");
                        txtCusname.setEnabled(true);
                        viewDialog.dismiss();
                    }
                }
            });
            btnmon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(customerClassAdapter!=null) {
                        customerClassAdapter.clear();
                        new AsyncCustomer().execute("mon");

                        txtCusname.setEnabled(true);
                        viewDialog.dismiss();
                    }
                }
            });
            btntue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(customerClassAdapter!=null) {
                        customerClassAdapter.clear();
                        new AsyncCustomer().execute("tue");
                        txtCusname.setEnabled(true);
                        viewDialog.dismiss();
                    }
                }
            });
            btnwed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(customerClassAdapter!=null) {
                        customerClassAdapter.clear();
                        new AsyncCustomer().execute("wed");
                        txtCusname.setEnabled(true);
                        viewDialog.dismiss();
                    }
                }
            });
            btnthu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(customerClassAdapter!=null) {
                        customerClassAdapter.clear();
                        new AsyncCustomer().execute("thu");

                        txtCusname.setEnabled(true);
                        viewDialog.dismiss();
                    }
                }
            });
            btnfri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(customerClassAdapter!=null) {
                        customerClassAdapter.clear();
                        new AsyncCustomer().execute("fri");
                        txtCusname.setEnabled(true);
                        viewDialog.dismiss();
                    }
                }
            });
            btnsat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(customerClassAdapter!=null) {
                        customerClassAdapter.clear();
                        new AsyncCustomer().execute("sat");

                        txtCusname.setEnabled(true);
                        viewDialog.dismiss();
                    }
                }
            });
            btnunassign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(customerClassAdapter!=null) {
                        customerClassAdapter.clear();
                        new AsyncCustomer().execute("");

                        txtCusname.setEnabled(true);
                        viewDialog.dismiss();
                    }
                }
            });

            viewDialog.show();
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
    }



}
