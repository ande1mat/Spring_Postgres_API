package com.springjpa.health;

import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * Created by z042183 on 7/22/18.
 * http://localhost:8090/actuator/health
 * http://localhost:8090/actuator/info
 * http://localhost:8090/actuator/metrics
 */
@Component
public class APIHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down()
                    .withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    public int check() {
        // Our logic to check health.  Eg. Check if database is down, or other dependency
        return 0;
    }
}

