package org.fankserver.blueprintinsight;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.Map;

public final class BlueprintStatusHullMod extends BaseHullMod {
    private static final String INDEVO_MOD_ID = "IndEvo";
    private static final String INDEVO_PROGRESS_KEY = "$IndEvo_researchProgress";
    private static final float SECTION_PAD = 10f;

    @Override
    public boolean shouldAddDescriptionToTooltip(ShipAPI.HullSize hullSize, ShipAPI ship,
                                                  boolean isForModSpec) {
        return ship != null && !isForModSpec;
    }

    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize,
                                          ShipAPI ship, float width, boolean isForModSpec) {
        if (ship == null || Global.getSector() == null) {
            return;
        }

        ShipHullSpecAPI baseHull = getBlueprintHull(ship.getHullSpec());
        String hullId = baseHull.getHullId();
        FactionAPI playerFaction = Global.getSector().getPlayerFaction();

        if (playerFaction.knowsShip(hullId)) {
            tooltip.addPara("Blueprint known", SECTION_PAD,
                    Misc.getPositiveHighlightColor(), "Blueprint known");
            tooltip.addPara("Your faction can produce this hull. Selling or disposing of this ship will not lose access to its blueprint.",
                    3f);
            return;
        }

        Float progress = getIndEvoProgress(hullId);
        if (progress != null && progress > 0f) {
            int percent = Math.round(Math.min(progress, 1f) * 100f);
            String percentage = percent + "%";
            tooltip.addPara("Blueprint unknown — reverse engineering: " + percentage,
                    SECTION_PAD, Misc.getHighlightColor(), percentage);
            tooltip.addPara("Industrial.Evolution has recorded research progress for this hull. Consider sending another example to an Engineering Hub instead of selling it.",
                    3f);
        } else if (isIndEvoEnabled()) {
            tooltip.addPara("Blueprint unknown — no reverse-engineering progress",
                    SECTION_PAD, Misc.getNegativeHighlightColor(),
                    "Blueprint unknown", "no reverse-engineering progress");
            tooltip.addPara("Your faction cannot produce this hull and Industrial.Evolution has no recorded Engineering Hub progress for it.",
                    3f);
        } else {
            tooltip.addPara("Blueprint unknown", SECTION_PAD,
                    Misc.getNegativeHighlightColor(), "Blueprint unknown");
            tooltip.addPara("Your faction cannot currently produce this hull.", 3f);
        }
    }

    private static ShipHullSpecAPI getBlueprintHull(ShipHullSpecAPI hull) {
        ShipHullSpecAPI result = hull;
        if (hull.isDefaultDHull() && hull.getDParentHull() != null) {
            result = hull.getDParentHull();
        } else if (hull.isRestoreToBase() && hull.getBaseHull() != null) {
            result = hull.getBaseHull();
        }
        return result;
    }

    private static boolean isIndEvoEnabled() {
        return Global.getSettings().getModManager().isModEnabled(INDEVO_MOD_ID);
    }

    private static Float getIndEvoProgress(String hullId) {
        if (!isIndEvoEnabled()) {
            return null;
        }

        SectorAPI sector = Global.getSector();
        if (sector == null) {
            return null;
        }
        MemoryAPI memory = sector.getMemoryWithoutUpdate();
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
