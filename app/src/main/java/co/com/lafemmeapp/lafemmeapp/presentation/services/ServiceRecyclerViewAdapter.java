package co.com.lafemmeapp.lafemmeapp.presentation.services;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

import co.com.lafemmeapp.core.domain.entities.ViewService;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.presentation.utils.BitmapTransform;

/**
 * Created by oscargallon on 5/3/17.
 */

public class ServiceRecyclerViewAdapter extends RecyclerView.Adapter<ServiceItemViewHolder> {

    private List<ViewService> mServiceList;


    private IServiceQuantityChange mServiceQuantityChange;


    public ServiceRecyclerViewAdapter(List<ViewService> mServiceList,
                                      Context context,
                                      IServiceQuantityChange mServiceQuantityChange) {
        this.mServiceList = mServiceList;
        this.mServiceQuantityChange = mServiceQuantityChange;

    }

    @Override
    public ServiceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_item, parent, false);
        return new ServiceItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServiceItemViewHolder holder, int position) {
        final ViewService service = mServiceList.get(position);
        holder.getmTVDescription().setText(service.getName());
        holder.getmTVPrice().setText(String.format(Locale.getDefault(),
                "$%,d", Integer.parseInt(service.getPrice())));
        if (service.getImageUrl() != null) {

            Picasso.with(holder.itemView.getContext())
                    .load(service.getImageUrl())
                    .transform(new BitmapTransform(200, 200))
                    .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                    .resize(200, 200)
                    .into(holder.getmIVBackground());
        }


        holder.getmBTNAddd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrSubtractQuantity(1, holder.getmBTNTotal(), service);

            }
        });

        holder.getmBTNSubtract().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrSubtractQuantity(-1, holder.getmBTNTotal(), service);

            }
        });
    }

    private void addOrSubtractQuantity(int value, Button btnTotal, ViewService viewService) {
        int result = 0;
        if (value < 0 && viewService.getQuantity() > 0) {
            result = viewService.getQuantity() - 1;
        } else if (value > 0 && viewService.getQuantity() < viewService.getMaxCount()) {
            result = viewService.getQuantity() + 1;
        } else if (value > 0 && viewService.getQuantity() >= viewService.getMaxCount()) {
            result = viewService.getQuantity();
        }
        btnTotal.setText(String.format(Locale.getDefault(),
                "%d", result));
        viewService.setQuantity(result);
        if (mServiceQuantityChange != null) {
            mServiceQuantityChange.onServiceQuantityChange(viewService);
        }
    }

    @Override
    public int getItemCount() {
        return mServiceList.size();
    }
}
