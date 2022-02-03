package me.Robay.guilds.actions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.Robay.guilds.Setting;
import me.Robay.guilds.cache.InviteQueue;
import me.Robay.guilds.utilities.Checker;

public class InvitePlayer {

	Checker checker = new Checker();

	public InvitePlayer(Player playerInvited, Player inviter) {

		InviteQueue.playerQueue.add(playerInvited);
		InviteQueue.guildQueue.add(checker.getPlayerRow(inviter.getUniqueId().toString()).column2());
		playerInvited.sendMessage(ChatColor.translateAlternateColorCodes('&',
				Setting.messagePrefix + "&7You have been invited to join the guild " + ChatColor.GOLD
						+ checker.getPlayerRow(inviter.getUniqueId().toString()).column2()));
		playerInvited.sendMessage(
				ChatColor.translateAlternateColorCodes('&', "&7Use the command &6/guild join &8<&6guild&8> &7to join"));
		inviter.sendMessage(ChatColor.translateAlternateColorCodes('&', Setting.messagePrefix + "&7You have invited &6" + playerInvited.getName() + " &7to your guild."));

	}
}