package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_user;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import co.com.lafemmeapp.core.domain.entities.SpecialistUser;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 5/8/17.
 */

public class SpecialistUserItemViewHolder extends RecyclerView.ViewHolder {

    private final CircularImageView mIVSpecialistUserAvatar;

    private final TextView mTVName;

    private final TextView mTVAditionalInfo;

    private SpecialistUser mSpecialistUser;

    private IFragmentCallbacks mFragmentCallbacks;

    private final View mItemView;

    public SpecialistUserItemViewHolder(View itemView, IFragmentCallbacks iFragmentCallbacks) {
        super(itemView);
        mItemView = itemView;
        mIVSpecialistUserAvatar = (CircularImageView)
                itemView.findViewById(R.id.iv_specialist_user_avatar);
        mTVName = (TextView) itemView.findViewById(R.id.tv_name);
        mTVAditionalInfo = (TextView) itemView.findViewById(R.id.tv_aditional_info);
        mFragmentCallbacks = iFragmentCallbacks;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentCallbacks != null && mSpecialistUser != null) {
                    mFragmentCallbacks.onViewClicked(v, mSpecialistUser);
                }
            }
        });
    }

    public View getmItemView() {
        return mItemView;
    }

    public void setmSpecialistUser(SpecialistUser mSpecialistUser) {
        this.mSpecialistUser = mSpecialistUser;
    }

    public CircularImageView getmIVSpecialistUserAvatar() {
        return mIVSpecialistUserAvatar;
    }

    public TextView getmTVName() {
        return mTVName;
    }

    public TextView getmTVAditionalInfo() {
        return mTVAditionalInfo;
    }

    public IFragmentCallbacks getmFragmentCallbacks() {
        return mFragmentCallbacks;
    }
}
