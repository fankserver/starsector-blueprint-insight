package org.fankserver.blueprintinsight;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.loading.Description;

import java.util.Map;

final class BlueprintDescriptionScript implements EveryFrameScript {
    private static final long REFRESH_INTERVAL_NANOS = 500_000_000L;
    private final Map<Description, BlueprintInsightModPlugin.DescriptionEntry> descriptions;
    private long nextRefreshNanos;

    BlueprintDescriptionScript(Map<Description, BlueprintInsightModPlugin.DescriptionEntry> descriptions) {
        this.descriptions = descriptions;
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
        for (Map.Entry<Description, BlueprintInsightModPlugin.DescriptionEntry> item
                : descriptions.entrySet()) {
            BlueprintInsightModPlugin.DescriptionEntry entry = item.getValue();
            String status = BlueprintStatusProvider.getStatusLine(entry.hullId);
            item.getKey().setText1(entry.originalText + "\n\n" + status);
        }
    }
}
