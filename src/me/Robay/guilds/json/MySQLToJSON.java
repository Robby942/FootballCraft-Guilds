package me.Robay.guilds.json;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.Robay.guilds.Hikari;
import me.Robay.guilds.Main;

public class MySQLToJSON {

	private JSONArray guildTable;
	private JSONArray playerTable;
	private JSONParser parser = new JSONParser();
//	public CompletableFuture<JSONArray> cf = new CompletableFuture<>();

	public void getMySQLToJSON(Connection conn) throws ParseException {

		//Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
	
				try {

					PreparedStatement ps;
					ps = Hikari.conn.prepareStatement(
							"SELECT JSON_ARRAYAGG(JSON_OBJECT('NAME', NAME, 'LEADER', LEADER, 'EXP', EXP, 'LEVEL', LEVEL,'TOTAL_EXP', TOTAL_EXP)) from "
									+ Hikari.guildTable);
					ResultSet results;
					results = ps.executeQuery();
					results.next();
					try {
						guildTable = (JSONArray) parser.parse(results.getString(1));
					} catch (ParseException e1) {

						e1.printStackTrace();
					}

					ps = Hikari.conn.prepareStatement(
							"SELECT JSON_ARRAYAGG(JSON_OBJECT('UUID', UUID, 'GUILD', GUILD, 'CONTRIBUTED_EXP', CONTRIBUTED_EXP, 'RANK', RANK, 'NAME', NAME)) from "
									+ Hikari.playerTable);
					results = ps.executeQuery();
					results.next();
					try {
						playerTable = (JSONArray) parser.parse(results.getString(1));
						
					} catch (ParseException e2) {

						e2.printStackTrace();
					}

				} catch (SQLException e3) {

					e3.printStackTrace();
				}
				
			//	cf.complete(this.playerTable);

			
	//	});

	}

	public JSONArray getGuildTable() {

		return this.guildTable;
	}

	public JSONArray getPlayerTable() {

		return this.playerTable;

	}
}
