package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.Log;
import com.societegenerale.commons.plugin.model.Rules;
import com.societegenerale.commons.plugin.service.RuleInvokerService;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;

public abstract class ArchUnitRulesTask extends DefaultTask {

    @InputFiles
    @Classpath
    public abstract ConfigurableFileCollection getClasspath();

    @Inject
    protected abstract WorkerExecutor getWorkerExecutor();

    private ArchUnitGradleConfig archUnitGradleConfig;

    private Log logger = new GradleLogAdapter(LoggerFactory.getLogger(ArchUnitRulesTask.class));

    private static final String PREFIX_ARCH_VIOLATION_MESSAGE = "ArchUnit Gradle plugin reported architecture failures listed below :";

    @Inject
    public ArchUnitRulesTask(ArchUnitGradleConfig archUnitGradleConfig) {
         this.archUnitGradleConfig = archUnitGradleConfig;
    }

    @TaskAction
    public void checkRules() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        if (archUnitGradleConfig.isSkip()) {
            logger.warn("Rule checking has been skipped!");
            return;
        }

        Rules rules = archUnitGradleConfig.getRules();

        if (!rules.isValid()) {
            throw new GradleException("Arch unit Gradle Plugin should have at least one preconfigured/configurable rule!");
        }



        RuleInvokerService ruleInvokerService = new RuleInvokerService(new GradleLogAdapter(LoggerFactory.getLogger(RuleInvokerService.class)),
                                                                        new GradleScopePathProvider(archUnitGradleConfig),
                                                                        archUnitGradleConfig.getExcludedPaths());

        WorkQueue queue = getWorkerExecutor().classLoaderIsolation(spec -> spec.getClasspath().from(getClasspath()));
        queue.submit(WorkerAction.class, params -> {
                params.getRuleInvokerService().set(ruleInvokerService);
                params.getRules().set(rules);
        });

/*
        ruleFailureMessage = ruleInvokerService.invokeRules(rules);

        if (!StringUtils.isEmpty(ruleFailureMessage)) {
            throw new GradleException(PREFIX_ARCH_VIOLATION_MESSAGE + " \n" + ruleFailureMessage);
        }

 */
    }

}
