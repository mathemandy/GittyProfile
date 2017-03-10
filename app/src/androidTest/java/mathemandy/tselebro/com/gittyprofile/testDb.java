package mathemandy.tselebro.com.gittyprofile;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

import mathemandy.tselebro.com.gittyprofile.data.ProfileContract;
import mathemandy.tselebro.com.gittyprofile.data.ProfileDbHelper;
import mathemandy.tselebro.com.gittyprofile.data.TesTUtilities;

import static mathemandy.tselebro.com.gittyprofile.data.ProfileContract.ProfileEntry.TABLE_NAME;

/**
 * Created by mathemandy on 10 Mar 2017.
 */

public class testDb extends AndroidTestCase {

    void deleteTheDatabase() {
        mContext.deleteDatabase(ProfileDbHelper.DATABASE_NAME);
    }

       /*
   * This function gets called before each test is esecuted to delete the database to make sure that we always have a
   * a clean state
*/

    public void testCreateDb() throws Throwable {
        //        build a hashset of al the table names we wish to look for
//            note that there will be another table in the DB that stores the
//            Android metadata(db version information)
        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add(TABLE_NAME);

        mContext.deleteDatabase((ProfileDbHelper.DATABASE_NAME));
        SQLiteDatabase db = new ProfileDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

//           Now we create the tables we want
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: this means that the database has been created correctly", c.moveToFirst());

//verify the database

        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());
//           if this fails this means that our database does not contain the necessary tables
        assertTrue("Error: Your database was created without  all the necessary tables", tableNameHashSet.isEmpty());

//now do our tables contain the right columns

        c = db.rawQuery("PRAGMA table_info (" + TABLE_NAME + ")", null);

        assertTrue("Error: This means we are unable to query the database for the table info", c.moveToFirst());
//Build a hashset of all the column names we want to look for

        final HashSet<String> pColumnHashSet = new HashSet<>();
        pColumnHashSet.add(ProfileContract.ProfileEntry.AVATAR_PATH);
        pColumnHashSet.add(ProfileContract.ProfileEntry.USERNAME);
        pColumnHashSet.add(ProfileContract.ProfileEntry.Profile_id);
        pColumnHashSet.add(ProfileContract.ProfileEntry.PROFILE_URL);


        int columnNameIndex = c.getColumnIndex("name");

        do {
            String columnName = c.getString(columnNameIndex);
            pColumnHashSet.remove(columnName);
        } while (c.moveToNext());
//               if this fails, it means that your database does not contain all the necessary details
        assertTrue("Error: The database doesnt contain all of the recquired Movie Details column", pColumnHashSet.isEmpty());
        db.close();
    }

    public void testProfileDetailsTable() {
        insertProfileEntry();
    }

    private long insertProfileEntry() {

//           First Step, get a reference to a writable database
//           if there is an error in those massive sql table creation strings,
//           errors will be thrown here

        ProfileDbHelper profileDbHelper = new ProfileDbHelper(mContext);

        SQLiteDatabase db = profileDbHelper.getWritableDatabase();
        // Second Step: Create ContentValues of what you want to insert


        ContentValues testValues = TesTUtilities.createMatheDetailsValue();
//           Third Step : Insert content values into the database and get row ID back

        long profileRowID;

        profileRowID = db.insert(ProfileContract.ProfileEntry.TABLE_NAME, null, testValues);

//           verify that we got a row back
        assertTrue("Error: Failure to insert Mathemandy Details values", profileRowID != -1);
//           Data's inserted. In Theory, Now pull some out to stare at it and verify it made the round trip
//           A cursor is your primary interface to the query results.
        Cursor cursor = db.query(TABLE_NAME, // Table to query
                null, //all columns
                null, //Columns for the "where" clause
                null,  //Values for the "where" clause
                null, //columns t o group to by
                null, //columns to filter by row groups
                null  //sort order
        );

//           Move the cursor to a valid database row and check to see if we got any records back
//           from the query
        assertTrue("Error: No Records returned from location query", cursor.moveToFirst());

        // Fifth Step: Validate data in resulting Cursor with the original ContentValues
        // (you can use the validateCurrentRecord function in TestUtilities to validate the
        // query if you like)

        TesTUtilities.validateCurrentRecord("Error: Location Query Validation Failed", cursor, testValues);

//           move the cursor to demonstrate that there is only one record from the database
        assertFalse("Error: More than one record returned from location query", cursor.moveToNext());

        cursor.close();
        db.close();

        return profileRowID;

    }

    public void setUp() {
        deleteTheDatabase();
    }

}
