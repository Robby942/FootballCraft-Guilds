package me.Robay.guilds;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import com.zaxxer.hikari.HikariDataSource;

public class Hikari {

	public static HikariDataSource connHikari;
	public static Connection conn;
	
	public static String guildTable = "Guilds";
	public static String playerTable = "Players";

	public Hikari() {


	 	connHikari = new HikariDataSource();
	    connHikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		connHikari.addDataSourceProperty("serverName", Config.mysql.getString("host"));
		connHikari.addDataSourceProperty("port", Config.mysql.getInt("port"));
		connHikari.addDataSourceProperty("databaseName", Config.mysql.getString("database"));
		connHikari.addDataSourceProperty("user", Config.mysql.getString("username"));
		connHikari.addDataSourceProperty("password", Config.mysql.getString("password"));
	    connHikari.addDataSourceProperty("cachePrepStmts", "true"); 
        connHikari.addDataSourceProperty("rewriteBatchedStatements", "true");
        connHikari.addDataSourceProperty("prepStmtCacheSize", "275");
        connHikari.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        connHikari.addDataSourceProperty("cachePrepStmts", "true");

		if (!isConnected()) {
			
			
			PreparedStatement ps;
			try {

				conn = (Connection) connHikari.getConnection();
				ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + Hikari.guildTable + " "
						+ "(NAME VARCHAR(100),LEADER VARCHAR(100),EXP INT (100),LEVEL INT(100), DATE VARCHAR(100),TOTAL_EXP INT (100), PRIMARY KEY (NAME))");

				ps.executeUpdate();
				System.out.println("[Guilds] Processed " + Hikari.guildTable + " MySQL Table");

				ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + Hikari.playerTable + " "
						+ "(UUID VARCHAR(100),GUILD VARCHAR(100),CONTRIBUTED_EXP INT (100),RANK VARCHAR(100),NAME VARCHAR(100), PRIMARY KEY (UUID))");

				ps.executeUpdate();
				System.out.println("[Guilds] Processed " + Hikari.playerTable + " MySQL Table");

			} catch (SQLException e)

			{
				e.printStackTrace();
			}

		}
		
		else {
			System.out.println("[Guilds] There was an error connecting to the database, the plugin will be disabled.");
			Bukkit.getPluginManager().disablePlugin(Main.plugin);
			
			
		}

	}

	public void onServerShutdown() {

		connHikari.close();
	}

	public boolean isConnected() {

		return conn!= null;
	}

}