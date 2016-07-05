package prisam.com.cricketscorer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DataBaseHelper;
import data.Team;


public class ManageTeamActvity extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper = null;
    public Dao<Team, Integer> teamDao = null;
    private EditText teamName;
    private Button addTeam;
    public ListView teamView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_team_actvity);

        createTeamDao();

        initialiseControls();


        addTeam.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String team = teamName.getText().toString();

                try {
                    teamDao.create(new Team(team));
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                showTeams();


            }


        });

        showTeams();


    }

    public void createTeamDao() {
        try {
            teamDao = getHelper().getTeamDao();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void showTeams() {


        try {

            List<Team> teams = teamDao.queryForAll();




            TeamAdapter a = new TeamAdapter(ManageTeamActvity.this, (ArrayList<Team>) teams);



            teamView.setAdapter(a);



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DataBaseHelper getHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }

        return dataBaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }

    private void initialiseControls() {

        teamName = (EditText) findViewById(R.id.editText);
        addTeam = (Button) findViewById(R.id.btnAddTeam);
        teamView = (ListView) findViewById(R.id.listView);


    }



        public void deleteTeam(TextView v) {
            DeleteBuilder<Team,Integer> deleteBuilder = teamDao.deleteBuilder();
            try {
                deleteBuilder.where().eq("teamName",v.getText().toString());
                deleteBuilder.delete();
            } catch (SQLException e) {
                e.printStackTrace();
            }





        }


    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }


}
