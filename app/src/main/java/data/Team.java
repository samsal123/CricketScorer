package data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Prince on 2/07/2016.
 */
public class Team implements Serializable {

    public int getTeamID() {
        return TeamID;
    }

    public void setTeamID(int teamID) {
        TeamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @DatabaseField(generatedId = true)
    public int TeamID;

    @DatabaseField
    public String teamName;

    public Team(String teamName) {

       this.teamName =teamName;


    }

    public Team() {
    }

}
