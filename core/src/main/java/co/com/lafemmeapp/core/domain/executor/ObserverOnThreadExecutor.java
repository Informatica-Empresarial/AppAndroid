package co.com.lafemmeapp.core.domain.executor;

import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 4/8/17.
 */

public interface ObserverOnThreadExecutor {
    Scheduler getScheduler();
}
