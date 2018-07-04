package com.nipponit.manojm.smartplanner10;



import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CustomGridAdapter extends BaseAdapter {
	
	private Context context; 
	private final String[] gridValues;
	Fonts fonts;

	//Constructor to initialize values
	public CustomGridAdapter(Context context, String[] gridValues) {
		this.context = context;
		this.gridValues = gridValues;

		fonts=new Fonts(context);
	}
	
	@Override
	public int getCount() {
		
		// Number of times getView method call depends upon gridValues.length
		return gridValues.length;
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}
	
	
    // Number of times getView method call depends upon gridValues.length
	
	public View getView(int position, View convertView, ViewGroup parent) {
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/Open_Sans/OpenSans-Regular.ttf");
		//LayoutInflator to call external grid_item.xml file
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);



			// get layout from grid_item.xml
			gridView = inflater.inflate(R.layout.griditem_prototype, null);

			TextView txt = (TextView)gridView.findViewById(R.id.lbl);
			txt.setTypeface(fonts.SetType("ubuntu-reg"));


			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_category);
			//textView.setText(gridValues[position]);
			textView.setTypeface(fonts.SetType("ubuntu-reg"));


			TextView textView1 = (TextView)gridView.findViewById(R.id.grid_item_value);
			textView1.setTypeface(fonts.SetType("ubuntu-reg"));
			//textView1.setText("example "+position);

			// set image based on selected text
			
//			ImageView imageView = (ImageView) gridView
//					.findViewById(R.id.grid_item_image);
//
//			String mobile = gridValues[position];
//
//			if (mobile.equals("Windows")) {
//
//				imageView.setImageResource(R.mipmap.ic_launcher);
//
//			} else if (mobile.equals("iOS")) {
//
//				imageView.setImageResource(R.mipmap.ic_launcher);
//
//			} else if (mobile.equals("Blackberry")) {
//
//				imageView.setImageResource(R.mipmap.ic_launcher);
//
//			} else {
//
//				imageView.setImageResource(R.mipmap.ic_launcher);
//			}

		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}
}
