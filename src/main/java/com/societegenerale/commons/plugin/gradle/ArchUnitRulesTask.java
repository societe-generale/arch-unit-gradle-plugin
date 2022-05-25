package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.Log;
import com.societegenerale.commons.plugin.model.Rules;
import com.societegenerale.commons.plugin.service.RuleInvokerService;
import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;

public class ArchUnitRulesTask extends DefaultTask {

    private ArchUnitGradleConfig archUnitGradleConfig;

    private File projectBuildDir;

    private Log logger = new GradleLogAdapter(LoggerFactory.getLogger(ArchUnitRulesTask.class));

    private static final String PREFIX_ARCH_VIOLATION_MESSAGE = "ArchUnit Gradle plugin reported architecture failures listed below :";

    @Inject
    public ArchUnitRulesTask(ArchUnitGradleConfig archUnitGradleConfig) {
         this.archUnitGradleConfig = archUnitGradleConfig;
    }

    @TaskAction
    public void checkRules() throws InstantiationException, IllegalAccessException, InvocationTargetException {

        if (archUnitGradleConfig.isSkip()) {
            logger.warn("Rule checking has been skipped!");
            return;
        }

        Rules rules = archUnitGradleConfig.getRules();

        if (!rules.isValid()) {
            throw new GradleException("Arch unit Gradle Plugin should have at least one preconfigured/configurable rule!");
        }

        String ruleFailureMessage = "";

        try {

            RuleInvokerService ruleInvokerService = new RuleInvokerService(new GradleLogAdapter(LoggerFactory.getLogger(RuleInvokerService.class)),
                                                                            new GradleScopePathProvider(archUnitGradleConfig),
                                                                            archUnitGradleConfig.getExcludedPaths(),
                                                                            projectBuildDir.getCanonicalPath());

            ruleFailureMessage = ruleInvokerService.invokeRules(rules);
        } catch (IOException e) {
            throw new RuntimeException("problem with the build directory",e);
        }

        if (!StringUtils.isEmpty(ruleFailureMessage)) {
            throw new GradleException(PREFIX_ARCH_VIOLATION_MESSAGE + " \n" + ruleFailureMessage);
        }
    }

    public void setProjectBuildDir(File projectBuildDir) {
        this.projectBuildDir = projectBuildDir;
    }


}
