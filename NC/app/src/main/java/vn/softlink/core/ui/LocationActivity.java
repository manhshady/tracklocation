package vn.softlink.core.ui;

import android.location.Criteria;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import vn.softlink.core.R;
import vn.softlink.core.livedata.EventObserver;
import vn.softlink.core.livedata.LocationLiveData;
import vn.softlink.core.model.response.LocationResponse;
import vn.softlink.core.mvvm.MVVMActivity;
import vn.softlink.core.ui.adapter.LocationAdapter;
import vn.softlink.core.util.view.BaseActivity;

public class LocationActivity extends MVVMActivity<LocationViewModel> {

    private LocationHelper locationHelper;

    @BindView(R.id.locationRecyclerView)
    RecyclerView locationRecyclerView;

    private LocationAdapter locationAdapter = new LocationAdapter();


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContainer() {
        throw new RuntimeException("not exist a fragment container");
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationHelper = new LocationHelper("my_action")
                .gpsEnable(true).fusedEnable(true).altitudeRequired(true)
                .speedRequired(true).bearingRequired(true).costAllowed(false)
                .powerRequirement(Criteria.POWER_LOW)
                .requestInterval(10000).requestDistance(0)
                .horizontalAccuracy(Criteria.ACCURACY_FINE)
                .verticalAccuracy(Criteria.ACCURACY_FINE)
                .speedAccuracy(Criteria.ACCURACY_FINE)
                .bearingAccuracy(Criteria.ACCURACY_FINE);
    }

    @NonNull
    @Override
    protected Class<LocationViewModel> mainViewModel() {
        return LocationViewModel.class;
    }

    @Override
    protected void onViewReady() {
        configRecycler(R.id.locationRecyclerView, locationAdapter, LinearLayoutManager.VERTICAL, true);
    }

    @Override
    protected void onRegisterData() {
        observe(LocationLiveData.getInstance(), new EventObserver<LocationResponse>() {
            @Override
            protected void onEvent(@NonNull LocationResponse data) {

//                data.setDeviceId("12345");
//                data.setLatitude(currentLocation.getLatitude());
//                data.setLongitude(currentLocation.getLongitude());
//                data.setTimeStamp(System.currentTimeMillis());


                locationAdapter.add(data);
                locationRecyclerView.smoothScrollToPosition(locationAdapter.lastPosition());
            }
        });
    }



    @Override
    protected void onPermissionGranted(int requestCode) {
        if (requestCode == LocationHelper.PERMISSION_REQUEST_CODE) {
            locationHelper.startService(this);


        }
    }

    @OnClick(R.id.mainButtonStartService)
    public void onBackgroundClick() {
        if (LocationHelper.isGrantedPermission(this)) {
            locationHelper.start(this);
        } else {
            LocationHelper.requestPermission(this);
        }
    }

    @OnClick(R.id.mainButtonStopService)
    public void onStopServiceClick() {
        locationHelper.stopLocationService(this);
    }

}
