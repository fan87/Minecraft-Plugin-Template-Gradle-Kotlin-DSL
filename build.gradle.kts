// I left a lot of comments in this file because some people don't know what they do
// If you think that build script without comments is cleaner, you can remove them.


import com.dragoncommissions.placeholderplugin.tasks.PlaceHolderTask
import com.dragoncommissions.remapper.RemapTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import tasks.MinecraftSingletonTask
import tasks.ReadMeChapterGenerator
import java.net.URI

// Do you want to use NMS? If you set this to true, it will require you to run Build Tool.
val enableNMS = false
// What's the Minecraft Version? If you have enableNMS on, it must be the exact version, or at least same major version.
val minecraftVersion = "1.18.1"


plugins {
    // Remove me if you don't want to use kotlin, but keeping it here makes no differences
    kotlin("jvm") version "1.6.20"

    // Equivalent to `maven-shade-plugin` in Maven
    id("com.github.johnrengelman.shadow") version "7.1.2"

    // [OPTIONAL] PlaceHolder Gradle Plugin that allows you to use placeholders
    id("com.dragoncommissions.placeholder-plugin") version "1.3"
    id("com.dragoncommissions.minecraft-remapper-plugin") version "1.0"

    `maven-publish`
}


group = "me.fan87"
version = "1.0-SNAPSHOT"


publishing {
    repositories {
        fan87Public()
    }
}


//<editor-fold desc="Repositories" defaultstate="collapsed">
repositories {
    mavenCentral()

    // Required by Spigot
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")

    // Makes your life easier
    mavenLocal()

    // My maven repository
    fan87Public()
}

fun RepositoryHandler.fan87Public() =
    maven {
        url = URI("https://maven.pkg.github.com/fan87/Public-Maven-Repository")
        credentials {
            username = System.getenv("GITHUB_USERNAME")
            password = System.getenv("GITHUB_PASSWORD")
        }
    }
//</editor-fold>
//<editor-fold desc="Dependencies" defaultstate="collapsed">
dependencies {
    // Remove me if you don't want to use kotlin
    implementation(kotlin("stdlib"))

    // Change the version if you want to
    // Also, change the name to `spigot` if you want to do NMS, but it will require you to run the build tool.
    compileOnly("org.spigotmc:spigot${if (enableNMS) "" else "-api"}:${minecraftVersion}-R0.1-SNAPSHOT${if (enableNMS) ":remapped-mojang" else ""}") {
        this.isChanging = false
    }

}
//</editor-fold>
//<editor-fold desc="Configure Kotlin" defaultstate="collapsed">
configureKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
//</editor-fold>
//<editor-fold desc="Register Tasks for Debugging" defaultstate="collapsed">
apply<RconPlugin>()
tasks.register<DefaultTask>("reloadServer") {
    group = "server control"
    dependsOn("copyPlugin")
    doLast {
        try {
            println("Server responded with " + RconExecutor.execute(project, "reload")[0])
            // You can change `reload` to any command you want!
        } catch (ignored: Exception) {

        }
    }
}
tasks.register<DefaultTask>("unloadPlugin") {
    group = "server control"
    mustRunAfter("build")
    doLast {
        try {
            println("Server responded with " + RconExecutor.execute(project, "plugman unload GradlePluginTemplate")[0])
        } catch (ignored: Exception) {

        }
    }
}
tasks.register<DefaultTask>("loadPlugin") {
    group = "server control"
    dependsOn("copyPlugin")
    doLast {
        try {
            println("Server responded with " + RconExecutor.execute(project, "plugman load GradlePluginTemplate")[0])
        } catch (ignored: Exception) {

        }
    }
}
// Hint: If you want a new task that executes another command, just copy the code above and replace some texts
//
// Example:
// tasks.register<DefaultTask>("stopServer") {
//     doLast {
//         println("Server responded with " + RconExecutor.execute(project, "stop")[0])
//         // You can also catch it!
//     }
// }
//
// If you want to execute multiple commands:
// tasks.register<DefaultTask>("reloadPlugin") {
//     doLast {
//         for (s in RconExecutor.execute(
//             project,
//             "plugman unload GradlePluginTemplate",
//             "plugman load GradlePluginTemplate"
//         )) {
//             println("Server responded with $s")
//         }
//         // You can change `reload` to any command you want!
//     }
// }

tasks.register<Copy>("copyPlugin") {
    group = "server control"
    mustRunAfter("unloadPlugin")
    if (enableNMS) {
        from(tasks.withType(RemapTask::class.java).first().archiveFile)
    } else {
        from(tasks.withType(PlaceHolderTask::class.java).first().archiveFile)
    }
    into(File(System.getenv("SERVER_DIR"), "plugins/"))
}
//</editor-fold>

tasks.register("debug") {
    description = "\"debug\" task will build the jar (including shading, placeholder and remap), then reload the " +
            "plugin if aviliable."
    group = "run configurations"
    dependsOn("build")
    dependsOn("kotlinSingleton")
    dependsOn("shadowJar")
    dependsOn("applyPlaceHolder")
    dependsOn("remap")
    dependsOn("unloadPlugin")
    dependsOn("copyPlugin")
    dependsOn("loadPlugin")
}

rconConfig {
    host.set("localhost") // OPTIONAL, you can delete this line safely
    port.set(25575) // OPTIONAL, you can delete this line safely
    password.set("rconpassword")  // TODO: Change password, Enable RCON, and change port/host if needed
    // If you don't want to use this feature, then it's not required
}



// PlaceHolder plugin is being maintained by Dragon Commissions, if you got any
// issue using placeholders, please let us know!
// More Info: https://github.com/DragonCommissions/placeholder-gradle-plugin
//<editor-fold desc="Register PlaceHolder Task" defaultstate="collapsed">
val applyPlaceHolderTask = tasks.register<PlaceHolderTask>("applyPlaceHolder") {
    mustRunAfter("kotlinSingleton")
    input.set(File(project.buildDir, "libs/" + project.name + "-" + project.version + "-all.jar"))
}
//</editor-fold>

// TODO: Add your own placeholders here!
//<editor-fold desc="PlaceHolders Registering" defaultstate="collapsed">
placeholders {
    val prefix: String = properties["placeholder.prefix"]?.toString()?:"GradlePluginTemplate.PlaceHolder."
    registerPlaceHolder(prefix + "Version", version.toString())
    registerPlaceHolder(prefix + "BuildTime", System.currentTimeMillis().toString())
    registerLazyPlaceHolder(prefix + "CommitID") {
        GitUtils.getCommitId(project.rootDir)
    }
}
//</editor-fold>

//<editor-fold desc="NMS Remapping" defaultstate="collapsed">
tasks.register<RemapTask>("remap") {
    onlyIf {
        enableNMS
    }
    group = "remap"
    dependsOn("build")
    mustRunAfter("applyPlaceHolder")
    targetVersion.set(minecraftVersion)
    archiveClassifier.set("remapped")
}
//</editor-fold>

// Things you don't have to change
//<editor-fold desc="Resources Filtering" defaultstate="collapsed">
tasks.processResources {
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand("version" to version)
    }
}
//</editor-fold>
//<editor-fold desc="Fat Jar" defaultstate="collapsed">
tasks.shadowJar {
    mustRunAfter("build")
}
//</editor-fold>
//<editor-fold desc="Kotlin Main Class Singleton" defaultstate="collapsed">
tasks.register<MinecraftSingletonTask>("kotlinSingleton") {
    mustRunAfter("shadowJar")
    input.set(File(project.buildDir, "libs/" + project.name + "-" + project.version + "-all.jar"))
}
//</editor-fold>
//<editor-fold desc="Readme Chapters Generator" defaultstate="collapsed">
tasks.register<ReadMeChapterGenerator>("generateTableOfContents") {

}
//</editor-fold>

// Some shortcuts that make the build script looks cleaner
//<editor-fold desc="Shortcuts" defaultstate="collapsed">
fun configureKotlin(kotlinCompile: Action<KotlinCompile>) {
    tasks.withType<KotlinCompile>().configureEach(kotlinCompile)
}
//</editor-fold>
