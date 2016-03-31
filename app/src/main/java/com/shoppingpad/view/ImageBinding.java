package com.shoppingpad.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shoppingpad.R;
import com.shoppingpad.TimeCalculate;
import com.squareup.picasso.Picasso;

/**
 * Created by bridgelabz4 on 18/3/16.
 */
public class ImageBinding
{
    static TimeCalculate time;
    static double totalTime;

    //to bind image we use this method it contanis the url and image view
    //position we are using picasso to load the image from url
    @BindingAdapter("bind:imageUrl")
    public static void getDisplayImage(ImageView imageView,String url)
    {
        Context context=imageView.getContext();
        time= new TimeCalculate();
        time.startTimer();
        Picasso.with(context).load(url).placeholder(R.drawable.nehra).into(imageView);
        time.endTime();
        totalTime=time.timeRequired();
        Log.w("Image set", ""+totalTime);
    }
}
