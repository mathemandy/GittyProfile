package mathemandy.tselebro.com.gittyprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import mathemandy.tselebro.com.gittyprofile.Fragments.ProfileFragment;
import mathemandy.tselebro.com.gittyprofile.Model.ProfileItem;

public class Profile extends AppCompatActivity implements ProfileFragment.OnProfileSelected {

    View mFragmentContainer;

    private ProfileItem mSelectedProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentContainer = findViewById(R.id.fragment);

        String PROFILE_FRAG_TAG = "ProfilesFrag";

        Fragment profileFragment = ProfileFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, profileFragment, PROFILE_FRAG_TAG).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProfileSelected(int profilePosition, ProfileItem profile) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("profile" , profile);
        mSelectedProfile = profile;

        Intent intent = new Intent(getApplicationContext(),Profile_Detail_Activity.class);
        intent.putExtras(bundle);
        startActivity(intent);







    }
}
