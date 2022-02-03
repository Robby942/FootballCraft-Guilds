package me.Robay.guilds.utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;

import me.Robay.guilds.Hikari;
import me.Robay.guilds.Main;

public class CheckGuildRow {

	private String column1;
	private String column2;
	private String column3;
	private String column4;
	private String column5;
	private String column6;
//	public CompletableFuture<ArrayList<String>> cf = new CompletableFuture<>();
	

	public void getGuildRow(String name) {

	//	Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {

			try {

				PreparedStatement ps = Hikari.conn
						.prepareStatement("SELECT * FROM " + Hikari.guildTable + " WHERE Name=?");
				ps.setString(1, name);
				ResultSet results = ps.executeQuery();
				results.next();

				ArrayList<String> columns = new ArrayList<>();

				columns.add(this.column1);
				columns.add(this.column2);
				columns.add(this.column3);
				columns.add(this.column4);
				columns.add(this.column5);
				columns.add(this.column6);
			//	cf.complete(columns);
				
				setColumns(results);

			} catch (SQLException e) {

				e.printStackTrace();
			}

	//	});

	}


	public void setColumns(ResultSet results) {

		try {
			this.column1 = results.getString(1);
			this.column2 = results.getString(2);
			this.column3 = results.getString(3);
			this.column4 = results.getString(4);
			this.column5 = results.getString(5);
			this.column6 = results.getString(6);
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

	public String column6() {

		return this.column6;
	}

}
