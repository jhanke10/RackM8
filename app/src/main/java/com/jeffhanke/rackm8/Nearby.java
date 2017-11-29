package com.jeffhanke.rackm8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Nearby extends AppCompatActivity {

    String[] locationArray = {"Digital Computer Laboratory","Kenney Gym Annex",
            "Grainger Engineering Library","Talbot Laboratory", "Everitt Laboratory",
            "Engineering Hall","Materials Science and Engineering Building",
            "Mechanical Engineering Building","Transportation Building",
            "University Laboratory High School","Mechanical Engineering Laboratory"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        ArrayList<String> buildingList = new ArrayList<String>();
        buildingList.addAll(Arrays.asList(locationArray));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_row, buildingList);

        ListView listView = (ListView) findViewById(R.id.location_list);
        listView.setAdapter(adapter);
    }
}
