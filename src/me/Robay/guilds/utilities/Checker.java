package me.Robay.guilds.utilities;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Checker
{
    private CheckGuildExists checkIfGuildExists;
    
    public Checker() {
        this.checkIfGuildExists = new CheckGuildExists();
    }
    
    public boolean checkIfGuildExists(String name) {
        return this.checkIfGuildExists.exists(name);
    }
    
    public CheckGuildRow getGuildRow(String name) {
      CheckGuildRow checkGuildRow = new CheckGuildRow();
      checkGuildRow.getGuildRow(name);
        
        return checkGuildRow;
    }
    
    public CheckPlayerRow getPlayerRow(String uuid) {
    CheckPlayerRow checkPlayerRow = new CheckPlayerRow();
    checkPlayerRow.getPlayerRow(uuid);
        return checkPlayerRow;
    }
    

    
    

    
    
}
