package app.quickfood.view;

import android.content.Context;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import app.quickfood.R;

public class Util {

    public static void loadImage(ImageView view, String url, CircularProgressDrawable progressDrawable){

        MultiTransformation transformation = new MultiTransformation(new CenterCrop(), new RoundedCorners(5));

        RequestOptions options= new RequestOptions()
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(view.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .transform(transformation)//for rounded corners
                .into(view);
    }

    public static CircularProgressDrawable getCircularProgressDrawable(Context context){
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.start();
        return progressDrawable;
    }
}
