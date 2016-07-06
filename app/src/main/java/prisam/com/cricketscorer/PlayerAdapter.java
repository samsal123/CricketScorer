package prisam.com.cricketscorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    Player player;
    private PlayerAdapterCallBack callback;



  AddPlayersToTeam addActivity = new AddPlayersToTeam();


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        addActivity.createPlayerDao();
        // Get the data item for this position
         player = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.playerlist, parent, false);
        }

        // Lookup view for data population
         txtPlayerName = (TextView) convertView.findViewById(R.id.listPlayerName);
        txtTeam  = (TextView) convertView.findViewById(R.id.txtPlayertTeamName);



        // Populate the data into the template view using the data object
       txtPlayerName.setText(player.firstName+ " "+player.lastName);
        txtTeam.setText(player.playerTeamID+"");


        btnDelete = (Button)convertView.findViewById(R.id.del);
        btnEdit = (Button)convertView.findViewById(R.id.edit);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(callback != null) {

                    callback.deletePressed(player.playerId);
                }


            }
        });



        return convertView;}

    public void setCallback(PlayerAdapterCallBack callback){

        this.callback = callback;
    }
    public interface PlayerAdapterCallBack {

        public void deletePressed(int position);
    }





    private void msg(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }


}
