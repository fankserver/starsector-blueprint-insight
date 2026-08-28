package com.fs.starfarer.api;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.loading.Description;
import java.util.List;
public interface SettingsAPI {
    List<ShipHullSpecAPI> getAllShipHullSpecs();
    ModManagerAPI getModManager();
    Description getDescription(String id, Description.Type type);
}
