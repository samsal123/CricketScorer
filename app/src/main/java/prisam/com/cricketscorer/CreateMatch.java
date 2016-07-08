package prisam.com.cricketscorer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import data.DataBaseHelper;
import data.Match;
import data.Player;
import data.Team;

public class CreateMatch extends AppCompatActivity {

    private TextView timeDate;
    private TextView matchVenue;
    private TextView matchOvers;
    private TextView matchInns;
    private TextView matchDays;
    private Button selectHome;
    private Button selectAway;
    private Button startMatch;
    private Button back;
    private List<Team> homeTeam;
    private List<Team> awayTeam;
    private ToggleButton option;
    private SeekBar toss;
    private DataBaseHelper dataBaseHelper = null;
    public Dao<Team, Integer> teamDao = null;
    public Dao<Match,Integer> matchDao = null;
    public Dao<Player,Integer> playerDao=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);

        intializeCOntrols();
       createAllDao();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy   HH:mm ");
        timeDate.setText( sdf.format( new Date() ));
        selectHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              alertDialog(view);

            }
        });
        selectAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               alertDialog(view);


            }
        });

        startMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String matchDateTime = timeDate.getText().toString();
                String matchPlace = matchVenue.getText().toString();
                try {
                    homeTeam = teamDao.query(queryforTeam(selectHome.getText().toString()));
                   int  homeTeamID = homeTeam.get(0).TeamID;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                     awayTeam = teamDao.query(queryforTeam(selectAway.getText().toString()));
                     int awayTeamID = awayTeam.get(0).TeamID;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                int matchOver = Integer.parseInt(matchOvers.getText().toString());
                int matchInn = Integer.parseInt(matchInns.getText().toString());
                int matchDay = Integer.parseInt(matchDays.getText().toString());

                try {
                    matchDao.create(new Match("23","tyt",1,2,10,1,1));
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent main = new Intent(CreateMatch.this, MainActivity.class );

                startActivity(main);
            }
        });

        toss.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(toss.getProgress()>50)toss.setProgress(100);
                if(toss.getProgress()<50)toss.setProgress(0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void intializeCOntrols(){

        timeDate = (TextView)findViewById(R.id.editdatetime);
        matchVenue = (TextView)findViewById(R.id.matchVenue);
        matchOvers = (TextView)findViewById(R.id.matchOvers);
        matchInns = (TextView)findViewById(R.id.matchInns);
        matchDays = (TextView)findViewById(R.id.matchDays);
        selectHome= (Button)findViewById(R.id.selectHomeTeam);
        selectAway =(Button)findViewById(R.id.selectAwayTeam);
        startMatch = (Button)findViewById(R.id.matchStart);
        option = (ToggleButton)findViewById(R.id.toggleButton);
        toss = (SeekBar)findViewById(R.id.seekBar);
        back = (Button)findViewById(R.id.backfromCreateMatch);



    }



private void alertDialog(View v) {

    final Button b1 = (Button)v;
    AlertDialog.Builder builderSingle = new AlertDialog.Builder(CreateMatch.this);
    builderSingle.setIcon(R.drawable.ic_launcher);


    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
            CreateMatch.this,
            android.R.layout.select_dialog_singlechoice);
    try {
        List<Team> teams = teamDao.queryForAll();
        if (teams.size() != 0) {
            for (int i = 0; i < teams.size(); i++) {
                arrayAdapter.add(teams.get(i).teamName);
            }
        }
        else msg("Please add Teams");
        }catch(SQLException e){
            e.printStackTrace();
        }

    builderSingle.setTitle("Select One Team:-");

    builderSingle.setNegativeButton(
            "cancel",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

    builderSingle.setAdapter(
            arrayAdapter,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
                    b1.setText(strName);

                }

            });
    builderSingle.show();


    }



    private void alertDialogSameTeam() {

        AlertDialog.Builder ale = new AlertDialog.Builder(CreateMatch.this);
        ale.setTitle("You have selected Same teams please select different Teams");
        ale.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    public void createAllDao() {
        try {
            teamDao = getHelper().getTeamDao();
            matchDao = getHelper().getMatchDao();
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

    private PreparedQuery<Team> queryforTeam(String teamname) {

        QueryBuilder<Team, Integer> qb = teamDao.queryBuilder();
        PreparedQuery<Team> newQ = null;
        try {
            qb.where().eq("teamName", teamname);
            newQ = qb.prepare();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newQ;
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
