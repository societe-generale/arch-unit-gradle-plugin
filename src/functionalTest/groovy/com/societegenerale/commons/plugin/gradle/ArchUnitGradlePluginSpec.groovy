package com.societegenerale.commons.plugin.gradle

import org.gradle.testkit.runner.TaskOutcome
import org.gradle.testkit.runner.UnexpectedBuildSuccess

class ArchUnitGradlePluginSpec extends AbstractGradleRunnerSpecification {
    def setup() {
        groovySettingsFile << """
            rootProject.name = "testProject"
        """
        groovyBuildscriptFile << """
            plugins {
                id("java")
                id("com.societegenerale.commons.arch-unit-gradle-plugin")
            }
        """
    }

    def 'plugin fails to apply without java plugin'() {
        setup:
        groovyBuildscriptFile.text = """
            plugins {
                id("com.societegenerale.commons.arch-unit-gradle-plugin")
            }
        """

        when:
        createRunner("projects").buildAndFail()

        then:
        notThrown(UnexpectedBuildSuccess)
    }

    def 'plugin applies'() {
        when:
        def runner = createRunner("projects")
        def result = runner.build()

        then:
        result.task(":projects").outcome == TaskOutcome.SUCCESS
    }
}
