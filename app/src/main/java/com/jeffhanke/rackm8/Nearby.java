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

    String[] locationArrayDist = {"Digital Computer Laboratory\n0.1 mi","Kenney Gym Annex\n0.1 mi",
            "Grainger Engineering Library\n0.2 mi","Talbot Laboratory\n0.2mi", "Everitt Laboratory\n0.3 mi",
            "Engineering Hall\n0.3 mi","Materials Science and Engineering Building\n0.4 mi",
            "Mechanical Engineering Building\n0.4 mi","Transportation Building\n0.5 mi",
            "University Laboratory High School\n0.5 mi","Mechanical Engineering Laboratory\n0.5 mi"};

    String[] locationArraySafe = {
            "Engineering Hall\nRating: 4.1/5.0",
            "Mechanical Engineering Laboratory\nRating: 3.8/5.0",
            "Transportation Building\nRating: 3.8/5.0",
            "Mechanical Engineering Building\nRating: 3.5/5.0",
            "Everitt Laboratory\nRating: 3.3/5.0",
            "University Laboratory High School\nRating: 3.2/5.0",
            "Materials Science and Engineering Building\nRating: 3.2/5.0",
            "Digital Computer Laboratory\nRating: 3.2/5.0",
            "Grainger Engineering Library\nRating: 3.1/5.0",
            "Kenney Gym Annex\nRating: 2.9/5.0",
            "Talbot Laboratory\nRating: 2.7/5.0"};

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
                mBottomNav.getMenu().getItem(2).setChecked(true);
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
