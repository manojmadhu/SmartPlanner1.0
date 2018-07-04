package com.nipponit.manojm.smartplanner10;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by manojm on 10/25/2017.
 */

public class WeekAdapter extends ArrayAdapter<WeekClass> {

    Fonts fonts;
    static String ubuntu_reg = "ubuntu-reg";

    private class ViewHolder{
        TextView txtweek;
        TextView txttarget;
        TextView txtsale;
        TextView lbl1;
        TextView lbl2;
    }


    public WeekAdapter(Context context, ArrayList<WeekClass> data) {
        super(context, R.layout.griditem_week_prototype,data);
        fonts=new Fonts(getContext());
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        WeekClass weekmodel = getItem(position);
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.griditem_week_prototype,parent,false);

            viewHolder.txtweek = (TextView)convertView.findViewById(R.id.grid_item_week);
            viewHolder.txttarget = (TextView)convertView.findViewById(R.id.grid_item_week_value);
            viewHolder.txtsale = (TextView)convertView.findViewById(R.id.grid_item_week_value_sale);
            viewHolder.lbl1 = (TextView)convertView.findViewById(R.id.lbl);
            viewHolder.lbl2 = (TextView)convertView.findViewById(R.id.lbl1);

            viewHolder.txtweek.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.txttarget.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.txtsale.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.lbl1.setTypeface(fonts.SetType(ubuntu_reg));
            viewHolder.lbl2.setTypeface(fonts.SetType(ubuntu_reg));

            convertView.setTag(viewHolder);


        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtweek.setText(weekmodel.getWeekno_());
        viewHolder.txttarget.setText(weekmodel.getTarget_());
        viewHolder.txtsale.setText(weekmodel.getSale_());

        return convertView;
    }
}
