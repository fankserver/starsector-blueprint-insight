package org.fankserver.blueprintinsight;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;

public final class BlueprintInsightModPlugin extends BaseModPlugin {
    static final String STATUS_HULLMOD_ID = "blueprint_insight_status";

    @Override
    public void onApplicationLoad() {
        for (ShipHullSpecAPI hull : Global.getSettings().getAllShipHullSpecs()) {
            if (shouldAnnotate(hull) && !hull.isBuiltInMod(STATUS_HULLMOD_ID)) {
                hull.addBuiltInMod(STATUS_HULLMOD_ID);
            }
        }
    }

    private static boolean shouldAnnotate(ShipHullSpecAPI hull) {
        return hull.getHullSize() != ShipAPI.HullSize.FIGHTER
                && !hull.getHints().contains(ShipHullSpecAPI.ShipTypeHints.MODULE)
                && !hull.getHints().contains(ShipHullSpecAPI.ShipTypeHints.STATION);
    }
}
