package uofc.bewsschmidtensf409finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This class is the main activity class for the Arithmetic calculator. It contains
 * all functions and listeners associated to the GUI. The functions handle the arithmetic
 * and displaying the numbers to the screen.
 *
 * @author Tevin Schmidt
 * @author James Bews
 * @since March 24, 2017
 *
 *
 */
public class CalculatorGUI extends AppCompatActivity {

    /**
     * The string representing the first entry into the calculator
     */
    private String firstEntry;

    /**
     * The string representing the second entry into the calculator
     */
    private String secondEntry;

    /**
     * The string representing the result of the arithmetic
     */
    private String result;

    /**
     * This will be the symbol corresponding to the desired operation
     * + for add, - for sub, * for mult, / for divide
     */
    private String opp;

    /**
     * There current entry being shown, 1 for firstEntry and 2 for secondEntry
     */
    private int current;


    /**
     * Creates all the listeners and assigns them to the required buttons when the
     * application is first started up, also initializes variables
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_gui);

        current = 1;

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen("1");
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen("2");
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen("3");
            }
        });

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen("4");
            }
        });

        Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen("5");
            }
        });

        Button button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen("6");
            }
        });

        Button button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen("7");
            }
        });

        Button button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen("8");
            }
        });

        Button button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen("9");
            }
        });

        Button button0 = (Button) findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen("0");
            }
        });

        Button decimalButton = (Button) findViewById(R.id.decimalButton);
        decimalButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addToScreen(".");
            }
        });

        Button clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                clearData();
            }
        });

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setAdd();
            }
        });

        Button subtractButton = (Button) findViewById(R.id.subtractButton);
        subtractButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setSub();
            }
        });

        Button divideButton = (Button) findViewById(R.id.divideButton);
        divideButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setDiv();
            }
        });

        Button multiplyButton = (Button) findViewById(R.id.multiplyButton);
        multiplyButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setMult();
            }
        });

        Button equalButton = (Button) findViewById(R.id.equalButton);
        equalButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                calculate();
            }
        });



    }

    /**
     * Adds the new digit to the current Entry and updates the outputScreen
     * @param newDigit is the digit to be concatenated to the existing string
     */
    public void addToScreen(String newDigit){
        if(current == 1){
            if(firstEntry == null){
                firstEntry = newDigit;
            }
            else {
                firstEntry = firstEntry.concat(newDigit);
            }
            display();
        }
        else{
            if(secondEntry == null){
                secondEntry = newDigit;
            }
            else {
                secondEntry = secondEntry.concat(newDigit);
            }
            display();
        }
    }

    /**
     * Updates the outputScreen to the current number
     */
    public void display(){
        EditText update = (EditText) findViewById(R.id.outputScreen);
        if(current == 1){
            update.setText(firstEntry);
        }
        else{
            update.setText(secondEntry);
        }
    }

    /**
     * Clears all fields so the user can restart calculation
     */
    public void clearData(){
        firstEntry = null;
        secondEntry = null;
        opp = "";
        result = "";
        current = 1;
    }

    /**
     * Changes the operation to addition and changes to secondEntry
     */
    public void setAdd(){
        opp = "+";
        current = 2;
    }

    /**
     * Changes the operation to subtraction and changes to secondEntry
     */
    public void setSub(){
        opp = "-";
        current = 2;
    }

    /**
     * Changes the operation to multiplication and changes to secondEntry
     */
    public void setMult(){
        opp = "*";
        current = 2;
    }

    /**
     * Changes the operation to divide and changes to secondEntry
     */
    public void setDiv(){
        opp = "/";
        current = 2;
    }

    /**
     * Calculates the result of the desired operation and updates the screen
     */
    public void calculate(){
        float first = Float.parseFloat(firstEntry);
        float second = Float.parseFloat(secondEntry);
        float res;
        if(opp.contentEquals("+")){
            res = first + second;
        }
        else if(opp.contentEquals("-")){
            res = first - second;
        }
        else if(opp.contentEquals("*")){
            res = first * second;
        }
        else if(opp.contentEquals("/")){
            res = first/second;
        }
        else{
            return;
        }
        result = Float.toString(res);


        EditText update = (EditText) findViewById(R.id.outputScreen);
        update.setText(result);

        clearData();
    }





}
