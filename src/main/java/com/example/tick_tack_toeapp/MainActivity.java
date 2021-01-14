package com.example.tick_tack_toeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // All variables
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] button = new Button[9];
    private Button resetGame;

    private  int playerOneScoreCount, playerTwoScoreCount, roundCount;
    boolean activeplayer;

    // p1 => 0
    // p2 => 1
    // empty => 2

    int[] gamestate = {2,2,2,2,2,2,2,2,2};

    int[][] winningPosition = {
            {0,1,2}, {3,4,5}, {6,7,8}, // rows
            {0,3,6}, {1,4,7}, {2,5,8}, // columns
            {0,4,8}, {2,4,6} // cross
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore =(TextView) findViewById(R.id.playerOneScore);
        playerTwoScore =(TextView) findViewById(R.id.playerTwoScore);
        playerStatus =(TextView)  findViewById(R.id.playerStatus);

        resetGame =(Button) findViewById(R.id.resetGame);

        for(int i=0; i< button.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            button[i] =(Button) findViewById(resourceID);
            button[i].setOnClickListener(this);
        }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activeplayer = true;
    }

    @Override
    public void onClick(View v) {
       // Log.i("test","button is clicked!");
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId()); // btn_2
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length())); // 2
// players turn to play
        if(activeplayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gamestate[gameStatePointer] = 0;
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gamestate[gameStatePointer] = 1;
        }
        roundCount++;


        if(checkwinner()){
            // player one win
            if(activeplayer){
                playerOneScoreCount ++;
                updatePlayerScore();
                Toast.makeText(this,"Player One Won!",Toast.LENGTH_SHORT).show();
                playAgain();
                // player two win
            } else {
                playerTwoScoreCount ++;
                updatePlayerScore();
                Toast.makeText(this,"Player Two Won!",Toast.LENGTH_SHORT).show();
                playAgain();
            }

            // no winner
        } else if(roundCount == 9){
            playAgain();
            Toast.makeText(this,"No Winner!",Toast.LENGTH_SHORT).show();

        } else {
            activeplayer = !activeplayer;
        }
    // check winner from 2 player
        if(playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("Player One is Winning!");
        } else if(playerTwoScoreCount > playerOneScoreCount){
            playerStatus.setText("Player Two is Winning!");
        } else {
            playerStatus.setText("");
        }

        // reset button and clear all varaible
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }
// Check winner
    public boolean checkwinner(){
        boolean winnerResult = false;

        for(int[] winningPosion : winningPosition) {
            if(gamestate[winningPosion[0]] == gamestate[winningPosion[1]] &&
             gamestate[winningPosion[1]] == gamestate[winningPosion[2]] &&
            gamestate[winningPosion[0]] != 2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }
// update palyer score
    public  void  updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }
    // reset game and play again
    public  void playAgain(){
        roundCount = 0;
        activeplayer = true;

        for(int i=0; i<button.length; i++){
            gamestate[i] = 2;
            button[i].setText("");
        }
    }
}