package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.Log;
import org.slf4j.Logger;


public class GradleLogAdapter implements Log {

    private Logger logger;

    public GradleLogAdapter(Logger logger){
        this.logger = logger;
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isDebugEnabled() { return logger.isDebugEnabled(); }

    @Override
    public void info(String s) {
        logger.info(s);
    }

    @Override
    public void debug(String s) {
        logger.debug(s);
    }

    @Override
    public void warn(String toString) {
        logger.warn(toString);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        logger.warn(s,throwable);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        logger.debug(s,throwable);
    }
}
