package com.societegenerale.commons.plugin.gradle;

import java.lang.reflect.InvocationTargetException;

import com.societegenerale.commons.plugin.service.RuleInvokerService;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.GradleException;
import org.gradle.workers.WorkAction;
import org.slf4j.LoggerFactory;

public abstract class WorkerAction implements WorkAction<WorkerActionParams> {

    private static final String PREFIX_ARCH_VIOLATION_MESSAGE = "ArchUnit Gradle plugin reported architecture failures listed below :";

    @Override
    public void execute() {

        ArchUnitGradleConfig archUnitGradleConfig = getParameters().getArchUnitGradleConfig().get();

        RuleInvokerService ruleInvokerService = new RuleInvokerService(new GradleLogAdapter(LoggerFactory.getLogger(RuleInvokerService.class)),
                new GradleScopePathProvider(archUnitGradleConfig),
                archUnitGradleConfig.getExcludedPaths());

        String ruleFailureMessage = "";

        try {

            ruleFailureMessage = ruleInvokerService.invokeRules(archUnitGradleConfig.getRules());

            if (!StringUtils.isEmpty(ruleFailureMessage)) {
                throw new GradleException(PREFIX_ARCH_VIOLATION_MESSAGE + " \n" + ruleFailureMessage);
            }

        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
