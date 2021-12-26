package com.tanim.androidbasetemplate.base;


import com.bcsprostuti.tanim.bcsprostuti.managers.DataManager;

public abstract class BaseRepository {
    private final DataManager dataManager;

    protected BaseRepository(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
