package ragdolls;

import javax.vecmath.Vector3f;

import ragdolls.client.Ragdoll;
import ragdolls.client.RagdollManager;

import com.bulletphysics.collision.dispatch.CollisionObject;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class EventHandlerFML {

	public static World lastWorld = null;
	
	@SubscribeEvent
	public void tickWorld(WorldTickEvent event) {
		if (event.phase == Phase.START) {
			//TEMP!
			/*for (int i = 0; i < event.world.playerEntities.size(); i++) {
				EntityPlayer entP = (EntityPlayer) event.world.playerEntities.get(i);
				entP.getEntityData().setBoolean("inBedCustom", false);
				entP.wakeUpPlayer(true, false, false);
			}*/
		}
	}
	
	@SubscribeEvent
	public void tickServer(ServerTickEvent event) {
		
		if (event.phase == Phase.START) {
			
		}
		
	}
	
	@SubscribeEvent
	public void tickClient(ClientTickEvent event) {
		if (event.phase == Phase.START) {
			
			//TEMP! DELETE ME LATER
			if (FMLClientHandler.instance().getClient().theWorld != null && FMLClientHandler.instance().getClient().theWorld.getTotalWorldTime() % 60 == 0) {
				for (int i = 0; i < RagdollManager.listRagdolls.size(); i++) {
					Ragdoll rd = RagdollManager.listRagdolls.get(i);
					rd.rigidBody.setActivationState(CollisionObject.ACTIVE_TAG);
					rd.rigidBody.applyCentralImpulse(new Vector3f(0, 20, 8));
				}
				
			}
			
			//test
			
			
			//moved to paced renderworldlast event code so i can visually debug it better
			/*
			PhysicsWorld phys = Ragdolls.instance.physMan.getPhysicsWorld(mc.theWorld);
            
            if (!FMLClientHandler.instance().getClient().isGamePaused()) {
            	phys.tick(50000f);
            }*/
		}
	}
	
	@SubscribeEvent
	public void tickRenderScreen(RenderTickEvent event) {
		if (event.phase == Phase.END) {

		}
	}
	
	@SubscribeEvent
	public void playerLoggedIn(PlayerLoggedInEvent event) {

	}
}
