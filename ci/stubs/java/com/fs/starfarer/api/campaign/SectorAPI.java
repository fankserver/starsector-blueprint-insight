package com.fs.starfarer.api.campaign;
import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
public interface SectorAPI {
    FactionAPI getPlayerFaction();
    MemoryAPI getMemoryWithoutUpdate();
    void addTransientScript(EveryFrameScript script);
    void removeTransientScriptsOfClass(Class<?> scriptClass);
}
