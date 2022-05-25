package com.societegenerale.commons.plugin.gradle;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.societegenerale.commons.plugin.model.Rules;
import com.societegenerale.commons.plugin.service.RuleInvokerService;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.GradleException;
import org.gradle.workers.WorkAction;
import org.slf4j.LoggerFactory;

public abstract class WorkerAction implements WorkAction<WorkerActionParams> {

    private static final String PREFIX_ARCH_VIOLATION_MESSAGE = "ArchUnit Gradle plugin reported architecture failures listed below :";

    @Override
    public void execute() {
        WorkerActionParams params = getParameters();

        Collection<String> excludedPaths = params.getExcludedPaths().get();

        RuleInvokerService ruleInvokerService = new RuleInvokerService(
            new GradleLogAdapter(LoggerFactory.getLogger(RuleInvokerService.class)),
            new GradleScopePathProvider(params.getMainClassesPath().get(), params.getTestClassesPath().get()),
            excludedPaths,
            params.getProjectBuildDirPath().get());

        String ruleFailureMessage = "";

        try {

            Rules rules = new Rules(params.getPreConfiguredRules().get(), params.getConfigurableRules().get());
            ruleFailureMessage = ruleInvokerService.invokeRules(rules);

            if (!StringUtils.isEmpty(ruleFailureMessage)) {
                throw new GradleException(PREFIX_ARCH_VIOLATION_MESSAGE + " \n" + ruleFailureMessage);
            }

        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}