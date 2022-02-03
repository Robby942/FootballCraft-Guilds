package me.Robay.guilds.listener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Robay.guilds.Main;
import me.Robay.guilds.Hikari;
import me.Robay.guilds.cache.InviteQueue;
import me.Robay.guilds.utilities.CheckPlayerExists;
import me.Robay.guilds.utilities.CheckPlayerRow;
import me.Robay.guilds.utilities.Checker;

public class Events implements Listener {

	private Checker checker = new Checker();
	private CheckPlayerRow checkPlayerRow = new CheckPlayerRow();

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {

		Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {

			Player player = event.getPlayer();
			CheckPlayerExists checkifExists = new CheckPlayerExists();
			if (checkifExists.exists(player.getUniqueId())) {
				System.out.println("[Guilds] Player already exists");

				return;
			} else {
				System.out.print("[Guilds] Player does not exist, creating data");
				try {

					PreparedStatement ps = Hikari.conn.prepareStatement("INSERT " + Hikari.playerTable
							+ " (UUID,GUILD,CONTRIBUTED_EXP,RANK,NAME) VALUES (?,?,?,?,?)");
					ps.setString(1, player.getUniqueId().toString());
					ps.setString(2, "N/A");
					ps.setInt(3, 0);
					ps.setString(4, "N/A");
					ps.setString(5, player.getName());
					ps.executeUpdate();

				} catch (SQLException e) {

					e.printStackTrace();

				}

			}
		


			
				if (!checkPlayerRow.column5().
						equals(event.getPlayer().getName())) {

					try {

						PreparedStatement ps = Hikari.conn.prepareStatement("UPDATE " + Hikari.playerTable
								+ " SET NAME = ? WHERE UUID='" + event.getPlayer().getUniqueId().toString() + "'");
						ps.setString(1, event.getPlayer().getName());
						ps.executeUpdate();
						System.out.print(
								"[Guilds] Updated " + event.getPlayer().getName() + "'s username in the database");

					} catch (SQLException e) {

						e.printStackTrace();
					}

				} else {

					System.out.print("[Guilds] " + event.getPlayer().getName()
							+ "'s username did not need to be updated in the database");

				}
		});
			}


	public void onPlayerLeaveEvent(PlayerQuitEvent event) {

		for (int i = 0; i < InviteQueue.playerQueue.size(); i++) {

			if (InviteQueue.playerQueue.get(i).equals(event.getPlayer())) {

				InviteQueue.playerQueue.remove(i);
				InviteQueue.guildQueue.remove(i);

			}

		}

	}

}
