package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

import co.com.lafemmeapp.core.domain.entities.SpecialistUser;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.presentation.utils.BitmapTransform;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;


/**
 * Created by oscargallon on 5/8/17.
 */

public class SpecialistUserRecyclerViewAdapter
        extends RecyclerView.Adapter<SpecialistUserItemViewHolder> {

    private List<SpecialistUser> mSpecialistUserList;


    private IFragmentCallbacks mFragmentCallbacks;

    public SpecialistUserRecyclerViewAdapter(List<SpecialistUser> mSpecialistUserList,
                                             IFragmentCallbacks fragmentCallbacks,
                                             @NonNull Context context) {
        this.mSpecialistUserList = mSpecialistUserList;
        mFragmentCallbacks = fragmentCallbacks;
    }

    @Override
    public SpecialistUserItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specialist_user_item, parent, false);
        return new SpecialistUserItemViewHolder(itemView, mFragmentCallbacks);
    }

    @Override
    public void onBindViewHolder(SpecialistUserItemViewHolder holder, int position) {
        final SpecialistUser specialistUser = getSpecialistAtPosition(position);
        holder.setmSpecialistUser(specialistUser);
        holder.getmTVName().setText(String.format(Locale.getDefault(),
                "%s %s", specialistUser.getName(), specialistUser.getLastName()));
        holder.getmTVAditionalInfo().setText(specialistUser.getCity());
        if (specialistUser.getAvatar() != null) {
            Picasso.with(holder.itemView.getContext())
                    .load(specialistUser.getAvatar())
                    .transform(new BitmapTransform(200,200))
                    .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                    .resize(200,200)
                    .into(holder.getmIVSpecialistUserAvatar());
        }


    }

    public SpecialistUser getSpecialistAtPosition(int position) {
        return mSpecialistUserList.get(position);
    }

    @Override
    public int getItemCount() {
        return mSpecialistUserList.size();
    }
}
