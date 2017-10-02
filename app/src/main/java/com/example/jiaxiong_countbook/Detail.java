package com.example.jiaxiong_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Detail extends AppCompatActivity {
    private EditText nameText;
    private EditText valueText;
    private EditText commentText;
    private EditText currentText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        /*Take from https://stackoverflow.com/questions/8452526/android-can-i-use-putextra-to-pass-multiple-values
        //2017/10/01
         */

        //collect the item's value from MainActivity

        Bundle extras = intent.getExtras();
        String name = extras.getString("EXTRA_NAME");
        final String value = extras.getString("EXTRA_VALUE");
        final String inital = extras.getString("EXTRA_INITAL");
        String comment = extras.getString("EXTRA_COMMENT");
        String position = extras.getString("EXTRA_POSITION");

        //setup parameters for all the dialogs and buttons.

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(name);
        nameText = (EditText) findViewById(R.id.detail_name);
        valueText =(EditText) findViewById(R.id.Inital_value);
        currentText = (EditText) findViewById(R.id.Current_value) ;
        commentText = (EditText) findViewById(R.id.detail_comment) ;
        Button addButton = (Button) findViewById(R.id.Add_button);
        Button Reduece = (Button) findViewById(R.id.Reduce);
        Button reset = (Button) findViewById(R.id.reset);
        Button delete = (Button) findViewById(R.id.Delete) ;
        Button change = (Button) findViewById(R.id.change);


        //Display the detail of the item
        nameText.setText(name);
        valueText.setText(inital);
        currentText.setText(value);
        commentText.setText(comment);

        //incremental the item's value
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);


                String value = currentText.getText().toString();
                int finalValue = Integer.parseInt(value);
                finalValue++;

                /*Take From https://stackoverflow.com/questions/15836797/android-how-to-convert-int-to-string
                //2017/10/01
                 */
                String finalvalue = String.valueOf(finalValue);
                String inital_value = valueText.getText().toString();
                String name = nameText.getText().toString();
                String comment = commentText.getText().toString();

                Intent goingBack = new Intent();
                Bundle backextras = new Bundle();
                backextras.putString("BACK_NAME",name);
                backextras.putString("BACK_VALUE",finalvalue);
                backextras.putString("BACK_COMMENT",comment);
                backextras.putString("BACK_INITAL",inital_value);
                goingBack.putExtras(backextras);

                setResult(RESULT_OK, goingBack);
                finish();
            }


    });

        //Decremental the item's value
        Reduece.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);


                String value = currentText.getText().toString();
                int finalValue = Integer.parseInt(value);
                finalValue--;

                /*Take From https://stackoverflow.com/questions/15836797/android-how-to-convert-int-to-string
                //2017/10/01
                 */
                String finalvalue = String.valueOf(finalValue);
                String inital_value = valueText.getText().toString();
                String name = nameText.getText().toString();
                String comment = commentText.getText().toString();

                Intent goingBack = new Intent();
                Bundle backextras = new Bundle();
                backextras.putString("BACK_NAME",name);
                backextras.putString("BACK_VALUE",finalvalue);
                backextras.putString("BACK_COMMENT",comment);
                backextras.putString("BACK_INITAL",inital_value);
                goingBack.putExtras(backextras);

                setResult(RESULT_OK, goingBack);
                finish();
            }


        });

        //reset the item's value to inital
        reset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                String value = currentText.getText().toString();
                int finalValue = Integer.parseInt(value);
                String inital_value = valueText.getText().toString();
                int inital_finalValue = Integer.parseInt(inital_value);
                finalValue = inital_finalValue;

                /*Take From https://stackoverflow.com/questions/15836797/android-how-to-convert-int-to-string
                //2017/10/01
                 */
                String finalvalue = String.valueOf(finalValue);

                String name = nameText.getText().toString();
                String comment = commentText.getText().toString();

                Intent goingBack = new Intent();
                Bundle backextras = new Bundle();
                backextras.putString("BACK_NAME",name);
                backextras.putString("BACK_VALUE",finalvalue);
                backextras.putString("BACK_COMMENT",comment);
                backextras.putString("BACK_INITAL",inital_value);
                goingBack.putExtras(backextras);

                setResult(RESULT_OK, goingBack);
                finish();
            }


        });

        /*deleted Item*/
        delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                setResult(RESULT_CANCELED);
                finish();
            }


        });

        //change the item's value
        change.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                String value = currentText.getText().toString();
                int finalValue = Integer.parseInt(value);
                String inital_value = valueText.getText().toString();
                String name = nameText.getText().toString();
                String comment = commentText.getText().toString();

                Intent goingBack = new Intent();
                Bundle backextras = new Bundle();
                backextras.putString("BACK_NAME",name);
                backextras.putString("BACK_VALUE",value);
                backextras.putString("BACK_COMMENT",comment);
                backextras.putString("BACK_INITAL",inital_value);
                goingBack.putExtras(backextras);

                setResult(RESULT_OK, goingBack);
                finish();
            }


        });

    }}
