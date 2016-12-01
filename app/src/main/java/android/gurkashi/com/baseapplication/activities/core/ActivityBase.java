package android.gurkashi.com.baseapplication.activities.core;

import android.app.Activity;
import android.content.Intent;
import android.gurkashi.com.baseapplication.application.core.ApplicationBase;
import android.gurkashi.com.baseapplication.model.core.DataBinder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gur on 4/23/2016.
 */
public abstract class ActivityBase<T extends ApplicationBase> extends AppCompatActivity {
    private final DataBinder createBinder = new DataBinder();
    private final DataBinder resumeBinder = new DataBinder();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        initBeforeLayoutInflation();

        setContentView(getContentViewLayoutResourceId());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);

        findViews();
        initViews();
    }

    @Override
    protected void onPause(){
        super.onPause();

        resumeBinder.clear();

        getBaseApplication().setCurrentActivity(null);
    }

    @Override
    protected void onResume(){
        super.onResume();

        getBaseApplication().setCurrentActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        createBinder.clear();
    }

    public <T extends Activity> void  startActivity(Class<T> activity){
        startActivity(new Intent(this, activity));
    }

    protected DataBinder getResumeBinder(){ return resumeBinder; }
    protected DataBinder getCreateBinder(){ return createBinder; }

    protected T getBaseApplication() {
        return (T) getApplicationContext();
    }

    protected abstract int getContentViewLayoutResourceId();

    protected abstract void findViews();
    protected abstract void initViews();

    protected void initBeforeLayoutInflation() {}
}