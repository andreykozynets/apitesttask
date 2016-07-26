package org.andrey.api.rest;

import java.util.concurrent.Callable;

public class Try<T> {

    private final Exception exception;

    private final T value;

    private Try(T value, Exception exception) {
        this.value = value;
        this.exception = exception;
    }

    public boolean isSuccessful() {
        return exception == null;
    }

    public T getValue() {
        return value;
    }

    public Exception getException() {
        return exception;
    }

    public static <T> Try<T> value(T value) {
        return new Try<T>(value, null);
    }

    public static <T> Try<T> exception(Exception exception) {
        return new Try<T>(null, exception);
    }

    public static  <T> Try<T> wrap(Callable<T> callable) {

        Try<T> aTry = null;

        try {

            aTry = Try.value(callable.call());
        } catch (Exception exception) {

            aTry = Try.exception(exception);
        }

        return aTry;
    }

    public static  <Void> Try<Void> wrap(final Runnable runnable) {

        return wrap(new Callable<Void>() {

            @Override
            public Void call() throws Exception {

                runnable.run();
                return null;
            }
        });
    }
}
