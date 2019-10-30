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
    public void info(String s) {
        logger.info(s);
    }

    @Override
    public void warn(String toString) {
        logger.warn(toString);
    }
}
