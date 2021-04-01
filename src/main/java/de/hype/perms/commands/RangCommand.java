package de.hype.perms.commands;

import com.google.common.collect.Lists;
import de.hype.perms.HypePermsBungee;
import de.hype.perms.utils.Rang;
import de.hype.perms.utils.RangSQL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;

public class RangCommand extends Command implements TabExecutor {

    public RangCommand(String name) {
        super(name);
    }

    public boolean isExist(Rang rang) {
        return Rang.getRangs().contains(rang);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length == 2) {
                ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(args[0]);
                if (targetPlayer == null) {
                    player.sendMessage(new TextComponent(HypePermsBungee.getInstance().getPrefix() + "§cDieser Spieler ist derzeit nicht auf dem Netzwerk§8!"));
                    return;
                }
                Rang rang = Rang.getRangByName(args[1]);
                if(rang != null) {
                    String rangColor = rang.getPrefix().substring(0, 2);
                    if (RangSQL.getRangId(player.getUniqueId().toString()) < 7) {
                        player.sendMessage(HypePermsBungee.getInstance().getPrefix() + "§7Nicht genug §cRechte§8.");
                        return;
                    }
                    RangSQL.setRang(targetPlayer.getUniqueId().toString(), rang);
                    player.sendMessage(HypePermsBungee.getInstance().getPrefix() + "§7Du hast dem Spieler " + rangColor + targetPlayer.getName()
                            + " §7die Gruppe " + rangColor + rang.getName() + " §7gesetzt§8.");
                    targetPlayer.sendMessage(HypePermsBungee.getInstance().getPrefix() + "§7Du hast einen neuen Rang erhalten§8!");
                    targetPlayer.sendMessage(HypePermsBungee.getInstance().getPrefix() + "§aNeuer Rang §8» " + rangColor + rang.getName());
                    ProxyServer.getInstance().createTitle().title(new TextComponent("§aNeuer rang"))
                            .subTitle(new TextComponent("§7Rang §8» " + rangColor + rang)).fadeIn(3).stay(120).fadeOut(3).send(targetPlayer);
                } else {
                    player.sendMessage(HypePermsBungee.getInstance().getPrefix() + "§7Der angegebene §6Rang §7existiert leider nicht§8.");
                }

            } else {
                player.sendMessage(new TextComponent(HypePermsBungee.getInstance().getPrefix() + "§cNutze /rang <Spieler> <Rang>§8!"));
            }
        } else {
            if (args.length == 2) {
                ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(args[0]);
                if (targetPlayer == null) {
                    sender.sendMessage(new TextComponent(HypePermsBungee.getInstance().getPrefix() + "§cDieser Spieler ist derzeit nicht auf dem Netzwerk§8!"));
                    return;
                }
                Rang rang = Rang.getRangByName(args[1]);
                if(rang != null) {
                    String rangColor = rang.getPrefix().substring(0, 2);
                    RangSQL.setRang(targetPlayer.getUniqueId().toString(), rang);
                    sender.sendMessage(HypePermsBungee.getInstance().getPrefix() + "§7Du hast dem Spieler " + rangColor + targetPlayer.getName()
                            + " §7die Gruppe " + rangColor + rang.getName() + " §7gesetzt§8.");
                } else {
                    sender.sendMessage(HypePermsBungee.getInstance().getPrefix() + "§7Der angegebene §6Rang §7existiert leider nicht§8.");
                }

//was passieren soll

            } else {
                sender.sendMessage(new TextComponent(HypePermsBungee.getInstance().getPrefix() + "§cNutze /rang <Spieler> <Rang>§8!"));
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (RangSQL.getRangId(player.getUniqueId().toString()) > 7) {
                ArrayList<String> complete = Lists.newArrayList();
                if (args.length >= 1) {
                    if(args.length == 1) {
                        ArrayList<String> players = new ArrayList<>();
                        for (ProxiedPlayer proxiedPlayer : ProxyServer.getInstance().getPlayers()) {
                            players.add(proxiedPlayer.getName());
                        }
                        return players;
                    }
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                    if(target == null) {
                        return Lists.newArrayList("");
                    }
                    if (args.length == 2) {
                        for (Rang rang : Rang.getRangs()) {
                            if (RangSQL.getRang(target.getUniqueId().toString()) != rang) {
                                complete.add(rang.getName());
                            }
                        }
                    }
                }
                return complete;
            } else {
                ArrayList<String> complete = Lists.newArrayList("Keine Rechte");
                return complete;
            }
        } else {
            ArrayList<String> complete = Lists.newArrayList();
            if (args.length >= 1) {
                if(args.length == 1) {
                    ArrayList<String> players = new ArrayList<>();
                    for (ProxiedPlayer proxiedPlayer : ProxyServer.getInstance().getPlayers()) {
                        players.add(proxiedPlayer.getName());
                    }
                    return players;
                }
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                if(target == null) {
                    return Lists.newArrayList("");
                }
                if (args.length == 2) {
                    for (Rang rang : Rang.getRangs()) {
                        if (RangSQL.getRang(target.getUniqueId().toString()) != rang) {
                            complete.add(rang.getName());
                        }
                    }
                }
            }
            return complete;
        }
    }
}

