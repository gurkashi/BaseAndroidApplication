package android.gurkashi.com.baseapplication.model.core;

public class ObservableText extends Observable<String>{
    public ObservableText(String value){
        super(value);
    }

    public ObservableText(){
        super("");
    }
}

