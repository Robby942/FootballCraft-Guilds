package me.Robay.guilds.utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;

import me.Robay.guilds.Hikari;
import me.Robay.guilds.Main;

public class CheckPlayerRow {

	private String column1;
	private String column2;
	private String column3;
	private String column4;
	public String column5;


	public void getPlayerRow(String uuid) {

	

			try {

				PreparedStatement ps = Hikari.conn
						.prepareStatement("SELECT * FROM " + Hikari.playerTable + " WHERE UUID=?");
				ps.setString(1, uuid.toString());
				ResultSet results = ps.executeQuery();
				results.next();
				setColumns(results);

		
	

			} catch (SQLException e) {

				e.printStackTrace();
			}


	}

	public void setColumns(ResultSet results) {

		try {
			this.column1 = results.getString(1);
			this.column2 = results.getString(2);
			this.column3 = results.getString(3);
			this.column4 = results.getString(4);
			this.column5 = results.getString(5);
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public String column1() {
		return this.column1;

	}

	public String column2() {
		return this.column2;

	}

	public String column3() {
		return this.column3;

	}

	public String column4() {
		return this.column4;

	}

	public String column5() {

		return this.column5;
	}

}
