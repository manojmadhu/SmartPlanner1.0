package com.nipponit.manojm.smartplanner10.Achievement.CategoryFigure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nipponit.manojm.smartplanner10.CategoryClass;
import com.nipponit.manojm.smartplanner10.Fonts;
import com.nipponit.manojm.smartplanner10.R;

import java.util.ArrayList;

/**
 * Created by manojm on 10/19/2017.
 */



public class CustomAchievementCategoryAdapter extends ArrayAdapter<CategoryAchievementClass> {


    private Context context;
    Fonts fonts;
    TextView lbl;
    static String ubuntu_reg = "ubuntu-reg";


    private static class ViewHolder{
        TextView txtcategory;
        TextView txttarget;
        TextView txtsale;
        TextView txtprecentage;
        TextView lbl1;
        TextView lbl2;
        TextView lbl3;

    }

    public CustomAchievementCategoryAdapter(Context context, ArrayList<CategoryAchievementClass> data) {
        super(context, R.layout.griditem_achievement_prototype,data);
        fonts=new Fonts(getContext());

    }


//    private int lastposition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryAchievementClass categoryModel = getItem(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.griditem_achievement_prototype,parent,false);

            viewHolder.txtcategory = (TextView)convertView.findViewById(R.id.grid_item_week);
            viewHolder.txttarget = (TextView)convertView.findViewById(R.id.grid_item_week_value);
            viewHolder.txtsale = (TextView)convertView.findViewById(R.id.grid_item_week_value_sale);
            viewHolder.txtprecentage = (TextView)convertView.findViewById(R.id.grid_item_week_precentage);

            viewHolder.lbl1 = (TextView)convertView.findViewById(R.id.lbl);
            viewHolder.lbl1.setText("My Target");
            viewHolder.lbl2 = (TextView)convertView.findViewById(R.id.lbl1);
            viewHolder.lbl3 = (TextView)convertView.findViewById(R.id.lbl2);

            viewHolder.txtcategory.setTypeface(fonts.SetType(ubuntu_reg));
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


        viewHolder.txtcategory.setText(categoryModel.getCategory_());
        viewHolder.txttarget.setText(categoryModel.getTarget_());
        viewHolder.txtsale.setText(categoryModel.getSale_());
        viewHolder.txtprecentage.setText(categoryModel.getPrecentage_());



        return convertView;
    }
}
