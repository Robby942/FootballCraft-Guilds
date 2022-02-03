package me.Robay.guilds.utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import me.Robay.guilds.Hikari;

public class CheckGuildExists {

	public boolean exists(String name) {

		try {

			PreparedStatement ps = Hikari.conn.prepareStatement("SELECT * FROM " + Hikari.guildTable + " WHERE NAME = ?");
			ps.setString(1, name);
			ResultSet results = ps.executeQuery();
			if (results.next()) {

				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;

	}

}