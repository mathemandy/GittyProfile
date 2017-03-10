package mathemandy.tselebro.com.gittyprofile.Fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mathemandy.tselebro.com.gittyprofile.Adapters.ProfileListAdapter;
import mathemandy.tselebro.com.gittyprofile.Model.ProfileItem;
import mathemandy.tselebro.com.gittyprofile.R;
import mathemandy.tselebro.com.gittyprofile.Utility.CheckConnectivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends Fragment {

    private ImageView mEmptyScreenIcon;

    private ProgressBar mProgressBar;

    private View mEmptyScreen;

    private TextView mEmptyScreenText;

    private TextView mTryAgainButton;

    private ListView mListView;

    private final String PROFILE_KEY = "profile";

    private Bundle mBundle;

    private ProfileListAdapter mProfileListAdapter;

    private List<ProfileItem> loadprofiles = new ArrayList<>();


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileListAdapter = new ProfileListAdapter(getActivity());

        if (savedInstanceState != null) {
            updateList();

            mBundle = savedInstanceState.getParcelable(PROFILE_KEY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBundle != null) {
            restartLoader(mBundle);
        }
    }

    public void restartLoader(Bundle bundle) {
        loadprofiles = bundle.getParcelableArrayList(PROFILE_KEY);
        if (loadprofiles != null) {
            mProfileListAdapter.swapData(loadprofiles);
        } else {
            this.mBundle = bundle;

        }
        updateList();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PROFILE_KEY, (ArrayList<ProfileItem>) loadprofiles);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_profile, container, false);

        mProgressBar = (ProgressBar) rootview.findViewById(R.id.loading_spinner);

        mEmptyScreen = rootview.findViewById(R.id.error_screen);

        mEmptyScreenText = (TextView) mEmptyScreen.findViewById(R.id.error_message);


        mEmptyScreenIcon = (ImageView) mEmptyScreen.findViewById(R.id.error_icon);

        mTryAgainButton = (TextView) mEmptyScreen.findViewById(R.id.try_again);

       mListView = (ListView) rootview.findViewById(R.id.list_view);

        mListView.setAdapter(mProfileListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProfileItem profiles = (ProfileItem) mProfileListAdapter.getItem(i);

                ((OnProfileSelected) getActivity()).onProfileSelected(i, profiles);
            }

        });

        if (savedInstanceState != null) {
            mProfileListAdapter.swapData(loadprofiles);
        }


        return rootview;
    }


    private void updateList() {
        CheckConnectivity mCheckConnectivity = new CheckConnectivity();
        String emptyText;
//        Hardcoded Value for our search Query
        String param = "java";

        if (mCheckConnectivity.isNetworkConnected(getActivity())) {
            FetchGithubProfile profileTask = new FetchGithubProfile();
            profileTask.execute(param);


        } else {

            crossfade(mEmptyScreen, mProgressBar);
            emptyText = getResources().getString(R.string.error_in_connection);
            customizeEmptyScreen(R.drawable.ic_signal_cellular_connected_no_internet_4_bar_black_24px, emptyText, false);
        }
    }

    public void crossfade(final View mContentview, final View mLoadingVView) {
        mContentview.setVisibility(View.VISIBLE);
        mLoadingVView.setVisibility(View.GONE);
    }

    public void customizeEmptyScreen(int showingIcon, String text, boolean canRetry) {
        mEmptyScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), showingIcon));
        mEmptyScreenText.setText(text);

        if (!canRetry) {
            mTryAgainButton.setVisibility(View.GONE);

        } else {
            mTryAgainButton.setVisibility(View.VISIBLE);
        }


    }

    public interface OnProfileSelected {
        void onProfileSelected(int profilePosition, ProfileItem profile);
    }


    /**
     * Created by mathemandy on 08 Mar 2017.
     */
    private class FetchGithubProfile extends AsyncTask<String, Void, List<ProfileItem>> {
        private final String LOG_TAG = FetchGithubProfile.class.getSimpleName();

        private List<ProfileItem> loadedProfile = new ArrayList<>();


        //    JSON Response keys

        private static final String GIT_NAME = "login";
        private static final String GIT_PHOTO = "avatar_url";
        private static final String GIT_PROFILE_LINK = "html_url";
        private static final String GIT_ID = "id";

        final String GIT_LIST = "items";

        FetchGithubProfile() {


        }


        List<ProfileItem> getGithubProfile(String devstr) throws JSONException {
            loadedProfile = new ArrayList<>();
            //    now we have s string representing the complete Profile in JSON Format
            //    Fortunately parsing is easy: constructor takes the JSON String and converts
            //    it into an object hierarchy for us.

            //    These are the names of the JSON objects that need to be extracted.


            JSONObject profileJson = new JSONObject(devstr);
            JSONArray profileArray = profileJson.getJSONArray(GIT_LIST);

            for (int i = 0; i < profileArray.length(); i++) {
                JSONObject singleProfile = profileArray.getJSONObject(i);

                //             Get the id of the user
                int id = singleProfile.getInt(GIT_ID);
                //            Get the username of the User
                String userName = singleProfile.getString(GIT_NAME);
                //            Get the Profile photo of the User
                String profile_Photo = singleProfile.getString(GIT_PHOTO);
                //            Get the Profile Url of the User
                String profile_url = singleProfile.getString(GIT_PROFILE_LINK);


                ProfileItem profileItem = new ProfileItem(id, profile_Photo, userName, profile_url);
                loadedProfile.add(profileItem);
            }

            return loadedProfile;
        }

        @Override
        protected void onPostExecute(List<ProfileItem> profileItems) {

            if (profileItems != null) {
                mProfileListAdapter.swapData(profileItems);
                crossfade(mListView, mProgressBar);

            }

        }

        @Override
        protected List<ProfileItem> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String searchQuery = params[0];

            //        These two need to be declared outside the try/catch
            //        so that they can be closed in the finally block
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            //        Will contain the raw JSON response as a string.
            String devListJsonStr = null;

            String location = "Lagos";

            try {

                //            Construct a URL for the Github Search Query
                //            Possible parameters are available at Github API page, at
                //            https://developer.github.com/v3/search/#search-repositories

                final String DEV_BASE_URL =
                        "https://api.github.com/search/users?";
                final String QUERY_PARAM = "q";
                final String LOCATION_PARAM = "location";

                Uri builtUri = Uri.parse(DEV_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, searchQuery)
                        .appendQueryParameter(LOCATION_PARAM, location)
                        .build();

                URL url = new URL(builtUri.toString().replace("&", "+").replace("location=", "location:"));

                //            Create the request to developer.github.com  and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //            Read the input string in a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    //                Nothing to do
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line).append("\n");

                }
                if (buffer.length() == 0) {
                    //                Strream was empty. no point in parsing.
                    return null;
                }

                devListJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the profile data, there's no point in attempting
                // to parse it.
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getGithubProfile(devListJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error closing Stream", e);
            }
            return new ArrayList<>();


        }
    }
}
