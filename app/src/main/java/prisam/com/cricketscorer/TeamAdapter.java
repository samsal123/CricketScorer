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
import com.j256.ormlite.dao.Dao;
import java.util.ArrayList;
import data.DataBaseHelper;
import data.Team;

/**
 * Created by z3004590 on 5/07/2016.
 */
public class TeamAdapter extends ArrayAdapter<Team> {


    private ArrayList<Team> teams;
    private Button btnDelete;
    private Button btnEdit;
    private TextView teamName;
    private Team team;

    private OnCustomClickListener callback;
    private DataBaseHelper dataBaseHelper = null;
    public Dao<Team, Integer> teamDao = null;
    //ManageTeamActvity manageTeamActvity = new ManageTeamActvity();

    public TeamAdapter(Context context, ArrayList<Team> teams, OnCustomClickListener callback) {
        super(context, 0, teams);
        this.callback = callback;
        this.teams = teams;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //manageTeamActvity.createTeamDao();
        // Get the data item for this position
        team = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_view, parent, false);
        }

        // Lookup view for data population
        teamName = (TextView) convertView.findViewById(R.id.textView);

        // Populate the data into the template view using the data object
        teamName.setText(team.teamName);

        btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
        btnEdit = (Button) convertView.findViewById(R.id.btnEdit);

        //btnDelete.setOnClickListener(new CustomOnClickListener(callback, position));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(teamName, position);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newint = new Intent(getContext(), AddPlayersToTeam.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newint.putExtra("TeamName", (teamName.getText()));
                newint.putExtra("TeamID", team.TeamID);
                getContext().startActivity(newint);

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    public void showDialog(final View view, final int position) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        TextView v = (TextView) view;
        alertDialogBuilder.setMessage("Are you sure Delete the team  " + getItem(position).teamName);

        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //manageTeamActvity.deleteTeam(team.TeamID);
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
}





