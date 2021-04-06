package de.hype.perms.utils;

import de.proxy.hypedcbot.discord.Proxy;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

import java.sql.*;

public class MySQL {

    public Connection connection;
    public String host, username, password, database;

    public MySQL(String host, String username, String password, String database) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;

        connect();
        createTable();
    }


    public void createTable() {
        update("CREATE TABLE IF NOT EXISTS rang(UUID VARCHAR(64) UNIQUE, Rang VARCHAR(64), DiscordId VARCHAR(64), OldRang VARCHAR(64));");
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true&useSSL=false", username, password);
            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(Proxy.getInstance().getPrefix() + "MySQL Connection Successfully"));
        } catch (SQLException ignored) {
            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(Proxy.getInstance().getPrefix() + "MySQL Connection Error"));
        }
    }

    public ResultSet getResult(String qry) {
        connect();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(qry);
            resultSet.close();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException ignored) {

        }
        return null;
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void update(String qry) {
        try {
            connect();
            connection.createStatement().executeUpdate(qry);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement getStatement(String qry) {
        PreparedStatement ps;
        try {
            connect();
            ps = connection.prepareStatement(qry);
            ps.close();
            connection.close();
            return ps;
        } catch (SQLException e1) {
            connect();
            e1.printStackTrace();
        }

        return null;
    }

}
