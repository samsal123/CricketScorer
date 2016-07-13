package Helpers;

import java.util.ArrayList;
import java.util.List;


import data.Team;
import prisam.com.cricketscorer.AddPlayersToTeamActivity;

/**
 * Created by Prince on 2/07/2016.
 */
public class Utilities {

    public static ArrayList getData(List<Team> teams) {
        ArrayList result = new ArrayList();

        for (int i = 0; i < teams.size(); i++) {
            result.add(teams.get(i).teamName);
        }
        return result;
    }

    public static void displayPlayers() {

        AddPlayersToTeamActivity adp = new AddPlayersToTeamActivity();

        adp.showPlayers();
    }

}
