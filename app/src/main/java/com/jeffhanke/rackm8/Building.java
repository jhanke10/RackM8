package com.jeffhanke.rackm8;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;

public class Building extends Fragment {

    String[] locationArray = {"Digital Computer Laboratory","Kenney Gym Annex",
            "Grainger Engineering Library","Talbot Laboratory", "Everitt Laboratory",
            "Engineering Hall","Materials Science and Engineering Building",
            "Mechanical Engineering Building","Transportation Building",
            "University Laboratory High School","Mechanical Engineering Laboratory"};

    public static Building newInstance() {
        Building fragment = new Building();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_building, container, false);

        final BottomNavigationView mBottomNav = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        final ListView listView = (ListView) view.findViewById(R.id.location_list2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, Map.newInstance());
                transaction.commit();
                mBottomNav.getMenu().getItem(1).setChecked(true);
            }
        });

        ArrayList<String> buildingList = new ArrayList<String>();
        buildingList.addAll(Arrays.asList(locationArray));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_row, buildingList);
        listView.setAdapter(adapter);

        final SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
                searchView.onActionViewExpanded();
            }
        });
        searchView.setQueryHint("Search Building");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateSearch(newText, listView);
                return false;
            }
        });

        return view;
    }

    public void updateSearch(String query, ListView listView) {
        ArrayList<String> buildingList = new ArrayList<String>();
        for(int i = 0; i < locationArray.length; i++) {
            if (locationArray[i].toLowerCase().contains(query.toLowerCase())) {
                buildingList.add(locationArray[i]);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_row, buildingList);

        listView.setAdapter(adapter);
    }
}
