package app.quickfood.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import app.quickfood.model.RestaurantApi;
import app.quickfood.model.RestaurantModel;
import app.quickfood.model.di.RestaurantService;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantViewModel extends ViewModel {

    //returns list of restaurantModel
    public MutableLiveData<List<RestaurantModel.Datauser>> restaurantList = new MutableLiveData<List<RestaurantModel.Datauser>>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    // private RestaurantService restaurantService = RestaurantService.getInstance();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    RestaurantApi api;

    public void refresh(String lat, String longi , int screennumber){
        getResponse(lat, longi , screennumber);
    }

    public void getResponse(String lat, String longi , int screennumber){

        String apiUrl = "" ;
        loading.setValue(true);

        //https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurant&location=31.962370,75.505333&radius=2000&region=us&type=restaurant&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg

        switch (screennumber){
            case 1:
                apiUrl = "json?query=restaurant&location="+lat+","+longi+"&radius=2000&type=restaurant&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg";
                break;
            case 2:
                apiUrl = "json?query=cafe&location="+lat+","+longi+"&radius=2000&type=cafe&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg";
                break;
            case 3:
                apiUrl = "json?query=hospital&location="+lat+","+longi+"&radius=2000&type=hospital&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg";
                break;
            case 4:
                apiUrl = "json?query=park&location="+lat+","+longi+"&radius=2000&type=park&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg";
                break;
            case 5:
                apiUrl = "json?query=parking&location="+lat+","+longi+"&radius=2000&type=parking&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg";
                break;
        }

        api = RestaurantService.getRestaurant().create(RestaurantApi.class);
        Call<RestaurantModel> call = api.getApiResponse(apiUrl);
        call.enqueue(new Callback<RestaurantModel>() {
            @Override
            public void onResponse(Call<RestaurantModel> call, Response<RestaurantModel> response) {
                String displayResponse = "";
                RestaurantModel resource = response.body();
                Log.d("onResponse: ",".." + resource.data);
                restaurantList.setValue(resource.data);
                countryLoadError.setValue(false);
                loading.setValue(false);
            }

            @Override
            public void onFailure(Call<RestaurantModel> call, Throwable t) {
                Log.d("Api Fail : ",t+"");
                countryLoadError.setValue(true);
                loading.setValue(false);
            }
        });
    }}



