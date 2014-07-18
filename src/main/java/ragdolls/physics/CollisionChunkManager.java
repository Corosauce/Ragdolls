package ragdolls.physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.vecmath.Vector3f;

import ragdolls.Ragdolls;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

public class CollisionChunkManager {

	public PhysicsWorld physWorld;
	
	//public List<CollisionChunk> listChunks = new ArrayList<CollisionChunk>();
	//public HashMap<Long, CollisionChunk> lookupChunks = new HashMap<Long, CollisionChunk>();
	
	private List<EntityColliderEntry> listChunkloaders = new ArrayList<EntityColliderEntry>();
	private HashMap<Object, EntityColliderEntry> lookupChunkloaders = new HashMap<Object, EntityColliderEntry>();
	
	public List<RigidBody> collisionEntries = new ArrayList<RigidBody>();
	public HashMap<Integer, RigidBody> lookupHashEntries = new HashMap<Integer, RigidBody>();
	
	public int cutoffRadius = 10;
	
	public CollisionChunkManager(PhysicsWorld parPhysWorld) {
		physWorld = parPhysWorld;
	}
	
	public boolean isTrackingEntity(Object parEnt) {
		return (lookupChunkloaders.containsKey(parEnt));
	}
	
	public void addChunkloader(Object parEnt) {
		EntityColliderEntry entry = new EntityColliderEntry(physWorld, this, parEnt);
		listChunkloaders.add(entry);
		lookupChunkloaders.put(parEnt, entry);
	}
	
	public void removeChunkloader(Object parEnt) {
		EntityColliderEntry entry = lookupChunkloaders.get(parEnt);
		if (entry != null) {
			entry.reset();
			listChunkloaders.remove(parEnt);
			lookupChunkloaders.remove(parEnt);
		}
		
	}
	
	//verify that using hashcode from vector of world pos actually works
	public void addEntry(RigidBody entry) {
		collisionEntries.add(entry);
		Vector3f vec = entry.getWorldTransform(new Transform()).origin;
		//Ragdolls.dbg("adding collision entry, hash: " + vec.hashCode());
		lookupHashEntries.put(vec.hashCode(), entry);
		
		physWorld.dynamicsWorld.addRigidBody(entry);
	}
	
	public void removeEntry(RigidBody entry) {
		collisionEntries.remove(entry);
		Vector3f vec = entry.getWorldTransform(new Transform()).origin;
		lookupHashEntries.remove(vec.hashCode());
		
		physWorld.dynamicsWorld.removeRigidBody(entry);
		
		//Ragdolls.dbg("removing collision entry, hash: " + vec.hashCode());
	}
	
	public void tick() {
		
		if (physWorld.worldMC == null) return;
		
		for (int i = 0; i < listChunkloaders.size(); i++) {
			EntityColliderEntry entry = listChunkloaders.get(i);
			entry.tick();
		}
		
		cleanupInvalidEntries();
	}
	

	
	public void cleanupInvalidEntries() {
		Vector3f distCompare = new Vector3f();
		
		//trim out distant bodies
		for (int i = 0; i < collisionEntries.size(); i++) {
			RigidBody body = collisionEntries.get(i);
			
			boolean tooFar = true;
			
			for (int j = 0; j < listChunkloaders.size(); j++) {
				EntityColliderEntry entry = listChunkloaders.get(j);
				
				Vector3f curPos = entry.getPos();
				
				Vector3f vec = body.getMotionState().getWorldTransform(new Transform()).origin;
				
				distCompare.sub(curPos, vec);
				
				if (distCompare.length() < cutoffRadius) {
					tooFar = false;
				}
			}
			
			if (tooFar) {
				removeEntry(body);
				i--;
			}
		}
		
		//could trim out changed blocks, but no need for our uses
	}
	
	public void reset() {
		for (int i = 0; i < listChunkloaders.size(); i++) {
			listChunkloaders.get(i).reset();
		}
		
		for (int i = 0; i < collisionEntries.size(); i++) {
			RigidBody body = collisionEntries.get(i);
			
			removeEntry(body);
			i--;
		}
		
		listChunkloaders.clear();
		lookupChunkloaders.clear();
	}
	
}
