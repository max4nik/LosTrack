import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;



public class ITEMS_InDataBase {

    DBHelper dbHelper;
    SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
    ContentValues contentValues = new ContentValues();


    private Tag myTag;
    private String name = myTag.getName();
    private String address = myTag.getAddress();


    //Constructor
    public ITEMS_InDataBase(Tag MyTag) {
        this.myTag.getName();
        this.myTag.getAddress();
        this.myTag = MyTag;

    }

    //methods to communicate with database
    public void saveTag(Tag tag) {
        this.myTag = tag;
        contentValues.put(DBHelper.KEY_NAME, myTag.getName());
        contentValues.put(DBHelper.KEY_ADDRESS, myTag.getAddress());

        dataBase.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
    }

    public void deleteTagByName(Tag tag) {
        this.myTag = tag;
        if (name.equalsIgnoreCase("")) {
            //break;
        }
        int deleteCount = dataBase.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_NAME + " = ?", new String[]{myTag.getName()});
        Log.d("mLog", "deleted rows count = " + deleteCount);
    }

    public void changeTag(Tag tag) {
        this.myTag = tag;
        if (name.equalsIgnoreCase("")) {
            //break;
        }

        contentValues.put(DBHelper.KEY_NAME, myTag.getName());
        contentValues.put(DBHelper.KEY_ADDRESS, myTag.getAddress());
        int updCount = dataBase.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_NAME + " = ?", new String[]{myTag.getName()});
        Log.d("mLog", "updates rows count = " + updCount);
    }


    public List<Tag> getDatabase() {
        Cursor cursor = dataBase.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

        List<Tag> result = new ArrayList<Tag>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int addressIndex = cursor.getColumnIndex(DBHelper.KEY_ADDRESS);

            do {
                Tag newTag = new Tag(cursor.getString(addressIndex), cursor.getString(nameIndex), Tag.VISIBILITY.INVISIBLE);
                result.add(newTag);
                //System.out.println("ID = " + cursor.getInt(idIndex) + ", name = " + cursor.getString(nameIndex) + ", address = " + cursor.getString(addressIndex));
            } while (cursor.moveToNext());
        } else
                //System.out.println("0 rows");

        cursor.close();
        return result;
    }


    public void clearDB() {
        dataBase.delete(DBHelper.TABLE_CONTACTS, null, null);
    }



}

class DBHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "Address";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_ADDRESS + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);

        onCreate(db);

    }
}
