package com.creysys.ThermalScience.tileEntity;

import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipe;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;

import java.util.List;

/**
 * Created by Creysys on 1/31/2015.
 */
public class TileEntityCarbothermicFurnace extends TileEntityMachine {

    @Override
    public int getSizeInventory() {
        return 3;
    }

    @Override
    public String getInventoryName() {
        return "carbothermicFurnace";
    }

    @Override
    public List<ThermalScienceRecipe> getRecipes() {
        return ThermalScienceRecipes.carbothermicFurnaceRecipes;
    }

    @Override
    public int getCraftingInputSize() {
        return 2;
    }

    @Override
    public int getCraftingOutputSize() {
        return 1;
    }

    @Override
    public int[] getCraftingInputSlots() {
        return new int[]{0,1};
    }

    @Override
    public int[] getCraftingOutputSlots() {
        return new int[]{2};
    }
}
