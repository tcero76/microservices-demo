package cl.sugarfever.common.util.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

public interface Scheduler {
    void process();
}
