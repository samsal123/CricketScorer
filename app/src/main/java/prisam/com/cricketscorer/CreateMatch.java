package prisam.com.cricketscorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateMatch extends AppCompatActivity {

    private TextView timeDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);

        intializeCOntrols();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy   HH:mm ");
        timeDate.setText( sdf.format( new Date() ));

    }


    private void intializeCOntrols(){

        timeDate = (TextView)findViewById(R.id.editdatetime);

    }
}
