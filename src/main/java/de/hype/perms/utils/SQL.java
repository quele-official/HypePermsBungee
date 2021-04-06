package de.hype.perms.utils;

import de.hype.perms.HypePermsBungee;
import de.proxy.hypedcbot.discord.mysql.DiscordSQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL {

    public static boolean playerExists(ProxiedPlayer player) {
        try {
            ResultSet resultSet = HypePermsBungee.getInstance().getMySQL().getResult("SELECT * FROM rang WHERE UUID= '" + player.getUniqueId().toString() + "'");

            assert resultSet != null;
            if(resultSet.next()) {
                return resultSet.getString("UUID") != null;
            }
        } catch(SQLException ignored) {

        }
        return false;
    }

    public static void registerPlayer(ProxiedPlayer player) {
        if(!(playerExists(player))) {
            HypePermsBungee.getInstance().getMySQL().update("INSERT INTO rang(UUID, Rang, DiscordId, OldRang) VALUES ('" + player.getUniqueId().toString() + "', 'Spieler', '', '')");
        } else {
            ProxyServer.getInstance().getConsole().sendMessage("User existiert bereits");
        }
    }

    public static void setRang(ProxiedPlayer player, Rang rang) {
        removeOldRang(player.getUniqueId().toString());

        HypePermsBungee.getInstance().getMySQL().update("UPDATE rang SET Rang= '" + rang.getName() + "' WHERE UUID= '" + player.getUniqueId().toString() + "'");

        DiscordSQL.setRangOnDiscord(player);
    }

    public static String getRangName(String uuid) {
        try {
            ResultSet result = HypePermsBungee.getInstance().getMySQL().getResult("SELECT * FROM rang WHERE UUID= '" + uuid + "'");

            assert result != null;
            if (result.next()) {
                return result.getString("Rang");
            }
        } catch (SQLException ignored) {
            return "Spieler";
        }
        return "Spieler";
    }

    public static Rang getRang(String uuid) {
        try {
            ResultSet result = HypePermsBungee.getInstance().getMySQL().getResult("SELECT * FROM rang WHERE UUID= '" + uuid + "'");

            assert result != null;
            if (result.next()) {
                return Rang.valueOf(result.getString("Rang"));
            }
        } catch (SQLException ignored) {
            return Rang.Spieler;
        }
        return Rang.Spieler;
    }

    public static Integer getRangId(String uuid) {
        try {
            ResultSet result = HypePermsBungee.getInstance().getMySQL().getResult("SELECT * FROM rang WHERE UUID= '" + uuid + "'");

            if(result != null) {
                if (result.next()) {
                    return Rang.valueOf(result.getString("Rang")).getId();
                }
            }
        } catch (NullPointerException | SQLException ignored) {
            return Rang.Spieler.getId();
        }
        return Rang.Spieler.getId();
    }

    public static void removeOldRang(String uuid) {
        try {
            ResultSet result = HypePermsBungee.getInstance().getMySQL().getResult("SELECT * FROM rang WHERE UUID= '" + uuid + "'");

            assert result != null;
            if (result.next()) {
                HypePermsBungee.getInstance().getMySQL().update("UPDATE rang SET OldRang= '" + result.getString("Rang") + "' WHERE UUID= '" + uuid + "'");
            }
        } catch (SQLException ignored) {

        }
    }
}
