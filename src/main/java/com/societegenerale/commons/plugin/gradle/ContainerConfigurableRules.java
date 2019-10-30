package com.societegenerale.commons.plugin.gradle;

import java.util.List;

public class ContainerConfigurableRules {

    private String applyOnPackageName;

    public String getApplyOnPackageName() {
        return applyOnPackageName;
    }

    public void setApplyOnPackageName(String applyOnPackageName) {
        this.applyOnPackageName = applyOnPackageName;
    }

    public String getApplyOnScope() {
        return applyOnScope;
    }

    public void setApplyOnScope(String applyOnScope) {
        this.applyOnScope = applyOnScope;
    }

    private String applyOnScope;
    private String rule;
    private List<String> check;
    private String name;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public List<String> getCheck() {
        return check;
    }

    public void setCheck(List<String> check) {
        this.check = check;
    }

    public ContainerConfigurableRules(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
