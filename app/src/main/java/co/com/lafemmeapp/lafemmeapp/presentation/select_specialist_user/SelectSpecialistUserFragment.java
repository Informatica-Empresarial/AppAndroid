package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_user;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import co.com.lafemmeapp.core.domain.entities.SpecialistUser;
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_time.SelectSpecialistTimeRecyclerViewAdapter;
import co.com.lafemmeapp.lafemmeapp.presentation.utils.RecyclerViewDividerItemDecoration;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectSpecialistUserFragment extends BaseFragment implements ISelectSpecialistUserFragmentView {


    private ISelectSpecialistUserFragmentPresenter mScheduleFragmentPresenter;

    private RecyclerView mRVSpecialistUser;

    private ProgressBar mPBSpecialist;

    private LinearLayout mLYNoSpecialist;

    private Button mBTNRandomSpecialist;


    public static SelectSpecialistUserFragment newInstance(ViewServicesRequest viewServicesRequest) {
        SelectSpecialistUserFragment selectSpecialistUserFragment =
                new SelectSpecialistUserFragment();
        Bundle bundle =
                new Bundle();
        bundle.putParcelable(AppModuleConstants.VIEW_SERVICES_SELECTED_KEY,
                viewServicesRequest);
        selectSpecialistUserFragment.setArguments(bundle);
        return selectSpecialistUserFragment;
    }

    public SelectSpecialistUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mScheduleFragmentPresenter = new SelectSpecialistUserFragmentPresenter(this);
        mScheduleFragmentPresenter.onAttach((IFragmentCallbacks) activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mScheduleFragmentPresenter = new SelectSpecialistUserFragmentPresenter(this);
        mScheduleFragmentPresenter.onAttach((IFragmentCallbacks) context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScheduleFragmentPresenter != null) {
            mScheduleFragmentPresenter.onCreate(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_specialist_user, container, false);
        if (mScheduleFragmentPresenter != null) {
            mScheduleFragmentPresenter.onCreateView(view);
        }
        return view;
    }

    @Override
    public void initViewComponents(View view) {
        super.hideKeyboard(view, getActivity().getApplicationContext());
        mRVSpecialistUser = (RecyclerView) view.findViewById(R.id.rv_specialist_user);
        mPBSpecialist = (ProgressBar) view.findViewById(R.id.pb_specialist);
        mLYNoSpecialist = (LinearLayout) view.findViewById(R.id.ly_no_specialist);
        mBTNRandomSpecialist = (Button) view.findViewById(R.id.btn_random_specialist);
    }

    @Override
    public void initComponents() {
        if (mRVSpecialistUser != null) {
            mRVSpecialistUser.setHasFixedSize(true);
            mRVSpecialistUser
                    .setLayoutManager(new LinearLayoutManager(getActivity()));
            mRVSpecialistUser.addItemDecoration(new RecyclerViewDividerItemDecoration(getActivity()
                    .getResources().getDrawable(R.drawable.recycler_view_divider)));
        }

        if (mScheduleFragmentPresenter != null) {
            mScheduleFragmentPresenter.getAppointmentAvailability();
        }

        mBTNRandomSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScheduleFragmentPresenter != null &&
                        mRVSpecialistUser != null
                        && mRVSpecialistUser.getAdapter() != null) {
                    mScheduleFragmentPresenter
                            .selectRandomSpecialist((SpecialistUserRecyclerViewAdapter)
                                    mRVSpecialistUser.getAdapter(), v);


                }
            }
        });
    }

    @Override
    public void showMessage(String title, String description, String action, boolean showBothButtons) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity())
                    .showMessage(title, description, action, showBothButtons);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mScheduleFragmentPresenter != null) {
            mScheduleFragmentPresenter.onDestroy();
        }
    }

    @Override
    public void populateView() {

    }

    @Override
    public void showSpecialistUsers(List<SpecialistUser> specialistUserList) {
        if (mRVSpecialistUser != null && mScheduleFragmentPresenter != null) {
            mRVSpecialistUser.setAdapter(new SpecialistUserRecyclerViewAdapter(specialistUserList,
                    mScheduleFragmentPresenter,
                    getActivity()));
        }
    }

    @Override
    public void showProgressBar() {
        if (mPBSpecialist != null) {
            mPBSpecialist.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dismissProgressBar() {
        if (mPBSpecialist != null) {
            mPBSpecialist.setVisibility(View.GONE);
        }
    }

    @Override
    public void changeLYNoSpecialistVisibility(int visibility) {
        if (mLYNoSpecialist != null) {
            mLYNoSpecialist.setVisibility(visibility);
        }
    }

    @Override
    public void changeBTNRandomSpeciliastVisibility(int visibility) {
        if (mBTNRandomSpecialist != null) {
            mBTNRandomSpecialist.setVisibility(visibility);
        }
    }

    @Override
    public void showProgressDialog() {
        super.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        super.hideProgressDialog();
    }
}
