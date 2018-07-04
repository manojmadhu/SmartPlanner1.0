package com.nipponit.manojm.smartplanner10;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by manojm on 10/19/2017.
 */



public class CustomCategoryAdapter extends ArrayAdapter<CategoryClass> {


    private Context context;
    Fonts fonts;
    TextView lbl;


    private static class ViewHolder{
        TextView txtcategory;
        TextView txttarget;
        TextView txtsale;
        TextView lbl2;

    }

    public CustomCategoryAdapter(Context context, ArrayList<CategoryClass> data) {
        super(context, R.layout.griditem_prototype,data);
        fonts=new Fonts(getContext());

    }


//    private int lastposition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryClass categoryModel = getItem(position);
        ViewHolder viewHolder = null;
        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.target_prototype,parent,false);

            viewHolder.txtcategory = (TextView) convertView.findViewById(R.id.grid_item_category);
            viewHolder.txttarget = (TextView) convertView.findViewById(R.id.grid_item_value);
            lbl = (TextView) convertView.findViewById(R.id.lbl);

            viewHolder.txtsale = (TextView)convertView.findViewById(R.id.grid_item_value_achieve);
            viewHolder.lbl2 = (TextView)convertView.findViewById(R.id.lbl1);

            viewHolder.txtcategory.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.txttarget.setTypeface(fonts.SetType("ubuntu-reg"));
            lbl.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.txtsale.setTypeface(fonts.SetType("ubuntu-reg"));
            viewHolder.lbl2.setTypeface(fonts.SetType("ubuntu-reg"));

//            result = convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
//            result = convertView;
        }


//        Animation animation = AnimationUtils.loadAnimation(getContext(),(position > lastposition) ? R.anim.up_from_bottom: R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastposition=position;

        viewHolder.txtcategory.setText(categoryModel.getCategory_());
        viewHolder.txttarget.setText(categoryModel.getTarget_());
        viewHolder.txtsale.setText(categoryModel.getAssign());
        return convertView;
    }
}
