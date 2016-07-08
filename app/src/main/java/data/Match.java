package data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Prince on 8/07/2016.
 */
public class Match implements Serializable {

    @DatabaseField (generatedId = true)
    public int matchID;
    @DatabaseField
    public String matchDateTime;
    @DatabaseField
    public String matchVenue;
    @DatabaseField
    public int homeTeamID;
    @DatabaseField
    public int awayTeamID;
    @DatabaseField
    public int matchOvers;
    @DatabaseField
    public int matchInns;
    @DatabaseField
    public int matchDays;

    public Match(String matchDateTime, String matchVenue, int homeTeamID, int awayTeamID, int matchOvers, int matchInns, int matchDays) {
        this.matchDateTime = matchDateTime;
        this.matchVenue = matchVenue;
        this.homeTeamID = homeTeamID;
        this.awayTeamID = awayTeamID;
        this.matchOvers = matchOvers;
        this.matchInns = matchInns;
        this.matchDays = matchDays;
    }

    public Match() {
    }
}
