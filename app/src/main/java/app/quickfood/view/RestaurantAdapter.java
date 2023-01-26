package app.quickfood.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.quickfood.R;
import app.quickfood.model.RestaurantModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RecyclerViewHolder>{

    private List<RestaurantModel.Datauser> restaurants;
    private static String imgApiBase = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
    private int screen=1;
    SharedPreferences preferences;
    String startlat ="" , startlng ="" , address ="";

    public RestaurantAdapter(List<RestaurantModel.Datauser> restaurants, int screen){
        this.restaurants = restaurants;
        this.screen = screen;
    }

    public void updateRestaurantList(List<RestaurantModel.Datauser> newRestaurants){
        restaurants.clear();
        restaurants.addAll(newRestaurants);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_view, parent,false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(restaurants.get(position) );
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txt_resName)
        TextView resName;

        @BindView(R.id.txt_open)
        TextView resOpen;

        @BindView(R.id.txt_resLocation)
        TextView resLocation;

        @BindView(R.id.res_rating)
        RatingBar ratingBar;

        @BindView(R.id.txt_rate)
        TextView txt_rate;

        @BindView(R.id.img_res)
        ImageView imageView;

        @BindView(R.id.rel_layout)
        RelativeLayout rel_layout;

        Bitmap image;

        String desLat="",desLong="";
        final Context context;

        public RecyclerViewHolder(@NonNull View itemView){

            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this,itemView);

            preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            if (preferences.contains("StartLat")) {
                startlat = preferences.getString("StartLat", "");
            }
            if (preferences.contains("StartLng")) {
                startlng = preferences.getString("StartLng", "");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String uri = "http://maps.google.com/maps?saddr=" + startlat + "," + startlng + "(" + "Start Destination" + ")&daddr=" + desLat + "," + desLong + " (" + address + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    context.startActivity(intent);
                }
            });
        }

        void bind(RestaurantModel.Datauser restaurantModel){
            String photoRef="";
            Boolean open = false;

            resName.setText(restaurantModel.getName());
            resLocation.setText(restaurantModel.getAddress());
            address = restaurantModel.getAddress().toString();
            ratingBar.setRating(restaurantModel.getRating());
            String rating = String.valueOf(restaurantModel.getRating());
            txt_rate.setText(rating);

             try {
//                 JSONArray jsonArray = new JSONArray("results");
//                 JSONObject js = jsonArray.toJSONObject();
                        if(restaurantModel.getOpeninghours() != null){
                            Log.d("open: ","..");

                            String openinghours = restaurantModel.getOpeninghours().toString();
                            JSONObject json = new JSONObject(openinghours);
                            open = json.getBoolean("open_now");

                            if(open){
                                resOpen.setText("open now");
                                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                                stars.getDrawable(2).setColorFilter(ContextCompat.getColor(ratingBar.getContext(), R.color.green), PorterDuff.Mode.SRC_ATOP);
                            }
                            else{
                                resOpen.setText("closed");
                                resOpen.setTextColor(Color.parseColor("#f5f5f5"));
                                // resOpen.setTextColor(ContextCompat.getColor(this.context.getApplicationContext(), R.color.green));
                                rel_layout.getBackground().setAlpha(100);
                                txt_rate.setTextColor(ContextCompat.getColor(txt_rate.getContext(),R.color.gray));
                                resName.setTextColor(ContextCompat.getColor(resName.getContext(),R.color.gray));
                                resLocation.setTextColor(ContextCompat.getColor(resLocation.getContext(),R.color.gray));
                                resOpen.setTextColor(ContextCompat.getColor(resOpen.getContext(),R.color.gray));
                                //rel_layout.setAlpha(2000);
                                imageView.setImageAlpha(100);
                                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                                stars.getDrawable(2).setColorFilter(ContextCompat.getColor(ratingBar.getContext(), R.color.gray), PorterDuff.Mode.SRC_ATOP);
                            }

                        }
                        else{
                            resOpen.setText("closed");
                            resOpen.setTextColor(Color.parseColor("#f5f5f5"));
                            // resOpen.setTextColor(ContextCompat.getColor(this.context.getApplicationContext(), R.color.green));
                            rel_layout.getBackground().setAlpha(100);
                            txt_rate.setTextColor(ContextCompat.getColor(txt_rate.getContext(),R.color.gray));
                            resName.setTextColor(ContextCompat.getColor(resName.getContext(),R.color.gray));
                            resLocation.setTextColor(ContextCompat.getColor(resLocation.getContext(),R.color.gray));
                            resOpen.setTextColor(ContextCompat.getColor(resOpen.getContext(),R.color.gray));
                            //rel_layout.setAlpha(2000);
                            imageView.setImageAlpha(100);
                            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(ContextCompat.getColor(ratingBar.getContext(), R.color.gray), PorterDuff.Mode.SRC_ATOP);
                        }

                        if(restaurantModel.getPhotos() != null){
                            Log.d("photos: ","..");
                            String photoRefArray = restaurantModel.getPhotos().toString();
                            JSONArray jsonArr = new JSONArray(photoRefArray);
                            photoRef =  jsonArr.getJSONObject(0).get("photo_reference").toString();
                            Util.loadImage(imageView,imgApiBase + photoRef+"&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg",Util.getCircularProgressDrawable(imageView.getContext()));
                        }
                        else{
                            switch (screen){
                                case 1:
                                    Glide.with(imageView.getContext()).load(R.drawable.restaurant).into(imageView);
                                    break;
                                case 2:
                                    Glide.with(imageView.getContext()).load(R.drawable.coffee).into(imageView);
                                    break;
                                case 3:
                                    Glide.with(imageView.getContext()).load(R.drawable.hospital).into(imageView);
                                    break;
                                case 4:
                                    Glide.with(imageView.getContext()).load(R.drawable.park).into(imageView);
                                    break;
                                case 5:
                                    Glide.with(imageView.getContext()).load(R.drawable.parkingdemo).into(imageView);
                                    break;

                            }
                        }

                        if(restaurantModel.getGeometry() != null){
                            String geometryarr = restaurantModel.getGeometry().toString();
                            JSONObject jsonn = new JSONObject(geometryarr);
                            JSONObject js2 = jsonn.getJSONObject("location");
                            desLat = String.valueOf(js2.getDouble("lat"));
                            desLong = String.valueOf(js2.getDouble("lng"));
                            Log.d("destrestadapter: ",desLat +".."+ desLong);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


        }

    }

}
