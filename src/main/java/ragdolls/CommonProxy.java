package ragdolls;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CommonProxy implements IGuiHandler
{

	public static Block blockTSensor;
	public static Block blockTSiren;
	public static Block blockWindVane;
	public static Block blockAnemometer;
	public static Block blockWeatherForecast;
	public static Block blockWeatherMachine;
	public static Block blockWeatherDeflector;
	
    public CommonProxy()
    {
    	
    }

    public void init()
    {
    	
    }
    
    public static void addItem(ItemStack is, String unlocalizedName) {
		addItem(is, unlocalizedName, "");
	}
	
	public static void addItem(ItemStack is, String unlocalizedName, String itemNameBase) {
		
		Item item = is.getItem();
		
		//vanilla calls
		item.setUnlocalizedName(Ragdolls.modID + ":" + unlocalizedName);
		item.setTextureName(Ragdolls.modID + ":" + unlocalizedName);
		item.setCreativeTab(CreativeTabs.tabMisc);
		LanguageRegistry.addName(item, itemNameBase); //really not usefull, since its dynamic from nbt
		
		
	}
    
    public static void addBlock(Block block, Class tEnt, String unlocalizedName, String blockNameBase) {
		addBlock(block, unlocalizedName, blockNameBase);
		GameRegistry.registerTileEntity(tEnt, unlocalizedName);
	}
	
	public static void addBlock(Block parBlock, String unlocalizedName, String blockNameBase) {
		//vanilla calls
		GameRegistry.registerBlock(parBlock, unlocalizedName);
		parBlock.setBlockName(Ragdolls.modID + ":" + unlocalizedName);
		parBlock.setBlockTextureName(Ragdolls.modID + ":" + unlocalizedName);
		//parBlock.setCreativeTab(tab);
		LanguageRegistry.addName(parBlock, blockNameBase);
	}
    
    public static void addMapping(Class par0Class, String par1Str, int entityId, int distSync, int tickRateSync, boolean syncMotion) {
    	EntityRegistry.registerModEntity(par0Class, par1Str, entityId, Ragdolls.instance, distSync, tickRateSync, syncMotion);
        //EntityList.addMapping(par0Class, par1Str, entityId);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        return null;
    }
}
