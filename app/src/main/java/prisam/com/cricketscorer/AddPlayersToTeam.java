package prisam.com.cricketscorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DataBaseHelper;
import data.Player;
import data.Team;

public class AddPlayersToTeam extends AppCompatActivity {

    TextView teamName;
    private DataBaseHelper dataBaseHelper = null;
    public Dao<Player, Integer> playerDao = null;

    Button btnAddPlayer;
    TextView playerName;
    ListView playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players_to_team);

        createPlayerDao();

        initialiseControls();





        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String player = playerName.getText().toString();

                try {
                    playerDao.create(new Player(player));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                showPlayers();



            }
        });


    }

    private void initialiseControls() {
        teamName = (TextView)findViewById(R.id.txtTeamName);
        btnAddPlayer= (Button)findViewById(R.id.btnAddPlayer);
        playerName = (TextView)findViewById(R.id.txtNewPlayer);
        playerList = (ListView)findViewById(R.id.listView2);

        Intent newint = getIntent();

        teamName.setText(newint.getStringExtra("TeamName"));
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

            List<Player> players = playerDao.queryForAll();


msg(players.toString());

            PlayerAdapter a1 = new PlayerAdapter(AddPlayersToTeam.this, (ArrayList<Player>) players);



        playerList.setAdapter(a1);



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

}
