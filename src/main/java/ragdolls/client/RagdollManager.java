package ragdolls.client;

import java.util.ArrayList;
import java.util.List;

import ragdolls.Ragdolls;
import ragdolls.physics.PhysicsWorld;
import cpw.mods.fml.client.FMLClientHandler;

public class RagdollManager {

	public static List<Ragdoll> listRagdolls = new ArrayList<Ragdoll>();
	
	public RagdollManager() {
		
	}
	
	public static void addRagdoll(Ragdoll parRagdoll) {
		listRagdolls.add(parRagdoll);
	}
	
	public static void removeRagdoll(Ragdoll parRagdoll) {
		listRagdolls.remove(parRagdoll);
	}
	
	public static void render(float partialTick) {
		for (int i = 0; i < listRagdolls.size(); i++) {
			listRagdolls.get(i).render(partialTick);
		}
	}
	
	public static void reset() {
		PhysicsWorld physWorld = Ragdolls.physMan.getPhysicsWorld(FMLClientHandler.instance().getClient().theWorld);
		physWorld.chunkManager.reset();
		for (int i = 0; i < listRagdolls.size(); i++) {
			listRagdolls.get(i).reset();
		}
		listRagdolls.clear();
	}
	
}
