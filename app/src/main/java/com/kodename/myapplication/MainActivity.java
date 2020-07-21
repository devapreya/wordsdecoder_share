package com.kodename.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {
    private Button[] cell;
    private int count = 0, player_on = 0,firstTeam = 1, redHigh, blueHigh, seeds, timeInNum,seconds,minutes;
    private long START_TIME_IN_MILLIS, mTimeLeftInMillis;
    private TextView blueScore, redScore, mText;
    private ArrayList<Integer> colorList, colorPressed;
    private ArrayList<String> list;
    private Button status,timer, timerStatus;
    private CountDownTimer countDownTimer;
    private Boolean counterIsOn = false, isPaused = false,checker_ten = false,checker_thirty = false,checker_five=false,checker_start=false;
    private Toast mToastToShow ;
    private String timeEntered = "1";
    private LinearLayout layout;
    private GoogleApiClient client;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        timeInNum = Integer.parseInt(timeEntered);
        START_TIME_IN_MILLIS = timeInNum * 60000;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        // Log.d("TIME", "IS: " + timeInNum);
        Random ran = new Random();
        seeds = ran.nextInt(9999 - 1) + 1;
        Scanner s = new Scanner(getResources().openRawResource(R.raw.wordlist));
        list = new ArrayList<>();
        while (s.hasNextLine()) {
            list.add(s.nextLine());
        }
        s.close();
        cell = new Button[25];
        cell[0] = findViewById(R.id.button00);
        cell[1] = findViewById(R.id.button01);
        cell[2] = findViewById(R.id.button02);
        cell[3] = findViewById(R.id.button03);
        cell[4] = findViewById(R.id.button04);
        cell[5] = findViewById(R.id.button10);
        cell[6] = findViewById(R.id.button11);
        cell[7] = findViewById(R.id.button12);
        cell[8] = findViewById(R.id.button13);
        cell[9] = findViewById(R.id.button14);
        cell[10] = findViewById(R.id.button20);
        cell[11] = findViewById(R.id.button21);
        cell[12] = findViewById(R.id.button22);
        cell[13] = findViewById(R.id.button23);
        cell[14] = findViewById(R.id.button24);
        cell[15] = findViewById(R.id.button30);
        cell[16] = findViewById(R.id.button31);
        cell[17] = findViewById(R.id.button32);
        cell[18] = findViewById(R.id.button33);
        cell[19] = findViewById(R.id.button34);
        cell[20] = findViewById(R.id.button40);
        cell[21] = findViewById(R.id.button41);
        cell[22] = findViewById(R.id.button42);
        cell[23] = findViewById(R.id.button43);
        cell[24] = findViewById(R.id.button44);
        layout = findViewById(R.id.mainActivity);
        timerStatus = findViewById(R.id.start_pause);
        status = findViewById(R.id.status);
        defineButtons();
        words(seeds);
        updateTimer();
        updateButtons();
        mToastToShow = Toast.makeText(this, "Time Up!", Toast.LENGTH_SHORT);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        findViewById(R.id.players).setBackgroundColor(getApplication().getResources().getColor(R.color.gray));
        if (savedInstanceState != null) {
            mTimeLeftInMillis = savedInstanceState.getLong("millisLeft", START_TIME_IN_MILLIS);
        }
       client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        // ATTENTION: This was auto-generated to handle app links.
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

    }


    private void defineButtons() {
        findViewById(R.id.players).setOnClickListener(buttonClickListener);
        findViewById(R.id.spyMaster).setOnClickListener(buttonClickListener);
        findViewById(R.id.endTurn).setOnClickListener(buttonClickListener);
        cell[0].setOnClickListener(buttonClickListener);
        cell[1].setOnClickListener(buttonClickListener);
        cell[2].setOnClickListener(buttonClickListener);
        cell[3].setOnClickListener(buttonClickListener);
        cell[4].setOnClickListener(buttonClickListener);
        cell[5].setOnClickListener(buttonClickListener);
        cell[6].setOnClickListener(buttonClickListener);
        cell[7].setOnClickListener(buttonClickListener);
        cell[8].setOnClickListener(buttonClickListener);
        cell[9].setOnClickListener(buttonClickListener);
        cell[10].setOnClickListener(buttonClickListener);
        cell[11].setOnClickListener(buttonClickListener);
        cell[12].setOnClickListener(buttonClickListener);
        cell[13].setOnClickListener(buttonClickListener);
        cell[14].setOnClickListener(buttonClickListener);
        cell[15].setOnClickListener(buttonClickListener);
        cell[16].setOnClickListener(buttonClickListener);
        cell[17].setOnClickListener(buttonClickListener);
        cell[18].setOnClickListener(buttonClickListener);
        cell[19].setOnClickListener(buttonClickListener);
        cell[20].setOnClickListener(buttonClickListener);
        cell[21].setOnClickListener(buttonClickListener);
        cell[22].setOnClickListener(buttonClickListener);
        cell[23].setOnClickListener(buttonClickListener);
        cell[24].setOnClickListener(buttonClickListener);
        findViewById(R.id.reset).setOnClickListener(buttonClickListener);
        findViewById(R.id.start_pause).setOnClickListener(buttonClickListener);
    }


    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.players:
                    player_on = 1;
                    findViewById(R.id.endTurn).setEnabled(true);
                    findViewById(R.id.spyMaster).setEnabled(true);
                    findViewById(R.id.players).setEnabled(false);
                    findViewById(R.id.spyMaster).setBackgroundResource(0);
                    findViewById(R.id.players).setBackgroundColor(getApplication().getResources().getColor(R.color.gray));
                    for (int i = 0; i < 25; i++) {
                        if (colorPressed.contains(i)) {
                            cell[i].setBackgroundColor(colorList.get(i));
                            cell[i].setEnabled(false);
                        } else {
                            cell[i].setBackgroundColor(Color.parseColor("#FFFFFF"));
                            cell[i].setEnabled(true);
                        }
                    }
                    resetTimer();
                    break;
                case R.id.spyMaster:
                    findViewById(R.id.players).setEnabled(true);
                    findViewById(R.id.spyMaster).setEnabled(false);
                    findViewById(R.id.players).setBackgroundResource(0);
                    findViewById(R.id.spyMaster).setBackgroundColor(getApplication().getResources().getColor(R.color.gray));
                    spyMasterView();
                    if (count == 1 || count == 2) {
                        resetTimerSM();
                    } else resetTimer();
                    break;

                case R.id.endTurn:
                    if(status.getText()=="Blue's turn"){
                        redTurn();
                    } else {
                        blueTurn();
                    }
                    if (counterIsOn) {
                        resetTimer();
                    }
                    break;
                case R.id.start_pause:
                    if (counterIsOn) {
                        pauseTimer();
                    } else {
                        startTimer();
                    }
                    break;
                case R.id.reset:
                    if (player_on == 2 && (count == 1 || count == 2)) {
                        resetTimerSM();
                    }
                    else {
                        resetTimer();
                    }
                    updateTimer();
                    break;
                case R.id.button00:
                    colorPressed(0);
                    break;
                case R.id.button01:
                    colorPressed(1);
                    break;
                case R.id.button02:
                    colorPressed(2);
                    break;
                case R.id.button03:
                    colorPressed(3);
                    break;
                case R.id.button04:
                    colorPressed(4);
                    break;
                case R.id.button10:
                    colorPressed(5);
                    break;
                case R.id.button11:
                    colorPressed(6);
                    break;
                case R.id.button12:
                    colorPressed(7);
                    break;
                case R.id.button13:
                    colorPressed(8);
                    break;
                case R.id.button14:
                    colorPressed(9);
                    break;
                case R.id.button20:
                    colorPressed(10);
                    break;
                case R.id.button21:
                    colorPressed(11);
                    break;
                case R.id.button22:
                    colorPressed(12);
                    break;
                case R.id.button23:
                    colorPressed(13);
                    break;
                case R.id.button24:
                    colorPressed(14);
                    break;
                case R.id.button30:
                    colorPressed(15);
                    break;
                case R.id.button31:
                    colorPressed(16);
                    break;
                case R.id.button32:
                    colorPressed(17);
                    break;
                case R.id.button33:
                    colorPressed(18);
                    break;
                case R.id.button34:
                    colorPressed(19);
                    break;
                case R.id.button40:
                    colorPressed(20);
                    break;
                case R.id.button41:
                    colorPressed(21);
                    break;
                case R.id.button42:
                    colorPressed(22);
                    break;
                case R.id.button43:
                    colorPressed(23);
                    break;
                case R.id.button44:
                    colorPressed(24);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.set_timer:
                View view = (LayoutInflater.from(MainActivity.this)).inflate(R.layout.set_timer,null);
                String[] items = new String[]{"1", "2", "3","4","5","6","7","8"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
                final Spinner dropdown = view.findViewById(R.id.timeSel);
                dropdown.setAdapter(adapter);
                builder.setCancelable(true).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        timeEntered = dropdown.getSelectedItem().toString();
                        timeInNum = Integer.parseInt(timeEntered);
                        START_TIME_IN_MILLIS = timeInNum * 60000;
                        if (player_on == 2 && (count == 1 || count == 2)){
                            resetTimerSM();}else {
                            resetTimer();}
                    }
                });
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.next:
                newGame();
                return true;
            case R.id.rules_text:
                View v = (LayoutInflater.from(MainActivity.this)).inflate(R.layout.rules,null);
                TextView mTextView=v.findViewById(R.id.rules_text);
                String mText = "";
                try{
                    InputStream is = getResources().openRawResource(R.raw.rules_new);
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    mText = new String (buffer);
                }catch (IOException ex) {
                    ex.printStackTrace();}
                mTextView.setText(mText);
                mTextView.setTextColor(getResources().getColor(android.R.color.white));
                mTextView.setBackgroundColor(getResources().getColor(android.R.color.black));
                mTextView.setMovementMethod(new ScrollingMovementMethod());
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("HOW TO PLAY");
                alertDialog.setView(v);
                alertDialog.setPositiveButton("OK", null);
                AlertDialog alert = alertDialog.create();
                alert.show();
                return true;
            case R.id.action_fav:
                startActivity(new Intent("android.settings.CAST_SETTINGS"));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
  public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://www.amazon.com"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    private void words(long seeds) {

        Random r = new Random();
        colorPressed = new ArrayList<>();
        r.setSeed(seeds);
        ArrayList<String> rlist = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            if (player_on == 0) {
                cell[i].setEnabled(true);
            }
            String rword = list.get(r.nextInt(list.size()));
            rlist.add(i, rword);
            list.remove(rword);
            cell[i].setText(rlist.get(i));
        }

        setColor();
        listOfColors();
    }

    @SuppressLint("SetTextI18n")
    private void colorPressed(int z)
    {
        String redWins ="RED WINS!!",blueWins = "BLUE WINS!!";
        cell[z].setBackgroundColor(colorList.get(z));
        cell[z].setEnabled(false);
        colorPressed.add(z);
        if (colorList.get(z) == -13388315) {
            blueHigh = blueHigh - 1;
            blueScore.setText("" + blueHigh);
            if(status.getText()=="Red's turn") {
                blueTurn();
                isCounterOn();
            }
        }
        if (colorList.get(z) == -48060) {
            redHigh = redHigh - 1;
            redScore.setText("" + redHigh);
            if(status.getText()=="Blue's turn") {
                redTurn();
                isCounterOn();
            }
        }
        if (colorList.get(z) == -524798) {
            if(status.getText()=="Blue's turn") {
                redTurn();
            } else {
                blueTurn();
            }
            isCounterOn();
        }
        if (colorList.get(z) == -3329330) {
            if(status.getText()=="Red's turn"){
                gameOver(blueWins);
            } else {
                gameOver(redWins);
            }
        }
        if (redHigh == 0) {
            gameOver(redWins);

        }
        if (blueHigh == 0) {
            gameOver(blueWins); }
    }

    private void listOfColors() {
        colorList = new ArrayList<>();
        Random ran = new Random();
        ran.setSeed(seeds);
        for (int b = 0; b < blueHigh; b++) {
            colorList.add(-13388315);
        }
        for (int red = 0; red < redHigh; red++) {
            colorList.add(-48060);
        }
        colorList.add(-3329330);
        for (int y = 0; y < 7; y++) {
            colorList.add(-524798);
        }
        Collections.shuffle(colorList, ran);
        Log.d("MYINT", "value: " + colorList.get(0));
    }

    private void setColor() {
        // Random ra = new Random();
        // String[] colorFix = new String[]{"red", "blue"};
        // String fixedColor = colorFix[ra.nextInt(colorFix.length)];
        //  Log.d("MYSTRING", "value: " + fixedColor);
        redScore = findViewById(R.id.redNum);
        blueScore = findViewById(R.id.blueNum);
        if (firstTeam%2==0) {
            redHigh = 9;
            blueHigh = 8;
            redTurn();
            redScore.setText(""+redHigh);
            blueScore.setText(""+blueHigh);
        } else {
            redHigh = 8;
            blueHigh = 9;
            blueTurn();
            redScore.setText(""+redHigh);
            blueScore.setText(""+blueHigh);
        }

    }
    private void redTurn()
    {
        status.setText("Red's turn");
        layout.setBackgroundResource(R.drawable.red_smoke);
    }
    private void blueTurn()
    {
        status.setText("Blue's turn");
        layout.setBackgroundResource(R.drawable.blue_smoke);
    }
    private void spyMasterView() {
        player_on = 2;
        findViewById(R.id.endTurn).setEnabled(false);
        count = count + 1;
        for (int i = 0; i < 25; i++) {
            cell[i].setBackgroundColor(colorList.get(i));
            cell[i].setEnabled(false);
        }
    }

    private void updateTimer() {
        minutes = (int)((mTimeLeftInMillis / 1000) / 60);
        seconds = (int)((mTimeLeftInMillis / 1000) % 60);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer = findViewById(R.id.timer);
        timer.setText(timeLeftFormatted);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                counterIsOn = true;
                mTimeLeftInMillis = (int) millisUntilFinished;
                if ((mTimeLeftInMillis == (int) millisUntilFinished)&&(!checker_start))
                    new Runnable(){
                        public void run() {
                            playSound(R.raw.timer_start);
                            checker_start = true;
                        }

                    }.run();

                if(minutes == 0){
                    if ((seconds == 30)&&(!checker_thirty))
                        new Runnable(){
                            public void run() {
                                playSound(R.raw.thirty_sec);
                                checker_thirty = true;}

                        }.run();

                    if ((seconds == 10)&&(!checker_ten))
                        new Runnable(){
                            public void run() {
                                playSound(R.raw.ten_sec);
                                checker_ten = true;}

                        }.run();
                    if ((seconds == 5)&&(!checker_five))
                        new Runnable(){
                            public void run() {
                                playSound(R.raw.five_sec);
                                checker_five = true;}

                        }.run();}
                updateTimer();
                updateButtons();

            }
            @Override
            public void onFinish() {
                playSound(R.raw.push_sound);
                playSound(R.raw.timer_end);
                mToastToShow.show();
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                if (player_on == 2 && (count == 1 || count == 2)) {
                    resetTimerSM();
                } else{
                    resetTimer();
                }
                checker_ten = false;
                checker_thirty = false;
                checker_five=false;
                checker_start=false;
            }
        }.start();
    }
    public void playSound(int sound) {

        MediaPlayer mp = MediaPlayer.create(getBaseContext(), sound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }

        });
        mp.setLooping(false);
        mp.start();}

    private void pauseTimer() {
        countDownTimer.cancel();
        counterIsOn = false;
        isPaused = true;
        updateButtons();
    }


    private void resetTimer() {
        if (counterIsOn) {
            countDownTimer.cancel();
        }
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        counterIsOn =false;
        updateTimer();
        updateButtons();
        checker_ten = false;
        checker_thirty = false;
        checker_five=false;
        checker_start=false;

    }

    private void resetTimerSM() {
        if (counterIsOn) {
            countDownTimer.cancel();
        }
        mTimeLeftInMillis = START_TIME_IN_MILLIS + 120000;
        counterIsOn = false;
        updateTimer();
        updateButtons();
        checker_ten = false;
        checker_thirty = false;
        checker_five=false;
        checker_start=false;
    }
    private void updateButtons() {
        if (counterIsOn) {
            timerStatus.setText("Pause");
        } else if(isPaused) {
            timerStatus.setText("Resume");
            isPaused = false;}
        else
            timerStatus.setText("Start");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("millisLeft", mTimeLeftInMillis);
        if ((countDownTimer != null)&&(counterIsOn)) {
            countDownTimer.cancel();
            counterIsOn = false;
            isPaused = true;
            updateTimer();
            updateButtons();
        }
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft", START_TIME_IN_MILLIS);
    }

    private void newGame(){
        if(list.isEmpty())
        {Scanner s = new Scanner(getResources().openRawResource(R.raw.wordlist));
            list = new ArrayList<>();
            while (s.hasNextLine()) {
                list.add(s.nextLine());
            }
            s.close();}
        firstTeam++;
        findViewById(R.id.endTurn).setEnabled(true);
        timerStatus.setEnabled(true);
        findViewById(R.id.spyMaster).setEnabled(true);
        findViewById(R.id.players).setEnabled(false);
        findViewById(R.id.spyMaster).setBackgroundResource(0);
        findViewById(R.id.players).setBackgroundColor(getApplication().getResources().getColor(R.color.gray));
        timer.setEnabled(true);
        findViewById(R.id.reset).setEnabled(true);
        seeds = seeds + 1;
        count = 0;
        words(seeds);
        for (int i = 0; i < 25; i++) {
            cell[i].setBackgroundColor(Color.parseColor("#FFFFFF"));
            cell[i].setEnabled(true);
            resetTimer();
        }

    }
    private void isCounterOn() {
        if (counterIsOn) {
            resetTimer();
        }
    }

    private void gameOver(String winner) {
        for (int j = 0; j < 25; j++) {
            cell[j].setEnabled(false);

        }
        timerStatus.setEnabled(false);
        timer.setEnabled(false);
        findViewById(R.id.spyMaster).setEnabled(true);
        findViewById(R.id.reset).setEnabled(false);

        String msg = "Game Ended: "+winner ;
        new AlertDialog.Builder(this).setTitle("CodeNames").
                setMessage(msg).setNeutralButton("NEXT GAME",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newGame();
            }
        }).setPositiveButton("REVEAL COLORS",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spyMasterView();
            }
        }).setNegativeButton("CLOSE",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }
}

