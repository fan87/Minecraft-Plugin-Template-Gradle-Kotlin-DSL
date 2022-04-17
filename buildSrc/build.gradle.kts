import java.net.URI

plugins {
    kotlin("jvm") version "1.6.20"
}

repositories {
    mavenCentral()
    dragonCommissionRepository()
}

dependencies {
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.1.0.202203080745-r")
    implementation("nl.vv32.rcon:rcon:1.1.0")
    implementation("org.ow2.asm:asm-util:9.3")
    implementation("org.ow2.asm:asm:9.3")

    implementation("org.yaml:snakeyaml:1.30")
}



fun RepositoryHandler.dragonCommissionRepository() {
    maven {
        name = "dragon-commissions-private"
        url = URI("https://maven.pkg.github.com/DragonCommissions/Maven-Repository")
        credentials {
            username = System.getenv("GITHUB_USERNAME")
            password = System.getenv("GITHUB_PASSWORD")
        }
    }
}