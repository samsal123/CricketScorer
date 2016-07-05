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

    //@DatabaseField(columnName = "LastName")
   // public String lastName;

    //@DatabaseField(canBeNull = true,foreign = true,columnName = "TeamID")
    //public int playerTeamID;

    public Player( String firstName) {
       // this.lastName = lastName;
        this.firstName = firstName;
    }

    public Player() {
    }
}
