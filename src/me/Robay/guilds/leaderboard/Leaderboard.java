package me.Robay.guilds.leaderboard;

import me.Robay.guilds.utilities.*;
import me.Robay.guilds.*;
import me.Robay.guilds.json.*;
import org.json.simple.parser.*;
import org.json.simple.*;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Leaderboard
{
    private LinkedHashMap<Integer, String> guildLeaderboard;
    private LinkedHashMap<Integer, String> sortedMap;
    private JSONArray guildTable;
    private Checker checker;
    
    public Leaderboard() {
        this.guildLeaderboard = new LinkedHashMap<Integer, String>();
        this.sortedMap = new LinkedHashMap<Integer, String>();
        this.checker = new Checker();
    }
    
    public void createLeaderboard() {
        try {
            MySQLToJSON json = new MySQLToJSON();
            json.getMySQLToJSON(Hikari.conn);
            this.guildTable = json.getGuildTable();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < this.guildTable.size(); ++i) {
             JSONObject object = (JSONObject)this.guildTable.get(i);
             int totalEXP = (int)(long)object.get((Object)"TOTAL_EXP");
            if (totalEXP != 0) {
                this.guildLeaderboard.put(totalEXP, (String)object.get((Object)"NAME"));
                System.out.println("Added " + object.get((Object)"NAME") + " because they had " + totalEXP + " EXP");
            }
        }
        this.sortedMap = this.sortHashMapByValues(this.guildLeaderboard);
       
    }
    
    public LinkedHashMap<Integer, String> sortHashMapByValues( HashMap<Integer, String> passedMap) {
        List<Integer> mapKeys = new ArrayList<Integer>(passedMap.keySet());
        List<String> mapValues = new ArrayList<String>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);
         LinkedHashMap<Integer, String> sortedMap = new LinkedHashMap<Integer, String>();
        for ( String val : mapValues) {
            final Iterator<Integer> keyIt = mapKeys.iterator();
            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                 String comp1 = passedMap.get(key);
                 String comp2 = val;
                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
    
    public HashMap<Integer, String> getLeaderboard() {
        return this.sortedMap;
    }
    
    public void leaderboardCommand(final Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------&r&8(&6Top Guilds&8)&8&m-----------------"));
        for (int i = 0; i < this.sortedMap.size(); ++i) {
            String name = new ArrayList<String>(this.sortedMap.values()).get(i).toString();
             String level = this.checker.getGuildRow(new ArrayList<String>(this.sortedMap.values()).get(i)).column4().toString();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6" + (i + 1) + "&8) &7" + name + " &8(&6Lv&8. &6" + level + "&8)"));
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m---------------------------------------------"));
    }
    
    public int leaderboardSize() {
    	
    	return this.sortedMap.size();
    	
    	
    }
    
    public HashMap<Integer, String> getLeaderBoard() {
    	
    	return this.sortedMap;
    	
    }
}
