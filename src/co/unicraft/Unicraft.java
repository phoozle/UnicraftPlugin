package co.unicraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.java.JavaPlugin;

public class Unicraft extends JavaPlugin implements Listener {
	public void onEnable() {
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable() {
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		Player player = evt.getPlayer();
		UnicraftPlayer unicraft_player = new UnicraftPlayer(player.getPlayerListName(), null, this);
		Boolean player_active = unicraft_player.isActive();
		
		
		if (player.hasPermission("unicraft.verified") && !player_active) {
			this.getServer().dispatchCommand(getServer().getConsoleSender(), "manuadd "+player.getPlayerListName()+" Default");
			((Player) player).sendMessage("You are no longer verified?! :(");
			((Player) player).sendMessage("Try rejoining in a moment...");
		} else if (!player.hasPermission("unicraft.verified") && player_active) {
			((Player) player).sendMessage("You have successfully been verified!");
			this.getServer().dispatchCommand(getServer().getConsoleSender(), "manuadd "+player.getPlayerListName()+" Builder");
		} else if (!player.hasPermission("unicraft.verified") && !player_active) {
			((Player) player).sendMessage("You are not verified, verify with /verify <unisa username>");
		} else {
			((Player) player).sendMessage("Welcome back verfied sir!");
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("verify")) {
			if (player.hasPermission("unicraft.verified")) {
				player.sendMessage("You are already verified!");
			} else {
				if (args.length == 1) {
					String email = args[0]+"@mymail.unisa.edu.au";
					
					UnicraftPlayer minecraft_player = new UnicraftPlayer(player.getPlayerListName(), email, this);
					
					if (minecraft_player.create() == 201) {
						player.sendMessage("Success, check your inbox!");
						return true;
					} else {
						player.sendMessage("Oops, something has gone wrong :(");
						return false;
					}
				} else {
					player.sendMessage("Usage: /verify <unisa username>");
					player.sendMessage("i,e, /verify bousj001");
				}
			}
		}
		
		return false; 
	}
}
