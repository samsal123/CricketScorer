package data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Prince on 2/07/2016.
 */
public class Team implements Serializable {

    @DatabaseField(generatedId = true)
    public int TeamID;

    @DatabaseField
    public String teamName;

//    @DatabaseField
//    public boolean toss;
//
//    @DatabaseField
//    public boolean isBat;

    public Team() {
    }

    public Team(String teamName) {
       this.teamName =teamName;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Team) {
            if (teamName.equals(((Team) o).teamName)) {
                return true;
            }
        }
        return false;
    }

}
