package me.fan87.gradleplugintemplate

import org.bukkit.Bukkit
import org.bukkit.ChatColor.*
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

object GradlePluginTemplate: JavaPlugin(), Listener {

    override fun onEnable() {
        Bukkit.getConsoleSender().sendMessage("${GREEN}Plugin has been enabled!")
        Bukkit.getConsoleSender().sendMessage(" - ${DARK_GRAY}Build Version: %GradlePluginTemplate.PlaceHolder.CommitID%")
        Bukkit.getConsoleSender().sendMessage(" - ${DARK_GRAY}Build Time: %GradlePluginTemplate.PlaceHolder.BuildTime%")

        // Since Kotlin supports Singleton (object key word),
        // you no longer have to do `GradlePluginTemplate.INSTANCE`
        //
        // You can now just type `GradlePluginTemplate` directly to access the instance anywhere
        // (You can't do this in other projects)
        server.pluginManager.registerEvents(GradlePluginTemplate, GradlePluginTemplate)
        GradlePluginTemplate.server // As you can see, everything became static
    }

}