// Allows you to use custom plugins without putting it on Gradle's Plugin Portal
pluginManagement {
    repositories {
        // Default
        gradlePluginPortal()

        // Local
        mavenLocal()

        // Our repository
        maven {
            url = java.net.URI("https://maven.pkg.github.com/DragonCommissions/Maven-Repository")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_PASSWORD")
            }
        }
    }
}

rootProject.name = "gradle-plugin-template"
