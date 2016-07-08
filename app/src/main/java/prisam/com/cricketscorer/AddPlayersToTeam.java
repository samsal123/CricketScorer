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

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DataBaseHelper;
import data.Player;

public class AddPlayersToTeam extends AppCompatActivity  implements OnCustomClickListener {


    private DataBaseHelper dataBaseHelper = null;
    public Dao<Player, Integer> playerDao = null;

    Button btnAddPlayer;
    TextView firstName;
    TextView lastName;
    ListView playerList;
    TextView playerTeam;
    Button refresh;
    int teamID;
    Player editPlayer;
    Button back;
   public PlayerAdapter a1;
    List<Player> playerdblist;
    InputMethodManager imm;

    @Override
    public void OnCustomClick(View aView, int position) {
        deletePlayer(position);
        //Toast.makeText(this, teamName.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players_to_team);

        createPlayerDao();

        initialiseControls();
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
                    playerDao.create(new Player(fName,lName,teamID));

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
                Intent newint = new Intent(AddPlayersToTeam.this,ManageTeamActvity.class);
                startActivity(newint);
            }
        });


    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(lastName.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    private void initialiseControls() {
        playerTeam = (TextView)findViewById(R.id.txtTeamName);
        btnAddPlayer= (Button)findViewById(R.id.btnAddPlayer);
        firstName = (TextView)findViewById(R.id.txtFirstName);
        lastName = (TextView)findViewById(R.id.txtlastname);
        playerList = (ListView)findViewById(R.id.listView2);
        refresh = (Button)findViewById(R.id.refresh);
        back = (Button)findViewById(R.id.btnGoBack);

        Intent newint = getIntent();

        playerTeam.setText(newint.getStringExtra("TeamName"));
      teamID = newint.getIntExtra("TeamID",0);
        editPlayer = (Player) newint.getSerializableExtra("Player");
        if (editPlayer!=null)
        {
            firstName.setText(editPlayer.firstName);
            lastName.setText(editPlayer.lastName);
        }
    }



    public void createPlayerDao() {
      try {
           playerDao = getHelper().getPlayerDao();

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


    public void showPlayers() {

        try {

         playerdblist = playerDao.query(queryforPlayers(teamID));

            a1 = new PlayerAdapter(AddPlayersToTeam.this,(ArrayList<Player>) playerdblist,this);
            playerList.setAdapter(a1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void deletePlayer(int id) {

        DeleteBuilder<Player,Integer> deleteBuilder = playerDao.deleteBuilder();
        try {
            deleteBuilder.where().eq("PlayerID",id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    private PreparedQuery<Player> queryforPlayers(int id) {

        QueryBuilder<Player, Integer> qb = playerDao.queryBuilder();
        PreparedQuery<Player> ppq = null;
        try {
            qb.where().eq("playerTeamID", id);
            ppq = qb.prepare();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ppq;
    }



    //private void msg(String s) {
//        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
   // }

}
