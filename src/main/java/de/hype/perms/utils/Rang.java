package de.hype.perms.utils;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public enum Rang {
    
    Admin("Admin", 9, "§4Admin §8| §4"),
    SrStaff("SrStaff", 8, "§cSrStaff §8| §c"),
    Staff("Staff", 7, "§cStaff §8| §c"),
    Partner("Partner", 6, "§aPartner §8| §a"),
    MediaPlus("Media+", 5, "§0YT§4+ §8| §0"),
    Media("Media", 4, "§5YT §8| §5"),
    Warrior("Warrior", 3, "§dWarrior §8| §d"),
    Hyper("Hyper", 2, "§eHyper §8| §d"),
    Spieler("Spieler", 1, "§7 §8| §7");


    private String name;
    private Integer id;
    private String prefix;

    Rang(String name, int id, String prefix) {
        this.name = name;
        this.id = id;
        this.prefix = prefix;
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

    public static ArrayList<Rang> getRangs() {
        return Lists.newArrayList(Admin, SrStaff, Staff, Partner, MediaPlus, Media, Warrior, Hyper, Spieler);
    }
}
