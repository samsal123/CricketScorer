package prisam.com.cricketscorer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.Team;

/**
 * Created by Prince on 2/07/2016.
 */
public class Utility {

    public static ArrayList getData(List<Team> teams){
        ArrayList result = new ArrayList();

        for(int i =0;i<teams.size();i++)
        {
            result.add(teams.get(i).teamName);
        }
        return result;
    }

    public static void displayPlayers(){

        AddPlayersToTeam adp = new AddPlayersToTeam();

        adp.showPlayers();
    }
}
