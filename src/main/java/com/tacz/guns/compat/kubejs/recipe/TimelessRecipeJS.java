package com.tacz.guns.compat.kubejs.recipe;

import com.tacz.guns.compat.kubejs.util.GunSmithTableResultInfo;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;

public class TimelessRecipeJS extends KubeRecipe {
    private String outputGroup = "";
    private GunSmithTableResultInfo info;

    public GunSmithTableResultInfo getResultInfo() {
        return info;
    }

    public void setResultInfo(GunSmithTableResultInfo info) {
        this.info = info;
    }

    /**
     * 设置后对配方结果的影响改至{@link com.tacz.guns.compat.kubejs.util.GunSmithTableResultInfo}
     */
    public TimelessRecipeJS outputGroupName(String group) {
        getResultInfo().setGroupName(group);
        return this;
    }

    /**
     * 设置后对配方结果的影响改至{@link com.tacz.guns.compat.kubejs.util.GunSmithTableResultInfo}
     */
    public TimelessRecipeJS outputGroup(GunSmithTableResultInfo.OutputGroupName group) {
        return outputGroupName(group.getName());
    }
}
