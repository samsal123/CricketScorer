package prisam.com.cricketscorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AddPlayersToTeam extends AppCompatActivity {

    TextView teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players_to_team);

        teamName = (TextView)findViewById(R.id.txtTeamName);

        Intent newint = getIntent();

        teamName.setText(newint.getStringExtra("TeamName"));


    }
}
