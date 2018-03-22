package market.goldandgo.videonew1.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.entity.AppInfo;

/**
 * Created by Go Goal on 1/27/2018.
 */

public class Mydatabase extends SQLiteOpenHelper {

    SQLiteDatabase dbase;

    public Mydatabase(Context context) {
        super(context, "fmovie7.db", null, 2);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE fmovie1(taskid CHAR, name CHAR, size CHAR, statusdown CHAR,id CHAR)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insertdata(String tasktoken,String name,String size,String statusdown,String id) {
        dbase = getWritableDatabase();
        String sql = "INSERT INTO fmovie1 VALUES ('"+tasktoken+"','" + name + "','" + size + "','" + statusdown + "','" + id + "')";
        dbase.execSQL(sql);
        dbase.close();
    }

    public ArrayList<get> getlist() {


        ArrayList<get> list =new ArrayList<get>();
        dbase = getReadableDatabase();
        String sel = "SELECT * FROM fmovie1 WHERE statusdown='0' OR statusdown='1' ";
        Cursor c = dbase.rawQuery(sel, null);
        if (c.moveToFirst()) {
            do {

                get eg=new get();
                eg.setTasktoken(c.getString(0));
                eg.setDmname(c.getString(1));
                eg.setDmsize(c.getString(2));
                eg.setDmstatus(c.getString(3));
                eg.setDmid(c.getString(4));
                list.add(eg);

            } while (c.moveToNext());

        }
        dbase.close();

        return list;
    }


    public ArrayList<get> getcompletelist() {


        ArrayList<get> list =new ArrayList<get>();
        dbase = getReadableDatabase();
        String sel = "SELECT * FROM fmovie1 WHERE statusdown='com'";
        Cursor c = dbase.rawQuery(sel, null);
        if (c.moveToFirst()) {
            do {

                get eg=new get();
                eg.setTasktoken(c.getString(0));
                eg.setDmname(c.getString(1));
                eg.setDmsize(c.getString(2));
                eg.setDmstatus(c.getString(3));
                eg.setDmid(c.getString(4));
                list.add(eg);

            } while (c.moveToNext());

        }
        dbase.close();

        return list;
    }

    public void update_row(String dmid, String statusdm) {
        dbase = getWritableDatabase();
        String sql = "UPDATE  fmovie1 SET statusdown='"+statusdm+"' WHERE id='"+dmid+"'";
        dbase.execSQL(sql);
        dbase.close();
    }

    public void delete_row(String id) {
        dbase = getWritableDatabase();
        String sql = "DELETE FROM fmovie1 WHERE id='"+id+"'";
        dbase.execSQL(sql);
        dbase.close();
    }


    public void update_complete(String taskId) {
        dbase = getWritableDatabase();
        String sql = "UPDATE  fmovie1 SET size='com' WHERE id='"+taskId+"'";
        dbase.execSQL(sql);
        dbase.close();
    }

    public String getdetailname(long taskId) {

        String name="Fmovie";
        dbase = getReadableDatabase();
        String sel = "SELECT * FROM fmovie1 WHERE taskid='"+taskId+"' ";
        Cursor c = dbase.rawQuery(sel, null);

        if (c.moveToFirst()) {
            do {

              name=c.getString(1);


            } while (c.moveToNext());

        }
     //   dbase.close();

        return name;
    }

    public List<AppInfo> getdata() {
        ArrayList<AppInfo> list =new ArrayList<AppInfo>();
        dbase = getReadableDatabase();
        String sel = "SELECT * FROM fmovie1 ";
        Cursor c = dbase.rawQuery(sel, null);
        if (c.moveToFirst()) {
            do {

                AppInfo appInfo = new AppInfo();

                appInfo.setUrl(c.getString(0));
                appInfo.setName(c.getString(1));
                appInfo.setComplete(c.getString(2));
                appInfo.setImage(c.getString(3));
                appInfo.setId(c.getString(4));
                list.add(appInfo);

            } while (c.moveToNext());

        }
        dbase.close();

        return list;
    }
}
