package mathemandy.tselebro.com.gittyprofile.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

/**
 * Created by mathemandy on 10 Mar 2017.
 */

public class TesTUtilities extends AndroidTestCase {

    static final String username = "mathemandy";

    static final String profile_url = "http://github.com/mathemandy";

    static final String avatar_url = "http://something.png";

    static int profile_id = 3458902;

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedvalues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedvalues);
}


    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedvalue) {

        Set<Map.Entry<String, Object>> valueset = expectedvalue.valueSet();

        for (Map.Entry<String, Object> entry : valueset) {
            String columnNAme = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnNAme);

            assertFalse("Column '" + columnNAme + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "'did not match the expected Value '" +
                    expectedvalue + "'." + error, expectedValue, valueCursor.getString(idx));

        }}

        static public ContentValues createMatheDetailsValue(){

//        create a new map of value, where column name s are the Keys

            ContentValues testvalues = new ContentValues();

            testvalues.put(ProfileContract.ProfileEntry.AVATAR_PATH, avatar_url);
            testvalues.put(ProfileContract.ProfileEntry.USERNAME, username);
            testvalues.put(ProfileContract.ProfileEntry.Profile_id, profile_id);
            testvalues.put(ProfileContract.ProfileEntry.PROFILE_URL, profile_url);
            return testvalues;
    }


    static  long insertMatheDetailsValues(Context context){
//        insert our test record into the database
        ProfileDbHelper profileDbHelper = new ProfileDbHelper(context);
        SQLiteDatabase db = profileDbHelper.getWritableDatabase();
        ContentValues testValues = TesTUtilities.createMatheDetailsValue();

        long profileRowID;
        profileRowID = db.insert(ProfileContract.ProfileEntry.TABLE_NAME, null, testValues);

        assertTrue("Error: Fsilure to insert Mathemandy Details values", profileRowID != -1);
        return profileRowID;

    }
}
