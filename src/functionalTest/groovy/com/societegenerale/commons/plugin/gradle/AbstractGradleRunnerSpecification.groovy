package com.societegenerale.commons.plugin.gradle

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Path

abstract class AbstractGradleRunnerSpecification extends Specification {
    @TempDir
    protected Path projectDir

    protected Path groovySettingsFile
    protected Path kotlinSettingsFile
    protected Path groovyBuildscriptFile
    protected Path kotlinBuildscriptFile

    def setup() {
        groovySettingsFile = projectDir.resolve("settings.gradle")
        kotlinSettingsFile = projectDir.resolve("settings.gradle.kts")
        groovyBuildscriptFile = projectDir.resolve("build.gradle")
        kotlinBuildscriptFile = projectDir.resolve("build.gradle.kts")
    }

    protected GradleRunner createRunner(String... arguments) {
        return GradleRunner.create()
                .withPluginClasspath()
                .withProjectDir(projectDir.toFile())
                .withArguments(
                        '--warning-mode=fail',
                        *arguments
                )
    }
}
