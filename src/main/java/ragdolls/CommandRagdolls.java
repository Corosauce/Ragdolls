package ragdolls;

import javax.vecmath.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import ragdolls.client.Ragdoll;
import ragdolls.client.RagdollManager;
import ragdolls.physics.PhysicsWorld;
import CoroUtil.util.CoroUtil;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.RigidBody;

public class CommandRagdolls extends CommandBase {

	@Override
	public String getCommandName() {
		return "ragdolls";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		
		String helpMsgStorm = "";
		
		try {
			if(var1 instanceof EntityPlayer)
			{
				if (var2[0].equals("test")) {
					Ragdolls.dbg("test!");
					jBulletTest((EntityPlayer) var1);
				} else if (var2[0].equals("test2")) {
					int count = 1;
					if (var2.length > 1) {
						count = Integer.valueOf(var2[1]);
					}
					for (int i = 0; i < count; i++) {
						RagdollManager.addRagdoll(new Ragdoll(new Vector3f((float)((EntityPlayer) var1).posX, (float)((EntityPlayer) var1).posY + 2 + i, (float)((EntityPlayer) var1).posZ)));
					}
				} else if (var2[0].equals("test3")) {
					RagdollManager.reset();
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception handling command");
			//CoroUtil.sendPlayerMsg((EntityPlayerMP) var1, helpMsgStorm);
			
		}
		
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;//par1ICommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
    }

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "";
	}
	
	public void jBulletTest(EntityPlayer entP) {
		// = getPlayerObjectClient();
		Minecraft mc = Minecraft.getMinecraft();
		boolean paused = mc.isSingleplayer() && mc.currentScreen != null && mc.currentScreen.doesGuiPauseGame() && !mc.getIntegratedServer().getPublic();
		
		if (!paused) {
		
			//System.out.println("player motionX: " + entP.motionX);
			//doesnt work for cancelling
			/*entP.motionX /= 0.98F;
			entP.motionY /= 0.98F;
			entP.motionZ /= 0.98F;*/
			
			PhysicsWorld physWorld = Ragdolls.physMan.getPhysicsWorld(entP.worldObj);
			
			BoxShape co;
			co = new BoxShape(new Vector3f(1, 1, 1));
			RigidBody rb = physWorld.addRigidBody(co, new Vector3f((float)entP.posX, (float)entP.posY, (float)entP.posZ), 1F);
			rb.setDamping(0.0F, 0.0F);
			physWorld.initObject(rb);
			
			rb.setGravity(new Vector3f(0, -150, 0));
			rb.setDamping(0.1F, 0.1F);
			rb.setRestitution(0.1F);
			rb.setFriction(0.3F);
			rb.setActivationState(CollisionObject.ACTIVE_TAG);
			
		}
	}

}
