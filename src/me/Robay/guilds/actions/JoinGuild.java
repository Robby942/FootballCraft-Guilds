package me.Robay.guilds.actions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.Robay.guilds.Hikari;
import me.Robay.guilds.Setting;

public class JoinGuild {
	
	public JoinGuild(Player playerJoining, String guildInvitedTo) {
		
		
		addPlayerToGuildInMYSQL(playerJoining, guildInvitedTo);
		
	}
	
	
	public void addPlayerToGuildInMYSQL(Player playerJoining, String guildInvitedTo) {
		

		try {

			PreparedStatement ps  = Hikari.conn.prepareStatement("UPDATE " + Hikari.playerTable + " SET GUILD = ?,RANK = 'Member' WHERE UUID='" + playerJoining.getUniqueId().toString()+ "'");
			ps.setString(1, guildInvitedTo);
			ps.executeUpdate();
			

		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		playerJoining.sendMessage(ChatColor.translateAlternateColorCodes('&', Setting.messagePrefix + "&7You have successfully joined the guild &6" + guildInvitedTo));
		
	}

}
