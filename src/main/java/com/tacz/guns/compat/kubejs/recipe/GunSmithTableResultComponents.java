package com.tacz.guns.compat.kubejs.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.tacz.guns.compat.kubejs.util.GunSmithTableResultInfo;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.world.item.ItemStack;

public class GunSmithTableResultComponents {
    public static final RecipeComponent<GunSmithTableResultInfo> RESULT_INFO = new RecipeComponent<>() {
        @Override
        public Codec<GunSmithTableResultInfo> codec() {
            return Codec.PASSTHROUGH.xmap(
                    dynamic -> GunSmithTableResultInfo.createFromJson(
                            dynamic.convert(JsonOps.INSTANCE).getValue().getAsJsonObject()),
                    info -> new Dynamic<>(JsonOps.INSTANCE, info.toJson())
            );
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.of(GunSmithTableResultInfo.class);
        }

        @Override
        public boolean hasPriority(Context cx, KubeRecipe recipe, Object from) {
            return from instanceof GunSmithTableResultInfo || from instanceof ItemStack || from instanceof JsonObject;
        }

        @Override
        public GunSmithTableResultInfo wrap(Context cx, KubeRecipe recipe, Object from) {
            return GunSmithTableResultInfo.of(from);
        }
    };
}
