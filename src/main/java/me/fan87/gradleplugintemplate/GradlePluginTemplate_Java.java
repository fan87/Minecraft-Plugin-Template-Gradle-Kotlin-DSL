package me.fan87.gradleplugintemplate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class GradlePluginTemplate_Java extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin has been enabled!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + " - ${DARK_GRAY}Build Version: %GradlePluginTemplate.PlaceHolder.CommitID%");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + " - ${DARK_GRAY}Build Time: %GradlePluginTemplate.PlaceHolder.BuildTime%");
        // If you want to use Java, change the plugin.yml to this class,
        // then delete the one in `src/main/kotlin`, then you are ready to
        // go!
    }
}
