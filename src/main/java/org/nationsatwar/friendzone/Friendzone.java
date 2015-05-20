package org.nationsatwar.friendzone;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.nationsatwar.friendzone.events.ChatCommands;
import org.nationsatwar.friendzone.proxy.CommonProxy;

@Mod(modid = Friendzone.MODID, 
	name = Friendzone.MODNAME, 
	version = Friendzone.MODVER)
public class Friendzone {

    @Instance(Friendzone.MODID)
    public static Friendzone instance;

	@SidedProxy(clientSide = Friendzone.CLIENT_PROXY_CLASS, serverSide = Friendzone.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final String MODID = "friendzone";
	public static final String MODNAME = "Friendzone";
	public static final String MODVER = "0.0.1";
	
	public static final String CLIENT_PROXY_CLASS = "org.nationsatwar.friendzone.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "org.nationsatwar.friendzone.proxy.CommonProxy";
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		
	}
	
	@EventHandler
	public void commandEvent(FMLServerStartingEvent event) {
		
		event.registerServerCommand(new ChatCommands());
	}
}