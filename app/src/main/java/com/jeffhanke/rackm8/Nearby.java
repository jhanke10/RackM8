package com.jeffhanke.rackm8;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Nearby extends Fragment {

    String[] locationArrayDist = {
            "Digital Computer Laboratory\nDistance: 0.1 mi\nRating: ⭐⭐⭐",
            "Kenney Gym Annex\nDistance: 0.1 mi\nRating: ⭐⭐",
            "Grainger Engineering Library\nDistance: 0.2 mi\nRating: ⭐⭐",
            "Talbot Laboratory\nDistance: 0.2 mi\nRating: ⭐",
            "Everitt Laboratory\nDistance: 0.3 mi\nRating: ⭐⭐⭐",
            "Engineering Hall\nDistance: 0.3 mi\nRating: ⭐⭐⭐⭐⭐",
            "Materials Science and Engineering Building\nDistance: 0.4 mi\nRating: ⭐⭐⭐",
            "Mechanical Engineering Building\nDistance: 0.4 mi\nRating: ⭐⭐⭐",
            "Transportation Building\nDistance: 0.5 mi\nRating: ⭐⭐⭐⭐",
            "University Laboratory High School\nDistance: 0.5 mi\nRating: ⭐⭐⭐",
            "Mechanical Engineering Laboratory\nDistance: 0.5 mi\nRating: ⭐⭐⭐⭐"};

    String[] locationArraySafe = {
            "Engineering Hall\nDistance: 0.3 mi\nRating: ⭐⭐⭐⭐⭐",
            "Mechanical Engineering Laboratory\nDistance: 0.5 mi\nRating: ⭐⭐⭐⭐",
            "Transportation Building\nDistance: 0.5 mi\nRating: ⭐⭐⭐⭐",
            "Mechanical Engineering Building\nDistance: 0.4 mi\nRating: ⭐⭐⭐",
            "Everitt Laboratory\nDistance: 0.3 mi\nRating: ⭐⭐⭐",
            "University Laboratory High School\nDistance: 0.5 mi\nRating: ⭐⭐⭐",
            "Materials Science and Engineering Building\nDistance: 0.4 mi\nRating: ⭐⭐⭐",
            "Digital Computer Laboratory\nDistance: 0.1 mi\nRating: ⭐⭐⭐",
            "Grainger Engineering Library\nDistance: 0.2 mi\nRating: ⭐⭐",
            "Kenney Gym Annex\nDistance: 0.1 mi\nRating: ⭐⭐",
            "Talbot Laboratory\nDistance: 0.2 mi\nRating: ⭐"};

    public static Nearby newInstance() {
        Nearby fragment = new Nearby();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_nearby, container, false);

        ArrayList<String> buildingList = new ArrayList<String>();
        buildingList.addAll(Arrays.asList(locationArrayDist));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_row, buildingList);

        final ListView listView = (ListView) view.findViewById(R.id.location_list2);
        listView.setAdapter(adapter);

        final BottomNavigationView mBottomNav = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, Map.newInstance());
                transaction.commit();
                mBottomNav.getMenu().getItem(1).setChecked(true);
            }
        });

        final Button distance = (Button) view.findViewById(R.id.distance);
        final Button safety = (Button) view.findViewById(R.id.safety);

        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distance.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                distance.setTextColor(Color.WHITE);

                safety.setBackgroundColor(Color.WHITE);
                safety.setTextColor(Color.DKGRAY);

                ArrayList<String> buildingList = new ArrayList<String>();
                buildingList.addAll(Arrays.asList(locationArrayDist));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_row, buildingList);
                listView.setAdapter(adapter);
            }
        });

        safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                safety.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                safety.setTextColor(Color.WHITE);

                distance.setBackgroundColor(Color.WHITE);
                distance.setTextColor(Color.DKGRAY);

                ArrayList<String> buildingList = new ArrayList<String>();
                buildingList.addAll(Arrays.asList(locationArraySafe));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_row, buildingList);
                listView.setAdapter(adapter);
            }
        });

        return view;
    }
}
