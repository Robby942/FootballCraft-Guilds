/*
* TODO:
*  Create less lag when accessing MySQL Database
*  Leave JSON stuff empty for now
*
*  
* 
*  
* 
*
*/

package me.Robay.guilds;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.Robay.guilds.commands.CommandGuild;
import me.Robay.guilds.listener.Events;

public class Main extends JavaPlugin {

	public static Plugin plugin;


	public void onEnable() {

		System.out.println("[Guilds] Guilds has been enabled!");
		registerCommands();
		plugin = this;
		Config.setup(this);
		setSettings(getConfig());
		Bukkit.getPluginManager().registerEvents(new Events(), this);
        new Hikari();
      new GuildExpansion().register();

	}

	public void onDisable() {

		System.out.println("[Guilds] Guilds has been disabled!");
		if (Hikari.conn != null)
			try {
				Hikari.conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}

	}

	public void registerCommands() {
		getCommand("guild").setExecutor((CommandExecutor) new CommandGuild());

	}

	public void setSettings(FileConfiguration config) {

		Setting.messagePrefix = getConfig().getString("Message Prefix");

	}

}
