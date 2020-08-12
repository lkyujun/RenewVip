package xyz.qmzc.RenewalVip;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


import java.util.List;

import static xyz.qmzc.RenewalVip.Main.*;

public class RenewVipListener implements Listener {
    static int price;
    String vip;
    List<String> cmds;
    @EventHandler
    public void onPlayerOpenGui(InventoryClickEvent evt){
        Player p = (Player)evt.getWhoClicked();
        if(evt.getInventory().getTitle().equals("倾梦之城续费系统")){
            evt.setCancelled(true);
        }
        if(evt.getRawSlot() < 0 || evt.getRawSlot() > evt.getInventory().getSize() || evt.getInventory() == null){
            return;
        }
        if(evt.getInventory().getTitle().equals("倾梦之城续费系统")){
            if(evt.getRawSlot() == 11){
            }else if(evt.getRawSlot() == 15){
                if(isVip(p)){
                    try {
                        vip = getVip(p);
                        price = vips_price.get(vip);
                        cmds = vips_cmds.get(vip);
                        if(playerPoints.getAPI().look(p.getUniqueId()) >= price){
                            cmds.forEach((cmd)->{
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd.replace("%player%",p.getName()));
                            });
                            playerPoints.getAPI().take(p.getUniqueId(),price);
                        }else{
                            p.sendMessage("§a[倾梦续费] 对不起，您的点券不足，无法完成续费");
                        }
                    }catch (Exception e){}
                }else{
                    p.sendMessage("对不起，您不是vip");
                }
            }
        }
    }
}
