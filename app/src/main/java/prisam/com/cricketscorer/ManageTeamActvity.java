package prisam.com.cricketscorer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import data.DataBaseHelper;
import data.Team;


public class ManageTeamActvity extends AppCompatActivity {

    private  DataBaseHelper dataBaseHelper =null;
    private Dao<Team,Integer> teamDao = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_team_actvity);

        final EditText teamName = (EditText)findViewById(R.id.editText);
        Button addTeam = (Button)findViewById(R.id.button);
        //TextView teamList = (TextView)findViewById(R.id.textView);
        Button show = (Button)findViewById(R.id.button2);

        addTeam.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {

                String team = teamName.getText().toString();


                try {
                    teamDao = getHelper().getTeamDao();
                    teamDao.create(new Team(team));
                } catch (SQLException e) {
                    e.printStackTrace();
                }



            }


        });

        show.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {

                //TextView teamList = (TextView)findViewById(R.id.textView);
                Team result = null;

                try {
                    teamDao = getHelper().getTeamDao();
                    List<Team> teams = teamDao.queryForAll();
                    //teamList.setText(Utility.getData(teams));

                    ArrayAdapter a = new ArrayAdapter(ManageTeamActvity.this, android.R.layout.simple_list_item_1,Utility.getData(teams));

                    ListView l1 = (ListView)findViewById(R.id.listView);

                    l1.setAdapter(a);

                } catch (SQLException e) {
                    e.printStackTrace();
                }



            }


        });
    }

    private DataBaseHelper getHelper()
    {
        if(dataBaseHelper==null)
        {
            dataBaseHelper = OpenHelperManager.getHelper(this,DataBaseHelper.class);
        }

        return dataBaseHelper;
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(dataBaseHelper!=null)
        {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }



}
