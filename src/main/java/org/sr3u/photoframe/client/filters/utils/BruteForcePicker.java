package org.sr3u.photoframe.client.filters.utils;

import org.sr3u.photoframe.server.Main;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BruteForcePicker implements ColorPicker {

    private Map<Integer, Color> selectionCache = new HashMap<>();

    @Override
    public Color closestColor(int rgb, Color[] palette) {
        return selectionCache.computeIfAbsent(rgb, k -> {
            Color c = new Color(rgb);
            Color closest = palette[0];
            for (Color n : palette) {
                if (distance(n, c) < distance(closest, c)) {
                    closest = n;
                }
            }
            return closest;
        });
    }

    @Override
    public void reset() {
        if (selectionCache.size() > Main.settings.getClient().getColorCacheSize()) {
            System.out.println("clearing selectionCache, as it was larger than the threshold (" + selectionCache.size() + " > " + Main.settings.getClient().getColorCacheSize() + ")");
            selectionCache.clear();
        }
    }

    protected double distance(Color c1, Color c2) {
        return squareDistance(c1, c2);
    }

    public static int squareDistance(Color c1, Color c2) {
        int Rdiff = c1.getRed() - c2.getRed();
        int Gdiff = c1.getGreen() - c2.getBlue();
        int Bdiff = c1.getGreen() - c2.getBlue();
        return Rdiff * Rdiff + Gdiff * Gdiff + Bdiff * Bdiff;
    }

    public static double normalizedDistance(Color c1, Color c2) {
        return squareDistance(c1, c2) / 195075.0;
    }
}
