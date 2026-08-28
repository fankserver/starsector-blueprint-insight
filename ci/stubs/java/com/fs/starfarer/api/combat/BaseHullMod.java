package com.fs.starfarer.api.combat;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
public class BaseHullMod {
    public boolean shouldAddDescriptionToTooltip(ShipAPI.HullSize hullSize, ShipAPI ship, boolean isForModSpec) { return true; }
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) { }
}
