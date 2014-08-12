package jp.co.cyberagent.aeromock.brew

import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path
import java.text.SimpleDateFormat

/**
 * Task to initialize vagrant configuration.
 * @author stormcat24
 */
class InitVagrantTask extends BaseTask {

    @TaskAction
    def process() {

        if (Files.exists(vagrantDir)) {
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())
            Path backupDir = vagrantBackupDir.resolve(timestamp)
            project.copy {
                from (vagrantDir.toFile()) {
                    exclude ".vagrant.d"
                }
                into backupDir.toFile()
            }
            println("Saved Backup '${backupDir.toString()}'. ")
        }

        // copy vagrant directories
        project.copy {
            from 'vagrant'
            into vagrantDir.toFile()
        }
    }
}
