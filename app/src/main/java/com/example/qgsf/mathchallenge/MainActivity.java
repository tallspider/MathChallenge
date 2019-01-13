package com.example.qgsf.mathchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView scorePanel;
    private TextView n1;
    private TextView n2;
    private TextView op;
    private EditText ans;
    private Button submitButton;
    private TextView sysMessage;

    private int num1, num2, operator, nright, nwrong, playerAns, actualAns;
    private boolean first;

    private final String[] RIGHT_MES = {"..\\> Unexpected slip",
                                 "..\\> Minor system error",
                                 "..\\> System breach",
                                 "..\\> Alert! Alert!",
                                 "..\\> Must elminate!",
                                 "..\\> Destroy player!",
                                 "..\\> System defeated."};

    private final String[] WRONG_MES = {"..\\> One hit.",
                                        "..\\> One more to sink player.",
                                        "..\\> Player dead."};

    private final String MES_START = "..\\> kill player ready.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gatherControls();
        setUpButton();
        nright = -1;
        nwrong = -1;
        first = true;
        submitButton.setText("Start");
    }

    private void gatherControls(){
        scorePanel = (TextView)this.findViewById(R.id.scorePanel);
        n1 = (TextView)this.findViewById(R.id.n1);
        n2 = (TextView)this.findViewById(R.id.n2);
        op = (TextView)this.findViewById(R.id.op);
        ans = (EditText)this.findViewById(R.id.ans);
        submitButton = (Button)this.findViewById(R.id.submit_button);
        sysMessage = (TextView)this.findViewById(R.id.sysMessage);
        n1.requestFocus();
    }

    private void setUpButton(){
        submitButton.setOnClickListener(this);
    }

    private void newQues(){
        num1 = (int)(Math.random() * 100);
        num2 = (int)(Math.random() * 100);
        operator = (int)(Math.random() * 3);
    }

    private void displayQues(){
        n1.setText(num1 + "");
        n2.setText(num2 + "");
        if(operator == 0)
            op.setText("+");
        if(operator == 1)
            op.setText("-");
        if(operator == 2)
            op.setText("*");
    }

    private void calculateAns(){
        if(operator == 0)
            actualAns = num1 + num2;
        if(operator == 1)
            actualAns = num1 - num2;
        if(operator == 2)
            actualAns = num1 * num2;
    }

    @Override
    public void onClick(View v){
        if(nright != RIGHT_MES.length - 1) submitButton.requestFocus();
        if(first){
            first = false;
            submitButton.setText("Submit");
        } else {
            calculateAns();
            try {
                playerAns = Integer.parseInt(ans.getText().toString());
            } catch (NumberFormatException e){
                playerAns = -1;
            }
            if(playerAns == actualAns){
                nright++;
                sysMessage.setText(RIGHT_MES[nright]);
                if(nright == RIGHT_MES.length - 1) {
                    submitButton.setEnabled(false);
                    submitButton.setTextColor(getApplication().getResources().getColor(R.color.disabledButton));
                }
            } else {
                nwrong++;
                sysMessage.setText(WRONG_MES[nwrong]);
                if(nwrong == WRONG_MES.length - 1)
                    submitButton.setEnabled(false);
            }

            scorePanel.setText("Score: " + (nright - nwrong));
            if(nright != RIGHT_MES.length - 1) {
                ans.setText("");
                ans.setHint("ans");
            }
        }
        newQues();
        if(nright != RIGHT_MES.length - 1) displayQues();
    }
}
