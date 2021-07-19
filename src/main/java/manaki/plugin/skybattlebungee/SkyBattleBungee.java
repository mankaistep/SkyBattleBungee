package manaki.plugin.skybattlebungee;

import manaki.plugin.skybattlebungee.executor.Executor;
import manaki.plugin.skybattlebungee.request.util.Requests;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class SkyBattleBungee extends Plugin implements Listener {

    public static final String CHANNEL = "game:skybattle";

    private Executor executor;

    @Override
    public void onEnable() {
        this.executor = new Executor(this);

        this.getProxy().registerChannel(CHANNEL);
        this.getLogger().info("Registered channel " + CHANNEL);

        BungeeCord.getInstance().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) throws IOException {
        if (!e.getTag().equals(CHANNEL)) return;

        var in = new DataInputStream(new ByteArrayInputStream(e.getData()));
        var type = in.readUTF();
        var data = in.readUTF();

        if (type.equalsIgnoreCase("skybattle-start")) {
            var sr = Requests.parseStart(data);
            this.executor.sendStart(sr);
        }

        else if (type.equalsIgnoreCase("skybattle-quit")) {
            var qr = Requests.parseQuit(data);
            this.executor.sendQuit(qr);
        }

        else if (type.equalsIgnoreCase("skybattle-join")) {
            var jr = Requests.parseJoin(data);
            this.executor.sendJoin(jr);
        }
    }

}
