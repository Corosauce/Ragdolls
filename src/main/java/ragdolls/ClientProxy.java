package ragdolls;

import java.net.URL;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.ClientCommandHandler;
import ragdolls.client.render.RenderEntityRagdoll;
import CoroUtil.bt.entity.EntityMobBase;
import CoroUtil.entity.render.TechneModelCoroAI;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	
	public static RenderEntityRagdoll rendererBipedPhysics;
	
    public ClientProxy()
    {
    	
    }

    @Override
    public void init()
    {
    	super.init();
    	
    	ClientCommandHandler.instance.registerCommand(new CommandRagdolls());
    	
    	rendererBipedPhysics = new RenderEntityRagdoll(loadModel("biped"), 1F);
    	
    	//satisfy internal ModelRendererBones texture using
    	addMapping(EntityMobBase.class, rendererBipedPhysics);
    }
    
    private static void addMapping(Class<? extends Entity> entityClass, Render render) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, render);
	}
    
    public static TechneModelCoroAI loadModel(String path) {

		//test
		TechneModelCoroAI model = null;
        try {
        	URL url =  ClassLoader.getSystemResource("assets/ragdolls/render/model/" + path + ".tcn");
        	model = new TechneModelCoroAI("TechneModelEpoch", url);
        	
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        return model;
        
	}
}
