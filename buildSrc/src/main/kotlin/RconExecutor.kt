
import nl.vv32.rcon.Rcon
import org.gradle.api.Project
import java.io.IOException


object RconExecutor {

    fun execute(project: Project , vararg commands: String): Array<String> {
        val config = project.extensions.getByType(RconAuthExtension::class.java)
        Rcon.open(config.host.get(), config.port.get()).use { rcon ->
            if (rcon.authenticate(config.password.get())) {
                return Array(commands.size) { i ->
                    rcon.sendCommand(commands[i])
                }
            } else {
                throw IOException("Failed to authenticate!")
            }
        }
    }

}