package xyz.qmzc.RenewalVip;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin implements CommandExecutor {
    private ItemStack item_bk;
    private ItemStack item_diamond;
    private ItemStack item_star;
    private ItemMeta itemMeta_bk;
    private ItemMeta itemMeta_diamond;
    private ItemMeta itemMeta_star;
    private Inventory inv;
    static Map<String, Integer> vips_price;
    static Map<String, List<String>> vips_cmds;
    FileConfiguration config;
    public static PlayerPoints playerPoints;
    List<String> getCmd;
    @Override
    public void onEnable() {
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.saveDefaultConfig();
        }
        onLoadData();
        getLogger().info("§4[倾梦之城]§l续费插件已启动");
        playerPoints = PlayerPoints.class.cast(this.getServer().getPluginManager().getPlugin("PlayerPoints"));
        getCommand("rv").setExecutor(this);
        getServer().getPluginManager().registerEvents(new RenewVipListener(),this);
    }
    public void onLoadData(){
        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        getCmd=new ArrayList<>();
        vips_price = new HashMap<>();
        vips_cmds = new HashMap<>();
        config = this.getConfig();
        for(String key:config.getKeys(false)){
            getCmd = getConfig().getStringList(key + ".commands");
            vips_price.put(config.getString(key + ".vip"),config.getInt(key + ".price"));
            vips_cmds.put(config.getString(key + ".vip"),getCmd);
        }
    }
    @Override
    public void onDisable() {
        getLogger().info("§4[倾梦之城]§l续费插件已关闭");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(label.equalsIgnoreCase("rv")){
                if(args.length == 0){
                    player.sendMessage("§a-----倾梦续费系统-----");
                    player.sendMessage("§a/rv open 打开续费界面");
                    player.sendMessage("§a-----倾梦续费系统-----");
                    return false;
                }
                if(args[0].equalsIgnoreCase("open") && isVip(player) ){
                    openGUI(player);
                }else{
                    player.sendMessage("§a[倾梦续费] §c指令输入错误或者你不是VIP");
                }
            }
            return true;
        }else {
            sender.sendMessage("§a[倾梦续费] 此命令只能是玩家打开");
            return false;
        }
    }
    public void openGUI(Player player){
        item_bk = new ItemStack(160,1,(short)15);
        item_diamond = new ItemStack(Material.DIAMOND);
        item_star = new ItemStack(Material.NETHER_STAR);
        itemMeta_bk = item_bk.getItemMeta();
        itemMeta_diamond = item_diamond.getItemMeta();
        itemMeta_star = item_star.getItemMeta();

        itemMeta_bk.setDisplayName("§c§l倾梦之城");
        itemMeta_star.setDisplayName("§a点击续费,花费§c§l" + vips_price.get(getVip(player)) + "点券");

        if(player.hasPermission("RenewVip.vip1")){
            itemMeta_diamond.setDisplayName("§c§l当前VIP组:§a§lVIP1");
        }else if(player.hasPermission("RenewVip.vip2")){
            itemMeta_diamond.setDisplayName("§c§l当前VIP组:§a§lVIP2");
        }else if(player.hasPermission("RenewVip.vip3")){
            itemMeta_diamond.setDisplayName("§c§l当前VIP组:§a§lVIP3");
        }else if(player.hasPermission("RenewVip.svip")){
            itemMeta_diamond.setDisplayName("§c§l当前VIP组:§a§lSVIP");
        }

        item_bk.setItemMeta(itemMeta_bk);
        item_diamond.setItemMeta(itemMeta_diamond);
        item_star.setItemMeta(itemMeta_star);

        inv= Bukkit.createInventory(player,3*9, "倾梦之城续费系统");
        inv.setItem(0,item_bk);
        inv.setItem(1,item_bk);
        inv.setItem(2,item_bk);
        inv.setItem(3,item_bk);
        inv.setItem(4,item_bk);
        inv.setItem(5,item_bk);
        inv.setItem(6,item_bk);
        inv.setItem(7,item_bk);
        inv.setItem(8,item_bk);
        inv.setItem(9,item_bk);
        inv.setItem(10,item_bk);
        inv.setItem(11,item_diamond);
        inv.setItem(12,item_bk);
        inv.setItem(13,item_bk);
        inv.setItem(14,item_bk);
        inv.setItem(15,item_star);
        inv.setItem(16,item_bk);
        inv.setItem(17,item_bk);
        inv.setItem(18,item_bk);
        inv.setItem(19,item_bk);
        inv.setItem(20,item_bk);
        inv.setItem(21,item_bk);
        inv.setItem(22,item_bk);
        inv.setItem(23,item_bk);
        inv.setItem(24,item_bk);
        inv.setItem(25,item_bk);
        inv.setItem(26,item_bk);
        if(isVip(player)){
            player.openInventory(inv);
        }else{
            player.sendMessage("§a[倾梦续费] §c对不起，您不是VIP不能使用此功能");
        }
    }


    public static boolean isVip(Player player){
        boolean isVip = player.hasPermission("RenewVip.vip1") || player.hasPermission("RenewVip.vip2") ||
                player.hasPermission("RenewVip.vip3") || player.hasPermission("RenewVip.svip");
        return isVip;
    }

    public static String getVip(Player player){
        if(player.hasPermission("RenewVip.vip1")){
            return "vip1";
        }else if(player.hasPermission("RenewVip.vip2")){
            return "vip2";
        }else if(player.hasPermission("RenewVip.vip3")){
            return "vip3";
        }else if(player.hasPermission("RenewVip.svip")){
            return "svip";
        }else{
            return null;
        }
    }
}
