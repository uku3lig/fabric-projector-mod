package com.github.hashicraft.projector.config;

import com.github.hashicraft.projector.ProjectorMod;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class RedirectConfig {
    private static final HashMap<String, String> redirects = new HashMap<>();

    public static void loadConfig() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("projector_redirects.json");

        try {
            redirects.clear();
            JsonObject object = new Gson().fromJson(Files.newBufferedReader(path), JsonObject.class);
            object.asMap().forEach((s, e) -> redirects.put(s, e.getAsString()));
            ProjectorMod.LOGGER.info("Redirect config loaded {} redirects", redirects.size());
        } catch (IOException e) {
            ProjectorMod.LOGGER.error("Error reading {} file", path, e);
        }
    }

    public static String getRedirectedUrl(String originalUrl) {
        return redirects.getOrDefault(originalUrl, originalUrl);
    }
}
