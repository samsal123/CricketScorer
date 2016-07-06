package prisam.com.cricketscorer;
import android.app.Activity;
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
import com.j256.ormlite.stmt.DeleteBuilder;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DataBaseHelper;
import data.Team;

/**
 * Created by z3004590 on 5/07/2016.
 */
public class TeamAdapter extends ArrayAdapter<Team> {

    public TeamAdapter(Context context, ArrayList<Team> teams) {
        super(context, 0, teams);
    }

    Button btnDelete;
    Button btnEdit;
    Team team;

    private DataBaseHelper dataBaseHelper = null;
    public Dao<Team, Integer> teamDao = null;
    ManageTeamActvity manageTeamActvity = new ManageTeamActvity();


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        manageTeamActvity.createTeamDao();
        // Get the data item for this position
         team = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_view, parent, false);
        }

        // Lookup view for data population
        final TextView teamName = (TextView) convertView.findViewById(R.id.textView);

        // Populate the data into the template view using the data object
        teamName.setText(team.teamName);

        btnDelete = (Button)convertView.findViewById(R.id.btnDelete);
        btnEdit = (Button)convertView.findViewById(R.id.btnEdit);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               showDialog(teamName);


            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newint=new Intent(getContext(),AddPlayersToTeam.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                newint.putExtra("TeamName",(teamName.getText()));
                newint.putExtra ("TeamID",team.TeamID);

                getContext().startActivity(newint);



            }
        });



        // Return the completed view to render on screen
        return convertView;}

    public void showDialog(final View view){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        TextView v= (TextView)view;
        alertDialogBuilder.setMessage("Are you sure Delete the team  " +v.getText().toString());

        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                manageTeamActvity.deleteTeam(team.TeamID);





            }
        });
        alertDialogBuilder.setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}





