package fr.scarex.csp.client.render.block;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelSolarCellConverter extends ModelBase
{
    public ModelRenderer top;
    public ModelRenderer base;
    public ModelRenderer rod1;
    public ModelRenderer rod2;
    public ModelRenderer rod3;
    public ModelRenderer rod4;
    public ModelRenderer itemSupport;

    public ModelSolarCellConverter() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.top = new ModelRenderer(this, 0, 0);
        this.top.mirror = true;
        this.top.addBox(-8F, -1F, -8F, 16, 2, 16);
        this.top.setRotationPoint(0F, 10F, 0F);
        this.top.setTextureSize(64, 32);
        setRotation(this.top, 0F, 0F, 0F);

        this.base = new ModelRenderer(this, 0, 19);
        this.base.mirror = true;
        this.base.addBox(-6F, 0F, -6F, 12, 1, 12);
        this.base.setRotationPoint(0F, 23F, 0F);
        this.base.setTextureSize(64, 32);
        setRotation(this.base, 0F, 0F, 0F);

        this.itemSupport = new ModelRenderer(this, 12, 23);
        this.itemSupport.mirror = true;
        this.itemSupport.addBox(-2F, -2F, -2F, 4, 2, 4);
        this.itemSupport.setRotationPoint(0F, 23F, 0F);
        this.itemSupport.setTextureSize(64, 32);
        setRotation(this.itemSupport, 0F, 0F, 0F);

        this.rod1 = new ModelRenderer(this, 56, 18);
        this.rod1.mirror = true;
        this.rod1.addBox(4F, -11F, 4F, 2, 12, 2);
        this.rod1.setRotationPoint(0F, 23F, 0F);
        this.rod1.setTextureSize(64, 32);
        setRotation(this.rod1, 0F, 0F, 0F);

        this.rod2 = new ModelRenderer(this, 56, 18);
        this.rod2.mirror = true;
        this.rod2.addBox(-6F, -11F, 4F, 2, 12, 2);
        this.rod2.setRotationPoint(0F, 23F, 0F);
        this.rod2.setTextureSize(64, 32);
        setRotation(this.rod2, 0F, 0F, 0F);

        this.rod3 = new ModelRenderer(this, 56, 18);
        this.rod3.mirror = true;
        this.rod3.addBox(4F, -11F, -6F, 2, 12, 2);
        this.rod3.setRotationPoint(0F, 23F, 0F);
        this.rod3.setTextureSize(64, 32);
        setRotation(this.rod3, 0F, 0F, 0F);

        this.rod4 = new ModelRenderer(this, 56, 18);
        this.rod4.mirror = true;
        this.rod4.addBox(-6F, -11F, -6F, 2, 12, 2);
        this.rod4.setRotationPoint(0F, 23F, 0F);
        this.rod4.setTextureSize(64, 32);
        setRotation(this.rod4, 0F, 0F, 0F);
    }

    public void renderAll() {
        GL11.glPushMatrix();
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        GL11.glTranslatef(0F, 0.31F, 0F);
        this.top.render(0.0625F);
        GL11.glPopMatrix();
        this.base.render(0.0625F);
        this.itemSupport.render(0.0625F);
        this.rod1.render(0.0625F);
        this.rod2.render(0.0625F);
        this.rod3.render(0.0625F);
        this.rod4.render(0.0625F);
    }

    public static void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
