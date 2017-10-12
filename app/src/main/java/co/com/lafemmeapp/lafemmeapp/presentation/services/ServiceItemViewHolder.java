package co.com.lafemmeapp.lafemmeapp.presentation.services;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import co.com.lafemmeapp.lafemmeapp.R;

/**
 * Created by oscargallon on 5/3/17.
 */

public class ServiceItemViewHolder extends RecyclerView.ViewHolder {

    private final Button mBTNAddd;

    private final Button mBTNSubtract;

    private final Button mBTNTotal;

    private final ImageView mIVBackground;

    private final TextView mTVDescription;

    private final TextView mTVPrice;

    public ServiceItemViewHolder(View itemView) {
        super(itemView);
        mBTNAddd = (Button) itemView.findViewById(R.id.btn_add);
        mBTNSubtract = (Button) itemView.findViewById(R.id.btn_subtract);
        mBTNTotal = (Button) itemView.findViewById(R.id.btn_total);
        mIVBackground = (ImageView) itemView.findViewById(R.id.iv_background);
        mTVDescription = (TextView) itemView.findViewById(R.id.tv_description);
        mTVPrice = (TextView) itemView.findViewById(R.id.tv_price);

    }

    public Button getmBTNAddd() {
        return mBTNAddd;
    }

    public Button getmBTNSubtract() {
        return mBTNSubtract;
    }

    public Button getmBTNTotal() {
        return mBTNTotal;
    }

    public ImageView getmIVBackground() {
        return mIVBackground;
    }

    public TextView getmTVDescription() {
        return mTVDescription;
    }

    public TextView getmTVPrice() {
        return mTVPrice;
    }
}
