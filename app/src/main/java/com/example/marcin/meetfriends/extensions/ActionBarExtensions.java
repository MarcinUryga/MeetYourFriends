package com.example.marcin.meetfriends.extensions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;

import com.example.marcin.meetfriends.utils.CircleTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by marci on 2017-11-18.
 */

public class ActionBarExtensions {

    public static void loadUserIcon(final Context context, final ActionBar actionBar, String uri) {
        Picasso.with(context)
                .load(uri)
                .resize(64, 64)
                .transform(new CircleTransform())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Drawable d = new BitmapDrawable(context.getResources(), bitmap);
                        actionBar.setIcon(d);
                        actionBar.setDisplayShowHomeEnabled(true);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
}
