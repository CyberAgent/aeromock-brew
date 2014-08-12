package jp.co.cyberagent.aeromock.brew

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Define aeromock-brew tasks.
 * @author stormcat24
 */
class AeromockBrewPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("use", type: UseTask)
        project.task("list", type: ListTask)
        project.task("versions", type: VersionsTask)
        project.task("initVagrant", type: InitVagrantTask)
    }
}
