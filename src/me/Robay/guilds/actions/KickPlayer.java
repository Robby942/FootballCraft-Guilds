package me.Robay.guilds.actions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.Robay.guilds.Hikari;
import me.Robay.guilds.Setting;

public class KickPlayer {

	public KickPlayer(Player playerKicked, Player playerKicking) {

		kickPlayerInMySQL(playerKicked, playerKicking);
	}

	public void kickPlayerInMySQL(Player playerKicked, Player playerKicking) {
		
		try {

			PreparedStatement ps  = Hikari.conn.prepareStatement("UPDATE " + Hikari.playerTable + " SET GUILD = ?,RANK = 'N/A' WHERE UUID='" + playerKicked.getUniqueId().toString()+ "'");
			ps.setString(1, "N/A");
			ps.executeUpdate();
			

		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		playerKicking.sendMessage(ChatColor.translateAlternateColorCodes('&', Setting.messagePrefix + "&7You have kicked &6" + playerKicked.getName() + " &7from your guild."));
		playerKicked.sendMessage(ChatColor.translateAlternateColorCodes('&', Setting.messagePrefix + "&7You have been kicked from your guild."));
		
	}


	

}
