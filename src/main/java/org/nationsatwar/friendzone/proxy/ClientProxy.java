package org.nationsatwar.friendzone.proxy;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;
import org.nationsatwar.palette.KeyBindings;

public class ClientProxy extends CommonProxy {

	public static KeyBinding friendzoneKey;
	
	@Override
	public void registerKeybindings() {
		
		friendzoneKey = KeyBindings.registerKey(Keyboard.KEY_F, "friendzoneKey");
	}
}