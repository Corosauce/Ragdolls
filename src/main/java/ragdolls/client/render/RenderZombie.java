package ragdolls.client.render;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import ragdolls.Ragdolls;
import CoroUtil.entity.render.TechneModelCoroAI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderZombie extends RenderEntityRagdoll
{
	public static ResourceLocation zombieTexture = new ResourceLocation(Ragdolls.modID + ":textures/entities/zombie.png");
	public static ResourceLocation zombieTextureVanilla = new ResourceLocation(Ragdolls.modID + ":textures/entities/zombieVanilla.png");
	
    public RenderZombie(TechneModelCoroAI model)
    {
        super(model, 0.5F);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return zombieTextureVanilla;
	}
    
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
    	super.doRender(par1Entity, par2, par4, par6, par8, par9);
    }
}
