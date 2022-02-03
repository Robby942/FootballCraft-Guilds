package me.Robay.guilds.utilities;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


import me.Robay.guilds.Hikari;

public class CheckPlayerExists {

	

	public boolean exists(UUID uuid) {
	
		try {

			PreparedStatement ps = Hikari.conn
					.prepareStatement("SELECT * FROM " + Hikari.playerTable + " WHERE UUID=?");
			ps.setString(1, uuid.toString());
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
