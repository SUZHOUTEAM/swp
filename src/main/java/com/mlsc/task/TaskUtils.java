package com.mlsc.task;

import com.mlsc.epdp.common.exception.ParamDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Future;

/**
 * 定时工具
 */
@Component
public class TaskUtils {

    @Autowired
    private ThreadPoolTaskExecutor poolTaskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    private static TaskUtils taskUtils;

    @PostConstruct
    public void init(){
        taskUtils = this;
    }


    public static <T>Future<T> executeTask(BaseThreadTask<T> task){
        return taskUtils.poolTaskExecutor.submit(task);
    }

    public static <T>T getTask(Class<T> clazz){
        if(BaseThreadTask.class.isAssignableFrom(clazz)){
            return (T)taskUtils.applicationContext.getBean(clazz);
        }else{
            throw new ParamDataException("参数应该为BaseThreadTask的子类");
        }
    }
}
