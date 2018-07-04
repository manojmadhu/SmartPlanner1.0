package com.nipponit.manojm.smartplanner10;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by manojm on 10/24/2017.
 */

public class CustomCategoryAchivementAdapter extends ArrayAdapter<Category_ach_class> {

    Fonts fonts;

    private static class ViewHolder{
        TextView txtcategory;
        TextView txttarget;
        TextView txtachievement;
        TextView lbl,lbl1;
    }


    public CustomCategoryAchivementAdapter(Context context, ArrayList<Category_ach_class> data) {
        super(context, R.layout.target_prototype,data);
        fonts=new Fonts(getContext());
    }



    private int lastposition = -1;

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        Category_ach_class category_ach_class = getItem(position);
        ViewHolder viewHolder = null;
        final View result;

        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.target_prototype,parent,false);

            viewHolder.txtcategory = (TextView)convertView.findViewById(R.id.grid_item_category);
            viewHolder.txttarget = (TextView)convertView.findViewById(R.id.grid_item_value);
            viewHolder.txtachievement = (TextView)convertView.findViewById(R.id.grid_item_value_achieve);
            viewHolder.lbl = (TextView)convertView.findViewById(R.id.lbl);
            viewHolder.lbl1 = (TextView)convertView.findViewById(R.id.lbl1);

            viewHolder.txtcategory.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.txttarget.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.txtachievement.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.lbl.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.lbl1.setTypeface(fonts.SetType("ubuntu-reg"));

//            result = convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
//            result = convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(getContext(),(position>lastposition)? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastposition = position;

        viewHolder.txtcategory.setText(category_ach_class.getCategory_());
        viewHolder.txttarget.setText(category_ach_class.getTarget_());
        viewHolder.txtachievement.setText(category_ach_class.getAchievement_());


        return convertView;
    }
}
