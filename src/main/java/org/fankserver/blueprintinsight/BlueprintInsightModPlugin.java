package org.fankserver.blueprintinsight;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.loading.Description;

import java.util.IdentityHashMap;
import java.util.Map;

public final class BlueprintInsightModPlugin extends BaseModPlugin {
    private static final Map<Description, DescriptionEntry> DESCRIPTIONS =
            new IdentityHashMap<Description, DescriptionEntry>();

    @Override
    public void onApplicationLoad() {
        DESCRIPTIONS.clear();
        for (ShipHullSpecAPI hull : Global.getSettings().getAllShipHullSpecs()) {
            if (!shouldAnnotate(hull)) {
                continue;
            }
            Description description = Global.getSettings().getDescription(
                    hull.getDescriptionId(), Description.Type.SHIP);
            if (description == null || !description.hasText1() || DESCRIPTIONS.containsKey(description)) {
                continue;
            }
            DESCRIPTIONS.put(description, new DescriptionEntry(
                    description.getText1(), BlueprintStatusProvider.getBlueprintHullId(hull)));
        }
    }

    @Override
    public void onGameLoad(boolean newGame) {
        SectorAPI sector = Global.getSector();
        sector.removeTransientScriptsOfClass(BlueprintDescriptionScript.class);
        BlueprintDescriptionScript script = new BlueprintDescriptionScript(DESCRIPTIONS);
        script.refresh();
        sector.addTransientScript(script);
    }

    private static boolean shouldAnnotate(ShipHullSpecAPI hull) {
        return hull.getHullSize() != ShipAPI.HullSize.FIGHTER
                && !hull.getHints().contains(ShipHullSpecAPI.ShipTypeHints.MODULE)
                && !hull.getHints().contains(ShipHullSpecAPI.ShipTypeHints.STATION);
    }

    static final class DescriptionEntry {
        final String originalText;
        final String hullId;

        DescriptionEntry(String originalText, String hullId) {
            this.originalText = originalText;
            this.hullId = hullId;
        }
    }
}
