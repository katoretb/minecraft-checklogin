package me.Kato14123.Checklogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public String host, database, username, password;
	public boolean islog;
	public int port;
	
	
	public void onEnable() {
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		getdata(p.getName());
		if(islog) {
			e.setJoinMessage("test1");
		}else {
			e.setJoinMessage("test2");
		}
	}
	
	public void getdata(String player) {
		host = this.getConfig().getString("mysql.host");
		port = this.getConfig().getInt("mysql.port");
		database = this.getConfig().getString("mysql.dbname");
		username = this.getConfig().getString("mysql.user");
		password = this.getConfig().getString("mysql.password");
		Connection connect = null;
		
		Statement s = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect =  DriverManager.getConnection("jdbc:mysql://" + this.host + ":" 
					+ this.port + "/" + this.database, this.username, this.password);
			
			s = connect.createStatement();
			
			String sql = "select * from authme where realname='" + player + "'";
			
			ResultSet rec = s.executeQuery(sql);
			
			while((rec!=null) && (rec.next()))
            { 
				islog = rec.getBoolean("isLogged");
            }
             
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(connect != null){
				s.close();
				connect.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}