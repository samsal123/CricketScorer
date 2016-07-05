package prisam.com.cricketscorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;

import data.DataBaseHelper;
import data.Player;
import data.Team;

/**
 * Created by Prince on 5/07/2016.
 */
public class PlayerAdapter extends ArrayAdapter<Player> {

    public PlayerAdapter(Context context, ArrayList<Player>players){
        super(context, 0, players);}

    Button btnDelete;
    Button btnEdit;
    TextView txtPlayerName;
    TextView txtTeam;

    private DataBaseHelper dataBaseHelper = null;
    public Dao<Team, Integer> teamDao = null;
    ManageTeamActvity manageTeamActvity = new ManageTeamActvity();


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        manageTeamActvity.createTeamDao();
        // Get the data item for this position
        Player player = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.playerlist, parent, false);
        }

        // Lookup view for data population
        TextView txtPlayerName = (TextView) convertView.findViewById(R.id.listPlayerName);

        // Populate the data into the template view using the data object
       txtPlayerName.setText(player.firstName);

        btnDelete = (Button)convertView.findViewById(R.id.del);
        btnEdit = (Button)convertView.findViewById(R.id.edit);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

        return convertView;}


}
