package com.societegenerale.commons.plugin.gradle;

import java.io.Serializable;
import java.util.ArrayList;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

public interface WorkerActionParams extends WorkParameters {
    Property<String> getMainClassesPath();

    Property<String> getTestClassesPath();

    Property<String> getProjectBuildDirPath();

    ListProperty<String> getExcludedPaths();

    ListProperty<String> getPreConfiguredRules();

    ListProperty<WorkerActionParams.ConfigurableRule> getConfigurableRules();

    class ConfigurableRule implements Serializable {
        static final long serialVersionUID = 1L;
        String rule;
        ApplyOn applyOn;
        ArrayList<String> checks = new ArrayList<>();
        boolean skip;
    }

    class ApplyOn implements Serializable {
        static final long serialVersionUID = 1L;
        String packageName;
        String scope;
    }

}
