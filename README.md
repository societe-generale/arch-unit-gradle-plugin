# ArchUnit Gradle plugin

[![Build Status](https://travis-ci.org/societe-generale/arch-unit-gradle-plugin.svg?branch=master)](https://travis-ci.org/societe-generale/arch-unit-gradle-plugin)

A gradle wrapper around ArchUnit, to easily share and enforce architecture rules across projects. This plugin is under construction and the java tests are still missing!

The option to use configurable rules has been added but has not been tested yet!

This plugin is based on https://github.com/societe-generale/arch-unit-build-plugin-core.

To use the plugin, some steps have to be done in the `build.gradle` file in the project where you want to use this plugin :

1. Your build script has to contain following code, to set a dependency on the plugin:

```
   buildscript {
       dependencies {
           classpath "com.societegenerale.commons.plugin.gradle:arch-unit-gradle-plugin:1.0.1-SNAPSHOT"
       }
       repositories {
           mavenCentral()
           mavenLocal()
       }
   }
```

2. You have to apply the java plugin and the ArchUnitGradlePlugin, then configure it:

```
    allprojects {
    
        apply plugin: 'java'
        apply plugin: 'com.societegenerale.commons.plugin.gradle.ArchUnitGradlePlugin'
    
        archUnit{
            configureArchUnitGradleExtensionPreConfiguredRules {
                        preConfiguredRule1 {
                            rule = "com.societegenerale.commons.plugin.rules.NoStandardStreamRuleTest"
                        }
                        preConfiguredRule2 {
                            rule = "com.societegenerale.commons.plugin.rules.NoJodaTimeRuleTest"
                        }
                        preConfiguredRule3 {
                            rule = "com.societegenerale.commons.plugin.rules.NoPowerMockRuleTest"
                        }
                     }

            configureArchUnitGradleExtensionConfigurableRules {
                        configurableRule1 {
                            applyOnPackageName = "packageName1"
                            applyOnScope = "scope1"
                            rule = "rule1"
                            check = ["check1a", "check1b"]
                        }
                        configurableRule2 {
                            applyOnPackageName = "packageName2"
                            applyOnScope = "scope2"
                            rule = "rule2"
                            check = ["check2a", "check2b"]
                        }
                    }
        }
    }
    
```
  
4. Build your project with gradlew clean build : if some of your code is not compliant with the rules defined, the build will fail, pointing you to the rule and the class that is violating it.

### Releasing a new version of the plugin

(to publish in local repo during tests, use gradle -Dmaven.repo.local=.m2/repository publishToMavenLocal)

- make sure everything is committed, then run `gradlew release -Prelease.useAutomaticVersion=true` : 

  - the -SNAPSHOT will be removed
  - the version will be tagged and committed
  - the version will be incremented with -SNAPSHOT  
  
