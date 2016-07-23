package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Helpers.OnCustomClickListener;
import data.Player;
import data.Team;
import prisam.com.cricketscorer.R;

/**
 * Created by Prince on 5/07/2016.
 */
public class PlayerAdapter extends ArrayAdapter<Player> {

    private Button btnDelete;
    private TextView playerName;
    private TextView playerTeam;
    private CheckBox playing;

    private Team team;
    private Player player;
    private ArrayList<Player> players;
    private LayoutInflater mInflater;

    private OnCustomClickListener callback;

    public PlayerAdapter(Context context, ArrayList<Player> players, OnCustomClickListener callback, Team team) {
        super(context, 0, players);
        mInflater = LayoutInflater.from(context);
        this.callback = callback;
        this.players = players;
        this.team = team;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView name;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        player = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.playerlist, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        initialiseControls(convertView);

        // set the text for the team name
        playerTeam.setText(team.teamName + "");
        playerName.setText(player.fullname);
        playing.setChecked(player.playing);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(playerName, position);
            }
        });



        playing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                player.playing = false;
                playing.setChecked(false);

            }
        });



        // Return the completed view to render on screen
        return convertView;
    }

    public void showDialog(final View view, final int position) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Are you sure to delete  '" + getItem(position).fullname + " " + "' ?");

        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.OnCustomClick(view, getItem(position).playerId);
                players.remove(position);
                notifyDataSetChanged();
            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void initialiseControls(View convertView) {
        playerTeam = (TextView) convertView.findViewById(R.id.txtPlayertTeamName);
        playerName = (TextView) convertView.findViewById(R.id.listPlayerName);
        btnDelete = (Button) convertView.findViewById(R.id.del);
        playing = (CheckBox)convertView.findViewById(R.id.checkBox);

    }

    private void msg(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

}
