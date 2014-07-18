package ragdolls.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBipedPhysics extends RenderBiped {

	public RenderBipedPhysics(ModelBiped par1ModelBiped, float par2) {
		super(par1ModelBiped, par2);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return new ResourceLocation("textures/entity/zombie/zombie.png");
	}

}
