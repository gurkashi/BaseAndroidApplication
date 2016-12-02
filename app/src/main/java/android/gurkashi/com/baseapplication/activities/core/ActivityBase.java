package android.gurkashi.com.baseapplication.activities.core;

import android.app.Activity;
import android.content.Intent;
import android.gurkashi.com.baseapplication.application.core.ApplicationBase;
import android.gurkashi.com.baseapplication.model.core.DataBinder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gurkashi.fj.lambdas.Predicate;
import com.gurkashi.fj.lambdas.Selector;
import com.gurkashi.fj.queries.stracture.Queriable;

import java.lang.reflect.Field;

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
        startActivity(new Intent(getApplicationContext(), activity));
    }

    protected DataBinder getResumeBinder(){ return resumeBinder; }
    protected DataBinder getCreateBinder(){ return createBinder; }

    protected T getBaseApplication() {
        return (T) getApplicationContext();
    }

    protected int getContentViewLayoutResourceId() {
        LayoutBinding layoutAnnotation = this.getClass().getAnnotation(LayoutBinding.class);

        if (layoutAnnotation != null){
            return layoutAnnotation.resourceId();
        }
        return 0;
    }

    protected void findViews(){
        Field[] fields = this.getClass().getDeclaredFields();

        Queriable.create(Field.class)
                .filter(new Predicate<Field>() {
                    @Override
                    public boolean predict(Field field) {
                        return View.class.isAssignableFrom(field.getType()) && field.getAnnotation(ViewBinding.class) != null;
                    }
                })
                .map(new Selector<Field, View>() {
                    @Override
                    public View select(Field field) {
                        ViewBinding annotation = field.getAnnotation(ViewBinding.class);
                        View view = findViewById(annotation.resourceId());
                        try {
                            boolean accessible = field.isAccessible();
                            field.setAccessible(true);
                            field.set(getActivity(), view);
                            field.setAccessible(accessible);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        return view;
                    }
                })
                .execute(fields);
    }

    protected abstract void initViews();

    protected void initBeforeLayoutInflation() {}

    protected ActivityBase<T> getActivity(){ return this; }
}