package ragdolls.client.render;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import ragdolls.Ragdolls;
import CoroUtil.bt.IBTAgent;
import CoroUtil.entity.render.AnimationStateObject;
import CoroUtil.entity.render.RenderEntityCoroAI;
import CoroUtil.entity.render.TechneModelCoroAI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityRagdoll extends RenderEntityCoroAI
{
	
    public RenderEntityRagdoll(TechneModelCoroAI par1ModelBiped, float par2)
    {
        super(par1ModelBiped, par2);
    }
    
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation(Ragdolls.modID + ":textures/entities/zombieVanilla.png");
	}
    
	@Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
    	
    	//bug with multiple entities using it.
    	
    	IBTAgent ent = (IBTAgent)par1Entity;

    	//performance?
    	if (par1Entity.worldObj.getTotalWorldTime() != ent.getAIBTAgent().lastAnimateUpdateTime) {
    		ent.getAIBTAgent().lastAnimateUpdateTime = par1Entity.worldObj.getTotalWorldTime();
    		
    		for (Map.Entry<String, AnimationStateObject> entry : ent.getAIBTAgent().profile.animationData.entrySet()) {
    			AnimationStateObject part = entry.getValue();
    			if (part.rotateMode == 0) {
	            	part.rotateAngleMaxRatePerTick = 0.1F;
	            	
	            	//hmm
	            	part.rotateAngleMaxRatePerTick = 1.0F;
	        		
	        		if (part != null) {
	    	        	float hmm = 1F;
	    	        	
	    	        	part.rotateAngleXPrev = part.rotateAngleX;
	    	        	part.rotateAngleYPrev = part.rotateAngleY;
	    	        	part.rotateAngleZPrev = part.rotateAngleZ;
	    	        	
	    	        	if (part.rotateAngleXDesired - part.rotateAngleX > part.rotateAngleMaxRatePerTick * hmm) {
	    	        		part.rotateAngleX += part.rotateAngleMaxRatePerTick * hmm;
	    	        	} else if (part.rotateAngleXDesired - part.rotateAngleX < -part.rotateAngleMaxRatePerTick * hmm) {
	    	        		part.rotateAngleX -= part.rotateAngleMaxRatePerTick * hmm;
	    	        	} else {
	    	        		part.rotateAngleX = part.rotateAngleXDesired;
	    	        	}
	    	        	
	    	        	if (part.rotateAngleYDesired - part.rotateAngleY > part.rotateAngleMaxRatePerTick * hmm) {
	    	        		part.rotateAngleY += part.rotateAngleMaxRatePerTick * hmm;
	    	        	} else if (part.rotateAngleYDesired - part.rotateAngleY < -part.rotateAngleMaxRatePerTick * hmm) {
	    	        		part.rotateAngleY -= part.rotateAngleMaxRatePerTick * hmm;
	    	        	} else {
	    	        		part.rotateAngleY = part.rotateAngleYDesired;
	    	        	}
	    	        	
	    	        	if (part.rotateAngleZDesired - part.rotateAngleZ > part.rotateAngleMaxRatePerTick * hmm) {
	    	        		part.rotateAngleZ += part.rotateAngleMaxRatePerTick * hmm;
	    	        	} else if (part.rotateAngleZDesired - part.rotateAngleZ < -part.rotateAngleMaxRatePerTick * hmm) {
	    	        		part.rotateAngleZ -= part.rotateAngleMaxRatePerTick * hmm;
	    	        	} else {
	    	        		part.rotateAngleZ = part.rotateAngleZDesired;
	    	        	}
	        		}
    			}
        	}
    	}
    	
    	super.doRender(par1Entity, par2, par4, par6, par8, par9);
    	
    	if (!(par1Entity instanceof IBTAgent) || !(par1Entity instanceof EntityLivingBase)) return;
    	
    	//if (renderManager.livingPlayer.getDistanceToEntity(par1Entity) > 24 && (!(par1Entity instanceof NPCBase))) return;
    	
    	IBTAgent entInt = (IBTAgent)par1Entity;
	    //GL11.glPopMatrix();
    }
}
