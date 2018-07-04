package com.nipponit.manojm.smartplanner10;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by manojm on 10/18/2017.
 */

public class Fonts {
    Context Mycontext;
    public Fonts(Context context) {
        Mycontext = context;
    }

    public Typeface SetType(String font){
        Typeface typeface = null;
        switch (font){
            case "ubuntu-reg":
                typeface = Typeface.createFromAsset(Mycontext.getAssets(), "font/Ubuntu/Ubuntu-Regular.ttf");
                break;
            case "ubuntu-bold":
                typeface = Typeface.createFromAsset(Mycontext.getAssets(), "font/Ubuntu/Ubuntu-Bold.ttf");
                break;
            case "ubuntu-medium":
                typeface = Typeface.createFromAsset(Mycontext.getAssets(), "font/Ubuntu/Ubuntu-Medium.ttf");
                break;
            case "ubuntu-medium-italic":
                typeface = Typeface.createFromAsset(Mycontext.getAssets(), "font/Ubuntu/Ubuntu-MediumItalic.ttf");
                break;
            case "ubuntu-light":
                typeface = Typeface.createFromAsset(Mycontext.getAssets(), "font/Ubuntu/Ubuntu-Light.ttf");
                break;
            case "sans-reg":
                typeface = Typeface.createFromAsset(Mycontext.getAssets(), "font/Open_Sans/OpenSans-Regular.ttf");
                break;
            case "sans-bold":
                typeface = Typeface.createFromAsset(Mycontext.getAssets(), "font/Open_Sans/OpenSans-Bold.ttf");
                break;


        }
        return typeface;
    }
}
