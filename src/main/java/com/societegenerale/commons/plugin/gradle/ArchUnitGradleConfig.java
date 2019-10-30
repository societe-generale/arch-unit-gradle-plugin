package com.societegenerale.commons.plugin.gradle;

import com.societegenerale.commons.plugin.model.ApplyOn;
import com.societegenerale.commons.plugin.model.ConfigurableRule;
import com.societegenerale.commons.plugin.model.Rules;
import groovy.lang.Closure;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;

import java.util.ArrayList;
import java.util.List;

public class ArchUnitGradleConfig {

    private final NamedDomainObjectContainer<ContainerPreConfiguredRules> containerPreConfiguredRules;
    private final NamedDomainObjectContainer<ContainerConfigurableRules> containerConfigurableRules;

    private Project project;

    private Rules rules = new Rules();

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

    public void setPreConfiguredRules(List<String> preConfiguredRules) {
        this.rules.setPreConfiguredRules(preConfiguredRules);
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public void updatePreConfiguredRules(){
        List<String> preConfiguredRules= new ArrayList<>();
        for (int i = 0; i< containerPreConfiguredRules.size(); i++){
            Object[] preConfiguredRulesArray = containerPreConfiguredRules.toArray();
            ContainerPreConfiguredRules containerPreConfiguredRules =(ContainerPreConfiguredRules) preConfiguredRulesArray[i];
            preConfiguredRules.add(containerPreConfiguredRules.getRule());
        }
        this.setPreConfiguredRules(preConfiguredRules);
        System.out.println(preConfiguredRules.size()+" pre configured rules have been set!");
    }

    public void updateConfigurableRules(){
        List<ConfigurableRule> configurableRules = new ArrayList<>();
        for(int i = 0; i< containerConfigurableRules.size(); i++){
            Object[] configurableRulesArray = containerConfigurableRules.toArray();
            ContainerConfigurableRules containerConfigurableRules =(ContainerConfigurableRules) configurableRulesArray[i];

            ConfigurableRule configurableRule= new ConfigurableRule();
            ApplyOn applyOn= new ApplyOn();
            applyOn.setPackageName(containerConfigurableRules.getApplyOnPackageName());
            applyOn.setScope(containerConfigurableRules.getApplyOnScope());

            configurableRule.setRule(containerConfigurableRules.getRule());
            configurableRule.setChecks(containerConfigurableRules.getCheck());
            configurableRule.setApplyOn(applyOn);
            configurableRules.add(configurableRule);
        }
        this.setConfigurableRules(configurableRules);
        System.out.println(configurableRules.size()+" configurable rules have been set!");
    }

    public Rules getRules() {
        return rules;
    }
  
    public boolean isSkip() {
        return skip;
    }

    public String getProjectPath() {
        return project.getProjectDir().toString();
    }

    public List getPreConfiguredRules() {
        return this.rules.getPreConfiguredRules();
    }

    public List getConfigurableRules() {
        return this.rules.getConfigurableRules();
    }

    public void setConfigurableRules(List<ConfigurableRule> configurableRules){
        this.rules.setConfigurableRules(configurableRules);
    }

}
