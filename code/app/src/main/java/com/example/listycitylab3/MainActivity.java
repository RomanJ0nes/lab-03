package com.example.listycitylab3;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> dataList;
    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;
    private int currentlySelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {
                "Edmonton", "Vancouver", "Moscow",
                "Sydney", "Berlin", "Vienna",
                "Tokyo", "Beijing", "Osaka", "New Delhi"
        };

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityList = findViewById(R.id.city_list);
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        //KEEP TRACK OF SELECTED CITY
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentlySelected = position;
            }
        });

        //BUTTON HAS BEEN PRESSED
        Button buttonPress = findViewById(R.id.buttonPress);
        buttonPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });
    }

    //SHOW POP-UP AND TAKE INPUT
    private void showPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add/Edit City");

        EditText input = new EditText(this);
        if (currentlySelected != -1) {
            input.setText(dataList.get(currentlySelected));
        }
        builder.setView(input);

        //CONFIRM
        builder.setPositiveButton("OK", (dialog, which) -> {
            String cityName = input.getText().toString();

                if (currentlySelected != -1) {          //A city was selected before pressing button (Edit)
                    dataList.set(currentlySelected, cityName);
                    currentlySelected = -1;

                } else {                                //No city was selected before pressing the button (Add)
                    dataList.add(cityName);
                }

                cityAdapter.notifyDataSetChanged();
        });

        //CANCEL
        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

        builder.show();

    }
}
