package com.tacz.guns.compat.kubejs.recipe;

import com.tacz.guns.compat.kubejs.util.GunSmithTableResultInfo;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.SizedIngredientComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.List;

public interface TimelessGunSmithTableRecipeSchema {
    RecipeKey<GunSmithTableResultInfo> RESULT = GunSmithTableResultComponents.RESULT_INFO.outputKey("result");
    RecipeKey<List<SizedIngredient>> MATERIALS = SizedIngredientComponent.FLAT.asListOrSelf().inputKey("materials");

    RecipeSchema SCHEMA = new RecipeSchema(RESULT, MATERIALS);
}
