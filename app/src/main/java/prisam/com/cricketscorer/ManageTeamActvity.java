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
    private Dao<Team, Integer> teamDao = null;
    private EditText teamName;
    private Button addTeam;
    private ListView teamView;
    private LinearLayout displayTeam;


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

    private void createTeamDao() {
        try {
            teamDao = getHelper().getTeamDao();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showTeams() {


        try {

            List<Team> teams = teamDao.queryForAll();




            ArrayAdapter a = new ArrayAdapter(ManageTeamActvity.this, android.R.layout.simple_list_item_1, Utility.getData(teams));



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
        displayTeam = (LinearLayout)findViewById(R.id.linear1);

        teamView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView v = (TextView)view;

                msg(v.getText().toString());
                showDialog(v);



                showTeams();

            }
        });


    }

    public void showDialog(final View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        TextView v= (TextView)view;
        alertDialogBuilder.setMessage("Team  " +v.getText().toString());

        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                deleteTeam((TextView) view);
                showTeams();

            }
        });
        alertDialogBuilder.setNegativeButton("Edit",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent newint = new Intent(ManageTeamActvity.this,AddPlayersToTeam.class);

                newint.putExtra("TeamName",((TextView) view).getText());

                startActivity(newint);

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    private void deleteTeam(TextView v) {
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
