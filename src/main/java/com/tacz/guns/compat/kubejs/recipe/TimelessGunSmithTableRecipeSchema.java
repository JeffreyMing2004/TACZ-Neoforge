package com.tacz.guns.compat.kubejs.recipe;

import com.tacz.guns.crafting.GunSmithTableIngredient;
import com.tacz.guns.compat.kubejs.util.GunSmithTableResultInfo;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

import java.util.List;

public interface TimelessGunSmithTableRecipeSchema {
    RecipeKey<GunSmithTableResultInfo> RESULT = GunSmithTableResultComponents.RESULT_INFO.outputKey("result");
    RecipeKey<List<GunSmithTableIngredient>> MATERIALS = GunSmithTableResultComponents.MATERIAL.asListOrSelf().inputKey("materials");

    RecipeSchema SCHEMA = new RecipeSchema(RESULT, MATERIALS);
}
