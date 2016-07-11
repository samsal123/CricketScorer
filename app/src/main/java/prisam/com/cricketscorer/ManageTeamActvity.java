package prisam.com.cricketscorer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DataBaseHelper;
import data.Team;


public class ManageTeamActvity extends AppCompatActivity implements OnCustomClickListener {

    private DataBaseHelper dataBaseHelper = null;
    private Dao<Team, Integer> teamDao = null;
    private EditText teamName;
    private Button addTeam;
    public ListView teamView;
    private Button back;

    @Override
    public void OnCustomClick(View aView, int position) {
        deleteTeam(position);
        Toast.makeText(this, "Team deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_team_actvity);

        createTeamDao();
        initialiseControls();
        hideKeyBoard();

        addTeam.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String team = teamName.getText().toString().trim();

                if(team.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid team name.", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        Team newTeam = new Team(team);
                        List<Team> teams = teamDao.queryForAll();
                        if(teams.contains(newTeam)){
                            Toast.makeText(getApplicationContext(), "Team already exists!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            teamDao.create(newTeam);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    hideKeyBoard();
                    teamName.setText("");
                    showTeams();
                }
            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newint = new Intent(ManageTeamActvity.this,MainActivity.class);
                startActivity(newint);

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

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(teamName.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public void showTeams() {
        try {

            List<Team> teams = teamDao.queryForAll();
            TeamAdapter a = new TeamAdapter(ManageTeamActvity.this, (ArrayList<Team>) teams, this);
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
        addTeam = (Button) findViewById(R.id.refresh);
        teamView = (ListView) findViewById(R.id.listView);
        back = (Button)findViewById(R.id.back);

    }

    public void deleteTeam(int teamID) {
        DeleteBuilder<Team, Integer> deleteBuilder = teamDao.deleteBuilder();
        try {
            deleteBuilder.where().eq("TeamID", teamID);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

}
