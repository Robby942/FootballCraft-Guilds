package me.Robay.guilds.actions;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import me.Robay.guilds.Hikari;
import me.Robay.guilds.json.MySQLToJSON;
import me.Robay.guilds.leaderboard.Leaderboard;
import me.Robay.guilds.utilities.Checker;

public class GuildInfo {

	private JSONArray guildTable;
	private JSONArray playerTable;
	private String guildName;
	private ArrayList<String> players = new ArrayList<>();
	private Checker checker = new Checker();
	private long level;
	private int leaderboardRank;
	Leaderboard leaderboard = new Leaderboard();

	public GuildInfo(Player player, boolean isPlayerInGuild, String guildSearched) {

		leaderboard.createLeaderboard();

		try {
			MySQLToJSON json = new MySQLToJSON();
			json.getMySQLToJSON(Hikari.conn);
			guildTable = json.getGuildTable();
			playerTable = json.getPlayerTable();
		} catch (ParseException e) {

			e.printStackTrace();
		}

		if (isPlayerInGuild) {
			displayGuildInfo(player);
		} else {

			for (int i = 0; i < guildTable.size(); i++) {
				JSONObject object = (JSONObject) this.guildTable.get(i);
				if (object.get("NAME").equals(guildSearched)) {

					displayGuildInfoNotInGuild(player, (String) checker.getGuildRow(guildSearched).column2());

				}

			}
		}
	}

	public void displayGuildInfo(Player p) {

		for (int i = 0; i < this.playerTable.size(); i++) {

			JSONObject playerObject = (JSONObject) this.playerTable.get(i);

			if (playerObject.get("UUID").equals(p.getUniqueId().toString())) {
				this.guildName = playerObject.get("GUILD").toString();
			}

		}

		for (int i = 0; i < this.playerTable.size(); i++) {

			JSONObject playerObject = (JSONObject) this.playerTable.get(i);

			if (playerObject.get("GUILD").equals(this.guildName)) {

				players.add(playerObject.get("NAME").toString());
			}

		}

		for (int i = 0; i < this.guildTable.size(); i++) {

			JSONObject guildObject = (JSONObject) this.guildTable.get(i);
			if (guildObject.get("NAME").equals(this.guildName)) {

				this.level = (long) guildObject.get("LEVEL");

			}

		}

		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&8&m-----------------&r&8(&6" + this.guildName + "&8)&8&m-----------------"));

		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Guild Level&8: &7" + this.level));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6Current EXP&8: &7" + String.valueOf(checker.getGuildRow(this.guildName).column3())));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6EXP Needed&8: &7"
				+ String.valueOf((10000 + (Integer.parseInt(checker.getGuildRow(this.guildName).column4()) * 2500)))));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6Date Created&8: &7" + checker.getGuildRow(this.guildName).column5()));
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Players&8:"));
		p.sendMessage("");
		for (int i = 0; i < players.size(); i++) {

			if (isPlayerOnline(players.get(i))) {
				p.sendMessage(ChatColor.GRAY + players.get(i) + ChatColor.GREEN + " ●");
			} else {

				p.sendMessage(ChatColor.GRAY + players.get(i) + ChatColor.RED + " ●");

			}
		}

		p.sendMessage("");

		for (int i = 0; i < leaderboard.getLeaderboard().size(); i++) {

			if (new ArrayList<String>(leaderboard.getLeaderboard().values()).get(i).equalsIgnoreCase(this.guildName)) {

				this.leaderboardRank = (i + 1);

			}

		}

		if (this.leaderboardRank == 0) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Leaderboard Rank&8: &7" + "Not Ranked"));
		} else {

			p.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&6Leaderboard Rank&8: &7" + this.leaderboardRank));

		}

		if (guildName.length() == 3) {

			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m---------------------------------------"));
		} else if (guildName.length() == 4) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m----------------------------------------"));

		} else if (guildName.length() == 5) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m----------------------------------------"));

		} else if (guildName.length() == 6) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m---------------------------------------"));

		} else if (guildName.length() == 7) {
			p.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&8&m--------------------------------------------"));

		} else if (guildName.length() == 8) {
			p.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&8&m-------------------------------------------"));

		} else if (guildName.length() == 9) {
			p.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&8&m--------------------------------------------"));

		} else if (guildName.length() == 10) {
			p.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&8&m---------------------------------------------"));

		}
	}

	public boolean isPlayerOnline(String playerName) {

		Player target = Bukkit.getPlayer(playerName);
		if (target != null) {
			return true;
		} else {

			return false;

		}

	}

	public void displayGuildInfoNotInGuild(Player p, String leader) {

		for (int i = 0; i < this.playerTable.size(); i++) {

			JSONObject playerObject = (JSONObject) this.playerTable.get(i);

			if (playerObject.get("UUID").equals(leader.toString())) {
				this.guildName = playerObject.get("GUILD").toString();
			}

		}

		for (int i = 0; i < this.playerTable.size(); i++) {

			JSONObject playerObject = (JSONObject) this.playerTable.get(i);

			if (playerObject.get("GUILD").equals(this.guildName)) {

				players.add(playerObject.get("NAME").toString());
			}

		}

		for (int i = 0; i < this.guildTable.size(); i++) {

			JSONObject guildObject = (JSONObject) this.guildTable.get(i);
			if (guildObject.get("NAME").equals(this.guildName)) {

				this.level = (long) guildObject.get("LEVEL");

			}

		}

		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&8&m-----------------&r&8(&6" + this.guildName + "&8)&8&m-----------------"));

		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Guild Level&8: &7" + this.level));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6Current EXP&8: &7" + String.valueOf(checker.getGuildRow(this.guildName).column3())));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6EXP Needed&8: &7"
				+ String.valueOf((10000 + (Integer.parseInt(checker.getGuildRow(this.guildName).column4()) * 2500)))));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6Date Created&8: &7" + checker.getGuildRow(this.guildName).column5()));
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Players&8:"));
		p.sendMessage("");
		for (int i = 0; i < players.size(); i++) {

			if (isPlayerOnline(players.get(i))) {
				p.sendMessage(ChatColor.GRAY + players.get(i) + ChatColor.GREEN + " ●");
			} else {

				p.sendMessage(ChatColor.GRAY + players.get(i) + ChatColor.RED + " ●");

			}
		}

		p.sendMessage("");

		for (int i = 0; i < leaderboard.getLeaderboard().size(); i++) {

			if (new ArrayList<String>(leaderboard.getLeaderboard().values()).get(i).equalsIgnoreCase(this.guildName)) {

				this.leaderboardRank = (i + 1);

			}

		}

		if (this.leaderboardRank == 0) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Leaderboard Rank&8: &7" + "Not Ranked"));
		} else {

			p.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&6Leaderboard Rank&8: &7" + this.leaderboardRank));

		}

		if (guildName.length() == 3) {

			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m---------------------------------------"));
		} else if (guildName.length() == 4) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m----------------------------------------"));

		} else if (guildName.length() == 5) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m----------------------------------------"));

		} else if (guildName.length() == 6) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m---------------------------------------"));

		} else if (guildName.length() == 7) {
			p.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&8&m--------------------------------------------"));

		} else if (guildName.length() == 8) {
			p.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&8&m-------------------------------------------"));

		} else if (guildName.length() == 9) {
			p.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&8&m--------------------------------------------"));

		} else if (guildName.length() == 10) {
			p.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&8&m---------------------------------------------"));

		}
	}

}
