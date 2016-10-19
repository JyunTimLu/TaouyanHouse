package tim.tayouanhouseprice.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tim.tayouanhouseprice.MapActivity;
import tim.tayouanhouseprice.R;
import tim.tayouanhouseprice.base.CustomUnit;
import tim.tayouanhouseprice.base.PriceFormator;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final int TRADE_TARGET = 0;
    private static final int TRADE_DATE = 1;
    private static final int ADDRESS = 2;
    private static final int AREA_TRANS_TOTAL_SIZE = 3;
    private static final int BUILDING_TYPE = 4;
    private static final int BUILDING_FINISH_DATE = 5;
    private static final int BUILDING_TRANS_FLOOR = 6;
    private static final int BUILDING_TOTAL_FLOOR = 7;
    private static final int TOTAL_PRICE = 8;

    private Context context;
    private DataSnapshot dataSnapshot;

    private List<String> data = new ArrayList<String>() {{

        add("交易標的");
        add("交易年月日");
        add("土地區段位置或建物區門牌");
        add("建物移轉總面積平方公尺");
        add("建物型態");
        add("建築完成年月");
        add("移轉層次");
        add("總樓層數");
        add("總價元");

    }};

    private List<String> tradeTarget = new ArrayList<>();
    private List<String> tradeDate = new ArrayList<>();
    public List<String> address = new ArrayList<>();
    private List<String> areaTransTotalSize = new ArrayList<>();
    private List<String> buildingFinishDate = new ArrayList<>();
    private List<String> buildingType = new ArrayList<>();
    private List<String> buildingTransFloor = new ArrayList<>();
    private List<String> buildingTotalFloor = new ArrayList<>();
    private List<String> price = new ArrayList<>();

    public RecyclerViewAdapter(Context context, DataSnapshot dataSnapshot) {
        this.context = context;
        this.dataSnapshot = dataSnapshot;

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            try {
                if (snapshot.child(data.get(TRADE_TARGET)).getValue().toString().equals("房地(土地+建物)") || snapshot.child(data.get(TRADE_TARGET)).getValue().toString().equals("房地(土地+建物)+車位")) {
                    tradeTarget.add(snapshot.child(data.get(TRADE_TARGET)).getValue().toString());
                    tradeDate.add(snapshot.child(data.get(TRADE_DATE)).getValue().toString());
                    address.add(snapshot.child(data.get(ADDRESS)).getValue().toString());
                    areaTransTotalSize.add(snapshot.child(data.get(AREA_TRANS_TOTAL_SIZE)).getValue().toString());
                    buildingFinishDate.add(CustomUnit.getHouseAge(snapshot.child(data.get(BUILDING_FINISH_DATE)).getValue().toString()));
                    buildingType.add(snapshot.child(data.get(BUILDING_TYPE)).getValue().toString());
                    buildingTransFloor.add(snapshot.child(data.get(BUILDING_TRANS_FLOOR)).getValue().toString());
                    buildingTotalFloor.add(snapshot.child(data.get(BUILDING_TOTAL_FLOOR)).getValue().toString());
                    price.add(snapshot.child(data.get(TOTAL_PRICE)).getValue().toString());
                }
            } catch (Exception e) {
                Log.d("ERR", e.toString());
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        ViewHolder holder = new ViewHolder(context, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        ButterKnife.apply(holder.textViews,);
        holder.textViews.get(0).setText(context.getResources().getString(R.string.trade_date, tradeDate.get(position)));
        holder.textViews.get(1).setText(context.getResources().getString(R.string.address, address.get(position)));
        holder.textViews.get(2).setText(context.getResources().getString(R.string.price, PriceFormator.getSimple(price.get(position))));
        holder.textViews.get(3).setText(context.getResources().getString(R.string.trade_target, tradeTarget.get(position)));
        holder.textViews.get(4).setText(context.getResources().getString(R.string.building_size, CustomUnit.getFootage(areaTransTotalSize.get(position))));
        holder.textViews.get(5).setText(context.getResources().getString(R.string.building_type, buildingType.get(position)));
        holder.textViews.get(6).setText(context.getResources().getString(R.string.sale_and_total_floor, buildingTransFloor.get(position), buildingTotalFloor.get(position)));
        holder.textViews.get(7).setText(context.getResources().getString(R.string.house_age, buildingFinishDate.get(position)));
        holder.textViews.get(8).setText(CustomUnit.getDecimalInstance().format(CustomUnit.getPricePerFootage(CustomUnit.getFootage(areaTransTotalSize.get(position)), PriceFormator.getSimple(price.get(position)))));
    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Context context;

        @OnClick(R.id.card_view)
        public void onClick() {
            Bundle bundle = new Bundle();
            bundle.putString("address", address.get(getAdapterPosition()));
            Intent i = new Intent(context, MapActivity.class).putExtras(bundle);
            context.startActivity(i);

        }

        @BindViews({R.id.trade_target, R.id.area_trans_total_size, R.id.building_finish_date, R.id.building_type, R.id.floor, R.id.trade_date, R.id.address, R.id.price, R.id.price_per_footage})
        List<TextView> textViews;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }
    }
}
