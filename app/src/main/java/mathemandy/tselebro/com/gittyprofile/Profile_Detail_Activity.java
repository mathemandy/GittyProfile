package mathemandy.tselebro.com.gittyprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import mathemandy.tselebro.com.gittyprofile.Fragments.Profile_detail_fragment;
import mathemandy.tselebro.com.gittyprofile.Model.ProfileItem;

/**
 * Created by mathemandy on 09 Mar 2017.
 */
public class Profile_Detail_Activity extends AppCompatActivity {

    private String FRAGMENT_TAG;

    private Toolbar mToolBar;

    private Fragment mFragment;

    private View mHeaderLayout;

    private ImageView headerImageView;

    private ProgressBar spinner;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private Target target;

    private ProfileItem mProfileItem;

    FloatingActionButton fab;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getExtras();

        FRAGMENT_TAG = "ProfileDeTails";

        mProfileItem = bundle.getParcelable("profile");

        configureToolbar();

        mFragment = Profile_detail_fragment.newInstance();

        mFragment.setArguments(bundle);

        setFragment(mFragment);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                String shareUsername = mProfileItem.getUsername();
                String Sharelink = mProfileItem.getProfileUrl();
                String ShareHead = "Check out this awesome developer";
                String Sharebody = "<@" + shareUsername  + ">" + "," + "<" + Sharelink + ">";
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, ShareHead  + Sharebody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }

      /*
        *
        * Setup the Collapsing ToolBar
        *
        * */

    public void configureToolbar(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);


        collapsingToolbarLayout.setTitle(mProfileItem.getUsername());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getApplication(), R.color.colorCollapsingToolBarText));
        } else {
            collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorCollapsingToolBarText));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), R.color.colorCollapsingToolBarText));
        } else {
            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorCollapsingToolBarText));
        }

        /*
        *
        * Setup the Image Header
        *
        * */

        mHeaderLayout = findViewById(R.id.headerLayout);

        spinner = (ProgressBar) mHeaderLayout.findViewById(R.id.profile_item_spinner);
        headerImageView = (ImageView) mHeaderLayout.findViewById(R.id.header);

        String backDropUrl = mProfileItem.getAvatarPath();


        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                headerImageView.setImageBitmap(bitmap);
                spinner.setVisibility(View.GONE);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                headerImageView.setBackgroundResource(R.color.colorCollapsingToolBarText);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                headerImageView.setBackgroundResource(R.color.colorCollapsingToolBarText);
                spinner.setVisibility(View.VISIBLE);

            }
        };
        Picasso.with(getApplicationContext()).load(backDropUrl).into(target);


    /**
     *
     * Setup the naviagation toolBar to go back
     * */


        mToolBar = (Toolbar) collapsingToolbarLayout.findViewById(R.id.toolbar2);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      NavUtils.navigateUpFromSameTask(Profile_Detail_Activity.this);
                                                  }
                                              }
        );

}

public void setFragment (
        Fragment fragment
){
    getSupportFragmentManager().beginTransaction().replace(R.id.details_container, fragment, FRAGMENT_TAG).commit();
}
}
