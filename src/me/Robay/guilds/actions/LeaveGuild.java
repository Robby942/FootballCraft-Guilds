package me.Robay.guilds.actions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.Robay.guilds.Hikari;
import me.Robay.guilds.Setting;

public class LeaveGuild {

	public LeaveGuild(Player player) {

		try {

			PreparedStatement ps = Hikari.conn.prepareStatement(
					"UPDATE " + Hikari.playerTable + " SET GUILD = 'N/A', RANK = 'N/A', CONTRIBUTED_EXP = 0 WHERE UUID='"
							+ player.getUniqueId().toString() + "'");
			ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		player.sendMessage(
				ChatColor.translateAlternateColorCodes('&', Setting.messagePrefix + "&7You have left your guild"));

	}

}
