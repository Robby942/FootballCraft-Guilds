package me.Robay.guilds;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	public static File configFile;
	public static FileConfiguration config;

	public static File mysqlFile;
	public static FileConfiguration mysql;

	public static void setup(Main main) {

		configFile = new File(main.getDataFolder(), "config.yml");
		mysqlFile = new File(main.getDataFolder(), "mysql.yml");

		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			main.saveResource("config.yml", false);
		}

		if (!mysqlFile.exists()) {
			mysqlFile.getParentFile().mkdirs();
			main.saveResource("mysql.yml", false);
		}

		config = new YamlConfiguration();
		mysql = new YamlConfiguration();

		try {

			config.load(configFile);
			mysql.load(mysqlFile);

		} catch (IOException | InvalidConfigurationException e) {

			e.printStackTrace();
		}

	}

}
