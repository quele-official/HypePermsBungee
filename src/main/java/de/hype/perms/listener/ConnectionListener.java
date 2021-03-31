package de.hype.perms.listener;

import de.hype.perms.utils.RangSQL;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        RangSQL.registerPlayer(event.getPlayer());
    }
}
