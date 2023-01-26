package app.quickfood.view;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import app.quickfood.R;
import app.quickfood.model.RestaurantApi;
import app.quickfood.model.RestaurantModel;
import app.quickfood.viewmodel.RestaurantViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ParkFragment extends Fragment implements LocationListener {

    @BindView(R.id.recycler_list)
    RecyclerView res_list;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.loading_view)
    ProgressBar loading_view;

    @BindView(R.id.text_err)
    TextView text_err;

    private RestaurantViewModel restaurantViewModel ;
    RestaurantApi api;

    private RestaurantAdapter restaurantAdapter =new RestaurantAdapter(new ArrayList<RestaurantModel.Datauser>(), 4);

    LocationManager lManager;
    String lattitude , longitute;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Context context;
    int screenNumber= 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=  inflater.inflate(R.layout.fragment_park, container, false);

        context = container.getContext();

        ButterKnife.bind(this, v);

        restaurantViewModel = ViewModelProviders.of(ParkFragment.this).get(RestaurantViewModel.class);
        checkPermissions();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkPermissions();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        res_list.setLayoutManager(new LinearLayoutManager(context));
        res_list.setAdapter(restaurantAdapter);
        res_list.setVisibility(View.VISIBLE);
        observerViewModel();

        return  v;
    }

    public void checkPermissions(){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

            getLocation();
        }
        else
        {
            requestPermissions( new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    1);
            checkPermissions();
        }

    }

    void getLocation(){
        try {
            lManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,this);

        }
        catch (SecurityException s){
            s.printStackTrace();
        }
    }

    void saveLatLng(Double lat , Double lng ) {
        sharedPref = context.getSharedPreferences("MyPref", 0);
        editor = sharedPref.edit();
        editor.clear();
        editor.putString("StartLat",String.valueOf(lat));
        editor.putString("StartLng",String.valueOf(lng));
        editor.commit();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d("onLocationChanged: ", "lat " + location.getLatitude() + "long : " + location.getLongitude());
        saveLatLng(location.getLatitude(),location.getLongitude() );
        restaurantViewModel.refresh(String.valueOf(location.getLatitude()) , String.valueOf(location.getLongitude()) , 4);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(context, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    private void observerViewModel(){

        restaurantViewModel.restaurantList.observe(getViewLifecycleOwner(), new Observer<List<RestaurantModel.Datauser>>() {
            @Override
            public void onChanged(List<RestaurantModel.Datauser> restaurantModels) {
                res_list.setVisibility(View.VISIBLE);
                text_err.setVisibility(View.GONE);
                loading_view.setVisibility(View.GONE);
                restaurantAdapter.updateRestaurantList(restaurantModels);
            }
        });

        restaurantViewModel.countryLoadError.observe(getViewLifecycleOwner(), isError -> {
            if (isError != null){
                text_err.setVisibility(isError ? View.VISIBLE : View.GONE);
            }

        });
        restaurantViewModel.loading.observe(getViewLifecycleOwner(), isLoading -> {

            if(isLoading != null){
                loading_view.setVisibility((isLoading ? View.VISIBLE : View.GONE));
                if(isLoading){
                    text_err.setVisibility(View.GONE);
                    res_list.setVisibility(View.GONE);
                }
            }

        });

    }

}