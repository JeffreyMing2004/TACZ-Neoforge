package com.tacz.guns.compat.kubejs.util;

import com.google.gson.JsonObject;
import com.tacz.guns.crafting.result.GunSmithTableResult;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.Map;

public class GunSmithTableResultInfo {
    private static final String TYPE_KEY = "type";
    private static final String ID_KEY = "id";
    private static final String COUNT_KEY = "count";
    private static final String NBT_KEY = "nbt";
    private static final String CUSTOM_ITEM_KEY = "item";
    private static final String OUTPUT_GROUP_KEY = "group";
    private final JsonObject json;

    private GunSmithTableResultInfo() {
        this(new JsonObject());
    }

    private GunSmithTableResultInfo(JsonObject jsonObject) {
        this.json = (jsonObject != null) ? jsonObject : new JsonObject();
    }

    public static GunSmithTableResultInfo create() {
        return new GunSmithTableResultInfo();
    }

    public static GunSmithTableResultInfo createFromJson(JsonObject jsonObject) {
        return new GunSmithTableResultInfo(jsonObject);
    }

    public static GunSmithTableResultInfo createFromItemStack(ItemStack stack) {
        GunSmithTableResultInfo info = create().setType(GunSmithTableResult.CUSTOM);
        JsonObject itemJson = new JsonObject();
        itemJson.addProperty("id", stack.getItemHolder().unwrapKey().map(key -> key.location().toString()).orElseThrow());
        itemJson.addProperty("count", stack.getCount());
        info.setCustomItem(itemJson);
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null && !customData.getUnsafe().isEmpty()) {
            info.setNbt(customData.getUnsafe());
        }
        return info;
    }

    /**
     * {@link GunSmithTableResultInfo}的TypeWrapper, 将其他类型转为{@link GunSmithTableResultInfo}
     * object为其他类型时优先解析{@link JsonObject}，其次{@link ItemStack}
     * 之后尝试转化为{@link String}解析为{@link ResourceLocation}
     * 以上均不成功时最终{@link JsonIO#of(Object)}解析
     * @param object 输入待转化对象
     * @return {@link GunSmithTableResultInfo}
     */
    public static GunSmithTableResultInfo of(Object object) {
        if (object instanceof GunSmithTableResultInfo info) {
            return info;
        } else if (object instanceof JsonObject jsonObject) {
            return createFromJson(jsonObject);
        } else if (object instanceof ItemStack stack) {
            return createFromItemStack(stack);
        }
        String idString = object.toString();
        ResourceLocation rl = ResourceLocation.tryParse(idString);
        if (rl != null) {
            TimelessItemWrapper.ItemIndexInfo indexInfo = TimelessItemWrapper.ItemIndexInfo.createFromResourceLocation(rl);
            if (indexInfo.isValidForRecipe()) {
                return create().setType(indexInfo.getParent()).setId(indexInfo.getIndexId());
            }
        }
        RegistryAccessContainer access = RegistryAccessContainer.current;
        if (access == null) {
            access = RegistryAccessContainer.BUILTIN;
        }
        ItemStack stack = ItemStackJS.wrap(access, object);
        if (!stack.isEmpty()) {
            return createFromItemStack(stack);
        }
        //以上都不匹配，尝试按Map处理
        if (object instanceof Map<?, ?> map) {
            return createFromJson(fromMap(map));
        }
        throw new IllegalArgumentException("Cannot convert " + object + " to GunSmithTableResultInfo");
    }

    private static JsonObject fromMap(Map<?, ?> map) {
        JsonObject json = new JsonObject();
        for (Map.Entry<?, ?> e : map.entrySet()) {
            String key = String.valueOf(e.getKey());
            Object v = e.getValue();
            switch (v) {
                case Boolean b -> json.addProperty(key, b);
                case Number n -> json.addProperty(key, n);
                case String s -> json.addProperty(key, s);
                case Map<?, ?> m -> json.add(key, fromMap(m));
                case null, default -> {
                }
            }
        }
        return json;
    }

    public String getType() {
        return GsonHelper.getAsString(this.json, TYPE_KEY);
    }

    public GunSmithTableResultInfo setType(String typeName) {
        this.json.addProperty(TYPE_KEY, typeName);
        return this;
    }

    public ResourceLocation getId() {
        return ResourceLocation.tryParse(GsonHelper.getAsString(this.json, ID_KEY));
    }

    public GunSmithTableResultInfo setId(ResourceLocation id) {
        this.json.addProperty(ID_KEY, id.toString());
        return this;
    }

    public CompoundTag getNbt() {
        if (!this.json.has(NBT_KEY)) {
            return null;
        }
        try {
            return TagParser.parseTag(GsonHelper.getAsString(this.json, NBT_KEY));
        } catch (Exception e) {
            return null;
        }
    }

    public GunSmithTableResultInfo setNbt(CompoundTag nbt) {
        this.json.addProperty(NBT_KEY, nbt.toString());
        return this;
    }

    public int getCount() {
        return Math.max(GsonHelper.getAsInt(this.json, COUNT_KEY, 1), 1);
    }

    public GunSmithTableResultInfo setCount(int count) {
        this.json.addProperty(COUNT_KEY, count);
        return this;
    }

    public JsonObject getCustomItem() {
        return GsonHelper.getAsJsonObject(this.json, CUSTOM_ITEM_KEY);
    }

    public GunSmithTableResultInfo setCustomItem(JsonObject itemJson) {
        this.json.add(CUSTOM_ITEM_KEY, itemJson);
        return this;
    }

    public GunSmithTableResultInfo setGroupName(String groupName) {
        this.json.addProperty(OUTPUT_GROUP_KEY, groupName);
        return this;
    }

    public GunSmithTableResultInfo setGroup(OutputGroupName group) {
        this.setGroupName(group.getName());
        return this;
    }

    public JsonObject toJson() {
        return json;
    }

    public enum OutputGroupName {
        AMMO("ammo"),
        EXTENDED_MAG("extended_mag"),
        GRIP("grip"),
        MG("mg"),
        MUZZLE("muzzle"),
        PISTOL("pistol"),
        RIFLE("rifle"),
        RPG("rpg"),
        SCOPE("scope"),
        SHOTGUN("shotgun"),
        SMG("smg"),
        SNIPER("sniper"),
        STOCK("stock");

        private final String name;

        OutputGroupName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
