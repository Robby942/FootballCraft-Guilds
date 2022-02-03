package me.Robay.guilds.level;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import me.Robay.guilds.Hikari;
import me.Robay.guilds.utilities.Checker;

public class Level {

	private int level;
	private long currentExp;
	private Checker checker = new Checker();

	public Level(int expAdded, Player adder) {

		this.level = Integer.parseInt(
				checker.getGuildRow(checker.getPlayerRow(adder.getUniqueId().toString()).column2()).column4());
		this.currentExp = Long.parseLong(
				checker.getGuildRow(checker.getPlayerRow(adder.getUniqueId().toString()).column2()).column3());

		if (this.currentExp + expAdded > 10000 + (this.level * 2500)) {

			levelUpInMySQL(this.level, expAdded, adder, expAdded % 1000 + (this.level * 2500));
			addContributedExpInMySQL(expAdded, adder);
		} else {

			addExpInMySQL(expAdded, adder);
			addContributedExpInMySQL(expAdded, adder);
		}

	}

	public void levelUpInMySQL(int currentLevel, int expAdded, Player adder, int expRemainder) {
		

		try {

			PreparedStatement ps  = Hikari.conn.prepareStatement("UPDATE " + Hikari.guildTable + " SET EXP = ?,LEVEL = ?,TOTAL_EXP = ? WHERE NAME='" + checker.getPlayerRow(adder.getUniqueId().toString()).column2() + "'");
			ps.setInt(1, expRemainder);
			ps.setInt(2, (this.level + 1));
			ps.setInt(3, expAdded);
			ps.executeUpdate();
			

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void addExpInMySQL(int expAdded, Player adder) {
		

		try {

			PreparedStatement ps  = Hikari.conn.prepareStatement("UPDATE " + Hikari.guildTable + " SET EXP = ?,TOTAL_EXP = ? WHERE NAME='" + checker.getPlayerRow(adder.getUniqueId().toString()).column2() + "'");
			ps.setInt(1, (Integer.parseInt(checker.getGuildRow(checker.getPlayerRow(adder.getUniqueId().toString()).column2()).column3()) + expAdded));
			ps.setInt(2, expAdded);
			ps.executeUpdate();
			

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	
	public void addContributedExpInMySQL(long expAdded, Player adder) {
		
		try {

			PreparedStatement ps  = Hikari.conn.prepareStatement("UPDATE " + Hikari.playerTable + " SET CONTRIBUTED_EXP = ? WHERE UUID='" + checker.getPlayerRow(adder.getUniqueId().toString()).column1() + "'");
			ps.setInt(1, (int) (Integer.parseInt(checker.getPlayerRow(adder.getUniqueId().toString()).column3()) + expAdded));
			ps.executeUpdate();
			

		} catch (SQLException e) {

			e.printStackTrace();
		}

		
	}

}
