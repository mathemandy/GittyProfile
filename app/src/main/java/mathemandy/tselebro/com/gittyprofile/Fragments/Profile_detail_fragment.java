package mathemandy.tselebro.com.gittyprofile.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mathemandy.tselebro.com.gittyprofile.Model.ProfileItem;
import mathemandy.tselebro.com.gittyprofile.R;

/**
 * Created by mathemandy on 10 Mar 2017.
 */

public class Profile_detail_fragment extends Fragment {

    private static final String PROFILE_KEY = "profile";

    private static final String PROFILE_SHARE_HASHTAG = " #GITHUb";

    private ShareActionProvider mShareActionProvider;

//    UI FIELDS
    private TextView mUsername;
    private TextView mProfileUrl;
    private TextView mTryAgain;
    private View emptyLayout;
    private Bundle mbundle;
    private ProfileItem profileItem;
    private ImageView mEmptyIcon;
    private TextView mErrorMessage;
    private TextView mErrorDescription;
    private View mDetailsLayout;



    public static Profile_detail_fragment newInstance() {
        return  new Profile_detail_fragment();

        }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbundle = getArguments();

        if (mbundle != null){
            updateProfileDetails(mbundle);

        }
    }

    private void updateProfileDetails(Bundle bundle){
        profileItem = bundle.getParcelable(PROFILE_KEY);

        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null){

            mbundle = savedInstanceState;
        }
        initFields(view);

        if (profileItem != null){
            fillData(profileItem);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_details_screen, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(PROFILE_KEY, profileItem);
    }

    public void initFields (View view){
//        initialise Empty layout

        emptyLayout = view.findViewById(R.id.error_screen);

        mEmptyIcon = (ImageView) emptyLayout.findViewById(R.id.error_icon);

        mErrorMessage = (TextView) emptyLayout.findViewById(R.id.error_message);

        mTryAgain = (TextView) emptyLayout.findViewById(R.id.try_again);

        mErrorDescription = (TextView) emptyLayout.findViewById(R.id.error_desc);

        mTryAgain.setVisibility(View.INVISIBLE);
        //        Init Details Layout
        mDetailsLayout = view.findViewById(R.id.layout_details);

        mUsername = (TextView) mDetailsLayout.findViewById(R.id.username);
        mProfileUrl = (TextView) mDetailsLayout.findViewById(R.id.profile_url);

    }

    public void fillData (ProfileItem profile){
        mUsername.setText(profile.getUsername());
        mProfileUrl.setText(profile.getProfileUrl());
        mProfileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mProfileUrl.getText().toString()));
                startActivity(intent);

            }
        });
    }




    private void crossfade(final View mContentView, final View mLoading) {

        mContentView.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.INVISIBLE);
    }

}
