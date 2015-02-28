package com.creysys.ThermalScience.compat;

import com.creysys.ThermalScience.gui.GuiCompressor;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

/**
 * Created by Creysys on 10 Feb 15.
 */
public class RecipeHandlerCompressor extends RecipeHandlerMachine {
    public RecipeHandlerCompressor() {
        super(new GuiCompressor(null,null), ThermalScienceRecipes.compressorRecipes);
    }
}