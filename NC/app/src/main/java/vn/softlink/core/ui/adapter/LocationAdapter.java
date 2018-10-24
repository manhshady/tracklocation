package vn.softlink.core.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import vn.softlink.core.R;
import vn.softlink.core.model.response.LocationResponse;
import vn.softlink.core.util.data.TimeHelper;
import vn.softlink.core.util.view.BaseRecyclerAdapter;
import vn.softlink.core.util.view.BaseViewHolder;

public class LocationAdapter extends BaseRecyclerAdapter<LocationResponse, LocationAdapter.VH> {


    @Override
    protected int getItemLayout(LocationResponse model) {
        return R.layout.item_location;
    }

    @Override
    protected VH getViewHolder(@NonNull View view, int layout) {
        return new VH(view);
    }



    class VH extends BaseViewHolder<LocationResponse>{

        @BindView(R.id.txvDeviceId)
        TextView txvDeviceId;
        @BindView(R.id.txvLatitude)
        TextView txvLatitude;
        @BindView(R.id.txvLongitude)
        TextView txvLongitude;
        @BindView(R.id.txvTimestamp)
        TextView txvTimestamp;

        public VH(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(LocationResponse model, int position) {
            txvDeviceId.setText(model.getDeviceId());
            txvLatitude.setText(String.valueOf(model.getLatitude()));
            txvLongitude.setText(String.valueOf(model.getLongitude()));
            txvTimestamp.setText(TimeHelper.format(model.getTimeStamp()));
        }
    }
}
