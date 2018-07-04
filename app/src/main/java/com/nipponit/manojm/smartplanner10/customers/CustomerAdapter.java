package com.nipponit.manojm.smartplanner10.customers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manojm on 10/18/2017.
 */

public class CustomerAdapter extends ArrayAdapter<CustomerModel> {

    private Context context;
    private ArrayList<CustomerModel> dataset;

    private ArrayList<CustomerModel> Original_List;
    private ArrayList<CustomerModel> Customer_List;
    private SparseBooleanArray mSelectedItemsIds;
    CustomerFilter filter;
    Typeface typeface;
    Fonts fonts;
    private int selectedItem=-1;
    List multipleSelectionList;



    private class ViewHolder{
        TextView txtccode;
        TextView txtcname;
        TextView txtccity;
    }


//    public CustomerAdapter(Context context,int resourceId,List items){
//        super(context,resourceId,items);
//        this.context = context;
//        mSelectedItemsIds = new SparseBooleanArray();
//        this.multipleSelectionList = items;
//        fonts=new Fonts(context);
//    }

    public CustomerAdapter(Context context, ArrayList<CustomerModel> data) {
        super(context,R.layout.customer_prototype_check,data);
        this.context = context;
        this.dataset=data;
        fonts=new Fonts(context);

        mSelectedItemsIds = new SparseBooleanArray();
        //this.multipleSelectionList = data;

        this.Customer_List = new ArrayList<>();
        this.Customer_List.addAll(data);

        this.Original_List=new ArrayList<>();
        this.Original_List.addAll(data);
    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomerModel customerModel = getItem(position);
        ViewHolder viewHolder = null;

        final View result;

//        LayoutInflater mInflater = (LayoutInflater) context
//                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView==null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.customer_prototype_check, parent,false);

            viewHolder.txtccode=(TextView) convertView.findViewById(R.id.lblCus_code);
            viewHolder.txtcname=(TextView) convertView.findViewById(R.id.lblCus_name);
            viewHolder.txtccity=(TextView) convertView.findViewById(R.id.lblCus_city);

            viewHolder.txtccode.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.txtcname.setTypeface(fonts.SetType("ubuntu-bold"));
            viewHolder.txtccity.setTypeface(fonts.SetType("ubuntu-reg"));


            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }


        try {

            CustomerModel model = Customer_List.get(position);
            if (model.isSelected()) {
                viewHolder.txtccode.setTextColor(convertView.getResources().getColor(R.color.colorQuora));
                viewHolder.txtccode.setText(customerModel.getCcode_());
            } else {
                viewHolder.txtccode.setTextColor(convertView.getResources().getColor(R.color.colorBlack));
                viewHolder.txtccode.setText(customerModel.getCcode_());
            }
        }catch (IndexOutOfBoundsException ex){
            Log.w("error",ex.getMessage());
        }






        viewHolder.txtcname.setText(customerModel.getCname_());
        viewHolder.txtccity.setText(customerModel.getCcity_());


        //highlightItem(position,convertView);

        return convertView;
    }

    @Override
    public void remove(@Nullable CustomerModel object) {
        Customer_List.remove(object);
        notifyDataSetChanged();
    }

    public List<CustomerModel> getCustomers(){
        return Customer_List;
    }

    public void toggleSelection(int position){
        selectView(position, !mSelectedItemsIds.get(position));
        setSelected(position);
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
        {
            mSelectedItemsIds.put(position, value);
        }
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }



    public void setSelected(int position){
        CustomerModel model = Customer_List.get(position);
        if(!model.isSelected())
            model.setSelected(true);
        else
            model.setSelected(false);
    }

    public void setSelectedFilter(int position){
        CustomerModel model = Customer_List.get(position);
        if(!model.isSelected())
            model.setSelected(true);
        else
            model.setSelected(false);
    }







    @NonNull
    @Override
    public Filter getFilter( ) {
        if(filter == null){

            filter = new CustomerFilter();
        }
        return filter;
    }


    private class CustomerFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0 ){
                ArrayList<CustomerModel> filtered_list = new ArrayList<>();



                for(int i = 0, l = Original_List.size(); i < l; i++){
                    if((Original_List.get(i).getCname_().toUpperCase()).contains(constraint.toString().toUpperCase())){

                        CustomerModel CustomerModel = new CustomerModel(Original_List.get(i).getId_(),Original_List.get(i).isSelected(),Original_List.get(i).getCcode_(),
                                Original_List.get(i).getCname_(),Original_List.get(i).getCcity_());

                        filtered_list.add(CustomerModel);
                    }
                }
                results.count = filtered_list.size(   );
                results.values = filtered_list;
            }else{
                synchronized (this){
                    results.values = Original_List;
                    results.count = Original_List.size();
                }
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Customer_List = (ArrayList<CustomerModel>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = Customer_List.size(); i < l; i++){
                add(Customer_List.get(i));
            }
            notifyDataSetChanged();
        }
    }

}
