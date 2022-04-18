package me.sapzilking.inflearnthejavatest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Test
@Tag("fast")
public @interface FastTest {
    //@Test와 @Tag를 메타 어노테이션으로 사용을 해서 FastTest라는 컴포즈드 어노테이션을 만들었다.
    //이렇게 안하고 @TAg("fast")라고 하면 fast는 문자열이다 typesafe하지않다
}
