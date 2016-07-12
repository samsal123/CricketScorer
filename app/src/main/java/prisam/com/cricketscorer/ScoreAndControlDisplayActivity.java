package prisam.com.cricketscorer;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;


public class ScoreAndControlDisplayActivity extends AppCompatActivity {

    Button btnOn, btnOff, btnDis;
    SeekBar brightness;
    TextView lumn;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    boolean isStriker = false;


    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceListActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device


        //view of the ledControl
        setContentView(R.layout.activity_score_and_control_display);


        new ConnectBT().execute(); //Call the class to connect

        TextView striker = (TextView) findViewById(R.id.striker);
        TextView nonStriker = (TextView) findViewById(R.id.nonStriker);

        striker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)

            {
                isStriker = true;
                selectBatsmen();

            }


        });
        nonStriker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)

            {
                selectBatsmen();

            }


        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void selectBatsmen() {

        ListView s1 = (ListView) findViewById(R.id.listView2);
        s1.setVisibility(View.VISIBLE);


    }

    int totalRuns = 0;
    int totalOvers = 0;
    int ballCount = 0;
    int wicketsDown = 0;
    int strikerRuns = 0;
    int stikerBalls = 0;
    int nonStrikerRuns = 0;
    int nonStrikerBalls = 0;
    boolean swap = true;

    public void btnClick(View v) {
        Button b1 = (Button) v;
        int whichButton = v.getId();

        switch (whichButton) {
            case R.id.btn1Run:
                totalRuns++;
                countBall();
                if (swap) {
                    strikerRuns++;
                    stikerBalls++;
                    swap = !swap;
                } else {
                    nonStrikerRuns++;
                    nonStrikerBalls++;
                    swap = !swap;

                }
                break;
            case R.id.btn2Run:
                totalRuns += 2;
                if (swap) {
                    strikerRuns += 2;
                    stikerBalls++;

                } else {
                    nonStrikerRuns += 2;
                    nonStrikerBalls++;


                }
                countBall();
                break;
            case R.id.btn3Run:
                totalRuns += 3;
                if (swap) {
                    strikerRuns += 3;
                    stikerBalls++;
                    swap = !swap;
                } else {
                    nonStrikerRuns += 3;
                    nonStrikerBalls++;
                    swap = !swap;

                }
                countBall();
                break;
            case R.id.btn4Run:
                totalRuns += 4;
                if (swap) {
                    strikerRuns += 4;
                    stikerBalls++;

                } else {
                    nonStrikerRuns += 4;
                    nonStrikerBalls++;


                }
                countBall();
                break;
            case R.id.btn5Run:
                totalRuns += 5;
                break;
            case R.id.btn6Run:
                totalRuns += 6;
                if (swap) {
                    strikerRuns += 6;
                    stikerBalls++;

                } else {
                    nonStrikerRuns += 6;
                    nonStrikerBalls++;


                }
                countBall();
                break;
            case R.id.btnMinus1ball:
                reduceBall();

                break;
            case R.id.btnAdd1ball:
                countBall();

                break;
            case R.id.btnDotBall:
                countBall();

                break;
            case R.id.btnWicket:
                if (wicketsDown < 10) {
                    wicketsDown++;
                    countBall();

                }
                break;
            case R.id.btnMinus1w:
                if (wicketsDown != 0) {
                    wicketsDown--;
                }
                break;
            case R.id.btnAdd1w:
                if (wicketsDown < 10) {
                    wicketsDown++;
                }
                break;
            case R.id.btnAdd1run:
                totalRuns++;
                break;
            case R.id.btnMinus1run:
                if (totalRuns != 0) {
                    totalRuns--;

                }
                break;
            case R.id.button4: {
                Disconnect();
            }
            break;


        }
        displayAll();

    }


    String displayTotal = "";
    String dispOver = "";

    public void displayAll() {
        TextView displayRuns = (TextView) findViewById(R.id.txtTotal);
        displayRuns.setText(totalRuns + "/" + wicketsDown);
        TextView displayOver = (TextView) findViewById(R.id.txtOvers);
        displayTotal = totalRuns + "/" + wicketsDown + " ";
        displayOver.setText(totalOvers + "." + ballCount);
        TextView strikerRunsDisplay = (TextView) findViewById(R.id.txtStrikerRuns);
        strikerRunsDisplay.setText(strikerRuns + "(" + stikerBalls + ")");
        TextView nonStrikerRunsDisplay = (TextView) findViewById(R.id.txtNonStrikerRuns);
        nonStrikerRunsDisplay.setText(nonStrikerRuns + "(" + nonStrikerBalls + ")");
        dispOver = totalOvers + "." + ballCount;

        TextView striker = (TextView) findViewById(R.id.striker);
        TextView nonStriker = (TextView) findViewById(R.id.nonStriker);

        dispOver += "#" + striker.getText().toString() + " " + strikerRunsDisplay.getText().toString() + "  " +
                "" + nonStriker.getText().toString() + " " + nonStrikerRunsDisplay.getText().toString();

        sendTotal();
        sendOvers(dispOver);


    }

    public void countBall() {


        ballCount++;


        if (ballCount == 6) {
            totalOvers++;
            ballCount = 0;
            swap = !swap;


        }


    }

    public void reduceBall() {
        if (ballCount != 0) {
            ballCount--;
        } else {
            totalOvers--;
            ballCount = 5;
        }

    }


    private void Disconnect() {
        if (btSocket != null) //If the btSocket is busy
        {
            try {
                btSocket.close(); //close connection
            } catch (IOException e) {
                msg("Error");
            }
        }
        finish(); //return to the first layout

    }

    private void sendOvers(String disp) {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write(disp.getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void sendTotal() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write(displayTotal.toString().getBytes());


            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ScoreAndControlDisplayActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
