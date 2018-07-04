package com.nipponit.manojm.smartplanner10;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manojm on 10/18/2017.
 */

public class CustomCustomerAdapter extends ArrayAdapter<CustomerClass> {

    private Context context;
    private ArrayList<CustomerClass> dataset;

    private ArrayList<CustomerClass> Original_List;
    private ArrayList<CustomerClass> Customer_List;

    CustomerFilter filter;
    Typeface typeface;
    Fonts fonts;


    private static class ViewHolder{
        TextView txtccode;
        TextView txtcname;
        TextView txtccity;
    }

    public CustomCustomerAdapter(Context context, ArrayList<CustomerClass> data) {
        super(context,R.layout.customer_prototype,data);
        this.context = context;
        this.dataset=data;
        fonts=new Fonts(context);

        this.Customer_List = new ArrayList<>();
        this.Customer_List.addAll(data);

        this.Original_List=new ArrayList<>();
        this.Original_List.addAll(data);
    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomerClass customerModel = getItem(position);
        ViewHolder viewHolder = null;

        final View result;

        if(convertView==null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.customer_prototype, parent, false);

            viewHolder.txtccode=(TextView) convertView.findViewById(R.id.lblCus_code);
            viewHolder.txtcname=(TextView) convertView.findViewById(R.id.lblCus_name);
            viewHolder.txtccity=(TextView) convertView.findViewById(R.id.lblCus_city);

            viewHolder.txtccode.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.txtcname.setTypeface(fonts.SetType("ubuntu-bold"));
            viewHolder.txtccity.setTypeface(fonts.SetType("ubuntu-reg"));

            result=convertView;
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtccode.setText(customerModel.getCcode_());
        viewHolder.txtcname.setText(customerModel.getCname_());
        viewHolder.txtccity.setText(customerModel.getCcity_());

        return convertView;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomerFilter();
        }
        return filter;
    }


    private class CustomerFilter extends android.widget.Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0 ){
                ArrayList<CustomerClass> filtered_list = new ArrayList<>();
                for(int i = 0, l = Original_List.size(); i < l; i++){
                    if((Original_List.get(i).getCname_().toUpperCase()).contains(constraint.toString().toUpperCase())){
                        CustomerClass customerClass = new CustomerClass(Original_List.get(i).getCcode_(),
                                Original_List.get(i).getCname_(),Original_List.get(i).getCcity_());

                        filtered_list.add(customerClass);
                    }
                }
                results.count = filtered_list.size();
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
            Customer_List = (ArrayList<CustomerClass>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = Customer_List.size(); i < l; i++){
                add(Customer_List.get(i));
            }
            notifyDataSetChanged();
        }
    }
}
