package app.quickfood.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RestaurantApi {
    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522%2C151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY
//https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurant&location=43.7321675,-79.6444171&radius=2000&region=us&type=cafe,bakery&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg
    //https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurant&location=43.7321675,-79.6444171&radius=2000&region=us&type=restaurant&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg
   // @GET("json?query=restaurant&location=43.7321675,-79.6444171&radius=2000&region=us&type=restaurant&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg")
   // @GET("json?query=restaurant&location=43.7321675,-79.6444171&radius=3000&region=us&type=restaurant&key=AIzaSyAyWj_qFT89wB0UIfnizPvEN5_W2Ey1WHg")
    @GET
    Call<RestaurantModel> getApiResponse(@Url String urlString);
}
