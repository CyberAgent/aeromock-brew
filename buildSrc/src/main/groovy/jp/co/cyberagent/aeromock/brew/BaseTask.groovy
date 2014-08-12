package jp.co.cyberagent.aeromock.brew

import org.gradle.api.internal.AbstractTask
import org.gradle.api.internal.NoConventionMapping

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Base task class. Support subclass.
 * @author stormcat24
 */
@NoConventionMapping
class BaseTask extends AbstractTask {

    Path getHomeDir() {
        return Paths.get(System.properties["user.home"])
    }

    Path getAeromockDir() {
        return homeDir.resolve(".aeromock")
    }

    Path getApplicationDir() {
        return aeromockDir.resolve("applications")
    }

    Path getVagrantDir() {
        return aeromockDir.resolve("vagrant")
    }

    Path getVagrantBackupDir() {
        return aeromockDir.resolve("vagrant_backup")
    }

    Path getCurrentSymlink() {
        return applicationDir.resolve("current")
    }

    String getCurrentVersion() {
        if (currentSymlink.toFile().exists()) {
            return currentSymlink.toRealPath().getFileName().toString()
        } else {
            null
        }
    }

    String getGithubRootRaw() {
        return "https://raw.githubusercontent.com/CyberAgent/aeromock/master"
    }
}
