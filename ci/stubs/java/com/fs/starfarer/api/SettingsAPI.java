package com.fs.starfarer.api;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import java.util.List;
public interface SettingsAPI {
    List<ShipHullSpecAPI> getAllShipHullSpecs();
    ModManagerAPI getModManager();
}
