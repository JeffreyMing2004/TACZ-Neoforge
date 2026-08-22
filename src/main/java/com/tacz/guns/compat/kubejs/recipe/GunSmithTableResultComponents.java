package com.tacz.guns.compat.kubejs.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.tacz.guns.compat.kubejs.util.GunSmithTableResultInfo;
import com.tacz.guns.crafting.GunSmithTableIngredient;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.IngredientWrapper;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.recipe.filter.RecipeMatchContext;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Map;

public class GunSmithTableResultComponents {
    public static final RecipeComponent<GunSmithTableResultInfo> RESULT_INFO = new RecipeComponent<>() {
        @Override
        public RecipeComponentType<?> type() {
            return RESULT_INFO_TYPE;
        }

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
        public boolean hasPriority(RecipeMatchContext cx, Object from) {
            return from instanceof GunSmithTableResultInfo || from instanceof ItemStack || from instanceof JsonObject;
        }

        @Override
        public GunSmithTableResultInfo wrap(RecipeScriptContext cx, Object from) {
            return GunSmithTableResultInfo.of(cx.cx(), from);
        }
    };

    /**
     * 材料输入组件：不依赖 KubeJS 内部易变 API（如 SizedIngredientComponent），
     * 序列化格式与运行时 {@link GunSmithTableIngredient#CODEC} 完全一致（{item: ..., count: n}）
     */
    public static final RecipeComponent<GunSmithTableIngredient> MATERIAL = new RecipeComponent<>() {
        @Override
        public RecipeComponentType<?> type() {
            return MATERIAL_TYPE;
        }

        @Override
        public Codec<GunSmithTableIngredient> codec() {
            return GunSmithTableIngredient.CODEC;
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.of(GunSmithTableIngredient.class);
        }

        @Override
        public boolean hasPriority(RecipeMatchContext cx, Object from) {
            return from instanceof GunSmithTableIngredient || from instanceof Ingredient
                    || from instanceof ItemStack || from instanceof CharSequence || from instanceof Map<?, ?>;
        }

        @Override
        public GunSmithTableIngredient wrap(RecipeScriptContext cx, Object from) {
            return wrapMaterial(cx.cx(), from);
        }
    };

    public static final RecipeComponentType<GunSmithTableResultInfo> RESULT_INFO_TYPE = RecipeComponentType.unit(
            KubeJS.id("gun_smith_table_result"), type -> RESULT_INFO);
    public static final RecipeComponentType<GunSmithTableIngredient> MATERIAL_TYPE = RecipeComponentType.unit(
            KubeJS.id("gun_smith_table_material"), type -> MATERIAL);

    static GunSmithTableIngredient wrapMaterial(Context cx, Object from) {
        int count = 1;
        Object source = from;
        if (from instanceof Map<?, ?> map && map.containsKey("item")) {
            if (map.get("count") instanceof Number n) {
                count = Math.max(n.intValue(), 1);
            }
            source = map.get("item");
        }
        if (source instanceof GunSmithTableIngredient holder) {
            return new GunSmithTableIngredient(holder.getIngredient(), count);
        }
        if (source == null) {
            throw new IllegalArgumentException("Invalid gun smith table material: " + from);
        }
        Ingredient ingredient = source instanceof Ingredient ing ? ing : IngredientWrapper.wrap(cx, source);
        return new GunSmithTableIngredient(ingredient, count);
    }
}
