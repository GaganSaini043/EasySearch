package app.quickfood.model.di;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RestaurantService {

    private static Retrofit retrofit = null;
    private static String Base_Url = "https://maps.googleapis.com/maps/api/place/textsearch/";

    @Provides
    public static Retrofit getRestaurant(){

        if(retrofit == null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
