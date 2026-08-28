package org.fankserver.blueprintinsight;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;

import java.util.Map;

final class BlueprintDescriptionScript implements EveryFrameScript {
    private static final long REFRESH_INTERVAL_NANOS = 500_000_000L;
    private final Map<ShipHullSpecAPI, BlueprintInsightModPlugin.HullEntry> hulls;
    private long nextRefreshNanos;

    BlueprintDescriptionScript(Map<ShipHullSpecAPI, BlueprintInsightModPlugin.HullEntry> hulls) {
        this.hulls = hulls;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return true;
    }

    @Override
    public void advance(float amount) {
        long now = System.nanoTime();
        if (now >= nextRefreshNanos) {
            refresh();
            nextRefreshNanos = now + REFRESH_INTERVAL_NANOS;
        }
    }

    void refresh() {
        for (Map.Entry<ShipHullSpecAPI, BlueprintInsightModPlugin.HullEntry> item
                : hulls.entrySet()) {
            BlueprintInsightModPlugin.HullEntry entry = item.getValue();
            String prefix = BlueprintStatusProvider.getStatusLine(entry.hullId);
            if (entry.originalPrefix != null && !entry.originalPrefix.trim().isEmpty()) {
                prefix += "\n" + entry.originalPrefix;
            }
            item.getKey().setDescriptionPrefix(prefix);
        }
    }
}
