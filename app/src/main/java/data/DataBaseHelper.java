package data;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;

import prisam.com.cricketscorer.R;

//import com.j256.ormlite.logger.Log;

/**
 * Created by Prince on 2/07/2016.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "CricketScorer.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Team,Integer> teamDao;
    private Dao<Player,Integer> playerDao;
    private Dao<Match,Integer> matchDao;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource,Team.class);
            TableUtils.createTable(connectionSource,Player.class);
            TableUtils.createTable(connectionSource,Match.class);
        } catch (SQLException e)
        {
          Log.e(DataBaseHelper.class.getName(),"Unable to create database",e);
             //e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource,Team.class,true);
            TableUtils.dropTable(connectionSource,Player.class,true);
            TableUtils.dropTable(connectionSource,Match.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
           // e.printStackTrace();
            Log.e(DataBaseHelper.class.getName(),"Unable to upgrade database from version ",e);
        }
    }

    public Dao<Team,Integer> getTeamDao() throws SQLException
    {
        if(teamDao == null)
        {
            teamDao=getDao(Team.class);
        }

        return teamDao;
    }

    public Dao<Player,Integer> getPlayerDao() throws SQLException
    {
        if(playerDao == null)
        {
            playerDao=getDao(Player.class);
        }

       return playerDao;
    }

    public Dao<Match,Integer> getMatchDao() throws SQLException
    {
        if(playerDao == null)
        {
            playerDao=getDao(Player.class);
        }

        return matchDao;
    }


    /*START ANDROID DATABASE MANAGER CODE*/
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }
    /*END ANDROID DATABASE MANAGER CODE*/

}
