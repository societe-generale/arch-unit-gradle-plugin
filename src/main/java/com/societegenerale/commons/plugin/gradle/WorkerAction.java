package com.societegenerale.commons.plugin.gradle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.societegenerale.commons.plugin.model.Rules;
import com.societegenerale.commons.plugin.service.RuleInvokerService;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.GradleException;
import org.gradle.workers.WorkAction;

public abstract class WorkerAction implements WorkAction<WorkerActionParams> {

    private static final String PREFIX_ARCH_VIOLATION_MESSAGE = "ArchUnit Gradle plugin reported architecture failures listed below :";

    @Override
    public void execute() {
        RuleInvokerService ruleInvokerService = getParameters().getRuleInvokerService().get();
        Rules rules = getParameters().getRules().get();

        String ruleFailureMessage = "";

        try {

            ruleFailureMessage = ruleInvokerService.invokeRules(rules);

            if (!StringUtils.isEmpty(ruleFailureMessage)) {
                throw new GradleException(PREFIX_ARCH_VIOLATION_MESSAGE + " \n" + ruleFailureMessage);
            }
    /*
            Class<?> c = Thread.currentThread().getContextClassLoader().loadClass(className);
            Method greetingMethod = c.getMethod("getGreeting");
            Object o = c.newInstance();
            System.out.println(greetingMethod.invoke(o));
     */

        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
