import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

class RconPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.extensions.create("rconAuthSettings", RconAuthExtension::class.java)
    }

}

fun Project.rconConfig(action: Action<RconAuthExtension>) =
    extensions.findByType(RconAuthExtension::class.java).apply { this?.let { action.execute(it) } }