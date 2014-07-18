package ragdolls.client.render;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ragdolls.client.render.model.ModelBipedPhysicsFAIL;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBipedPhysicsFAIL
{
	float scale = 1F;
	//REWORK THIS CLASS, make it a parentless class, made purely for applying jbullet rotations onto ModelBipedPhysics class members
	
    public ModelBipedPhysicsFAIL modelBipedMain;

    public RenderBipedPhysicsFAIL(ModelBipedPhysicsFAIL par1ModelBiped, float par2)
    {
        scale = par2;
        modelBipedMain = par1ModelBiped;
    }

    public void doRender(double x, double y, double z, float partialTick)
    {
    	GL11.glPushMatrix();
    	GL11.glDisable(GL11.GL_CULL_FACE);
    	
    	//no change with these...
    	//GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    	//GL11.glMatrixMode(GL11.GL_MODELVIEW);
    	
    	
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        
        GL11.glTranslated(x, y, z);
        
        float internalScale = 0.0625F;
        GL11.glScalef(scale * internalScale, scale * internalScale, scale * internalScale);
        
        //this.modelBipedMain.setLivingAnimations(par1EntityLiving, f7, f6, par9);
        
        //these values were: limbswing amount, interpolated limb swing amount, interpolated ticks existed, interpolated head rotation minus body rotation, interpolated pitch, some sort of scale?
        this.modelBipedMain.render(scale);
        //super.doRender(par1EntityLiving, par2, d3, par6, par8, par9);
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
    {
        return null;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntityLiving)par1Entity);
    }
}
