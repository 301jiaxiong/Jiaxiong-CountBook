package com.example.jiaxiong_countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    //save data in file.sav
    private static final String FILENAME = "file.sav";

    private EditText nameText;
    private EditText valueText;
    private EditText commentText;
    private ListView olditemList;
    private TextView number;

    //create ArrayList and adapter to save item object
    private ArrayList<Item> itemList;
    private ArrayAdapter<Item> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //set up dialog box parameters
        nameText = (EditText) findViewById(R.id.Name);
        valueText =(EditText) findViewById(R.id.Inital);
        commentText = (EditText) findViewById(R.id.comment) ;
        Button addButton = (Button) findViewById(R.id.add);
        Button clearButton = (Button) findViewById(R.id.clear);
        olditemList = (ListView) findViewById(R.id.oldItemList);
        number = (TextView)findViewById(R.id.numberofitems);


        //respond when add Button click
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                //get name,value and comment(inital value
                //and current value are same at the begin)
                //pass to the item
                try {

                    String name = nameText.getText().toString();

                    /*Take from https://stackoverflow.com/questions/15037465/converting-edittext-to-int-android
                    //2017/10/01
                     */
                    String value = valueText.getText().toString();
                    int finalValue = Integer.parseInt(value);

                    String comment = commentText.getText().toString();
                    Item newItem = new Item(name,finalValue,comment,finalValue);
                    //delete the value in the dialog after click
                    itemList.add(newItem);
                    nameText.setText("");
                    valueText.setText("");
                    commentText.setText("");
                }
               catch (Exception e){
                   System.out.println("Value type inputed not correct");

                }




                //update the arrayList size show at the top
                int numberOfItem =itemList.size();
                String finalnumber = String.valueOf(numberOfItem);
                number.setText(finalnumber+"  Items:");

                adapter.notifyDataSetChanged();
                saveInFile();
                //finish();

            }
        });
        //clearButton click, clear the list
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                itemList.clear();
                //update the arrayList size show at the top
                number.setText("0  Items:");
                adapter.notifyDataSetChanged();
                saveInFile();
            }

        });

        /* Click set up Take From https://www.youtube.com/watch?v=2kUBcbUMDKE
        **2017/10/01
         */

        //click the item in the ArrayList
        olditemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {

                //Ready to go to Detail activity
                Intent intent = new Intent(view.getContext(), Detail.class);

                String name = itemList.get(i).getName();
                int value =itemList.get(i).getValue();
                int inital_value =itemList.get(i).getInit();
                String comment = itemList.get(i).getComment();
                String finalvalue = String.valueOf(value);
                String initalFinalvalue = String.valueOf(inital_value);
                String position = String.valueOf(i);

                /*Take From https://stackoverflow.com/questions/8452526/
                android-can-i-use-putextra-to-pass-multiple-values
                //2017/10/01
                 */

                //sending the value of item to the detail and wait for result
                Bundle extras = new Bundle();
                extras.putString("EXTRA_NAME",name);
                extras.putString("EXTRA_VALUE",finalvalue);
                extras.putString("EXTRA_COMMENT",comment);
                extras.putString("EXTRA_INITAL",initalFinalvalue);
                extras.putString("EXTRA_POSITION",position);
                intent.putExtras(extras);
                //itemList.remove(i);

                //start the activity
                startActivityForResult(intent,1);

            }}

        );

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        //update the arrayList size show at the top
        int numberOfItem =itemList.size();
        String finalnumber = String.valueOf(numberOfItem);
        number.setText(finalnumber+"  Items:");


        adapter = new ArrayAdapter<Item>(this,
                R.layout.list_item, itemList);
        olditemList.setAdapter(adapter);

    }




    /*Take From https://stackoverflow.com/questions/14292398/
    how-to-pass-data-from-2nd-activity-to-1st-activity
    -when-pressed-back-android
    * 2017/10/01
    * */
    @Override

    //return from the Detail activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if return success update the values of item
        if(resultCode == RESULT_OK) {
            Bundle backextras = data.getExtras();
            String valueSentBack = backextras.getString("BACK_VALUE");
            String nameback = backextras.getString("BACK_NAME");
            String commentback = backextras.getString("BACK_COMMENT");
            String initalback = backextras.getString("BACK_INITAL");
            String positionback = backextras.getString("BACK_POSITION");

            int positionValue = Integer.parseInt(positionback);
            if(nameback.equals("delete") ){
                itemList.remove(positionValue);
            }
            else {
                int valueback = Integer.parseInt(valueSentBack);
                int intal_valueback = Integer.parseInt(initalback);

                //passing new item values to the Item
                Item newItem = new Item(nameback, valueback, commentback, intal_valueback);
                itemList.set(positionValue, newItem);
            }

            adapter.notifyDataSetChanged();
            saveInFile();
        }
        else {
            //not return success do nothing
            adapter.notifyDataSetChanged();
            saveInFile();
        }

        //update the arrayList size show at the top
        int numberOfItem =itemList.size();
        String finalnumber = String.valueOf(numberOfItem);
        number.setText(finalnumber+"  Items:");


    }


    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //2017/09/19
            Type listType = new TypeToken<ArrayList<Item>>() {}.getType();
            itemList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            itemList = new ArrayList<Item>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(itemList,out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}

