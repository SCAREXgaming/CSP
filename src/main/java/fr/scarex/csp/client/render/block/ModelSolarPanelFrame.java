package fr.scarex.csp.client.render.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelSolarPanelFrame extends ModelBase
{
    public ModelRenderer top;
    public ModelRenderer base;
    public ModelRenderer rod;

    public ModelSolarPanelFrame() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.top = new ModelRenderer(this, 0, 0);
        this.top.mirror = true;
        this.top.addBox(-8F, -2F, -8F, 16, 2, 16);
        this.top.setRotationPoint(0F, 10F, 0F);
        this.top.setTextureSize(64, 32);
        setRotation(this.top, 0F, 0F, 0F);

        this.base = new ModelRenderer(this, 0, 19);
        this.base.mirror = true;
        this.base.addBox(-6F, 0F, -6F, 12, 1, 12);
        this.base.setRotationPoint(0F, 23F, 0F);
        this.base.setTextureSize(64, 32);
        setRotation(this.base, 0F, 0F, 0F);

        this.rod = new ModelRenderer(this, 56, 16);
        this.rod.mirror = true;
        this.rod.addBox(-1F, -13F, -1F, 2, 13, 2);
        this.rod.setRotationPoint(0F, 23F, 0F);
        this.rod.setTextureSize(64, 32);
        setRotation(this.rod, 0F, 0F, 0F);
    }

    public void renderAll() {
        this.top.render(0.0625F);
        this.base.render(0.0625F);
        this.rod.render(0.0625F);
    }

    public static void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
