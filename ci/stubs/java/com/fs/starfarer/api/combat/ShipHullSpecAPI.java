package com.fs.starfarer.api.combat;
import java.util.EnumSet;
public interface ShipHullSpecAPI {
    String getHullId();
    ShipAPI.HullSize getHullSize();
    EnumSet<ShipTypeHints> getHints();
    boolean isBuiltInMod(String id);
    void addBuiltInMod(String id);
    boolean isDefaultDHull();
    ShipHullSpecAPI getDParentHull();
    boolean isRestoreToBase();
    ShipHullSpecAPI getBaseHull();
    enum ShipTypeHints { MODULE, STATION }
}
