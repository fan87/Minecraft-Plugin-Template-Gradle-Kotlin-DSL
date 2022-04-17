import org.gradle.api.provider.Property

abstract class RconAuthExtension {

    abstract val host: Property<String>
    abstract val port: Property<Int>
    abstract val password: Property<String>

    init {
        host.convention("localhost")
        port.convention(25575)
    }

}