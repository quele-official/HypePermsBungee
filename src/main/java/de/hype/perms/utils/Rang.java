package de.hype.perms.utils;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public enum Rang {

    Admin("Admin", 9, "§4Admin §8| §4", 693213354311024711L),
    SrStaff("SrStaff", 8, "§cSrStaff §8| §c", 693213357666467962L),
    Staff("Staff", 7, "§cStaff §8| §c", 693213362024349746L),
    Partner("Partner", 6, "§aPartner §8| §a", 693213366818439168L),
    MediaPlus("Media+", 5, "§0Media§4+ §8| §0", 693213367460036618L),
    Media("Media", 4, "§5Media §8| §5", 693213367824810056L),
    Warrior("Warrior", 3, "§dWarrior §8| §d", 693213369934676068L),
    Hyper("Hyper", 2, "§eHyper §8| §d", 693213370597244948L),
    Spieler("Spieler", 1, "§7 §8| §7", 693213371448819834L);


    private final String name;
    private final Integer id;
    private final String prefix;
    private final Long discordId;

    Rang(String name, int id, String prefix, Long discordId) {
        this.name = name;
        this.id = id;
        this.prefix = prefix;
        this.discordId = discordId;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public Long getDiscordId() {
        return discordId;
    }

    public static ArrayList<Rang> getRangs() {
        return Lists.newArrayList(Admin, SrStaff, Staff, Partner, MediaPlus, Media, Warrior, Hyper, Spieler);
    }

    public static Rang getRangByName(String name) {
        for(Rang rang : getRangs()) {
            if(rang.getName().equalsIgnoreCase(name)) {
                return rang;
            }
        }
        return null;
    }
}
