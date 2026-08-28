package org.fankserver.blueprintinsight;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;

import java.util.Map;

final class BlueprintStatusProvider {
    private static final String INDEVO_MOD_ID = "IndEvo";
    private static final String INDEVO_PROGRESS_KEY = "$IndEvo_researchProgress";

    private BlueprintStatusProvider() {
    }

    static String getBlueprintHullId(ShipHullSpecAPI hull) {
        ShipHullSpecAPI result = hull;
        if (hull.isDefaultDHull() && hull.getDParentHull() != null) {
            result = hull.getDParentHull();
        } else if (hull.isRestoreToBase() && hull.getBaseHull() != null) {
            result = hull.getBaseHull();
        }
        return result.getHullId();
    }

    static String getStatusLine(String hullId) {
        if (Global.getSector().getPlayerFaction().knowsShip(hullId)) {
            return "Blueprint: known";
        }

        if (isIndEvoEnabled()) {
            float progress = getIndEvoProgress(hullId);
            if (progress > 0f) {
                int percent = Math.round(Math.min(progress, 1f) * 100f);
                return "Reverse engineering: " + percent + "%";
            }
        }
        return "Blueprint: unknown";
    }

    private static boolean isIndEvoEnabled() {
        return Global.getSettings().getModManager().isModEnabled(INDEVO_MOD_ID);
    }

    private static float getIndEvoProgress(String hullId) {
        MemoryAPI memory = Global.getSector().getMemoryWithoutUpdate();
        if (!memory.contains(INDEVO_PROGRESS_KEY)) {
            return 0f;
        }
        Object value = memory.get(INDEVO_PROGRESS_KEY);
        if (!(value instanceof Map<?, ?>)) {
            return 0f;
        }
        Object progress = ((Map<?, ?>) value).get(hullId);
        return progress instanceof Number ? ((Number) progress).floatValue() : 0f;
    }
}
