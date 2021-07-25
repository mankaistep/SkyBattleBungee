package manaki.plugin.skybattlebungee.executor;

import manaki.plugin.skybattlebungee.SkyBattleBungee;
import manaki.plugin.skybattlebungee.request.JoinRequest;
import manaki.plugin.skybattlebungee.request.QuitRequest;
import manaki.plugin.skybattlebungee.request.StartRequest;
import manaki.plugin.skybattlebungee.request.result.PlayerResult;
import manaki.plugin.skybattlebungee.team.Team;
import manaki.plugin.skybattlebungee.team.TeamPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Executor {

    public static final String MAIN_SERVER = "sorasky3";
    public static final String GAME_SERVER = "skybattle";

    private Plugin plugin;

    public Executor(Plugin plugin) {
        this.plugin = plugin;
    }

    public void sendStart(StartRequest sr) {
        // Server
        ServerInfo gameSv = ProxyServer.getInstance().getServerInfo(GAME_SERVER);

        // Send players
        for (Team team : sr.getTeams()) {
            for (TeamPlayer tplayer : team.getPlayers()) {
                var name = tplayer.getName();
                var player = BungeeCord.getInstance().getPlayer(name);
                player.connect(gameSv);
            }
        }

        // Send request to game server
        var rs = sr.toString();
        var stream = new ByteArrayOutputStream();
        var out = new DataOutputStream(stream);
        try {
            out.writeUTF("skybattle-start");
            out.writeUTF(rs);
        } catch (IOException e) {
            ProxyServer.getInstance().getLogger().severe("An I/O error occurred!");
        }
        gameSv.sendData(SkyBattleBungee.CHANNEL, stream.toByteArray());
    }

    public void sendQuit(QuitRequest qr) {
        // Server
        ServerInfo mainSv = ProxyServer.getInstance().getServerInfo(MAIN_SERVER);

        // Send player
        var name = qr.getPlayer();
        var player = BungeeCord.getInstance().getPlayer(name);
        player.connect(mainSv);

        // Send request
        var rs = qr.toString();
        var stream = new ByteArrayOutputStream();
        var out = new DataOutputStream(stream);
        try {
            out.writeUTF("skybattle-quit");
            out.writeUTF(rs);
        } catch (IOException e) {
            ProxyServer.getInstance().getLogger().severe("An I/O error occurred!");
        }
        mainSv.sendData(SkyBattleBungee.CHANNEL, stream.toByteArray());
    }

    public void sendResult(PlayerResult pr) {
        // Server
        ServerInfo mainSv = ProxyServer.getInstance().getServerInfo(MAIN_SERVER);

        // Send request
        var rs = pr.toString();
        var stream = new ByteArrayOutputStream();
        var out = new DataOutputStream(stream);
        try {
            out.writeUTF("skybattle-player-result");
            out.writeUTF(rs);
        } catch (IOException e) {
            ProxyServer.getInstance().getLogger().severe("An I/O error occurred!");
        }
        mainSv.sendData(SkyBattleBungee.CHANNEL, stream.toByteArray());
    }

    public void sendJoin(JoinRequest jr) {
        // Server
        ServerInfo gameSv = ProxyServer.getInstance().getServerInfo(GAME_SERVER);

        // Send player
        var name = jr.getPlayer();
        var player = BungeeCord.getInstance().getPlayer(name);
        player.connect(gameSv);

        // Send request to game server
        var js = jr.toString();
        var stream = new ByteArrayOutputStream();
        var out = new DataOutputStream(stream);
        try {
            out.writeUTF("skybattle-join");
            out.writeUTF(js);
        } catch (IOException e) {
            ProxyServer.getInstance().getLogger().severe("An I/O error occurred!");
        }
        gameSv.sendData(SkyBattleBungee.CHANNEL, stream.toByteArray());
    }

}
