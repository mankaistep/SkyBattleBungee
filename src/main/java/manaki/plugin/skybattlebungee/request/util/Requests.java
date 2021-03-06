package manaki.plugin.skybattlebungee.request.util;

import com.google.gson.GsonBuilder;
import manaki.plugin.skybattlebungee.request.JoinRequest;
import manaki.plugin.skybattlebungee.request.QuitRequest;
import manaki.plugin.skybattlebungee.request.StartRequest;
import manaki.plugin.skybattlebungee.request.result.PlayerResult;

public class Requests {

    public static StartRequest parseStart(String s) {
        return new GsonBuilder().create().fromJson(s, StartRequest.class);
    }

    public static QuitRequest parseQuit(String s) {
        return new GsonBuilder().create().fromJson(s, QuitRequest.class);
    }

    public static JoinRequest parseJoin(String s) {
        return new GsonBuilder().create().fromJson(s, JoinRequest.class);
    }

    public static PlayerResult parseResult(String s) {
        return new GsonBuilder().create().fromJson(s, PlayerResult.class);
    }

}
