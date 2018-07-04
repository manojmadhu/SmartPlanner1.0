package com.nipponit.manojm.smartplanner10.Achievement.CustomerFigure;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
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
import com.nipponit.manojm.smartplanner10.com.nipponit.manojm.smartplanner10.customer.CustomerClass_target;

import java.util.ArrayList;

/**
 * Created by manojm on 10/18/2017.
 */

public class CustomCustomerAchievementAdapter_target extends ArrayAdapter<CustomerAchievementClass_target> {

    private Context context;
    private ArrayList<CustomerAchievementClass_target> dataset;

    private ArrayList<CustomerAchievementClass_target> Original_List;
    private ArrayList<CustomerAchievementClass_target> Customer_List;

    CustomerFilter filter;
    CustomerFilter_TarCus filter_target ;
    Typeface typeface;
    Fonts fonts;


    private static class ViewHolder{
        TextView txtccode;
        TextView txtcname;
        TextView txtccity;
        TextView txtctarget;
    }

    public CustomCustomerAchievementAdapter_target(Context context, ArrayList<CustomerAchievementClass_target> data) {
        super(context,R.layout.customer_target_prototype,data);
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
        CustomerAchievementClass_target customerModel = getItem(position);
        ViewHolder viewHolder = null;

        final View result;

        if(convertView==null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.customer_target_prototype, parent, false);

            viewHolder.txtccode=(TextView) convertView.findViewById(R.id.lblCus_code);
            viewHolder.txtcname=(TextView) convertView.findViewById(R.id.lblCus_name);
            viewHolder.txtccity=(TextView) convertView.findViewById(R.id.lblCus_city);
            viewHolder.txtctarget=(TextView) convertView.findViewById(R.id.lblCus_target);

            viewHolder.txtccode.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.txtcname.setTypeface(fonts.SetType("ubuntu-bold"));
            viewHolder.txtccity.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.txtctarget.setTypeface(fonts.SetType("ubuntu-reg"));

            result=convertView;
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtccode.setText(customerModel.getCcode_());
        viewHolder.txtcname.setText(customerModel.getCname_());
        viewHolder.txtccity.setText(customerModel.getCcity_());
        viewHolder.txtctarget.setText(customerModel.getTarget_());

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


    private class CustomerFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0 ){
                ArrayList<CustomerAchievementClass_target> filtered_list = new ArrayList<>();
                for(int i = 0, l = Original_List.size(); i < l; i++){
                    if((Original_List.get(i).getCname_().toUpperCase()).contains(constraint.toString().toUpperCase())){
                        CustomerAchievementClass_target customerAchievementClass_target = new CustomerAchievementClass_target(Original_List.get(i).getCcode_(),
                                Original_List.get(i).getCname_(),Original_List.get(i).getCcity_(),Original_List.get(i).getTarget_());

                        filtered_list.add(customerAchievementClass_target);
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
            Customer_List = (ArrayList<CustomerAchievementClass_target>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = Customer_List.size(); i < l; i++){
                add(Customer_List.get(i));
            }
            notifyDataSetChanged();
        }
    }


    public Filter getFilter_target(){
        if(filter_target==null){
            filter_target = new CustomerFilter_TarCus();
        }
        return filter_target;
    }





    private class CustomerFilter_TarCus extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString();
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0 ){
                ArrayList<CustomerAchievementClass_target> filtered_list = new ArrayList<>();

                for(int i = 0, l = Original_List.size(); i < l; i++){
                    if(!(Original_List.get(i).getTarget_().toUpperCase()).contains(constraint.toString().toUpperCase())){
                        CustomerAchievementClass_target customerAchievementClass_target = new CustomerAchievementClass_target(Original_List.get(i).getCcode_(),
                                Original_List.get(i).getCname_(),Original_List.get(i).getCcity_(),Original_List.get(i).getTarget_());

                        filtered_list.add(customerAchievementClass_target);
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
            Customer_List = (ArrayList<CustomerAchievementClass_target>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = Customer_List.size(); i < l; i++){
                add(Customer_List.get(i));
            }
            notifyDataSetChanged();
        }
    }
}
