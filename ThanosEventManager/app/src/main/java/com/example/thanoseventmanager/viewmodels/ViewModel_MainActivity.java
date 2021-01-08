package com.example.thanoseventmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thanoseventmanager.modeles.Event;

import java.util.List;

public class ViewModel_MainActivity extends ViewModel {
    private MutableLiveData<List<Event>> listAllEvents = new MutableLiveData<List<Event>>();

    public LiveData<List<Event>> getListAllEvent() { return this.listAllEvents; }

    public void setListAllEvents(List<Event> newListEvents) { this.listAllEvents.setValue(newListEvents); }
}
