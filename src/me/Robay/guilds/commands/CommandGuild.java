package me.Robay.guilds.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import me.Robay.guilds.Setting;
import me.Robay.guilds.actions.CreateGuild;
import me.Robay.guilds.actions.DeleteGuild;
import me.Robay.guilds.actions.GuildInfo;
import me.Robay.guilds.actions.InvitePlayer;
import me.Robay.guilds.actions.JoinGuild;
import me.Robay.guilds.actions.KickPlayer;
import me.Robay.guilds.actions.LeaveGuild;
import me.Robay.guilds.cache.InviteQueue;
import me.Robay.guilds.leaderboard.Leaderboard;
import me.Robay.guilds.level.Level;
import me.Robay.guilds.utilities.Checker;

public class CommandGuild implements CommandExecutor {

	private Checker checker = new Checker();
	private Leaderboard leaderboard = new Leaderboard();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 0) {
				getHelpMessage(p);

			} else if (args[0].equalsIgnoreCase("help")) {

				getHelpMessage(p);

			}

			if (args.length != 0) {

				if (args[0].equalsIgnoreCase("create") && p.hasPermission("guild.create")) {

					if (args.length == 1) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You need to specify a guild name before creating a guild."));
						return false;
					} else if (!isValidName(args[1])) {

						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7Invalid guild name."));
						return false;

					} else if (args[1].length() > 10 || args[1].length() < 3) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7Invalid guild name length."));
						return false;
					} else if (!checker.getPlayerRow(p.getUniqueId().toString()).column2().equalsIgnoreCase("N/A")) {
				
				
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You are already in a guild"));

						return false;

					} else if (checker.checkIfGuildExists(args[1])) {

						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Setting.messagePrefix
								+ "&7A guild with this name already exists. Please try another name."));

						return false;
					} else {
						new CreateGuild(p, args[1]);
						return false;
					}

				}

				if (args[0].equalsIgnoreCase("leave")) {

					if (checker.getPlayerRow(p.getUniqueId().toString()).column4().equalsIgnoreCase("Leader")) {

						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Setting.messagePrefix
								+ "&7You cannot leave this guild with trasnferring guild ownership to another guild member."));
						return false;

					} else if (checker.getPlayerRow(p.getUniqueId().toString()).column2().equalsIgnoreCase("N/A")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You are not in a guild."));
						return false;
					}

					else

						new LeaveGuild(p);

				}

				if (args[0].equalsIgnoreCase("top")) {
					this.leaderboard.createLeaderboard();
					this.leaderboard.leaderboardCommand(p);
			
				

				}
				
				if (args[0].equalsIgnoreCase("debug") && p.hasPermission("guild.admin")) {
			
			    	   
					Bukkit.broadcastMessage(checker.getPlayerRow(p.getUniqueId().toString()).toString());
			         System.out.println("here ^");
			         
		
				

				}


				if (args[0].equalsIgnoreCase("invite")) {

					if (args.length == 1) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You need to specify a player to invite to your guild."));
						return false;

					}

					else if (checker.getPlayerRow(p.getUniqueId().toString()).column2().equalsIgnoreCase("N/A")) {
						System.out.println(checker.getPlayerRow(p.getUniqueId().toString()).column2());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You are not in a guild."));
						return false;
					}

					else if (args[1].equalsIgnoreCase(p.getName())) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You cannot invite yourself to a guild."));
						return false;

					} else if (!isValidPlayer(args[1])) {

						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7That player is not online."));

					}

					else if (!checker.getPlayerRow(Bukkit.getPlayer(args[1]).getUniqueId().toString()).column2()
							.equalsIgnoreCase("N/A")) {

						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7This player is already in a guild."));
						return false;

					} else {

						for (int i = 0; i < InviteQueue.playerQueue.size(); i++) {

							if (InviteQueue.playerQueue.get(i).equals(Bukkit.getPlayer(args[1]))) {

								p.sendMessage(ChatColor.translateAlternateColorCodes('&',
										Setting.messagePrefix + "&7The player &6" + Bukkit.getPlayer(args[1]).getName()
												+ " &7has already been invited to your guild."));
								return false;

							}

						}

						new InvitePlayer(Bukkit.getPlayer(args[1]), p);
						return false;

					}

				}

				if (args[0].equalsIgnoreCase("disband")) {

					if (args.length == 1) {

						if (!checker.getPlayerRow(p.getUniqueId().toString()).column4().equalsIgnoreCase("Leader")) {

							p.sendMessage(ChatColor.translateAlternateColorCodes('&',
									Setting.messagePrefix + "&7You do not own a guild to disband."));
							return false;

						} else {

							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Setting.messagePrefix
									+ "&7Please enter your guild name to confirm the disbandment of your guild."));
							return false;
						}
					} else if (args.length > 1) {

						if (!checker.getPlayerRow(p.getUniqueId().toString()).column4().equalsIgnoreCase("Leader")) {

							p.sendMessage(ChatColor.translateAlternateColorCodes('&',
									Setting.messagePrefix + "&7You do not own a guild to disband."));
							return false;

						}

						if (!args[1].equalsIgnoreCase(checker.getPlayerRow(p.getUniqueId().toString()).column2())) {

							p.sendMessage(ChatColor.translateAlternateColorCodes('&',
									Setting.messagePrefix + "&7Your guild name that you entered was not correct."));
							return false;
						} else {

							new DeleteGuild(p, args[1], true);
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Setting.messagePrefix
									+ "&7Your guild deletion has been queued and will delete in 5 minutes."));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&',
									"&7Use /guild canceldisband to cancel this action."));
							return false;

						}

					}

				}
				if (args[0].equalsIgnoreCase("canceldisband")) {

					if (!checker.getPlayerRow(p.getUniqueId().toString()).column4().equalsIgnoreCase("Leader")) {

						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You do not own a guild"));
						return false;

					} else {

						new DeleteGuild(p, checker.getPlayerRow(p.getUniqueId().toString()).column2(), false);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You have cancelled your guild disband operation."));
						return false;
					}
				}

				if (args[0].equalsIgnoreCase("invites")) {

					ArrayList<String> invites = new ArrayList<>();

					for (int i = 0; i < InviteQueue.playerQueue.size(); i++) {

						if (InviteQueue.playerQueue.get(i).equals(p)) {
							invites.add(InviteQueue.guildQueue.get(i));

						}

					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&8&m-----------------&r&8(&6Guild Invites&8)&8&m-----------------"));
					for (int i = 0; i < invites.size(); i++) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&6" + (i + 1) + "&8) &7" + invites.get(i)));

					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&8&m----------------------------------------------"));

					return false;

				}

				if (args[0].equalsIgnoreCase("join")) {
					ArrayList<String> invites = new ArrayList<>();

					for (int i = 0; i < InviteQueue.playerQueue.size(); i++) {

						if (InviteQueue.playerQueue.get(i).equals(p)) {
							invites.add(InviteQueue.guildQueue.get(i));

						}

					}

					if (args.length == 1) {

						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Setting.messagePrefix
								+ "&7Please enter the name of the guild you would like to join. View your invites with /guild invites."));
						return false;

					}
					if (invites.contains(args[1])) {

						for (int i = 0; i < InviteQueue.playerQueue.size(); i++) {

							if (InviteQueue.guildQueue.get(i).contains(args[1])) {

								InviteQueue.guildQueue.remove(i);
								InviteQueue.playerQueue.remove(i);
							}

						}
						new JoinGuild(p, args[1]);

						return false;

					} else {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You have not been invited to this guild."));
						return false;

					}

				}

				if (args[0].equalsIgnoreCase("kick")) {

					if (args.length == 1) {

						if (!checker.getPlayerRow(p.getUniqueId().toString()).column4().equalsIgnoreCase("Leader")) {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&',
									Setting.messagePrefix + "&7You cannot kick a player from a guild."));
							return false;

						} else {

							p.sendMessage(ChatColor.translateAlternateColorCodes('&',
									Setting.messagePrefix + "&7Please specify a player to kick from your guild."));
							return false;

						}

					} else if (!checker.getPlayerRow(p.getUniqueId().toString()).column4().equalsIgnoreCase("Leader")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You cannot kick a player from a guild."));
						return false;

					} else if (!isValidPlayer(args[1])) {

						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7That player is not online."));
						return false;

					} else if (args[1].equalsIgnoreCase(p.getName())) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7You cannot kick yourself from a guild"));
						return false;

					} else if (!checker.getPlayerRow(Bukkit.getPlayer(args[1]).getUniqueId().toString()).column2()
							.equalsIgnoreCase(checker.getPlayerRow(p.getUniqueId().toString()).column2())) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Setting.messagePrefix + "&7That player is not in your guild"));
						return false;

					} else {
						
						if (args[1].equalsIgnoreCase(p.getName())) {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&',
									Setting.messagePrefix + "&7You cannot kick yourself from a guild"));
							return false; 
							} 
						
						else {
						

						new KickPlayer(Bukkit.getPlayer(args[1]), p);

						return false;

					}
					}

				}

				if (args[0].equalsIgnoreCase("info")) {

					if (args.length == 1) {

						if (checker.getPlayerRow(p.getUniqueId().toString()).column2().equalsIgnoreCase("N/A")) {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&',
									Setting.messagePrefix + "&7You are not in a guild"));
							return false;

						} else {

							new GuildInfo(p, true, "None");
							return false;
						}

					} else {

						new GuildInfo(p, false, args[1]);
						return false;

					}
				}

			}

		}

		else {

			if (args.length == 0) {

				System.out.print("[Guilds] You cannot use commands from console");
				
				return false;
			} else {
				
				if (args[0].equalsIgnoreCase("addexp"))  {
					
					new Level(Integer.parseInt(args[1]), Bukkit.getPlayer(args[2]));
					
					
					
				}
				
			}
			
		}

		return false;
	}

	public void getHelpMessage(Player p) {

		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&8&m-----------------&r&8(&6Guilds v0.1&8)&8&m-----------------"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/guild help &8- &7Show help page"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/guild create <name> &8- &7Create a guild"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/guild leave &8- &7Leave your current guild"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6/guild invite <player> &8- &7Invite a player to your guild"));
		p.sendMessage(
				ChatColor.translateAlternateColorCodes('&', "&6/guild disband <guild name> &8- &7Disband your guild"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6/guild canceldisband &8- &7Cancel your guild disband operation"));
		p.sendMessage(
				ChatColor.translateAlternateColorCodes('&', "&6/guild invites &8- &7View your current guild invites"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6/guild join &8- &7Join a guild that you've been invited to"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6/guild kick &8- &7Kick a player from your guild"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6/guild info <guild> &8- &7View the information of your guild or another guild"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6/guild top &8- &7View the best guilds"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m---------------------------------------"));


	}

	public boolean isValidName(String name) {
		return name.matches("[a-zA-Z]+");
	}

	public boolean isValidPlayer(String playerName) {

		Player target = Bukkit.getPlayer(playerName);
		if (target != null) {
			return true;
		} else {

			return false;

		}

	}
}
