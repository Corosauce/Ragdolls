package ragdolls;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent.Save;
import ragdolls.client.RagdollManager;
import ragdolls.physics.PhysicsWorld;
import CoroUtil.OldUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventHandlerForge {

	public long lastWorldTime = 0;
	
	@SubscribeEvent
	public void worldSave(Save event) {
		Ragdolls.writeOutData(false);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void worldRender(RenderWorldLastEvent event)
    {
		//tick physics here to do visual debug properly
		Minecraft mc = Minecraft.getMinecraft();
		if (this.lastWorldTime != Minecraft.getMinecraft().theWorld.getTotalWorldTime()) {
			this.lastWorldTime = Minecraft.getMinecraft().theWorld.getTotalWorldTime();
			PhysicsWorld phys = Ragdolls.physMan.getPhysicsWorld(mc.theWorld);
			
			if (!phys.chunkManager.isTrackingEntity(mc.thePlayer)) {
				//phys.chunkManager.addChunkloader(mc.thePlayer);
			}
			
	        if (!mc.isGamePaused()) phys.tick(50000f);
		}
		
		RagdollManager.render(event.partialTicks);
    }
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderPlayer(RenderPlayerEvent.Pre event) {
		
		//System.out.println("sdfsdf");
		
		/*if (event.entity instanceof EntityPlayer) {
			try {
				if (event.entity.getEntityData().getBoolean("inBedCustom")) {
					OldUtil.setPrivateValueBoth(EntityPlayer.class, event.entity, "field_71083_bS", "sleeping", true);
				} else {
					if ((Boolean) OldUtil.getPrivateValueBoth(EntityPlayer.class, event.entity, "field_71083_bS", "sleeping")) {
						OldUtil.setPrivateValueBoth(EntityPlayer.class, event.entity, "field_71083_bS", "sleeping", false);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		if (Minecraft.getMinecraft().currentScreen instanceof GuiSleepMP) {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				//send packet to get out
				
				//for now...
				System.out.println("out!");
				event.entity.getEntityData().setBoolean("inBedCustom", false);
			}
		}*/
		//System.out.println(Minecraft.getMinecraft().currentScreen);
	}
}
