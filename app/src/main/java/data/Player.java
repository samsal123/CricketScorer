package data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Prince on 2/07/2016.
 */
public class Player implements Serializable {

    @DatabaseField(generatedId = true)
    public int playerId;

    @DatabaseField
    public String firstName;

    @DatabaseField
    public String lastName;

    //@DatabaseField
    //public int playerTeamID;

    @DatabaseField
    public boolean playing;

    // Foreign key defined to hold associations for players with team
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel=3)
    public Team team;

    public Player() {
    }

    public Player( String firstName, String lastName, Team team) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.team = team;
        //this.playerTeamID = playerTeamID;
    }
}
