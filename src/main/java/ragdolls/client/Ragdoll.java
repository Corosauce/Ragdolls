package ragdolls.client;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Iterator;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;

import ragdolls.ClientProxy;
import ragdolls.Ragdolls;
import ragdolls.physics.PhysicsWorld;
import ragdolls.physics.entity.RagdollCharacter;
import CoroUtil.bt.IBTAgent;
import CoroUtil.bt.PersonalityProfile;
import CoroUtil.bt.entity.EntityMobBase;
import CoroUtil.entity.render.AnimationStateObject;
import cpw.mods.fml.client.FMLClientHandler;

public class Ragdoll {

	public EntityMobBase entAI;
	public RagdollCharacter ragdollChar;
	
	//probably only used for the initial spawn position, but past this, jbullet has control, maybe update this with jbullets managed rigid body origin?
	public Vector3f pos = new Vector3f();
	
	public Ragdoll(Vector3f parPos) {
		pos = parPos;
		//rigidBody = createRigidBody();
		PhysicsWorld physWorld = Ragdolls.physMan.getPhysicsWorld(FMLClientHandler.instance().getClient().theWorld);
		ragdollChar = new RagdollCharacter(physWorld.dynamicsWorld, pos);
		physWorld.chunkManager.addChunkloader(this);
	}
	
	public RigidBody createRigidBody() {
		PhysicsWorld physWorld = Ragdolls.physMan.getPhysicsWorld(FMLClientHandler.instance().getClient().theWorld);
		
		BoxShape co;
		co = new BoxShape(new Vector3f(1, 1, 1));
		RigidBody rb = physWorld.addRigidBody(co, pos, 1F);
		rb.setDamping(0.0F, 0.0F);
		physWorld.initObject(rb);
		
		rb.setGravity(new Vector3f(0, -150, 0));
		rb.setDamping(0.1F, 0.1F);
		rb.setRestitution(0.1F);
		rb.setFriction(0.3F);
		rb.setActivationState(CollisionObject.ACTIVE_TAG);
		
		//physWorld.chunkManager.addChunkloader(this);
		
		return rb;
	}
	
	public void reset() {
		PhysicsWorld physWorld = Ragdolls.physMan.getPhysicsWorld(FMLClientHandler.instance().getClient().theWorld);
		ragdollChar.destroy();
		/*physWorld.dynamicsWorld.removeRigidBody(rigidBody);
		rigidBody = null;*/
		entAI = null;
	}
	
	public void updateDataFromRigidBody() {
		pos = ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_SPINE.ordinal()].getWorldTransform(new Transform()).origin;
		
		
	}
	
	public void render(float partialTick) {
		
		updateDataFromRigidBody();
		
		float x = (float) (pos.x - RenderManager.renderPosX + 0.5F);//(float) FMLClientHandler.instance().getClient().renderViewEntity.posX;
		float y = (float) (pos.y - RenderManager.renderPosY - 0.8F);//(float) FMLClientHandler.instance().getClient().renderViewEntity.posY;
		float z = (float) (pos.z - RenderManager.renderPosZ + 0.5F);//(float) FMLClientHandler.instance().getClient().renderViewEntity.posZ;
		
		y += 0;
		
		Minecraft mc = FMLClientHandler.instance().getClient();
		
		//TEST
		//EntityZombie ent = new EntityZombie(FMLClientHandler.instance().getClient().theWorld);
		if (entAI == null) {
			entAI = new EntityMobBase(mc.theWorld);
			entAI.setPosition(pos.x, pos.y, pos.z);
			ClientProxy.rendererBipedPhysics.setRenderManager(RenderManager.instance);
		}
		
		
		
		
		try {
			//rendererTest.doRender(ent, x, y, z, 1F, 1F);
			
			mc.entityRenderer.enableLightmap((double)partialTick);
			
			
			GL11.glPushMatrix();
			
			//RenderManager.instance.renderEngine.bindTexture(texture);
		
			if (entAI instanceof IBTAgent) {
				
				PersonalityProfile profile = ((IBTAgent) entAI).getAIBTAgent().profile;
				
				
				
				/*Quat4f quat = new Quat4f();
				trns.getRotation(quat);*/
				
				//buffer.put(bulletTransform).flip();
				
				//relative rotation fixing code test!
				//GL11.glLoadIdentity();
				//GL11.glMatrixMode(GL11.GL_MODELVIEW);

				
				
				//GL11.glLoadMatrix(buffer);

				//GL11.glTranslatef(x, y, z);				
				//test more
				//GL11.glTranslatef(0, -60, 0);
				
				
				//GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX , buffer);
				
				//FloatBuffer buffer2 = ByteBuffer.allocateDirect(4*16).order(ByteOrder.nativeOrder()).asFloatBuffer();
				//GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX , buffer2);
				//float test[] = new float[16];
				//buffer.get(test);
				//System.out.println();

				/*Transform trns = new Transform();
				ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_SPINE.ordinal()].getWorldTransform(trns);
				float matrix[] = new float[16];
				trns.getOpenGLMatrix(matrix);
				//remove translations
				matrix[12] = 0;
				matrix[13] = 0;
				matrix[14] = 0;*/
				/*FloatBuffer buffer = ByteBuffer.allocateDirect(4*16).order(ByteOrder.nativeOrder()).asFloatBuffer().put(matrix);
				buffer.flip();
				GL11.glMultMatrix(buffer);*/
				

				//System.out.println("----------------");
				
				Iterator it = profile.animationData.values().iterator();
				while (it.hasNext()) {
					AnimationStateObject obj = (AnimationStateObject) it.next();
					//System.out.println(obj.name);
					//obj.rotateMode = 1;
					//needs actual data or rather, needs axes
					//obj.matrix = new float[16];
					//temp
					//obj.matrix = matrix;
				}
				
				/*rightarmlower
				head
				leftleg
				top
				leftarm
				body
				leftarmlower
				rightarm
				leftleglower
				rightleglower
				rightleg*/
				
				profile.animationData.get("head").rotateMode = 1;
				profile.animationData.get("head").matrix = getMatrixForPart(ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_HEAD.ordinal()]);
				
				profile.animationData.get("body").rotateMode = 1;
				profile.animationData.get("body").matrix = getMatrixForPart(ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_PELVIS.ordinal()]);
				
				profile.animationData.get("rightarm").rotateMode = 1;
				profile.animationData.get("rightarm").matrix = getMatrixForPart(ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_RIGHT_UPPER_ARM.ordinal()]);
				
				profile.animationData.get("rightarmlower").rotateMode = 1;
				profile.animationData.get("rightarmlower").matrix = getMatrixForPart(ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_RIGHT_LOWER_ARM.ordinal()]);
				
				profile.animationData.get("leftarm").rotateMode = 1;
				profile.animationData.get("leftarm").matrix = getMatrixForPart(ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_LEFT_UPPER_ARM.ordinal()]);
				
				profile.animationData.get("leftarmlower").rotateMode = 1;
				profile.animationData.get("leftarmlower").matrix = getMatrixForPart(ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_LEFT_LOWER_ARM.ordinal()]);
				
				profile.animationData.get("rightleg").rotateMode = 1;
				profile.animationData.get("rightleg").matrix = getMatrixForPart(ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_RIGHT_UPPER_LEG.ordinal()]);
				
				profile.animationData.get("rightleglower").rotateMode = 1;
				profile.animationData.get("rightleglower").matrix = getMatrixForPart(ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_RIGHT_LOWER_LEG.ordinal()]);
				
				profile.animationData.get("leftleg").rotateMode = 1;
				profile.animationData.get("leftleg").matrix = getMatrixForPart(ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_LEFT_UPPER_LEG.ordinal()]);
				
				profile.animationData.get("leftleglower").rotateMode = 1;
				profile.animationData.get("leftleglower").matrix = getMatrixForPart(ragdollChar.bodies[RagdollCharacter.BodyPart.BODYPART_LEFT_LOWER_LEG.ordinal()]);
				
				
				//reset
				/*profile.animationData.get("body").rotateAngleX = 0;
				profile.animationData.get("body").rotateAngleXPrev = 0;
				profile.animationData.get("body").rotateAngleY = 0;
				profile.animationData.get("body").rotateAngleYPrev = 0;
				profile.animationData.get("body").rotateAngleZ = 0;
				profile.animationData.get("body").rotateAngleZPrev = 0;*/
				
				
				//profile.animationData.get("body").rotateAngleX = (float) (quat.x*2F);
				//profile.animationData.get("body").rotateAngleXPrev = (float) (quat.x*2F);
				/*profile.animationData.get("body").rotateAngleY = (float) (quat.y);
				profile.animationData.get("body").rotateAngleYPrev = (float) (quat.y);
				profile.animationData.get("body").rotateAngleZ = (float) (quat.z);
				profile.animationData.get("body").rotateAngleZPrev = (float) (quat.z);*/
				
				//quat.
				
				/*Iterator it = profile.animationData.values().iterator();
				while (it.hasNext()) {
					AnimationStateObject obj = (AnimationStateObject) it.next();
					
					float rate = 0.01F;
					
					obj.rotateAngleX += rate;
					obj.rotateAngleXPrev += rate;
					if (obj.rotateAngleX > 360) obj.rotateAngleX = 0;
					if (obj.rotateAngleXPrev > 360) obj.rotateAngleXPrev = 0;
					
					rate /= 2F;
					
					obj.rotateAngleZ = rate;
					obj.rotateAngleZPrev = rate;
					if (obj.rotateAngleZ > 360) obj.rotateAngleZ = 0;
					if (obj.rotateAngleZPrev > 360) obj.rotateAngleZPrev = 0;
					
					rate /= 2F;
					
					obj.rotateAngleY = rate;
					obj.rotateAngleYPrev = rate;
					if (obj.rotateAngleY > 360) obj.rotateAngleY = 0;
					if (obj.rotateAngleYPrev > 360) obj.rotateAngleYPrev = 0;
				}*/
				
				
			}
			
			//x = y = z = 0;
			
			ClientProxy.rendererBipedPhysics.doRender((Entity)entAI, x, y, z, 1F, partialTick);
			
			GL11.glPopMatrix();
			mc.entityRenderer.disableLightmap((double)partialTick);
			
			//renderer.doRender(ent, x, y, z, 1F, partialTick);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		
	}
	
	public float[] getMatrixForPart(RigidBody part) {
		Transform trns = new Transform();
		part.getWorldTransform(trns);
		float matrix[] = new float[16];
		trns.getOpenGLMatrix(matrix);
		
		//remove translations
		matrix[12] = 0;
		matrix[13] = 0;
		matrix[14] = 0;
		return matrix;
	}
	
}
