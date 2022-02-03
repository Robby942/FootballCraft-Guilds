package me.Robay.guilds.actions;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.Robay.guilds.Hikari;
import me.Robay.guilds.Setting;

import java.sql.PreparedStatement;

public class CreateGuild {

	
	public CreateGuild(Player player, String guildName) {

		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
		
		addGuildToMySQL(player, guildName, format.format(now));

	}

	public void addGuildToMySQL(Player leader, String guildName, String dateCreated) {
	
	
		try {

			PreparedStatement ps = Hikari.conn
					.prepareStatement("INSERT " + Hikari.guildTable + " (NAME,LEADER,EXP,LEVEL,DATE,TOTAL_EXP) VALUES (?,?,?,?,?,?)");
			ps.setString(1, guildName);
			ps.setString(2, leader.getUniqueId().toString());
			ps.setInt(3, 0);
			ps.setInt(4, 1);
			ps.setString(5, dateCreated);
			ps.setInt(6, 0);
			ps.executeUpdate();
			PreparedStatement ps2 = Hikari.conn.prepareStatement("UPDATE " + Hikari.playerTable + " SET GUILD = ?,RANK = 'Leader' WHERE UUID='" + leader.getUniqueId().toString()+ "'");
			ps2.setString(1, guildName);
			ps2.executeUpdate();
			

		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
				Setting.messagePrefix + "&6" + leader.getName() + " &7has created the guild &6" + guildName));
	
	}
}