package simplesns.uiseon.com.simplesns.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {  //데이터베이스 클래스




    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,

                      int version) {

        super(context, name, factory, version);

// TODO Auto-generated constructor stub

    }




    @Override

    public void onCreate(SQLiteDatabase db) {

// TODO Auto-generated method stub




        db.execSQL("CREATE TABLE today(_id INTEGER PRIMARY KEY AUTOINCREMENT, "

                + "title TEXT, " + "date TEXT , " + "time TEXT, "

                + "memo TEXT );");




    }




    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

// TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXIST today;");

        onCreate(db);

    }




}
