
package uchida.eurekatask1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SimpleDatabaseHelper extends SQLiteOpenHelper {
    static final private int VERSION = 1;
    static final private String CREATE_TBL = "CREATE TABLE " + Constants.TBL_NAME
            + " (" + Constants.CATEGORY + " TEXT, "
            + Constants.TITLE + " TEXT, "
            + Constants.IMAGE_URL + " TEXT, "
            + Constants.LIKES_COUNT + " TEXT, "
            + Constants.NAME + " TEXT)";

    public SimpleDatabaseHelper(Context context) {
        super(context, Constants.DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {

    }

}
