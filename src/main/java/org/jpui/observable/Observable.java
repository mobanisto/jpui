package org.jpui.observable;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    private boolean changed = false;
    private final List<Observer> observers = new ArrayList<>();

    protected void setChanged() {
        changed = true;
    }

    protected void notifyObservers() {
        if (!changed) {
            return;
        }
        for (Observer observer : observers) {
            observer.update(this);
        }
        changed = false;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }
}
