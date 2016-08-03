package prisam.com.cricketscorer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Adapters.PlayerAdapter;
import Helpers.OnCustomClickListener;
import data.DataBaseHelper;
import data.Player;
import data.Team;

public class AddPlayersToTeamActivity extends AppCompatActivity implements OnCustomClickListener {

    private DataBaseHelper dataBaseHelper = null;
    private Button btnAddPlayer;
    private TextView fullName;
    private TextView txtnickName;
    private ListView playerList;
    private TextView playerTeam;
    private Button refresh;
    private int teamID;
    private Button back;
    private String fName;
    private String nickName;
    private boolean playing;
    private List<Player> playersList;


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

                nickName = txtnickName.getText().toString();
                if (txtnickName.getText().length() > 4) {
                    showDialog();
                } else {
                    try {
                        if (playersList.size() < 12)
                            dataBaseHelper.getPlayerDao().create(new Player(fName, nickName, true, getTeams.get(0)));
                        else
                            dataBaseHelper.getPlayerDao().create(new Player(fName, nickName, false, getTeams.get(0)));


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    showPlayers();
                    hideKeyBoard();
                    fullName.setText("");
                    txtnickName.setText("");

                }
            }
        });

        fullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                fName = fullName.getText().toString();

                if (!b) {
                    txtnickName.setText(fName.substring(0, 4));
                }

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
        imm.hideSoftInputFromWindow(fullName.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    private void initialiseControls() throws SQLException {
        playerTeam = (TextView) findViewById(R.id.txtTeamName);
        btnAddPlayer = (Button) findViewById(R.id.btnAddPlayer);
        fullName = (TextView) findViewById(R.id.txtFirstName);
        txtnickName = (TextView) findViewById(R.id.txtLastName);
        playerList = (ListView) findViewById(R.id.listView2);
        refresh = (Button) findViewById(R.id.refresh);
        back = (Button) findViewById(R.id.btnGoBack);

        Intent newint = getIntent();

        playerTeam.setText(newint.getStringExtra("TeamName"));
        teamID = newint.getIntExtra("TeamID", 0);


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
            playersList = dataBaseHelper.getPlayerDao().query(statementBuilder.prepare());

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

    private void showDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(AddPlayersToTeamActivity.this);

        alert.setTitle("Nick Name");
        alert.setMessage("Please enter a valid Nick Name with 4 characters");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                txtnickName.setText(fName.substring(0, 4));
            }
        });

        alert.show();

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

}
