package ragdolls.physics.debug;

import javax.vecmath.Vector3f;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.bulletphysics.linearmath.IDebugDraw;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DebugOutput extends IDebugDraw {

	private int debugMode;
	
	@Override
	public void drawLine(Vector3f from, Vector3f to, Vector3f color) {
		// TODO Auto-generated method stub
		//dbg("color: " + color);
		//Overlays.renderLineFromToBlockCenter(from.x, from.y + 80, from.z, to.x, to.y + 80, to.z, (int)color.x*255 << 24 | (int)color.x*255 << 16 | (int)color.x*255 << 8);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			if (color.y == 1) {
				drawLineClient(from, to, color);
			}
		}
		//
		if (color.x != 0) {
			//System.out.println(Math.toDegrees(Math.atan2(from.y-to.y, from.z-to.z)));
			//GL11.glPushMatrix();
			//GL11.glTranslatef(from.x, from.y+1F, from.z);
			//GL11.glRotatef((float) Math.toDegrees(Math.atan2(from.z-to.z, from.x-to.x)), 0, 1, 0);
			//GL11.glRotatef((float) Math.toDegrees(Math.atan2(from.y-to.y, from.z-to.z)), 1, 0, 0);
			//Overlays.renderBlock(Block.lavaStill, 0, (int)from.x, (int)from.y, (int)from.z);
			//GL11.glPopMatrix();
		}
		//System.out.println("drawLine() from: " + from);
		
	}
	
	@SideOnly(Side.CLIENT)
	public void drawLineClient(Vector3f from, Vector3f to, Vector3f color) {
		//System.out.println("drawLine() from: " + from);
		renderLineFromToBlockCenter(from.x, from.y, from.z, to.x, to.y, to.z, (int)color.z*255 << 16 | (int)color.y*255 << 8 | (int)color.x*255);
	}

	@Override
	public void drawContactPoint(Vector3f PointOnB, Vector3f normalOnB,
			float distance, int lifeTime, Vector3f color) {
		// TODO Auto-generated method stub
		dbg("drawContactPoint: " + PointOnB);
	}

	@Override
	public void reportErrorWarning(String warningString) {
		// TODO Auto-generated method stub
		dbg("reportErrorWarning: ");
	}

	@Override
	public void draw3dText(Vector3f location, String textString) {
		// TODO Auto-generated method stub
		dbg("draw3dText: ");
	}

	@Override
	public void setDebugMode(int debugMode) {
		// TODO Auto-generated method stub
		dbg("setDebugMode: " + debugMode);
		this.debugMode = debugMode;
	}

	@Override
	public int getDebugMode() {
		// TODO Auto-generated method stub
		return debugMode/*DebugDrawModes.MAX_DEBUG_DRAW_MODE*/;
	}
	
	public void dbg(Object obj) {
		System.out.println(obj);
	}
	
	public static void renderLineFromToBlockCenter(double x1, double y1, double z1, double x2, double y2, double z2, int stringColor) {
		renderLineFromToBlock(x1+0.5D, y1+0.5D, z1+0.5D, x2+0.5D, y2+0.5D, z2+0.5D, stringColor);
	}
	
	public static void renderLineFromToBlock(double x1, double y1, double z1, double x2, double y2, double z2, int stringColor) {
	    Tessellator tessellator = Tessellator.instance;
	    RenderManager rm = RenderManager.instance;
	    
	    float castProgress = 1.0F;
	
	    float f10 = 0F;
	    double d4 = MathHelper.sin(f10);
	    double d6 = MathHelper.cos(f10);
	
	    double pirateX = x1;
	    double pirateY = y1;
	    double pirateZ = z1;
	    double entX = x2;
	    double entY = y2;
	    double entZ = z2;
	
	    double fishX = castProgress*(entX - pirateX);
	    double fishY = castProgress*(entY - pirateY);
	    double fishZ = castProgress*(entZ - pirateZ);
	    GL11.glDisable(GL11.GL_TEXTURE_2D);
	    //GL11.glDisable(GL11.GL_LIGHTING);
	    tessellator.startDrawing(GL11.GL_LINE_STRIP);
	    //GL11.GL_LINE_WIDTH
	    //int stringColor = 0x888888;
	    //if (((EntityNode)entitypirate).render) {
	    	//stringColor = 0x880000;
	    //} else {
	    	//stringColor = 0xEF4034;
		//}
	    tessellator.setColorOpaque_I(stringColor);
	    int steps = 1;
	
	    for (int i = 0; i <= steps; ++i) {
	        float f4 = i/(float)steps;
	        tessellator.addVertex(
	            pirateX - rm.renderPosX + fishX * f4,//(f4 * f4 + f4) * 0.5D + 0.25D,
	            pirateY - rm.renderPosY + fishY * f4,//(f4 * f4 + f4) * 0.5D + 0.25D,
	            pirateZ - rm.renderPosZ + fishZ * f4);
	    }
	    
	    tessellator.draw();
	    //GL11.glEnable(GL11.GL_LIGHTING);
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
