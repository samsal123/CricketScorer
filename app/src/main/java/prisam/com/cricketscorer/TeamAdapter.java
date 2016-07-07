package prisam.com.cricketscorer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

import data.Team;

/**
 * Created by z3004590 on 5/07/2016.
 */
public class TeamAdapter extends ArrayAdapter<Team> {

    private Team team;
    private Button btnDelete;
    private Button btnEdit;
    private TextView teamName;
    private ArrayList<Team> teams;

    private Context context;
    private LayoutInflater mInflater;
    private OnCustomClickListener callback;

    public TeamAdapter(Context context, ArrayList<Team> teams, OnCustomClickListener callback) {
        super(context, 0, teams);
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.callback = callback;
        this.teams = teams;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView name;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        team = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listitem_view, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        initialiseControls(convertView);

        // set the text for the team name
        teamName.setText(team.teamName);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(teamName, position);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPlayersToTeamIntent = new Intent(context, AddPlayersToTeam.class);//.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                addPlayersToTeamIntent.putExtra("TeamName", team.teamName);
                addPlayersToTeamIntent.putExtra("TeamID", team.TeamID);
                context.startActivity(addPlayersToTeamIntent);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    public void showDialog(final View view, final int position) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Are you sure to delete the selected team  '" + getItem(position).teamName + "'");

        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.OnCustomClick(view, getItem(position).TeamID);
                teams.remove(position);
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

    private void initialiseControls(View convertView){
        teamName = (TextView) convertView.findViewById(R.id.textView);
        btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
        btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
    }
}





