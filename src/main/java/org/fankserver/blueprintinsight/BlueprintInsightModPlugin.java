package org.fankserver.blueprintinsight;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;

import java.util.IdentityHashMap;
import java.util.Map;

public final class BlueprintInsightModPlugin extends BaseModPlugin {
    private static final Map<ShipHullSpecAPI, HullEntry> HULLS =
            new IdentityHashMap<ShipHullSpecAPI, HullEntry>();

    @Override
    public void onApplicationLoad() {
        HULLS.clear();
        for (ShipHullSpecAPI hull : Global.getSettings().getAllShipHullSpecs()) {
            if (!shouldAnnotate(hull)) {
                continue;
            }
            String originalPrefix = hull.getDescriptionPrefix();
            if (originalPrefix == null || originalPrefix.trim().isEmpty()) {
                continue;
            }
            HULLS.put(hull, new HullEntry(
                    originalPrefix, BlueprintStatusProvider.getBlueprintHullId(hull)));
        }
    }

    @Override
    public void onGameLoad(boolean newGame) {
        SectorAPI sector = Global.getSector();
        sector.removeTransientScriptsOfClass(BlueprintDescriptionScript.class);
        BlueprintDescriptionScript script = new BlueprintDescriptionScript(HULLS);
        script.refresh();
        sector.addTransientScript(script);
    }

    private static boolean shouldAnnotate(ShipHullSpecAPI hull) {
        return hull.getHullSize() != ShipAPI.HullSize.FIGHTER
                && !hull.getHints().contains(ShipHullSpecAPI.ShipTypeHints.MODULE)
                && !hull.getHints().contains(ShipHullSpecAPI.ShipTypeHints.STATION);
    }

    static final class HullEntry {
        final String originalPrefix;
        final String hullId;

        HullEntry(String originalPrefix, String hullId) {
            this.originalPrefix = originalPrefix;
            this.hullId = hullId;
        }
    }
}
