package android.gurkashi.com.baseapplication.model.core;

import android.gurkashi.com.baseapplication.application.core.ApplicationBase;

/**
 * Created by gur on 4/23/2016.
 */
public class ApplicationModel {
    private final ApplicationBase application;
    private final SharedPreferencesManager sharedPreferencesManager;

    public ApplicationModel(ApplicationBase application){
        this.application = application;
        this.sharedPreferencesManager = application.getSharedPreferencesManager();
    }

    protected SharedPreferencesManager getSharedPreferencesManager(){ return sharedPreferencesManager; }
}



