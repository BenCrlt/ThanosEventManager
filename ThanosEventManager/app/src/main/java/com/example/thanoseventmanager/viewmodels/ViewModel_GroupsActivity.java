package com.example.thanoseventmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thanoseventmanager.modeles.Groupe;

public class ViewModel_GroupsActivity extends ViewModel {
    private MutableLiveData<Groupe> groupSelected = new MutableLiveData<Groupe>();

    public LiveData<Groupe> getGroupSelected() { return this.groupSelected; }

    public void setGroupSelected(Groupe groupe) { this.groupSelected.setValue(groupe); }
}