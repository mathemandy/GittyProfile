package mathemandy.tselebro.com.gittyprofile.data;

import android.provider.BaseColumns;

/**
 * Created by mathemandy on 10 Mar 2017.
 */

public class ProfileContract {

    public interface ProfileColumns
    {
        String Profile_id = "id";

        String AVATAR_PATH = "avatar_url";

        String USERNAME = "username";

        String PROFILE_URL = "html_url";
    }

//    inner class that determines the table contents of the movie details
    public static final class ProfileEntry implements BaseColumns, ProfileColumns{
//    the table name
    public static final String TABLE_NAME = "profileDetails";
}
}
