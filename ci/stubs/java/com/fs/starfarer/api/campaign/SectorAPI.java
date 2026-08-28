package com.fs.starfarer.api.campaign;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
public interface SectorAPI {
    FactionAPI getPlayerFaction();
    MemoryAPI getMemoryWithoutUpdate();
}
