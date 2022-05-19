package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.model.Rules;
import com.societegenerale.commons.plugin.service.RuleInvokerService;
import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

public interface WorkerActionParams extends WorkParameters {
     Property<RuleInvokerService> getRuleInvokerService();
    Property<Rules> getRules();
}
