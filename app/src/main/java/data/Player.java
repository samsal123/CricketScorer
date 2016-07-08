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

    @DatabaseField
    public int playerTeamID;
    @DatabaseField
    public boolean playing;

    public Player( String firstName,String lastName,int playerTeamID) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.playerTeamID = playerTeamID;

    }


    public Player() {
    }
}
