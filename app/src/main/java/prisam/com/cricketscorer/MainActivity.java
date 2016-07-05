package prisam.com.cricketscorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button manageTeam;
    private Button connectBt;
    private Button addplayer;

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

        addplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newint = new Intent(MainActivity.this,AddPlayersToTeam.class);

                startActivity(newint);
            }
        });



    }

    public void initialiseControls(){

        manageTeam = (Button)findViewById(R.id.btnManageTeam);
        connectBt = (Button)findViewById(R.id.btnConnect);
        addplayer = (Button)findViewById(R.id.btnaddplayeractivity);

    }
}
