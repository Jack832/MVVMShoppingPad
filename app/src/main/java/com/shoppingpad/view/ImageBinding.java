package com.shoppingpad.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

/**
 * Created by bridgelabz4 on 18/3/16.
 */
public class ImageBinding
{

    //to bind image we use this method it contanis the url and image view
    //position we are using picasso to load the image from url
    @BindingAdapter("bind:imageUrl")
    public static void getDisplayImage(ImageView imageView,String url)
    {
        Context context=imageView.getContext();
        Picasso.with(context).load(url).into(imageView);
    }
}
