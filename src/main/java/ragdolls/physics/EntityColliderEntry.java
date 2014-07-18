package ragdolls.physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.vecmath.Vector3f;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import ragdolls.Ragdolls;
import ragdolls.client.Ragdoll;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import cpw.mods.fml.client.FMLClientHandler;

public class EntityColliderEntry {

	public Object trackedObject;
	public PhysicsWorld manager;
	public CollisionChunkManager chunkManager;
	public Entity entForCollisionScan; //to satisfy mc code
	
	public int scanRadius = 3;
	
	public EntityColliderEntry(PhysicsWorld parMan, CollisionChunkManager parChunkManager, Object parEnt) {
		trackedObject = parEnt;
		manager = parMan;
		chunkManager = parChunkManager;
		entForCollisionScan = FMLClientHandler.instance().getClient().thePlayer;
	}
	
	public Vector3f getPos() {
		Vector3f pos = new Vector3f();
		if (trackedObject instanceof Entity) {
			pos = new Vector3f((float)((Entity)trackedObject).posX, (float)((Entity)trackedObject).posY, (float)((Entity)trackedObject).posZ);
		} else if (trackedObject instanceof Ragdoll) {
			pos = ((Ragdoll) trackedObject).pos;
		} else {
			Ragdolls.dbg("warning: unsupported object for EntityColliderEntry");
		}
		return pos;
	}
	
	public void tick() {
		scanForAdditions();
	}
	
	public void reset() {
		trackedObject = null;
		manager = null;
		chunkManager = null;
		entForCollisionScan = null;
		
		//Ragdolls.dbg("final entry count of rigid bodies: " + collisionEntries.size());
	}
	
	public void scanForAdditions() {
		boolean flag = false;
		Vector3f curPos = getPos();
		
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(curPos.x, curPos.y, curPos.z, curPos.x, curPos.y, curPos.z).expand(scanRadius, scanRadius, scanRadius);
		List boxes = manager.worldMC.getCollidingBoundingBoxes(entForCollisionScan, aabb);
		if (boxes.size() > 0) {
			for (int i = 0; i < boxes.size(); i++) {
				AxisAlignedBB box = (AxisAlignedBB) boxes.get(i);
				Vector3f newPos = new Vector3f((float)(box.maxX + box.minX)/2F, (float)(box.maxY + box.minY)/2F - 1, (float)(box.maxZ + box.minZ)/2F);
				Vector3f newSize = new Vector3f((float)(box.maxX - box.minX)/2F, (float)(box.maxY - box.minY)/2F, (float)(box.maxZ - box.minZ)/2F);
				
				if (!chunkManager.lookupHashEntries.containsKey(newPos.hashCode())) {
					CollisionShape colShape = new BoxShape(newSize);
					
					Transform trns = new Transform();
					trns.setIdentity();
					trns.origin.set(newPos);
					
					DefaultMotionState state = new DefaultMotionState(trns);
					
					RigidBodyConstructionInfo info = new RigidBodyConstructionInfo(0, state, colShape, new Vector3f());
					RigidBody body = new RigidBody(info);
					
					chunkManager.addEntry(body);
				}
			}
			
		}
	}
	
}
