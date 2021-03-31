package de.hype.perms;

import de.hype.perms.listener.ConnectionListener;
import de.hype.perms.utils.MySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class HypePermsBungee extends Plugin {

    private static HypePermsBungee instance;

    @Override
    public void onEnable() {
        instance = this;

        new MySQL("127.0.0.1", "root", "", "test");

        MySQL.connect();

        ProxyServer.getInstance().getPluginManager().registerListener(this, new ConnectionListener());
    }

    @Override
    public void onDisable() {

    }

    public static HypePermsBungee getInstance() {
        return instance;
    }

    public String getPrefix() {
        return "§cHypePerms §8| §7";
    }
}
