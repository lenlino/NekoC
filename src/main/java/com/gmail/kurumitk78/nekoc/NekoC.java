package com.gmail.kurumitk78.nekoc;

import com.gmail.kurumitk78.nekoc.commands.*;
import com.gmail.kurumitk78.nekoc.events.*;
import com.gmail.kurumitk78.nekoc.commands.Hiss;
import com.gmail.kurumitk78.nekoc.database.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.logging.Level;

public final class NekoC extends JavaPlugin {
    public static NekoC self;
    public FileConfiguration config;
    public static List<String> nekoListP;
    public static List<String> kittenListP;
    public static boolean globalCommands;
    public static String prefix = "test";

    public String dbPrefix = "dot";


    public static MySQLCore database;
    private static DatabaseManager databaseManager;
    private static DatabaseHelper databaseHelper;
    private static String host = "put-host-here"; // Host to connect for MySQL
    private static String user = "put-user-here"; // User to try and connect with for MySQL
    private static String pass = "put-pass-here"; // Password for above
    private static String databat = "put-database-name-here"; // Database name to use
    private static short port = 1234; // Port for the host for MySQL
    private boolean useSSL = false; // Use ssl for MySQL?


    @Override
    public void onEnable() {
        self=this;
        try {
            setupDatabase();
        } catch (Exception e) {
            // ahhh shit
            e.printStackTrace();
        }











        if (!(new File(this.getDataFolder(), "config.yml").exists())) { // Generates the config if missing,
            this.saveDefaultConfig();
        }
        config = this.getConfig();





        NekoC.globalCommands = config.getBoolean("GlobalCommandMessages");
        NekoC.nekoListP = config.getStringList("Nekos");
        if (this.config.getBoolean("Pet")) {
            this.getCommand("Pet").setExecutor(new Pet());
        }
        if (this.config.getBoolean("Lovebite")) {
            this.getCommand("Lovebite").setExecutor(new lovebite());
        }
        if (this.config.getBoolean("Nightvision")) {
            this.getCommand("nightvision").setExecutor(new Nightvision());
        }
        if (this.config.getBoolean("Purr")) {
            this.getCommand("Purr").setExecutor(new Purr());
        }
        if (this.config.getBoolean("Earscratch")) {
            this.getCommand("EarScratch").setExecutor(new EarScratch());
        }
        if (this.config.getBoolean("Attention")) {
            this.getCommand("attention").setExecutor(new Attention());
        }
        if (this.config.getBoolean("Hiss")) {
            this.getCommand("hiss").setExecutor(new Hiss());
        }
        if (this.config.getBoolean("Scratch")) {
            this.getCommand("scratch").setExecutor(new Scratch());
        }
        if (this.config.getBoolean("NekoChat")) {
            Bukkit.getPluginManager().registerEvents(new NekoChat(), this);
        }
        if (this.config.getBoolean("MeatOnly")) {
            Bukkit.getPluginManager().registerEvents(new MeatOnly(), this);
        }
        if(this.config.getBoolean("Catnip")){
            Bukkit.getPluginManager().registerEvents(new CatNip(),this);
        }

        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("PluginPrefix"));


        //Name to UUID conversion
        for(int i = 0; i < nekoListP.size(); i++){
            Bukkit.getLogger().info("Checking if " + nekoListP.get(0) );
            if (!nekoListP.get(i).matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                Bukkit.getLogger().info("Is not UUID");
                nekoListP.set(i, uuidConvert(nekoListP.get(i)));
            }
            config.set("Nekos", nekoListP);
            this.saveConfig();
        }


        //Bukkit.getPluginManager().registerEvents(new CatNip(), this);
        this.getCommand("nekotf").setExecutor(new NekoTF());
        int pluginId = 9164; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static boolean isNeko(final Player p) {
        final String name = p.getUniqueId().toString();
        return NekoC.nekoListP.contains(name);
    }

    public static String uuidConvert(final String name) {
        Bukkit.getLogger().info("Converting" + name + "toUUID");
        OfflinePlayer p = Bukkit.getOfflinePlayer(name);
        return p.getUniqueId().toString();

    }
    /** - Gets the database prefix for usage in DatabaseHelper */
    public String getDbPrefix() {
        return dbPrefix;
    }

    /** - Getter for the database manager */
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /** - Getter for the database helper */
    public DatabaseManager getDatabaseHelper() {
        return databaseManager;
    }

    /** - Sets up the database on init; Run on enable / plugin reload *if you want* */
    private boolean setupDatabase() {
        try {
            ConfigurationSection dbCfg = getConfig().getConfigurationSection("database");
            AbstractDatabaseCore dbCore;
            // SQLite database - Doing this handles file creation
          //  dbCore = new SQLiteCore(this, new File(this.getDataFolder(), "dataDot.db"));

            // TODO: Uncomment the below and comment the above to use MySQL. If you wanna make it switch based on config options, it should be pretty easy.

            dbCore = new MySQLCore(this, host, user, pass, databat, Short.toString(port), useSSL);
            this.databaseManager = new DatabaseManager(this, ServiceInjector.getDatabaseCore(dbCore));
            // Make the database up to date
            this.databaseHelper = new DatabaseHelper(this, this.databaseManager);
        } catch (DatabaseManager.ConnectionException e) {
            getLogger().log(Level.SEVERE, "Error when connecting to the database", e);
            getServer().getPluginManager().disablePlugin(this);
            return false;
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Error when setup database", e);
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }






}
