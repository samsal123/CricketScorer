package prisam.com.cricketscorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button manageTeam;
    private Button connectBt;
    private Button dbView;

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



    }

    public void initialiseControls(){

        manageTeam = (Button)findViewById(R.id.btnManageTeam);
        connectBt = (Button)findViewById(R.id.btnConnect);
        dbView = (Button)findViewById(R.id.dbView);

    }
}
