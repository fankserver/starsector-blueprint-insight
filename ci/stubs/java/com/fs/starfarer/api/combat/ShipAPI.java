package com.fs.starfarer.api.combat;
public interface ShipAPI {
    ShipHullSpecAPI getHullSpec();
    enum HullSize { DEFAULT, FIGHTER, FRIGATE, DESTROYER, CRUISER, CAPITAL_SHIP }
}
