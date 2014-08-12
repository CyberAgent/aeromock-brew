package jp.co.cyberagent.aeromock.brew

import org.gradle.api.tasks.TaskAction

import java.nio.file.Files

/**
 * Task to download specified version, and install on local.
 * @author stormcat24
 */
class UseTask extends BaseTask {

    def coreModules = [
        "aeromock-server",
        "aeromock-cli",
    ]

    @TaskAction
    def process() {

        def aeromockVersion = project.properties["aeromockVersion"]

        if (aeromockVersion == null || aeromockVersion.isEmpty()) {
            throw new AeromockBrewJobFailedException("specified target version.")
        }

        def resolveVersion = null
        if (aeromockVersion == "latest") {
            resolveVersion = "latest.release"
        } else {
            resolveVersion = aeromockVersion
        }

        // configuration settings
        project.configurations.create("core")
        coreModules.each { name ->
            project.dependencies.add("core", project.dependencies.create("ameba.aeromock:${name}_2.11:$resolveVersion"))
        }

        // download modules.txt
        def modulesFile = aeromockDir.resolve(".modules").toFile()
        project.download {
            src "${githubRootRaw}/modules.txt"
            dest modulesFile
            onlyIfNewer true
        }

        if (!modulesFile.exists()) {
            throw new AeromockBrewJobFailedException("Failed to download module info.")
        }

        def templateModules = []
        modulesFile.eachLine { templateModules += it }

        templateModules.each { name ->
            project.configurations.create(name)
            project.dependencies.add(name, project.dependencies.create("ameba.aeromock:${name}_2.11:$resolveVersion"))
        }

        // copy core modules
        def coreDependencies = []
        def foundVersion = fetchFoundVersion()
        if (foundVersion == null) {
            throw new AeromockBrewJobFailedException("$aeromockVersion not found.")
        }

        println("Found $foundVersion")

        def targetVersionDir = applicationDir.resolve(foundVersion)
        if (!targetVersionDir.toFile().exists()) {

            project.configurations.getByName("core").files.each { file ->
                project.copy {
                    from file
                    into targetVersionDir.resolve("lib/core").toFile()
                }
                coreDependencies += file.getName()
            }

            // copy template modules
            def templateLibDir = targetVersionDir.resolve("lib/templates")
            templateModules.each { name ->
                def targetLibDir = templateLibDir.resolve(name)
                project.configurations.getByName(name).files.findAll {
                    !coreDependencies.contains(it.getName())
                }.each { file ->
                    project.copy {
                        from file
                        into targetLibDir.toFile()
                    }
                }
            }

            // copy resources
            project.copy {
                from 'resources'
                into targetVersionDir.toFile()
            }

        }

        Files.deleteIfExists(currentSymlink)
        Files.createSymbolicLink(currentSymlink, targetVersionDir)

        println("Prepared Aeromock $foundVersion")
    }

    private def fetchFoundVersion() {
        try {
            def versions = project.configurations.getByName("core").files
                .findAll { it.getName().contains("aeromock-server") }
                .collect {
                def matcher = it.getName() =~ /^aeromock-server_[0-9\.]+-(.+)\.jar$/
                if (matcher.matches()) {
                    return matcher.group(1)
                } else {
                    return null
                }
            }
            return versions.isEmpty() ? null : versions.first()
        } catch (Exception e) {
            return null
        }
    }
}
