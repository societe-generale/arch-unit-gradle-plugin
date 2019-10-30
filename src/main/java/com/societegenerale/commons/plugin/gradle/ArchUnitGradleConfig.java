package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.model.ApplyOn;
import com.societegenerale.commons.plugin.model.ConfigurableRule;
import com.societegenerale.commons.plugin.model.Rules;
import groovy.lang.Closure;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ArchUnitGradleConfig {

    private final NamedDomainObjectContainer<ContainerPreConfiguredRules> containerPreConfiguredRules;
    private final NamedDomainObjectContainer<ContainerConfigurableRules> containerConfigurableRules;

    private Logger log = LoggerFactory.getLogger(ArchUnitGradleConfig.class);

    private Project project;

    private boolean skip = false;

    public ArchUnitGradleConfig(Project project,NamedDomainObjectContainer<ContainerPreConfiguredRules> archUnitGradleExtensionPreConfiguredRules,NamedDomainObjectContainer<ContainerConfigurableRules> archUnitGradleExtensionConfigurableRules) {
        this.containerPreConfiguredRules =archUnitGradleExtensionPreConfiguredRules;
        this.containerConfigurableRules =archUnitGradleExtensionConfigurableRules;
        this.project = project;
    }

    public void configureArchUnitGradleExtensionPreConfiguredRules(Closure closure){
        containerPreConfiguredRules.configure(closure);
    }

    public void configureArchUnitGradleExtensionConfigurableRules(Closure closure){
        containerConfigurableRules.configure(closure);
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    private  List<String> preconfiguredRules(){

        List<String> preConfiguredRules = containerPreConfiguredRules.stream().map(ContainerPreConfiguredRules::getRule).collect(Collectors.toList());

        log.info("extracting {} pre-configured rules from config",preConfiguredRules.size());

        return preconfiguredRules();
    }

    private List<ConfigurableRule> configurableRules(){

        List<ConfigurableRule> configurableRules =containerConfigurableRules.stream().map(r -> new ConfigurableRule(r.getRule(),
                                                                                                                    new ApplyOn(r.getApplyOnPackageName(),r.getApplyOnScope()),
                                                                                                                    r.getCheck(),
                                                                                                                    false))
                                                                            .collect(Collectors.toList());

        log.info("extracting {} configurable rules from config",configurableRules.size());

        return configurableRules;
    }

    public Rules getRules() {

        return new Rules(preconfiguredRules(),configurableRules());
    }
  
    public boolean isSkip() {
        return skip;
    }

    public String getProjectPath() {
        return project.getProjectDir().toString();
    }

}
