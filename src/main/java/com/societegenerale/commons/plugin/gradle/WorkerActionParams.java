package com.societegenerale.commons.plugin.gradle;

import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

public interface WorkerActionParams extends WorkParameters {
    Property<ArchUnitGradleConfig> getArchUnitGradleConfig();
}
