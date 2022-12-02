package com.project.challenge.infrastructure.rest.configuration;

import com.codahale.metrics.Timer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimedLogAspect {
    private final Timer timer = new Timer();

    @Around("@annotation(com.project.challenge.application.annotations.TimerLog)")
    public Object timed(ProceedingJoinPoint joinPoint) throws Throwable {
        Timer.Context context = timer.time();
        StringBuilder stringBuilder = new StringBuilder("Processing time: ");

        try{
            Object result =joinPoint.proceed();
            stopAndLog(context, stringBuilder);
            return result;
        }catch (Exception error){
            stopAndLog(context, stringBuilder);
            throw error;
        }
    }

    private void stopAndLog(Timer.Context context, StringBuilder stringBuilder){
        context.stop();
        stringBuilder.append(timer.getMeanRate());
        log.info(stringBuilder.toString());
    }
}
