package com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.Database;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.nipponit.manojm.smartplanner10.Achievement.subdayclass;
import com.nipponit.manojm.smartplanner10.Achievement.subweekclass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by manojm on 10/16/2017.
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class DB_Class extends SQLiteOpenHelper {
    private static String SM_DB="Smart_DB";
    private static String connString_DND="jdbc:jtds:sqlserver://192.168.101.145;DATABASENAME=DND_SYS";
    private static String connString_SAP="jdbc:jtds:sqlserver://192.168.101.145;DATABASENAME=SAP_SYS";

    private static String Uname="nippolac";
    private static String Password="nplk#456";
    private static String driver="net.sourceforge.jtds.jdbc.Driver";

    private Connection conn=null;

    private static String TB_Customers = "TB_Customers";
    private static String colCus_Cus_Code="Cus_Code";
    private static String colCus_Cus_Name="Cus_Name";
    private static String colCus_Cus_City="Cus_City";
    private static String colCus_Sales_Code="Sales_Code";
    private static String colCus_Assign_Day="Ass_Day";
    private static String colCus_Area_Name="Area_Name";
    private static String colCus_SendSts="Send_State";

    private static String TB_Category="TB_Category";
    private static String colCat_ID="CatID";
    private static String colCat_Category="Category";

    private static String TB_Target="TB_Target";
    private static String colTar_Sales_code="Sales_Code";//TEXT
    private static String colTar_Cus_Code="Cus_Code";//NUMERIC
    private static String colTar_CatID="CatID";//NUMERIC
    private static String colTar_Category="Category";//TEXT
    private static String colTar_Target="Target";//NUMERIC
    private static String colTar_Date="Date_";//NUMERIC(year+month)
    private static String colTar_Week="Week_";//TEXT
    private static String colTar_Day="Day_";

    private static String TB_AREA_TARGET = "TB_AREA_TARGET";
    private static String colAT_Date = "Date_Month";//NUMERIC
    private static String colAT_Week = "Week";//TEXT
    private static String colAT_Day = "Day";//TEXT
    private static String colAT_CatID = "CatID";//NUMERIC
    private static String colAT_Category = "Category";//TEXT
    private static String colAT_Target = "Target";//NUMERIC

    private static String TB_Achievement = "TB_Achievement";
    private static String colAch_Sale = "TotalSale";
    private static String colAch_Target = "Target";
    private static String colAch_Sales_Code = "Sales_Code";
    private static String colAch_Cus_Code = "Cus_Code";
    private static String colAch_CatID = "CatID";
    private static String colAch_Category = "Category";
    private static String colAch_Date = "Date_";
    private static String colAch_Week = "Week_";
    private static String colAch_Day = "Day_";

    private static String TB_TargetLock = "TB_TargetLock";
    private static String colTL_Date = "Date_";
    private static String colTL_Week = "Week_";
    private static String colTL_Day = "Day_";
    private static String colTL_sts = "Status_";
    private static String colTL_cusSts = "cus_Status_";


    private static String TB_Month  = "TB_Month";
    private static String colMo_year = "Year_";
    private static String colMo_Month = "Month_";
    private static String colMo_Week = "Week_";
    private static String colMo_Day = "Day_";


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public DB_Class(Context context) {
        super(context, SM_DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        String SQL_TBL_Customers =
                "CREATE TABLE "+TB_Customers+" ("+colCus_Cus_Code+" NUMERIC(10),"+colCus_Cus_Name+" TEXT,"+colCus_Cus_City+" TEXT,"+colCus_Sales_Code+" TEXT,"+colCus_Area_Name+" TEXT,"+colCus_Assign_Day+" TEXT,"+colCus_SendSts+" TEXT)";
        String SQL_TBL_CATEGORY =
                "CREATE TABLE "+TB_Category+" ("+colCat_ID+" NUMERIC(10),"+colCat_Category+" TEXT)";
        String SQL_TBL_TARGET =
                "CREATE TABLE "+TB_Target+" ("+colTar_Sales_code+" TEXT,"+colTar_Cus_Code+" NUMERIC(10),"+colTar_CatID+" NUMERIC(10),"+colTar_Category+" TEXT,"+colTar_Target+" NUMERIC(20),"+colTar_Date+" NUMERIC,"+colTar_Week+" TEXT,"+colTar_Day+" TEXT)";
        String SQL_TBL_AREA_TARGET =
                "CREATE TABLE "+TB_AREA_TARGET+" ("+colAT_Date+" NUMERIC(10), "+colAT_Week+" TEXT,"+colAT_Day+" TEXT,"+colAT_CatID+" NUMERIC(20), "+colAT_Category+" TEXT, "+colAT_Target+" NUMERIC(20))";
        String SQL_TBL_TARGET_LOCK =
                "CREATE TABLE "+TB_TargetLock+" ("+colTL_Date+" NUMERIC(10),"+colTL_Week+" TEXT,"+colTL_Day+" TEXT,"+colTL_sts+" TEXT,"+colTL_cusSts+" TEXT)";
        String SQL_TBL_Achievement =
                "CREATE TABLE "+TB_Achievement+" ("+colAch_Sales_Code+" TEXT,"+colAch_Cus_Code+" NUMERIC(10),"+colAch_CatID+" NUMERIC(10),"+colAch_Category+" TEXT,"+colAch_Date+" NUMERIC(10),"+colAch_Week+" NUMERIC(2),"+colAch_Day+" NUMERIC(2),"+colAch_Sale+" NUMERIC(10),"+colAch_Target+" NUMERIC(10))";
        String SQL_TBL_Month =
                "CREATE TABLE "+TB_Month+" ("+colMo_year+" NUMERIC(4),"+colMo_Month+" NUMERIC(2),"+colMo_Week+" NUMERIC(2),"+colMo_Day+" NUMERIC(2))";


        db.execSQL(SQL_TBL_Customers);
        db.execSQL(SQL_TBL_CATEGORY);
        db.execSQL(SQL_TBL_TARGET);
        db.execSQL(SQL_TBL_AREA_TARGET);
        db.execSQL(SQL_TBL_TARGET_LOCK);
        db.execSQL(SQL_TBL_Achievement);
        db.execSQL(SQL_TBL_Month);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TB_Category+"");
        db.execSQL("DROP TABLE IF EXISTS "+TB_Customers+"");
        db.execSQL("DROP TABLE IF EXISTS "+TB_Target+"");
        db.execSQL("DROP TABLE IF EXISTS "+TB_AREA_TARGET);
        db.execSQL("DROP TABLE IF EXISTS "+TB_TargetLock+"");
        db.execSQL("DROP TABLE IF EXISTS "+TB_Achievement+"");
        db.execSQL("DROP TABLE IF EXISTS "+TB_Month+"");
    }

    public boolean Download_Customers(String SIM_SERIAL){
        SQLiteDatabase DB = this.getWritableDatabase();
        boolean isok=false;
        try {
            Class.forName(driver);
            conn= DriverManager.getConnection(connString_DND,Uname,Password);
            Log.w("connection","open con");
            Statement stmt_area = conn.createStatement();
            String Cus_detail = "SELECT * FROM dbo.GetCustomers ('"+SIM_SERIAL+"')";
            ResultSet rs_Cus_detail = stmt_area.executeQuery(Cus_detail);

            if(rs_Cus_detail!=null){
                DB.delete(TB_Customers,null,null);
            }

            while(rs_Cus_detail.next()){

                String Cus_Code = rs_Cus_detail.getString("CUS_CODE");
                String Cus_Name = rs_Cus_detail.getString("CUS_NAME");
                String Cus_City = rs_Cus_detail.getString("CUS_CITY");
                String S_Code = rs_Cus_detail.getString("SALES_CODE");
                String S_Area = rs_Cus_detail.getString("AREA");
                String S_Day = rs_Cus_detail.getString("DAY");

                ContentValues values = new ContentValues();
                values.put(colCus_Cus_Code,Cus_Code);
                values.put(colCus_Cus_Name,Cus_Name);
                values.put(colCus_Cus_City,Cus_City);
                values.put(colCus_Sales_Code,S_Code);
                values.put(colCus_Area_Name,S_Area);
                values.put(colCus_Assign_Day,S_Day);

                //DB.delete(TB_Customers,colCus_Cus_Code+"="+Cus_Code+"",null);

                DB.insert(TB_Customers,null,values);

                isok=true;

            }

            DB.close();
            conn.close();

        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return isok;
    }

    public Cursor LoadCustomers(){
        Cursor mycursor=null;
        SQLiteDatabase db=this.getReadableDatabase();
        try{
            String scode = returnScode();
            String [] col={colCus_Cus_Code,colCus_Cus_Name,colCus_Cus_City,colCus_Sales_Code};
            mycursor=db.query(TB_Customers,col,colCus_Sales_Code+"='"+scode+"'",null,null,null,colCus_Cus_Name);
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return mycursor;
    }
    public Cursor LoadCustomers_Area(String scode){
        Cursor mycursor=null;
        SQLiteDatabase db=this.getReadableDatabase();
        try{
            String [] col={colCus_Cus_Code,colCus_Cus_Name,colCus_Cus_City,colCus_Sales_Code};
            mycursor=db.query(TB_Customers,col,colCus_Sales_Code+"='"+scode+"'",null,null,null,colCus_Cus_Name);
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return mycursor;
    }



    public String ReturnCus_Cat_Target(String ccode,String cat,String date_,String week_,String day_){
        String target="0.00";
        SQLiteDatabase db=this.getReadableDatabase();
        try{
            String [] col = {colTar_Target};
            Cursor cursor = db.query(TB_Target,col,colTar_Cus_Code+"="+ccode+" AND "+colTar_CatID+"="+cat+" AND "+colTar_Date+"="+date_+" AND "+colTar_Week+"='"+week_+"' AND "+colTar_Day+"='"+day_+"'",null,null,null,null);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    target = cursor.getString(0);
                }
            }

        }catch (Exception e){
            Log.w("error",e.getMessage());
        }
        return target;
    }



    public boolean DownloadCategory(String Simserial,String date_month,String year,String month){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isok=false;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(connString_SAP,Uname,Password);
            Log.w("connection","open");
            Statement stmt_cat = conn.createStatement();

            // DOWNLOAD ROOT MAIN CATEGORY
            String Query = "SELECT RC_ID,RC_Category FROM RootCategoryMaster";
            ResultSet rs_cat = stmt_cat.executeQuery(Query);
            if(rs_cat!=null) {
                db.delete(TB_Category,null,null);
                while (rs_cat.next()) {
                    String catID = rs_cat.getString("RC_ID");
                    String category = rs_cat.getString("RC_Category");
                    ContentValues values = new ContentValues();
                    values.put(colCat_ID, catID);
                    values.put(colCat_Category, category);
                    db.delete(TB_Category, colCat_ID + "=" + catID + "", null);
                    db.insert(TB_Category, null, values);
                    isok = true;
                }
            }


            //DOWNLOAD AREA TARGET FOR MONTH
            String Query_target = "SELECT * FROM DBO.func_ReturnTarget("+date_month+",'"+Simserial+"') ORDER BY MONTH_WEEK ASC";
            ResultSet rs_target = stmt_cat.executeQuery(Query_target);
            if(rs_target!=null) {
                db.delete(TB_AREA_TARGET,null,null);
                while (rs_target.next()) {
                    String target_ = rs_target.getString("Cat_Target");
                    String date_ = rs_target.getString("Date_Month");
                    String week_ = rs_target.getString("Month_Week");
                    String day_ = rs_target.getString("Month_Day");
                    String category_ = rs_target.getString("Category");
                    String catId_ = rs_target.getString("CatID");

                    ContentValues values = new ContentValues();
                    values.put(colAT_Date, date_);
                    values.put(colAT_Target, target_);
                    values.put(colAT_Week, week_);
                    values.put(colAT_Day, String.format("%02d",Integer.parseInt(day_)));
                    values.put(colAT_Category, category_);
                    values.put(colAT_CatID, catId_);
                    db.delete(TB_AREA_TARGET, colAT_Date + "=" + date_ + " AND " + colAT_CatID + "=" + catId_ + " AND " + colAT_Week + "=" + week_ + " AND " + colAT_Day + "=" + day_ + "", null);
                    db.insert(TB_AREA_TARGET, null, values);

                    isok = true;
                }
            }


            // DOWNLOAD Root month planner with days and weeks
            String monthQuery = "SELECT TargetYear,TargetMonth,TargetWeek,TargetDay FROM RootTargetPrecentage WHERE TargetYear="+year+" AND TargetMonth="+month+"";
            ResultSet rs_month = stmt_cat.executeQuery(monthQuery);
            if(rs_month!=null){
                // Delete achievement table and insert
                db.delete(TB_Month,null,null);
                while(rs_month.next()){
                    ContentValues values = new ContentValues();
                    values.put(colMo_year,rs_month.getString("TargetYear"));
                    values.put(colMo_Month,rs_month.getString("TargetMonth"));
                    values.put(colMo_Week,rs_month.getString("TargetWeek"));
                    values.put(colMo_Day,rs_month.getString("TargetDay"));

                    db.insert(TB_Month,null,values);
                }
            }





            db.close();
            conn.close();

        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return isok;
    }

    public void Download_Set_Target(String year,String month){
        SQLiteDatabase db = this.getWritableDatabase();
        try{

            String scode = returnScode();

            Class.forName(driver);
            conn=DriverManager.getConnection(connString_SAP,Uname,Password);
            Statement stmt = conn.createStatement();
            Log.w("connection","open");
            String Query = "SELECT * FROM viewRootPlanMaster where SalesCode='"+scode+"' and TargetYear="+year+" and TargetMonth="+month+"";
            ResultSet Rs_plan = stmt.executeQuery(Query);
            ContentValues values=new ContentValues();
            db.delete(TB_Target,null,null);
            while (Rs_plan.next()){
                String tyear=Rs_plan.getString("TargetYear");
                String tmonth=String.format("%02d",Rs_plan.getInt("TargetMonth"));
                values.put(colTar_Date,tyear+tmonth);
                values.put(colTar_Week,Rs_plan.getString("TargetWeek"));
                values.put(colTar_Day,String.format("%02d",Rs_plan.getInt("TargetDay")));
                values.put(colTar_Target,Rs_plan.getString("CatTarget"));
                values.put(colTar_Category,Rs_plan.getString("RC_Category"));
                values.put(colTar_CatID,Rs_plan.getString("Category"));
                values.put(colTar_Cus_Code,Rs_plan.getString("CusCode"));
                values.put(colTar_Sales_code,Rs_plan.getString("SalesCode"));

                db.insert(TB_Target,null,values);
            }

        }catch (Exception ex){
                Log.w("error",ex.getMessage());
        }
    }


    public boolean Download_TargetLock(String scode){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean istrue = false;
        try {
            Class.forName(driver);
            conn=DriverManager.getConnection(connString_SAP,Uname,Password);
            Statement stmt = conn.createStatement();
            Statement stmtcus = conn.createStatement();
            Statement stmtselect = conn.createStatement();

            Log.w("connection","open");
            String Query = "SELECT * FROM RootTargetLock";
            String QueryCustomer = "SELECT CUSCODE,DAY FROM RootCustomerPlanMaster WHERE STATUS = 'x' AND SALESCODE = '"+scode+"'";

            ResultSet rslock = stmt.executeQuery(Query);
            ResultSet rscustomer = stmtselect.executeQuery(QueryCustomer);

            if(rslock!=null){
                db.delete(TB_TargetLock,null,null);
            while (rslock.next()) {
                String year_month = rslock.getString("TargetDate");
                //String week_ = rslock.getString("TargetWeek");
                //String day_ = String.format("%02d", Integer.parseInt(rslock.getString("TargetDay")));
                String status_ = rslock.getString("Flag").trim();
                String cusStatus_ = rslock.getString("CusFlag").trim();


                boolean log = checkLock(year_month);
                if (log == false) {

//                    String LocalQuery = "INSERT INTO " + TB_TargetLock + " (" + colTL_Date + "," + colTL_Week + "," + colTL_Day + "," + colTL_sts + ") VALUES (" + year_month + ",'" + week_ + "','" + day_ + "','" + status_ + "')" +
//                            "WHERE NOT EXISTS (SELECT " + colTL_sts + " FROM " + TB_TargetLock + " WHERE " + colTL_Date + "=" + year_month + " AND " + colTL_Week + "='" + week_ + "' AND " + colTL_Day + "='" + day_ + "')";

                    String LocalQuery = "INSERT INTO " + TB_TargetLock + " (" + colTL_Date + "," + colTL_sts + ","+colTL_cusSts+") VALUES (" + year_month + ",'" + status_ + "','"+cusStatus_+"')";
                    db.execSQL(LocalQuery);
                    istrue = true;
                }
            }
            }

            // FOR UPDATE CUSTOMER DAY ASSIGNMENT
            if(rscustomer !=null){
                while (rscustomer.next()){
                    String cuscode = rscustomer.getString("CusCode");
                    String day = rscustomer.getString("Day");

                    ContentValues value = new ContentValues();
                    value.put(colCus_Assign_Day,day);

                    int i = db.update(TB_Customers,value,colCus_Cus_Code+"="+cuscode+"",null);

                    if(i>0) {
                        String updateHost = "UPDATE RootCustomerPlanMaster SET STATUS = NULL WHERE CUSCODE='"+cuscode+"' AND SALESCODE='"+scode+"'";
                        stmtcus.execute(updateHost);
                    }
                }
            }



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e){
            Log.w("error",e.getMessage());
        }
        return istrue;
    }


    public boolean checkLock (String date){
        boolean sts = false;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String [] col = {colTL_sts};
            Cursor cursor = db.query(TB_TargetLock,col,colTL_Date+"="+date+"",null,null,null,null);
            if(cursor.getCount()>0){
                sts = true;
            }
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return sts;
    }

    public Cursor LoadCategory(){
        Cursor mycursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String [] col = {colCat_Category};
            mycursor = db.query(TB_Category,col,null,null,null,null,null);
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return mycursor;
    }

    /** SELECT CUSTOMER WISE CATEGORY ACHIEVEMENT TO DATE **/
    public String ReturnTarget(String cat,String ccode,String date){
        String target = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String [] col={colTar_Target};
            Cursor cursor = db.query(TB_Target,col,colTar_Cus_Code+"="+ccode+" AND "+colTar_Category+"='"+cat+"' AND "+colTar_Date+"="+date+"",null,null,null,null);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    target = cursor.getString(0);
                }
            }
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return target;
    }

    // TO GET WEEK WISE COMPANY TOTAL TARGET
    public Cursor ReturnAreaTarget(String month_date){
        Cursor cursor=null;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String Local_Query = "SELECT  "+colAT_Week+",SUM("+colAT_Target+") FROM "+TB_AREA_TARGET+" WHERE "+colAT_Date+"="+month_date+" GROUP BY "+colAT_Week+"";
            //String Local_Query = "SELECT  "+colAT_Week+",("+colAT_Target+") FROM "+TB_AREA_TARGET+" WHERE "+colAT_Date+"="+month_date+" ";
            cursor = db.rawQuery(Local_Query,null);
        }catch (Exception e){
            Log.w("error",e.getMessage());
        }
        return cursor;
    }

    // TO GET COMPANY TOTAL TARGET
    public double GetAreaTarget(String month_date){
        double target = 0.00;
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query = "SELECT SUM("+colAT_Target+") FROM "+TB_AREA_TARGET+" WHERE "+colAT_Date+"="+month_date+"";
            Cursor cursor= db.rawQuery(query,null);
            while(cursor.moveToNext()){
                target = cursor.getDouble(0);
            }

        }catch (Exception e){

        }
        return target;
    }

    public Cursor ReturnWeekTarget(String month_date,String month_week){
        Cursor cursor=null;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String Local_Query = "SELECT "+colAT_Day+",SUM("+colAT_Target+") FROM "+TB_AREA_TARGET+" WHERE "+colAT_Date+"="+month_date+" AND "+colAT_Week+"="+month_week+"  GROUP BY "+colAT_Day+" ORDER BY "+colAT_Day+" ASC";
            //String Local_Query = "SELECT  "+colAT_Week+",("+colAT_Target+") FROM "+TB_AREA_TARGET+" WHERE "+colAT_Date+"="+month_date+" ";
            cursor = db.rawQuery(Local_Query,null);
        }catch (Exception e){
            Log.w("error",e.getMessage());
        }
        return cursor;
    }

    public Cursor ReturnWeek_Category_Target(String month,String week,String day){
            Cursor cursor=null;
            SQLiteDatabase db = this.getReadableDatabase();
            try{
            //String Local_Query = "SELECT CATEGORY,SUM(CAT_TARGET) FROM func_ReturnTarget(201710,'8994029702709149433') WHERE MONTH_WEEK=1 GROUP BY CATEGORY";
            String Local_Query = "SELECT "+colAT_CatID+","+colAT_Category+",SUM("+colAT_Target+") FROM "+TB_AREA_TARGET+" " +
                    "WHERE "+colAT_Date+"="+month+" AND "+colAT_Week+"="+week+" AND "+colAT_Day+"='"+day+"'  GROUP BY "+colAT_CatID+","+colAT_Category+"";

            cursor = db.rawQuery(Local_Query,null);
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return cursor;
    }

    public Cursor ReturnWeek_AssignTarget(String month_date,String week){
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String Local_Query = "SELECT  "+colTar_Week+",SUM("+colTar_Target+") FROM "+TB_Target+" WHERE "+colTar_Date+"="+month_date+" AND "+colTar_Week+"="+week+" GROUP BY "+colTar_Week+"";
            cursor = db.rawQuery(Local_Query,null);
        }catch (Exception ex){
            Log.w("Error",ex.getMessage());
        }
        return cursor;
    }

    public Cursor ReturnDay_AssignTarget(String month_date,String week,String day){
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String Local_Query = "SELECT  "+colTar_Day+",SUM("+colTar_Target+") FROM "+TB_Target+" WHERE "+colTar_Date+"="+month_date+" AND "+colTar_Week+"="+week+" AND "+colTar_Day+"='"+day+"' GROUP BY "+colTar_Day+"";
            cursor = db.rawQuery(Local_Query,null);
        }catch (Exception ex){
            Log.w("Error",ex.getMessage());
        }
        return cursor;
    }

    public Cursor ReturnCat_AssignTarget(String month_date,String week,String day,String catid){
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String Local_Query = "SELECT  "+colTar_CatID+",SUM("+colTar_Target+") FROM "+TB_Target+" " +
                    "WHERE "+colTar_Date+"="+month_date+" AND "+colTar_Week+"="+week+" AND "+colTar_Day+"='"+day+"' AND "+colTar_CatID+" = "+catid+" GROUP BY "+colTar_CatID+"";
            cursor = db.rawQuery(Local_Query,null);
        }catch (Exception ex){
            Log.w("Error",ex.getMessage());
        }
        return cursor;
    }


    private boolean returnSaveState(long res){
        if(res==-1)
            return false;
        else
            return true;
    }

    public String returnScode(){
        String scode="";
        SQLiteDatabase db=this.getReadableDatabase();
        String [] col = {colCus_Sales_Code};
        Cursor cursor = db.rawQuery("SELECT DISTINCT "+colCus_Sales_Code+" FROM "+TB_Customers+" WHERE "+colCus_Area_Name+" <> '' ",null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                return scode = cursor.getString(0);
            }
        }
        return scode;
    }


    public String returnScode(String scode){
        String sarea="";
        SQLiteDatabase db=this.getReadableDatabase();
        String [] col = {colCus_Sales_Code};
        Cursor cursor = db.rawQuery("SELECT DISTINCT "+colCus_Area_Name+" FROM "+TB_Customers+" WHERE "+colCus_Sales_Code+"='"+scode+"' AND "+colCus_Area_Name+" <> ''",null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                return sarea = cursor.getString(0);
            }
        }
        return sarea;
    }


    public ArrayList<String> returnArea(){
        ArrayList<String> area = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT "+colCus_Sales_Code+","+colCus_Area_Name+" FROM "+TB_Customers+" WHERE "+colCus_Area_Name+" <> ''",null);
        if(cursor.getCount()>0){
         while (cursor.moveToNext()){
             area.add(0,cursor.getString(0));
             area.add(1,cursor.getString(1));
         }
        }
        return area;
    }


    public ArrayList<String> returnArea_ASM(){
        ArrayList<String> area = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT "+colCus_Sales_Code+","+colCus_Area_Name+" FROM "+TB_Customers+" WHERE "+colCus_Area_Name+" <> '' ",null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                area.add(0,cursor.getString(0)+"-"+cursor.getString(1));
            }
        }
        return area;
    }



    public boolean Add_Target(String scode,String ccode,String category,String target,String date){
        SQLiteDatabase db=this.getWritableDatabase();
        boolean is_insert=false;
        try{
            ContentValues values = new ContentValues();
            values.put(colTar_Sales_code,scode);
            values.put(colTar_Cus_Code,ccode);
            values.put(colTar_Category,category);
            values.put(colTar_Target,target);
            values.put(colTar_Date,date);

            String sql="SELECT "+colTar_Target+" FROM "+TB_Target+" WHERE "+colTar_Cus_Code+"="+ccode+" AND "+colTar_Category+"='"+category+"' AND "+colTar_Date+"="+date+"";
            Cursor cursor = db.rawQuery(sql,null);
            if(cursor.getCount()>0) {
                ContentValues values1 = new ContentValues();
                values1.put(colTar_Target,target);
                is_insert = returnSaveState(db.update(TB_Target, values1, colTar_Cus_Code + "=" + ccode + " AND " + colTar_Category + "='" + category + "' AND "+colTar_Date+"="+date+"", null));
            }
            else
                is_insert = returnSaveState(db.insert(TB_Target,null,values));

        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return is_insert;
    }

    public boolean Add_Week_Target(String scode,String ccode,String catID,String target,String date,String week,String day){
        SQLiteDatabase db=this.getWritableDatabase();
        boolean is_insert=false;
        try{
            ContentValues values = new ContentValues();
            values.put(colTar_Sales_code,scode);
            values.put(colTar_Cus_Code,ccode);
            values.put(colTar_CatID,catID);
            values.put(colTar_Target,target);
            values.put(colTar_Date,date);
            values.put(colTar_Week,week);
            values.put(colTar_Day,day);



            String sql="SELECT "+colTar_Target+" FROM "+TB_Target+" WHERE "+colTar_Cus_Code+"="+ccode+" AND "+colTar_CatID+"="+catID+" AND "+colTar_Date+"="+date+" AND "+colTar_Week+"='"+week+"' AND "+colTar_Day+"='"+day+"'";
            Cursor cursor = db.rawQuery(sql,null);
            if(cursor.getCount()>0) {
                ContentValues values1 = new ContentValues();
                values1.put(colTar_Target,target);
                is_insert = returnSaveState(db.update(TB_Target, values1, colTar_Cus_Code + "=" + ccode + " AND " + colTar_CatID + "=" + catID + " AND "+colTar_Date+"="+date+" AND "+colTar_Week+"='"+week+"' AND "+colTar_Day+"='"+day+"'", null));
            }
            else
                is_insert = returnSaveState(db.insert(TB_Target,null,values));

        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return is_insert;
    }


    public boolean Send_Target (String tardate,String tarweek,String tarday,String simserial){
        boolean issend=false;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String [] col = {colTar_Cus_Code,colTar_CatID,colTar_Target,colTar_Sales_code};
            Cursor cursor = db.query(TB_Target,col,colTar_Date+"="+tardate+" AND "+colTar_Week+"="+tarweek+" AND "+colTar_Day+"='"+tarday+"'", null,null,null,null);

            Class.forName(driver);
            conn=DriverManager.getConnection(connString_SAP,Uname,Password);
            Statement InsStmt = conn.createStatement();

            Statement ChkStmt = conn.createStatement();
            ResultSet ChkRs = ChkStmt.executeQuery("Select Flag from RootTargetLock where TargetDate="+tardate+" and TargetWeek="+tarweek+" and TargetDay="+tarday+"");
            String flag="";
            if(ChkRs!=null){
                while (ChkRs.next()){
                    flag = ChkRs.getString(0);
                }
            }

            if(!flag.equals("x")){
                if(cursor.getCount()>0) {
                    while (cursor.moveToNext()){
                        String ccode_ = cursor.getString(0);
                        String category_ = cursor.getString(1);
                        String target_ = cursor.getString(2);
                        String scode_ = cursor.getString(3);

                        String year = tardate.substring(0,4);
                        String month = tardate.substring(4,6);

                        String ins_row = " if not exists (Select CatTarget from RootPlanMaster where Category='"+category_+"' and SalesCode='"+scode_+"' and CusCode='"+ccode_+"' and TargetYear='"+year+"' and TargetMonth='"+month+"' and TargetWeek="+tarweek+" and TargetDay="+tarday+") " +
                                " Insert into RootPlanMaster (TargetYear,TargetMonth,TargetWeek,TargetDay,CusCode,Category,CatTarget,SimSerial,SalesCode,CreatedDate) values ('"+year+"','"+month+"',"+tarweek+","+tarday+",'"+ccode_+"','"+category_+"',"+target_+",'"+simserial+"','"+scode_+"',GETDATE()) " +
                                " else " +
                                " update RootPlanMaster set CatTarget="+target_+" where Category='"+category_+"' and SalesCode='"+scode_+"' and CusCode='"+ccode_+"' and TargetYear='"+year+"' and TargetMonth='"+month+"' and TargetWeek="+tarweek+" and TargetDay="+tarday+"";

                        issend = InsStmt.execute(ins_row);
                        issend=true;
                    }
                    conn.close();
                }
            }

        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return issend;
    }

    public boolean Send_Target_category (String tardate,String tarweek,String tarday,String catid,String simserial){
        boolean issend=false;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String [] col = {colTar_Cus_Code,colTar_CatID,colTar_Target,colTar_Sales_code};
            Cursor cursor = db.query(TB_Target,col,colTar_Date+"="+tardate+" AND "+colTar_Week+"="+tarweek+" AND "+colTar_Day+"='"+tarday+"' AND "+colTar_CatID+"="+catid+"", null,null,null,null);

            Class.forName(driver);
            conn=DriverManager.getConnection(connString_SAP,Uname,Password);
            Statement InsStmt = conn.createStatement();

            Statement ChkStmt = conn.createStatement();
            ResultSet ChkRs = ChkStmt.executeQuery("Select Flag from RootTargetLock where TargetDate="+tardate+" and TargetWeek="+tarweek+" and TargetDay="+tarday+"");
            String flag="";
            if(ChkRs!=null){
                while (ChkRs.next()){
                    flag = ChkRs.getString(0);
                }
            }

            if(!flag.equals("x")){
                if(cursor.getCount()>0) {
                    while (cursor.moveToNext()){
                        String ccode_ = cursor.getString(0);
                        String category_ = cursor.getString(1);
                        String target_ = cursor.getString(2);
                        String scode_ = cursor.getString(3);

                        String year = tardate.substring(0,4);
                        String month = tardate.substring(4,6);

                        String ins_row = " if not exists (Select CatTarget from RootPlanMaster where Category='"+category_+"' and SalesCode='"+scode_+"' and CusCode='"+ccode_+"' and TargetYear='"+year+"' and TargetMonth='"+month+"' and TargetWeek="+tarweek+" and TargetDay="+tarday+") " +
                                " Insert into RootPlanMaster (TargetYear,TargetMonth,TargetWeek,TargetDay,CusCode,Category,CatTarget,SimSerial,SalesCode,CreatedDate) values ('"+year+"','"+month+"',"+tarweek+","+tarday+",'"+ccode_+"','"+category_+"',"+target_+",'"+simserial+"','"+scode_+"',GETDATE()) " +
                                " else " +
                                " update RootPlanMaster set CatTarget="+target_+" where Category='"+category_+"' and SalesCode='"+scode_+"' and CusCode='"+ccode_+"' and TargetYear='"+year+"' and TargetMonth='"+month+"' and TargetWeek="+tarweek+" and TargetDay="+tarday+"";

                        InsStmt.execute(ins_row);

                        issend=true;
                    }
                    conn.close();
                }
            }

        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return issend;
    }


    public boolean Download_Achievement (String year,String month,String myscode){
        boolean insert = false;
        SQLiteDatabase db=this.getReadableDatabase();
        try{
            Class.forName(driver);
            conn=DriverManager.getConnection(connString_SAP,Uname,Password);
            Statement Stmt = conn.createStatement();
            //String scode = this.returnScode();
            String scode = "";
            if(myscode.equals("")) {
                scode = this.returnScode();
            }else
                scode=myscode;

//            ArrayList<String> area = returnArea_ASM();
//            int areasize = area.size();
//            if(areasize>1){
//                // ASM SIM SERIAL
//
//                // Delete achievement table and insert
//                db.delete(TB_Achievement,null,null);
//
//                for(int j=0;j<areasize;j++) {
//                    String myarea = area.get(1).toString().split("-")[0];
//                    // DOWNLOAD ROOT ACHIEVEMENT ACCORDING TO SALES CODE
//                    String Query = "SELECT SalesCode,cuscode,targetyear,targetmonth,targetday,category,RC_Category,cattarget,totalsale FROM viewRootSaleAchievement WHERE TargetYear="+year+" AND TargetMonth="+month+" AND SalesCode='"+myarea+"'";
//                    ResultSet rs_achivement = Stmt.executeQuery(Query);
//                    if(rs_achivement!=null){
//                        while(rs_achivement.next()){
//                            ContentValues values = new ContentValues();
//                            values.put(colAch_Sales_Code,rs_achivement.getString("SalesCode"));
//                            values.put(colAch_Cus_Code,rs_achivement.getString("cuscode"));
//                            values.put(colAch_Date,rs_achivement.getString("targetyear")+rs_achivement.getString("targetmonth"));
//                            values.put(colAch_Day,rs_achivement.getString("targetday"));
//                            values.put(colAch_CatID,rs_achivement.getString("category"));
//                            values.put(colAch_Category,rs_achivement.getString("RC_Category"));
//                            values.put(colAch_Target,rs_achivement.getString("cattarget"));
//                            values.put(colAch_Sale,rs_achivement.getString("totalsale"));
//
//
//                            db.insert(TB_Achievement,null,values);
//                        }
//                    }
//                }
//            }

            {
                // DOWNLOAD ROOT ACHIEVEMENT ACCORDING TO SALES CODE
                String Query = "SELECT SalesCode,cuscode,targetyear,targetmonth,targetday,category,RC_Category,cattarget,totalsale FROM viewRootSaleAchievement WHERE TargetYear=" + year + " AND TargetMonth=" + month + " AND SalesCode='" + scode + "' AND CatTarget <> '0' ";
                ResultSet rs_achivement = Stmt.executeQuery(Query);
                if (rs_achivement != null) {
                    // Delete achievement table and insert
                    db.delete(TB_Achievement, null, null);
                    while (rs_achivement.next()) {
                        ContentValues values = new ContentValues();
                        values.put(colAch_Sales_Code, rs_achivement.getString("SalesCode"));
                        values.put(colAch_Cus_Code, rs_achivement.getString("cuscode"));
                        values.put(colAch_Date, rs_achivement.getString("targetyear") + rs_achivement.getString("targetmonth"));
                        values.put(colAch_Day, rs_achivement.getString("targetday"));
                        values.put(colAch_CatID, rs_achivement.getString("category"));
                        values.put(colAch_Category, rs_achivement.getString("RC_Category"));
                        values.put(colAch_Target, rs_achivement.getString("cattarget"));
                        values.put(colAch_Sale, rs_achivement.getString("totalsale"));


                        db.insert(TB_Achievement, null, values);
                    }
                }
            }

            conn.close();;
            db.close();
        }catch (Exception ex){
            db.close();
            Log.w("error",ex.getMessage());
        }
        return insert;
    }


    public Cursor Get_Area_Achievement(String yearmonth,String area){
        Cursor cursor = null;
        double precentage=0.00;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            //String query = "SELECT SUM("+colAch_Target+"),SUM("+colAch_Sale+") FROM "+TB_Achievement+" WHERE "+colAch_Date+"="+yearmonth+" AND "+colAch_Sales_Code+"='"+area+"'";
            String query = "SELECT SUM("+colAch_Target+"),SUM("+colAch_Sale+") FROM "+TB_Achievement+" WHERE "+colAch_Date+"="+yearmonth+"";
            cursor = db.rawQuery(query,null);

        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return cursor;
    }


    public boolean Download_Month(String year,String month){
        boolean insert = false;
        SQLiteDatabase db=this.getReadableDatabase();
        try{
            Class.forName(driver);
            conn=DriverManager.getConnection(connString_SAP,Uname,Password);
            Statement Stmt = conn.createStatement();
           // String scode = this.returnScode();

            // DOWNLOAD Root month planner with days and weeks
            String Query = "SELECT TargetYear,TargetMonth,TargetWeek,TargetDay FROM RootTargetPrecentage WHERE TargetYear="+year+" AND TargetMonth="+month+"";
            ResultSet rs_month = Stmt.executeQuery(Query);
            if(rs_month!=null){
                // Delete achievement table and insert
                db.delete(TB_Month,null,null);
                while(rs_month.next()){
                    ContentValues values = new ContentValues();
                    values.put(colMo_year,rs_month.getString("TargetYear"));
                    values.put(colMo_Month,rs_month.getString("TargetMonth"));
                    values.put(colMo_Week,rs_month.getString("TargetWeek"));
                    values.put(colMo_Day,rs_month.getString("TargetDay"));

                    db.insert(TB_Month,null,values);
                }
            }

            conn.close();
            db.close();
        }catch (Exception ex){
            db.close();
            Log.w("error",ex.getMessage());
        }
        return insert;
    }


    public ArrayList<subweekclass> Set_Week_Achievement(String year,String month){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<subweekclass> data=new ArrayList<>();
        try{

            for(int i=1;i<=4;i++) {
                double Salevalue = 0.00,TargetValue=0.00;
                String[] coldays = {colMo_Day};
                Cursor cursor = db.query(TB_Month, coldays, colMo_year + "=" + year + " AND "+colMo_Month+ "="+month+" AND " + colTar_Week + "='"+i+"'", null, null, null, null);
                while(cursor.moveToNext()){
                    String day = cursor.getString(0);
                    String[] colSale={colAch_Sale,colAch_Target};
                    String query_achi = "SELECT SUM("+colAch_Sale+"),SUM("+colAch_Target+") FROM "+TB_Achievement+" WHERE "+colAch_Date+"="+year+month+" AND "+colAch_Day+"="+day+"";
                    Cursor curSale = db.rawQuery(query_achi,null);
                    while (curSale.moveToNext()){
                        Double Sale = curSale.getDouble(0);
                        Double Target = curSale.getDouble(1);

                        TargetValue = TargetValue + Target;
                        Salevalue = Salevalue + Sale;
                    }
                }

                data.add(new subweekclass(Integer.toString(i),Double.toString(TargetValue),Double.toString(Salevalue)));

            }
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }

        return data;
    }


    public ArrayList<subdayclass> Set_Day_Achievement(String year,String month,String week){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<subdayclass> data=new ArrayList<>();
        try{
            String[] coldays = {colMo_Day};
            Cursor cursorD = db.query(TB_Month, coldays, colMo_year + "=" + year + " AND "+colMo_Month+ "="+month+" AND " + colTar_Week + "='"+week+"'", null, null, null, null);
            while(cursorD.moveToNext()) {
                double Salevalue = 0.00,TargetValue=0.00;
                String day = cursorD.getString(0);
                String Local_Query = "SELECT  SUM("+colAch_Target+"),SUM("+colAch_Sale+") FROM "+TB_Achievement+" WHERE "+colAch_Date+"="+year+month+" AND "+colAch_Day+"="+day+"";
                Cursor cursor = db.rawQuery(Local_Query,null);
                while (cursor.moveToNext()) {
                    double Target = cursor.getDouble(0);
                    double Sale = cursor.getDouble(1);

                    TargetValue = TargetValue + Target;
                    Salevalue = Salevalue + Sale;
                }
                data.add(new subdayclass(day,Double.toString(TargetValue),Double.toString(Salevalue)));
            }




        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }

        return data;
    }


    public Cursor Set_Category_Achievement(String year,String month,String day){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=null;
        try {
//            String Local_Query = "SELECT "+colAT_CatID+","+colAT_Category+",SUM("+colAT_Target+") FROM "+TB_AREA_TARGET+" " +
//                    "WHERE "+colAT_Date+"="+month+" AND "+colAT_Week+"="+week+" AND "+colAT_Day+"="+day+"  GROUP BY "+colAT_CatID+","+colAT_Category+"";
            String query = "SELECT "+colAch_CatID+","+colAch_Category+",SUM("+colAch_Target+"),SUM("+colAch_Sale+") FROM "+TB_Achievement+" WHERE "+colAch_Date+"="+year+month+" AND "+colAch_Day+"="+day+" GROUP BY "+colAch_CatID+","+colAch_Category+"";
            cursor=db.rawQuery(query,null);
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return  cursor;
    }

    public String ReturnCus_Cat_Achievement(String ccode,String cat,String date_,String day_){
        String target="0.00";
        SQLiteDatabase db=this.getReadableDatabase();
        try{
            String [] col = {colAch_Target,colAch_Sale};
            Cursor cursor = db.query(TB_Achievement,col,colAch_Cus_Code+"="+ccode+" AND "+colAch_CatID+"="+cat+" AND "+colAch_Date+"="+date_+" AND "+colAch_Day+"='"+day_+"'",null,null,null,null);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    target = cursor.getString(1);
                }
            }

        }catch (Exception e){
            Log.w("error",e.getMessage());
        }
        return target;
    }



    public ArrayList<String> ReturnWeek_days(String week){
        ArrayList<String> data=new ArrayList<>();
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT "+colMo_Day+" FROM "+TB_Month+" WHERE "+colMo_Week+"="+week+"",null);
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    NumberFormat f = new DecimalFormat("00");
                    data.add(f.format( Long.parseLong(cursor.getString(0))));
                }
            }

        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return data;
    }

    public Cursor Load_TargetAssigned(String ccode,String date_,String week_,String day_){
      Cursor mycursor=null;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            //String query = "SELECT SUM("+colAch_Target+"),SUM("+colAch_Sale+") FROM "+TB_Achievement+" WHERE "+colAch_Cus_Code+"="+ccode+" AND "+colAch_Date+"="+date_+ " AND "+colAch_Day+"='"+day_+"'";
            String query = "SELECT SUM("+colTar_Target+"),0 FROM "+TB_Target+" WHERE "+colTar_Cus_Code+"="+ccode+" AND "+colTar_Date+"="+date_+ " AND "+colTar_Day+"='"+day_+"'";
            mycursor = db.rawQuery(query,null);
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return mycursor;
    }

    public Cursor Load_TargetAchievement(String ccode,String date_,String day_){
        Cursor mycursor=null;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String query = "SELECT SUM("+colAch_Target+"),SUM("+colAch_Sale+") FROM "+TB_Achievement+" WHERE "+colAch_Cus_Code+"="+ccode+" AND "+colAch_Date+"="+date_+ " AND "+colAch_Day+"='"+day_+"'";
            mycursor = db.rawQuery(query,null);
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return mycursor;
    }

    public Cursor Set_CUS_Category_Achievement(String year,String month,String day,String cuscode){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=null;
        try {
//            String Local_Query = "SELECT "+colAT_CatID+","+colAT_Category+",SUM("+colAT_Target+") FROM "+TB_AREA_TARGET+" " +
//                    "WHERE "+colAT_Date+"="+month+" AND "+colAT_Week+"="+week+" AND "+colAT_Day+"="+day+"  GROUP BY "+colAT_CatID+","+colAT_Category+"";
            String query = "SELECT "+colAch_CatID+","+colAch_Category+",SUM("+colAch_Target+"),SUM("+colAch_Sale+") FROM "+TB_Achievement+" WHERE "+colAch_Date+"="+year+month+" AND "+colAch_Day+"="+day+" AND "+colAch_Cus_Code+"="+cuscode+" GROUP BY "+colAch_CatID+","+colAch_Category+"";
            cursor=db.rawQuery(query,null);
        }catch (Exception ex){
            Log.w("error",ex.getMessage());
        }
        return  cursor;
    }



    /** FOR CUSTOMER MANAGEMENT PURPOSE**/

    public int Update_customer_day(String ccode,String day){
        SQLiteDatabase db=this.getWritableDatabase();
        int update = 0;
        try{
            ContentValues values=new ContentValues();
            values.put(colCus_Assign_Day,day);
            values.put(colCus_SendSts,"x");
            
            update = db.update(TB_Customers,values,colCus_Cus_Code+"="+ccode+"",null);            
            
        }catch (Exception e){
            Log.w("error",e.getMessage());
        }
        return  update;
    }

    public int Update_customer_status(String ccode){
        SQLiteDatabase db=this.getWritableDatabase();
        int update = 0;
        try{
            ContentValues values=new ContentValues();
            values.put(colCus_SendSts,"s");

            update = db.update(TB_Customers,values,colCus_Cus_Code+"="+ccode+"",null);

        }catch (Exception e){
            Log.w("error",e.getMessage());
        }
        return  update;
    }

    public Cursor Retrivew_customers(String day){
        Cursor cursor=null;
        SQLiteDatabase db=this.getReadableDatabase();
        try{
            String [] columns={colCus_Cus_Code,colCus_Cus_Name,colCus_Area_Name,colCus_Sales_Code};
            if(day.equals("")) {
                cursor = db.query(TB_Customers, columns, colCus_Assign_Day + " IS NULL", null, null, null, colCus_Cus_Name);
            }else
                cursor = db.query(TB_Customers, columns, colCus_Assign_Day + "='"+day+"'", null, null, null, colCus_Cus_Name);
        }catch (SQLiteException ex){
            Log.w("Error",ex.getMessage());
        }
        return cursor;
    }

    public boolean SendToHost_Customer(){
        SQLiteDatabase db=this.getReadableDatabase();
        boolean issend=false;
        try{

            String [] col = {colCus_Cus_Code,colCus_Sales_Code,colCus_Assign_Day};
            Cursor cur = db.query(TB_Customers,col,colCus_SendSts+" = 'x'",null,null,null,null);
            if(cur.getCount()>0){

                Class.forName(driver);
                conn = DriverManager.getConnection(connString_SAP,Uname,Password);
                Statement stmt = conn.createStatement();

                while(cur.moveToNext()) {
                    String cuscode = cur.getString(0);
                    String salecode = cur.getString(1);
                    String assday = cur.getString(2);

                    String ins_row = "IF NOT EXISTS (SELECT [RCP_ID] FROM [RootCustomerPlanMaster] WHERE CUSCODE='"+cuscode+"' AND SALESCODE = '"+salecode+"') INSERT INTO [RootCustomerPlanMaster] (CUSCODE,SALESCODE,DAY) VALUES ('"+cuscode+"','"+salecode+"','"+assday+"')" +
                            " ELSE UPDATE [RootCustomerPlanMaster] SET DAY = '"+assday+"' WHERE CUSCODE='"+cuscode+"' AND SALESCODE='"+salecode+"' AND STATUS IS NULL";

                    issend = stmt.execute(ins_row);
                    Update_customer_status(cuscode);
                    issend = true;

                }
            }


        }catch (SQLiteException ex){
            Log.w("error",ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issend;
    }


    public  boolean chkcusLock(String tday){
        SQLiteDatabase db = this.getReadableDatabase();
        boolean islock=false;
        try{
            String col[] = {colTL_cusSts};
            Cursor cursor = db.query(TB_TargetLock,col,colTL_Date+" = "+tday+" AND "+colTL_cusSts+" = 'x'",null,null,null,null);
            if(cursor.getCount()>0){
                islock = true;
            }

        }catch (Exception ee){
            Log.w("error",ee.getMessage());
        }
        return islock;
    }

}