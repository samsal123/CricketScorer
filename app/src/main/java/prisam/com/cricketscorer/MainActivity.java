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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseControls();

        manageTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newint = new Intent(MainActivity.this,ManageTeamActvity.class);
                startActivity(newint);

            }
        });


        dbView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(MainActivity.this,AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

        connectBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newint = new Intent(MainActivity.this,DeviceList.class);
                startActivity(newint);
            }
        });

        gotoMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent scorecard = new Intent(MainActivity.this,ScoreAndControlDisplay.class);
                startActivity(scorecard);
            }
        });



    }



    public void initialiseControls(){

        manageTeam = (Button)findViewById(R.id.btnManageTeam);
        connectBt = (Button)findViewById(R.id.btnConnect);
        dbView = (Button)findViewById(R.id.dbView);
        gotoMatch = (Button)findViewById(R.id.btnMatch);

    }



}
