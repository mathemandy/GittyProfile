package mathemandy.tselebro.com.gittyprofile.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static android.R.attr.name;

/**
 * Created by mathemandy on 10 Mar 2017.
 */

public class ProfileDbHelper extends SQLiteOpenHelper {

//     At this point, if you change the database schema, you must increment the database version

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "profile.db";

    public ProfileDbHelper(Context context) {
        super(context, DATABASE_NAME, null,  DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        create a table to hold the Profile data, this consist of the username, id, avatar_url, html_url

        final String SQL_CREATE_PROFILE_DETAILS_TABLE = "CREATE TABLE " + ProfileContract.ProfileEntry.TABLE_NAME + "( " +
                ProfileContract.ProfileEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
                ProfileContract.ProfileEntry.Profile_id + " INTEGER NOT NULL UNIQUE, " +
                ProfileContract.ProfileEntry.AVATAR_PATH + " TEXT NOT NULL , " +
                ProfileContract.ProfileEntry.USERNAME + " TEXT UNIQUE NOT NULL, " +
                ProfileContract.ProfileEntry.PROFILE_URL + " TEXT NOT NULL , " +
                "UNIQUE (" + ProfileContract.ProfileEntry.Profile_id + ") ON CONFLICT REPLACE);";

   db.execSQL(SQL_CREATE_PROFILE_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + ProfileContract.ProfileEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
