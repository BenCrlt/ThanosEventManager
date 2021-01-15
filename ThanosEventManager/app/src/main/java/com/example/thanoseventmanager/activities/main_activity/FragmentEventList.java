package com.example.thanoseventmanager.activities.main_activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.listAdapter.EventListAdapter;
import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.viewmodels.ViewModel_MainActivity;

public class FragmentEventList extends Fragment {

    ListView listView;
    ViewModel_MainActivity viewModel;

    public FragmentEventList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Création d'un objet ListView correspondant à "listEvents" du layout activity_main_after_login.xml
        View v = inflater.inflate(R.layout.fragment_event_list, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel_MainActivity.class);

        listView = v.findViewById(R.id.listGroups);
        listView.setAdapter(new EventListAdapter(getContext(), viewModel.getListAllEvent().getValue()));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //Récupération de l'objet event
            Event eventSelected = viewModel.getListAllEvent().getValue().get(position);

            //Création du ViewModel de cet event
            viewModel = new ViewModelProvider(requireActivity()).get(ViewModel_MainActivity.class);
            viewModel.setEventToView(eventSelected);

            //Affichage du fragment Event View
            ((MainActivity)requireActivity()).onClickFragmentEventView();
        });
        // Inflate the layout for this fragment
        viewModel.getListAllEvent().observe(getActivity(), events -> {
            ((EventListAdapter) listView.getAdapter()).setListeEvent(events);
            ((EventListAdapter) listView.getAdapter()).notifyDataSetChanged();
        });
        ((MainActivity) getActivity()).updateListeEvents();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).updateListeEvents();
    }

    @Override
    public void onResume() { super.onResume(); }

    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onStop() { super.onStop(); }

    @Override
    public void onDestroy() { super.onDestroy(); }

    @Override
    public void onAttach(@NonNull Context context) { super.onAttach(context); }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) { super.onActivityCreated(savedInstanceState); }

    @Override
    public void onDestroyView() { super.onDestroyView(); }

    @Override
    public void onDetach() { super.onDetach(); }
}
