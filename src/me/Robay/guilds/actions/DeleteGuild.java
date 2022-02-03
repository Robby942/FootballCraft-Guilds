package me.Robay.guilds.actions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Robay.guilds.Main;
import me.Robay.guilds.Hikari;
import me.Robay.guilds.Setting;
import me.Robay.guilds.cache.DeleteQueue;

public class DeleteGuild {

	

	public DeleteGuild(Player player, String guildName, Boolean deletionStatus) {


		if (deletionStatus) {
			DeleteQueue.queue.add(guildName);
			deleteQueueRunnable(player, guildName);
		
		} else {
        
            
			DeleteQueue.queue.remove(guildName);
		

		}

	}

	public void deleteGuildFromMySQL(Player player, String guildName) {
		try {

			PreparedStatement ps = Hikari.conn.prepareStatement("DELETE FROM " + Hikari.guildTable + " WHERE LEADER=?");
			ps.setString(1, player.getUniqueId().toString());
			ps.executeUpdate();

			ps = Hikari.conn
					.prepareStatement("UPDATE " + Hikari.playerTable + " SET GUILD = ?, RANK = 'N/A' WHERE GUILD = ?");
			ps.setString(1, "N/A");
			ps.setString(2, guildName);
			ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
				Setting.messagePrefix + "&6" + player.getName() + " &7has disbanded the guild &6" + guildName));
	}

	public void deleteQueueRunnable(Player player, String guildName) {

		
		new BukkitRunnable() {
			@Override
			public void run() {

				deleteQueueRunnableEnd(player, guildName);
			}
		}.runTaskLater(Main.plugin, 1);

	}

	public void deleteQueueRunnableEnd(Player player, String guildName) {

		if (DeleteQueue.queue.contains(guildName)) {
			deleteGuildFromMySQL(player, guildName);
		} else {
		

		}

	}
}
