package com.nipponit.manojm.smartplanner10.Achievement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nipponit.manojm.smartplanner10.DateClass;
import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;

import java.util.ArrayList;

/**
 * Created by manojm on 10/25/2017.
 */

public class DateAchievementAdapter extends ArrayAdapter<DateAchievementClass> {

    Fonts fonts;
    static String ubuntu_reg = "ubuntu-reg";

    private class ViewHolder{
        TextView txtDate;
        TextView txtDay;
        TextView txttarget;
        TextView txtsale;
        TextView txtprecentage;
        TextView lbl1;
        TextView lbl2;
        TextView lbl3;
    }


    public DateAchievementAdapter(Context context, ArrayList<DateAchievementClass> data) {
        super(context, R.layout.griditem_week_prototype,data);
        fonts=new Fonts(getContext());
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        DateAchievementClass datemodel = getItem(position);
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.griditem_achievement_prototype,parent,false);

            viewHolder.txtDate = (TextView)convertView.findViewById(R.id.grid_item_week);
            viewHolder.txtDay = (TextView)convertView.findViewById(R.id.grid_item_day);
            viewHolder.txttarget = (TextView)convertView.findViewById(R.id.grid_item_week_value);
            viewHolder.txtsale = (TextView)convertView.findViewById(R.id.grid_item_week_value_sale);
            viewHolder.txtprecentage = (TextView)convertView.findViewById(R.id.grid_item_week_precentage);
            viewHolder.lbl1 = (TextView)convertView.findViewById(R.id.lbl);
            viewHolder.lbl1.setText("My Target");
            viewHolder.lbl2 = (TextView)convertView.findViewById(R.id.lbl1);
            viewHolder.lbl3 = (TextView)convertView.findViewById(R.id.lbl2);

            viewHolder.txtDate.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.txtDay.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.txttarget.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.txtsale.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.txtprecentage.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.lbl1.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.lbl2.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.lbl3.setTypeface(fonts.SetType(ubuntu_reg));

            convertView.setTag(viewHolder);


        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtDate.setText(datemodel.getDate_());
        viewHolder.txtDay.setText(datemodel.getDay_());
        viewHolder.txttarget.setText(datemodel.getTarget_());
        viewHolder.txtsale.setText(datemodel.getAssign_());
        viewHolder.txtprecentage.setText(datemodel.getPrecentage_());

        return convertView;
    }
}
