package app.quickfood.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import app.quickfood.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    NavController navController;

    RestaurantFragment restaurantFragment = new RestaurantFragment();
    CafeFragment cafeFragment = new CafeFragment();
    Hospital hospital = new Hospital();
    ParkFragment parkFragment = new ParkFragment();
    ParkingFragment parkingFragment = new ParkingFragment();
    FragmentManager fm = getSupportFragmentManager();
    Fragment active = restaurantFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        bottomNavigationView.setOnItemSelectedListener(this);

        fm.beginTransaction().add(R.id.container, cafeFragment, "2").hide(cafeFragment).commit();
        fm.beginTransaction().add(R.id.container, hospital, "3").hide(hospital).commit();
        fm.beginTransaction().add(R.id.container, parkFragment, "4").hide(parkFragment).commit();
        fm.beginTransaction().add(R.id.container, parkingFragment, "5").hide(parkingFragment).commit();
        fm.beginTransaction().add(R.id.container,restaurantFragment, "1").commit();

    }

        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item){

            Bundle bundle = new Bundle();

            switch (item.getItemId()) {
                case R.id.action_restaurants:
                    fm.beginTransaction().hide(active).show(restaurantFragment).commit();
                    active = restaurantFragment;
                    return true;

                case R.id.action_cafe:
                    fm.beginTransaction().hide(active).show(cafeFragment).commit();
                    active = cafeFragment;
                    return true;

                case R.id.action_hospital:
                    fm.beginTransaction().hide(active).show(hospital).commit();
                    active = hospital;
                    return true;

                case R.id.action_parks:
                    fm.beginTransaction().hide(active).show(parkFragment).commit();
                    active = parkFragment;
                    return true;

                case R.id.action_parking:
                    fm.beginTransaction().hide(active).show(parkingFragment).commit();
                    active = parkingFragment;
                    return true;

            }
            return false;
        }


    }
