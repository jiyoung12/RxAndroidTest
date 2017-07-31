package com.jiyoung.rxandroidexamples.net.model;

/**
 * Created by Jiyoung on 2017-07-16.
 */

public class Response<T> {
    private T subject;

    public T getSubject() {
        return subject;
    }

    public void setSubject(T subject) {
        this.subject = subject;
    }
}
