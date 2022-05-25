package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.model.ConfigurableRule;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

public interface WorkerActionParams extends WorkParameters {
    Property<String> getMainClassesPath();

    Property<String> getTestClassesPath();

    Property<String> getProjectBuildDirPath();

    ListProperty<String> getExcludedPaths();

    ListProperty<String> getPreConfiguredRules();

    ListProperty<ConfigurableRule> getConfigurableRules();

}
