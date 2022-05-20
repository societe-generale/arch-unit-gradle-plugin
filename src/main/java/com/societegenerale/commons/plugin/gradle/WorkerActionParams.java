package com.societegenerale.commons.plugin.gradle;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

import java.io.Serializable;
import java.util.ArrayList;

public interface WorkerActionParams extends WorkParameters {
    Property<String> getMainClassesPath();

    Property<String> getTestClassesPath();

    ListProperty<String> getExcludedPaths();

    ListProperty<String> getPreConfiguredRules();

    ListProperty<ConfigurableRule> getConfigurableRules();

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
