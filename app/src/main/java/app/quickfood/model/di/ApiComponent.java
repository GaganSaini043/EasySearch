package app.quickfood.model.di;

import app.quickfood.viewmodel.RestaurantViewModel;
import dagger.Component;

@Component(modules = {RestaurantService.class})
public interface ApiComponent {

    void inject(RestaurantViewModel restaurantViewModel);
}
