package android.gurkashi.com.baseapplication.verification.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Matching {
    String[] regex() default { ".*" };
    String message() default "Text is not matching pattern";
}

