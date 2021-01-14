package com.example.thanoseventmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Invitation;

import java.util.List;

public class ViewModel_MainActivity extends ViewModel {

    private MutableLiveData<List<Event>> listAllEvents = new MutableLiveData<List<Event>>();
    private MutableLiveData<Event> eventToView = new MutableLiveData<>();
    private MutableLiveData<List<Invitation>> listeInvit = new MutableLiveData<List<Invitation>>();

    public LiveData<List<Event>> getListAllEvent() { return this.listAllEvents; }

    public void setListAllEvents(List<Event> newListEvents) { this.listAllEvents.setValue(newListEvents); }

    public LiveData<Event> getEventToView() { return this.eventToView; }

    public void setEventToView(Event event) { this.eventToView.setValue(event); }
}
