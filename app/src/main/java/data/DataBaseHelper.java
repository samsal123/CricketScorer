package data;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
//import com.j256.ormlite.logger.Log;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import prisam.com.cricketscorer.R;

/**
 * Created by Prince on 2/07/2016.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "CricketScorer.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Team,Integer> teamDao;
    private Dao<Player,Integer> playerDao;



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource,Team.class);
            TableUtils.createTable(connectionSource,Player.class);
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




}
