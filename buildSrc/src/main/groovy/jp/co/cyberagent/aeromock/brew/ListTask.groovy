package jp.co.cyberagent.aeromock.brew

import org.gradle.api.tasks.TaskAction

/**
 * Task to show installed versions on local.
 * @author stormcat24
 */
class ListTask extends BaseTask {

    @TaskAction
    def process() {

        def installedVersions = applicationDir.toFile().listFiles().findAll {
            it.getName() != "current" && it.isDirectory()
        }.collect {it.getName()}

        println("\ninstalled versions, as follows.")
        installedVersions.each { version ->
            if (currentVersion != null && version == currentVersion) {
                println(" \u001b[32m$version\u001b[00m")
            } else {
                println(" $version")
            }
        }

    }
}
