package co.com.lafemmeapp.lafemmeapp.presentation.on_boarding.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnBoardingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnBoardingFragment extends BaseFragment implements IOnBoardingFragmentView {


    private TextView mTVTitle;

    private TextView mTVDescription;

    private IOnBoardingFragmentPresenter mOnBoardingFragmentPresenter;

    private ImageView mIVOnBoardingImage;

    public OnBoardingFragment() {
    }

    public static OnBoardingFragment newInstance(String title, String description,
                                                 int imageResource) {
        OnBoardingFragment fragment = new OnBoardingFragment();
        Bundle bundle = new Bundle();
        if (title != null) {
            bundle.putString(AppModuleConstants.TITLE_KEY, title);
        }
        if (description != null) {
            bundle.putString(AppModuleConstants.DESCRIPTION_KEY, description);
        }

        bundle.putInt(AppModuleConstants.IMAGE_RESOURCE_KEY, imageResource);


        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentCallbacks) {
            mOnBoardingFragmentPresenter = new OnBoardingFragmentPresenter(this);
            mOnBoardingFragmentPresenter.onAttach((IFragmentCallbacks) context);

        } else {
            throw new RuntimeException("You must implement IOnBoardingFragmentCallbacks");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mOnBoardingFragmentPresenter != null) {
            mOnBoardingFragmentPresenter.onCreate(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding, container, false);
        if (mOnBoardingFragmentPresenter != null) {
            mOnBoardingFragmentPresenter.onCreateView(view);
        }
        return view;
    }

    @Override
    public void initViewComponents(View view) {
        super.hideKeyboard(view, getActivity().getApplicationContext());
        mTVTitle = (TextView) view.findViewById(R.id.tv_title);
        mTVDescription = (TextView) view.findViewById(R.id.tv_description);
        mIVOnBoardingImage = (ImageView) view.findViewById(R.id.iv_onboarding_image);

    }

    @Override
    public void initComponents() {

    }

    @Override
    public void showMessage(String title, String description, String action, boolean showBothButtons) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity())
                    .showMessage(title, description, action, showBothButtons);
        }
    }

    @Override
    public void populateView() {

    }

    @Override
    public void setTVTitleText(String title) {
        if (mTVTitle != null && title != null) {
            mTVTitle.setText(title);
        }
    }

    @Override
    public void setTVDescriptionText(String description) {
        if (mTVDescription != null && description != null) {
            mTVDescription.setText(description);
        }
    }

    @Override
    public void setImageResource(int imageResource) {
        if (mIVOnBoardingImage != null) {
            mIVOnBoardingImage.setImageDrawable(getResources().getDrawable(imageResource));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOnBoardingFragmentPresenter != null) {
            mOnBoardingFragmentPresenter.onDestroy();
        }
    }

    @Override
    public void hideProgressDialog() {
        super.hideProgressDialog();
    }

    @Override
    public void showProgressDialog() {
        super.showProgressDialog();
    }
}
