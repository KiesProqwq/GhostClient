package cn.KiesPro.module;

import java.util.ArrayList;

import cn.KiesPro.module.combat.*;
import cn.KiesPro.module.misc.*;
import cn.KiesPro.module.movement.*;
import cn.KiesPro.module.player.*;
import cn.KiesPro.module.render.*;
import cn.KiesPro.module.utility.*;

public class ModuleManager {

	public ArrayList<Module> modules;
	
	public ModuleManager() {
		(modules = new ArrayList<Module>()).clear();
		
		//combat
		this.modules.add(new AntiBot());
		this.modules.add(new AimAssist());
		this.modules.add(new HitBox());
		this.modules.add(new Clicker());
		this.modules.add(new Velocity());
		//this.modules.add(new WTap());
		
		//player
		this.modules.add(new FastPlace());
		this.modules.add(new ChestSteal());
		this.modules.add(new AutoTool());
		//this.modules.add(new AutoMine());
		//this.modules.add(new AutoRespawn());
		
		//render
		this.modules.add(new ClickGUI());
		this.modules.add(new HUD());
		this.modules.add(new FPSSpoof());
		this.modules.add(new Fullbright());
		this.modules.add(new ESP());
		this.modules.add(new NoBob());
		this.modules.add(new NoHurtCam());
		this.modules.add(new ViewClip());
		this.modules.add(new NameTags());
		this.modules.add(new NewNameTags());
		this.modules.add(new ChestESP());
		
		
		//movement
		this.modules.add(new Sprint());
		this.modules.add(new Eagle());
		this.modules.add(new NoJumpDelay());

		//misc
		this.modules.add(new SelfDestruct());
		
		//Utility
		this.modules.add(new AutoGG());
		this.modules.add(new AutoGetRank());
		this.modules.add(new PixelGameHelper());
	}
	
	public Module getModule(String name) {
		for (Module m : this.modules) {
			if (m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	
	public ArrayList<Module> getModuleList() {
		return this.modules;
	}
	
	public ArrayList<Module> getModulesInCategory(Category c) {
		ArrayList<Module> mods = new ArrayList<Module>();
		for (Module m : this.modules) {
			if (m.getCategory() == c) {
				mods.add(m);
			}
		}
		return mods;
	}
}
