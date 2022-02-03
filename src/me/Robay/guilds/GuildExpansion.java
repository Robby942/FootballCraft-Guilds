package me.Robay.guilds;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.Robay.guilds.leaderboard.Leaderboard;
import me.Robay.guilds.utilities.Checker;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class GuildExpansion extends PlaceholderExpansion {

	Checker checker = new Checker();
	Leaderboard leaderBoard = new Leaderboard();

	@Override
	public String getAuthor() {

		return "Robay";
	}

	@Override
	public String getIdentifier() {

		return "guild";
	}

	@Override
	public String getVersion() {

		return "0.1";
	}

	@Override
	public boolean canRegister()

	{
		return true;
	}

	@Override
	public boolean persist() {

		return true;
	}

	@Override

	public String onPlaceholderRequest(Player p, String params) {

		if (p == null) {

			return "";
		}

		if (params.equals("name")) {

			return checker.getPlayerRow(p.getUniqueId().toString()).column2().toString();

		}

		if (params.equals("level")) {

			return checker.getGuildRow(checker.getPlayerRow(p.getUniqueId().toString()).column2()).column3().toString();

		}

		if (params.equals("exp")) {

			return checker.getGuildRow(checker.getPlayerRow(p.getUniqueId().toString()).column2()).column3().toString();

		}
		
		if (params.equals("top_") || params.length() >= 4) {
			String number = params.substring(4);
			leaderBoard.createLeaderboard();
			if (Integer.parseInt(number) > leaderBoard.leaderboardSize() || Integer.parseInt(number) <= 0) {
				
				return "Guild does not exist";
				
			} else {
				
				
			return new ArrayList<String>(leaderBoard.getLeaderboard().values()).get(Integer.parseInt(number) - 1).toString();
			}

			
		}
		
				
		return null;
	}
}
