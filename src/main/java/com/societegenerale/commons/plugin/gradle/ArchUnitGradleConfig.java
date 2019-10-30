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

    NamedDomainObjectContainer<ContainerPreConfiguredRules> ContainerPreConfiguredRules;
    NamedDomainObjectContainer<ContainerConfigurableRules> ContainerConfigurableRules;

    private Project project;

    private Rules rules = new Rules();

    private boolean skip = false;

    public ArchUnitGradleConfig(Project project,NamedDomainObjectContainer ArchUnitGradleExtensionPreConfiguredRules,NamedDomainObjectContainer ArchUnitGradleExtensionConfigurableRules) {
        this.ContainerPreConfiguredRules =ArchUnitGradleExtensionPreConfiguredRules;
        this.ContainerConfigurableRules =ArchUnitGradleExtensionConfigurableRules;
        this.project = project;
    }

    public void configureArchUnitGradleExtensionPreConfiguredRules(Closure closure){
        ContainerPreConfiguredRules.configure(closure);
        //System.out.println(ContainerPreConfiguredRules.getByName("Rule1").getName()+" HALLO");
    }

    public void configureArchUnitGradleExtensionConfigurableRules(Closure closure){
        ContainerConfigurableRules.configure(closure);
    }

    public void setPreConfiguredRules(List<String> preConfiguredRules) {
        this.rules.setPreConfiguredRules(preConfiguredRules);
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public void updatePreConfiguredRules(){
        List<String> preConfiguredRules= new ArrayList<>();
        for (int i = 0; i< ContainerPreConfiguredRules.size(); i++){
            Object[] preConfiguredRulesArray = ContainerPreConfiguredRules.toArray();
            ContainerPreConfiguredRules containerPreConfiguredRules =(ContainerPreConfiguredRules) preConfiguredRulesArray[i];
            preConfiguredRules.add(containerPreConfiguredRules.getRule());
        }
        this.setPreConfiguredRules(preConfiguredRules);
        System.out.println(preConfiguredRules.size()+" pre configured rules have been set!");
    }

    public void updateConfigurableRules(){
        List<ConfigurableRule> configurableRules = new ArrayList<>();
        for(int i = 0; i< ContainerConfigurableRules.size(); i++){
            Object[] configurableRulesArray = ContainerConfigurableRules.toArray();
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
