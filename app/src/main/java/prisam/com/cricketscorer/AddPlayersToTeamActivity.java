package prisam.com.cricketscorer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DataBaseHelper;
import data.Player;
import data.Team;

public class AddPlayersToTeamActivity extends AppCompatActivity implements OnCustomClickListener {

    private DataBaseHelper dataBaseHelper = null;
    private Button btnAddPlayer;
    private TextView firstName;
    private TextView lastName;
    private ListView playerList;
    private TextView playerTeam;
    private Button refresh;
    private int teamID;
    private Player editPlayer;
    private Button back;

    public PlayerAdapter a1;
    private List<Team> getTeams;
    //private InputMethodManager imm;

    @Override
    public void OnCustomClick(View aView, int position) {
        deletePlayer(position);
        Toast.makeText(this, "Player deleted.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players_to_team);

        try {
            initialiseControls();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showPlayers();
        a1.notifyDataSetChanged();

        hideKeyBoard();

        // getWindow().setSoftInputMode(
        // WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        // );

        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();

                try {

                    dataBaseHelper.getPlayerDao().create(new Player(fName, lName, getTeams.get(0)));

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                showPlayers();
                hideKeyBoard();
                firstName.setText("");
                lastName.setText("");

            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPlayers();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newint = new Intent(AddPlayersToTeamActivity.this, ManageTeamActvity.class);
                startActivity(newint);
            }
        });

    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(lastName.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    private void initialiseControls() throws SQLException {
        playerTeam = (TextView) findViewById(R.id.txtTeamName);
        btnAddPlayer = (Button) findViewById(R.id.btnAddPlayer);
        firstName = (TextView) findViewById(R.id.txtFirstName);
        lastName = (TextView) findViewById(R.id.txtlastname);
        playerList = (ListView) findViewById(R.id.listView2);
        refresh = (Button) findViewById(R.id.refresh);
        back = (Button) findViewById(R.id.btnGoBack);

        Intent newint = getIntent();

        playerTeam.setText(newint.getStringExtra("TeamName"));
        teamID = newint.getIntExtra("TeamID", 0);

        editPlayer = (Player) newint.getSerializableExtra("Player");
        if (editPlayer != null) {
            firstName.setText(editPlayer.firstName);
            lastName.setText(editPlayer.lastName);
        }

        dataBaseHelper = getDBHelper();
        getTeams = dataBaseHelper.getTeamDao().queryForEq("teamID", teamID);

    }

    private DataBaseHelper getDBHelper() {
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

    public void showPlayers() {

        try {

            //playerdblist = playerDao.query(queryforPlayers(teamID));
            //Team t = getTeams.get(0);
            // query for all orders that match a certain account
            //List<Player> results =  dataBaseHelper.getPlayerDao().queryBuilder().where().eq("team_id", getTeams.get(0).TeamID).query();
            //List<Player> playerdblist1 = playerDao.queryForEq("team",t.TeamID);

            // construct a query using the QueryBuilder
            QueryBuilder<Player, Integer> statementBuilder = dataBaseHelper.getPlayerDao().queryBuilder();
            statementBuilder.where().eq("team_id", getTeams.get(0).TeamID);
            List<Player> playersList = dataBaseHelper.getPlayerDao().query(statementBuilder.prepare());

            a1 = new PlayerAdapter(AddPlayersToTeamActivity.this, (ArrayList<Player>) playersList, this, getTeams.get(0));
            playerList.setAdapter(a1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deletePlayer(int id) {

        try {
            DeleteBuilder<Player, Integer> deleteBuilder = dataBaseHelper.getPlayerDao().deleteBuilder();
            deleteBuilder.where().eq("PlayerID", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    private PreparedQuery<Player> queryforPlayers(int id) {
//
//        QueryBuilder<Player, Integer> qb = null;
//        try {
//            qb = dataBaseHelper.getPlayerDao().queryBuilder();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        PreparedQuery<Player> ppq = null;
//        try {
//            qb.where().eq("Team", getTeams.get(0));
//            ppq = qb.prepare();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return ppq;
//    }

    //private void msg(String s) {
//        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    // }

}
