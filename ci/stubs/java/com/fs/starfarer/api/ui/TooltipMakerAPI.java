package com.fs.starfarer.api.ui;
import java.awt.Color;
public interface TooltipMakerAPI {
    Object addPara(String text, float pad);
    Object addPara(String text, float pad, Color color, String... highlights);
}
