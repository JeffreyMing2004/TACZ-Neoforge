package com.tacz.guns.compat.kubejs;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.GunProperties;
import com.tacz.guns.api.item.gun.GunItemManager;
import com.tacz.guns.compat.kubejs.custom.CustomGunItemBuilder;
import com.tacz.guns.compat.kubejs.events.GunKubeJSEvents;
import com.tacz.guns.compat.kubejs.events.TimelessClientEvents;
import com.tacz.guns.compat.kubejs.events.TimelessCommonEvents;
import com.tacz.guns.compat.kubejs.events.TimelessServerEvents;
import com.tacz.guns.compat.kubejs.recipe.TimelessGunSmithTableRecipeSchema;
import com.tacz.guns.compat.kubejs.recipe.TimelessRecipeJS;
import com.tacz.guns.compat.kubejs.util.GunSmithTableResultInfo;
import com.tacz.guns.compat.kubejs.util.TimelessItemWrapper;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeFactoryRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;

public class TimelessKubeJSPlugin implements KubeJSPlugin {
    public static final String KUBEJS_MODID = "kubejs";
    private static final Map<String, ResourceLocation> GUNTYPE_REGISTER_MAP = new HashMap<>();

    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.ITEM, reg -> reg.add("tacz_gun", CustomGunItemBuilder.class, CustomGunItemBuilder::new));
    }

    @Override
    public void registerBindings(BindingRegistry event) {
        event.add("TimelessItem", TimelessItemWrapper.class);
        event.add("GunProperties", GunProperties.class);
        event.add("GunSmithTableResultInfo", GunSmithTableResultInfo.class);
    }

    @Override
    public void registerEvents(dev.latvian.mods.kubejs.event.EventGroupRegistry registry) {
        //提早加载防止出现问题
        TimelessCommonEvents.INSTANCE.init();
        TimelessServerEvents.INSTANCE.init();
        TimelessClientEvents.INSTANCE.init();
        registry.register(GunKubeJSEvents.GROUP);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.namespace(GunMod.MOD_ID).register("gun_smith_table_crafting", TimelessGunSmithTableRecipeSchema.SCHEMA);
    }

    @Override
    public void registerRecipeFactories(RecipeFactoryRegistry registry) {
        registry.register(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "gun_smith_table_crafting"),
                TimelessRecipeJS.class, TimelessRecipeJS::new);
    }

    public static void registerGunType(String typeName, ResourceLocation itemId) {
        GUNTYPE_REGISTER_MAP.put(typeName, itemId);
    }

    /**
     * 在物品注册事件时，将 KubeJS 自定义枪械物品接入 GunItemManager
     * 需要在模组构造阶段通过 mod 总线监听 {@link RegisterEvent}
     */
    public static void onRegisterItems(RegisterEvent event) {
        if (!GUNTYPE_REGISTER_MAP.isEmpty() && event.getRegistryKey().equals(Registries.ITEM)) {
            for (Map.Entry<String, ResourceLocation> entry : GUNTYPE_REGISTER_MAP.entrySet()) {
                GunItemManager.registerGunItem(entry.getKey(), DeferredItem.createItem(entry.getValue()));
            }
            GUNTYPE_REGISTER_MAP.clear();
        }
    }
}
