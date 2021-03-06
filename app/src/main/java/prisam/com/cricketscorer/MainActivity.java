package prisam.com.cricketscorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button manageTeam;
    private Button connectBt;
    private Button dbView;
    private Button gotoMatch;
    private Button matchSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseControls();

        manageTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newint = new Intent(MainActivity.this, ManageTeamActvity.class);
                startActivity(newint);

            }
        });

        connectBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newint = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(newint);
            }
        });

        gotoMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent scorecard = new Intent(MainActivity.this, ScoreAndControlDisplayActivity.class);
                startActivity(scorecard);
            }
        });

        matchSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent matchsetup = new Intent(MainActivity.this, MatchSetupActivity.class);
                startActivity(matchsetup);
            }
        });

        dbView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(MainActivity.this, AndroidDatabaseManagerActivity.class);
                startActivity(dbmanager);
            }
        });
    }

    public void initialiseControls() {

        manageTeam = (Button) findViewById(R.id.btnManageTeam);
        connectBt = (Button) findViewById(R.id.btnConnect);
        dbView = (Button) findViewById(R.id.dbView);
        gotoMatch = (Button) findViewById(R.id.btnMatch);
        matchSetup = (Button) findViewById(R.id.matchSetup);

    }


}
