package com.societegenerale.commons.plugin.gradle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import com.societegenerale.commons.plugin.model.ApplyOn;
import com.societegenerale.commons.plugin.model.ConfigurableRule;
import com.societegenerale.commons.plugin.model.Rules;
import com.societegenerale.commons.plugin.service.RuleInvokerService;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.GradleException;
import org.gradle.api.provider.Provider;
import org.gradle.workers.WorkAction;
import org.slf4j.LoggerFactory;

public abstract class WorkerAction implements WorkAction<WorkerActionParams> {

    private static final String PREFIX_ARCH_VIOLATION_MESSAGE = "ArchUnit Gradle plugin reported architecture failures listed below :";

    @Override
    public void execute() {
        WorkerActionParams params = getParameters();
        RuleInvokerService ruleInvokerService = new RuleInvokerService(new GradleLogAdapter(LoggerFactory.getLogger(RuleInvokerService.class)),
                new GradleScopePathProvider(params.getMainClassesPath().get(), params.getTestClassesPath().get()),
                params.getExcludedPaths().get());

        String ruleFailureMessage = "";

        try {
            List<ConfigurableRule> configurableRules = params.getConfigurableRules().map(crules -> crules.stream()
                    .map(crule -> new ConfigurableRule(
                            crule.rule,
                            new ApplyOn(crule.applyOn.packageName, crule.applyOn.scope),
                            crule.checks,
                            crule.skip))
                    .collect(Collectors.toList()))
                    .get();
            Rules rules = new Rules(params.getPreConfiguredRules().get(), configurableRules);
            ruleFailureMessage = ruleInvokerService.invokeRules(rules);

            if (!StringUtils.isEmpty(ruleFailureMessage)) {
                throw new GradleException(PREFIX_ARCH_VIOLATION_MESSAGE + " \n" + ruleFailureMessage);
            }

        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
