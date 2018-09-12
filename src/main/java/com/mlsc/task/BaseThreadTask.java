package com.mlsc.task;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Created by zhanghj on 2017/8/9.
 */
public abstract class BaseThreadTask<T> implements Callable<T>,Serializable {

    private static final long serialVersionUID = -8056778700898449970L;

    @Override
    public T call() throws Exception {
        return execute();
    }

    protected abstract T execute();

}

