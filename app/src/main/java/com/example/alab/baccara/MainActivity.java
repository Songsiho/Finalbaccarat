package com.example.alab.baccara;

import android.support.v7.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.text.InputType;
import android.widget.Toast;

import com.example.alab.baccara.R;


public class MainActivity extends AppCompatActivity {

    private int capital, base, win, winnings = 0;
    private int bet = 0;
    private boolean result = false;
    private boolean end = true;
    private boolean userslect = true;
    private int usecase,count = 0;

    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCapital = (Button) findViewById(R.id.button_capital);
        btnCapital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inPutCapital();
            }
        });

        Button btnStart = (Button) findViewById(R.id.button_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (usecase) {
                    case 1:
                        if (isResult()) {
                            setResult(false);
                            setCapital(getCapital() + (10 * getBase()));
                            setWinnings(getWinnings() + (10 * getBase()));
                        }
                        //lose
                        if (getCapital() >= 1000 * getBase()) {
                            Toast.makeText(MainActivity.this, "2 win", Toast.LENGTH_SHORT).show();
                        }
                        //win
                        count= 0;
                        displayTextWinnings(getWinnings());
                        break;
                    default:
                        break;
                }
                baccaraAlgorithm();
            }
        });
    }

    //바카라 알고리즘
    public void baccaraAlgorithm() {
        //all-in
        if (getBet() >= 256 * getBase()) {
            //All-in
            finalbetMessagebox();

            if (isResult()) {
                setResult(false);
                setBet(getBase());
            } else {
                finish();
            }
            usecase = 0;
            return;
        }
        //if winnings = base * 5
        if (getWin() == 4) {
            fivebetMessagebox();
            setWin(0);


            usecase=1;

            return;
        }



        if (isResult()) {
            winAlgorithm();
        }

        //lose
        else{
            if(count == 0) {
                betMessagebox();
                count++;
                usecase=2;
                return;
            }
            loseAlgorithm();

        }
        betMessagebox();
        //winnings twice -> end

        //2. 배팅 금액 띄우기 -> 메세지 박스

        usecase=2;
        //3. 결과 입력 받기 -> 메세지 박스

        //win

        //4. if(result) -> win lose 메소드
        //}
    }//while end message ->

    //초기 Capital 입력받기
    protected void inPutCapital() {
        capitalMessagebox();
    }

    //Display Capital
    public void displayTextCapital(int capital) {
        TextView capitalView = (TextView) findViewById(R.id.text_capital);
        capitalView.setText(String.valueOf(capital));
    }

    //Display Winnings
    public void displayTextWinnings(int winnings) {
        TextView winningsView = (TextView) findViewById(R.id.text_winnigs);
        winningsView.setText(String.valueOf(winnings));
    }

    public void fivebetMessagebox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Bet " + (getBase() * 5)).setTitle("Betting");

        //AlertDialog dialog = builder.create();
        builder.setPositiveButton("Win", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                //finalAlgorithm();
                setResult(true);
            }
        });
        builder.setNegativeButton("Lose", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                setResult(false);
            }
        });
        // Set other dialog properties

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    //Betting message

    /*
    public void betMessagebox() {
        runOnUiThread(runnable = new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //customize alert dialog to allow desired input
                builder.setTitle("Bet").setMessage("Bet " + getBet());
                ;

                // builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        setResult(true);
                        synchronized (runnable) {
                            runnable.notify();
                        }
                        Toast.makeText(MainActivity.this, "I am in after yes runnable notify", Toast.LENGTH_LONG).show();
                        // return;
                    }
                });

                builder.setNegativeButton("Lose", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        setResult(false);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                Toast.makeText(MainActivity.this, "I am in after show alert", Toast.LENGTH_LONG).show();
            }
        });

        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {

        }
    }
    */

    public void betMessagebox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Bet " + getBet()).setTitle("Betting");

        //AlertDialog dialog = builder.create();
        builder.setPositiveButton("Win", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button\
                setResult(true);
            }
        });
        builder.setNegativeButton("Lose", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                setResult(false);
            }
        });
        // Set other dialog properties

        //stop

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void capitalMessagebox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("How much money do you have?").setTitle("RICH");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                setCapital(Integer.parseInt(input.getText().toString()));
                displayTextCapital(getCapital());
                setBase(getCapital() / 500);
                setBet(getBase());
            }
        });
        AlertDialog dialog = builder.create();

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    public void finalbetMessagebox() {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Bet All-in! " + "Good Luck").setTitle("Betting");


        //AlertDialog dialog = builder.create();
        builder.setPositiveButton("Win", new DialogInterface.OnClickListener()

                {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button\
                        setResult(true);
                    }
                }

        );
        builder.setNegativeButton("Lose", new DialogInterface.OnClickListener()

                {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        setResult(false);
                    }
                }

        );
        // Set other dialog properties

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void winAlgorithm() {
        runOnUiThread(runnable = new Runnable() {
            @Override
            public void run() {
                setWin(getWin()+1);
                setBet(getBase());
                setResult(false);
            }
        });
        /*
        try {
            synchronized (this) {
                wait(5000);
            }
        } catch (InterruptedException e) {

        }*/

    }

    public void loseAlgorithm() {
        setBet(getBet() * 2);
        //8 lose
    }


    //setter
    public void setCapital(int capital) {
        this.capital = capital;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    //getter
    public int getCapital() {
        return capital;
    }

    public int getBase() {
        return base;
    }

    public int getBet() {
        return bet;
    }

    public boolean isResult() {
        return result;
    }

    public boolean isUserslect() {
        return userslect;
    }

    public void setUserslect(boolean userslect) {
        this.userslect = userslect;
    }

    public int getWinnings() {
        return winnings;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }

}