package cn.KiesPro.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import cn.KiesPro.Client;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import net.minecraft.client.Minecraft;

public class SaveLoad {

	private File dir;
	private File dataFile;
    private String extension, fileName;
    
	public SaveLoad() {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Kies");
		if (!dir.exists()) {
			dir.mkdir();
		}
		dataFile = new File(dir, "Config.txt");
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
		
		this.load();
	}
	
	public void save() {
		if (Client.instance.destructed) {
			return;
		}
		ArrayList<String> toSave = new ArrayList<String>();
		
		for (Module mod : Client.instance.moduleManager.modules) {
			toSave.add("MOD:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getKey());
		}
		
		for (Setting set : Client.instance.settingsManager.getSettings()) {
			if (set.isCheck()) {
				toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean());
			}
			if (set.isCombo()) {
				toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString());
			}
			if (set.isSlider()) {
				toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble());
			}
		}
		
		try {
			PrintWriter pw = new PrintWriter(this.dataFile);
			for (String str : toSave) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *方法重载
	 *重载了用来ConfigManager
	 */
    public void save(String configname) {
		if (Client.instance.destructed) {
			return;
		}
        ArrayList<String> toSave = new ArrayList<String>();

		for (Module mod : Client.instance.moduleManager.modules) {
			toSave.add("MOD:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getKey());
		}
		
		for (Setting set : Client.instance.settingsManager.getSettings()) {
			if (set.isCheck()) {
				toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean());
			}
			if (set.isCombo()) {
				toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString());
			}
			if (set.isSlider()) {
				toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble());
			}
		}

        try {
            PrintWriter printWriter = new PrintWriter(this.dataFile);
            for (String str : toSave) {
                printWriter.println(str);
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	public void load() {
		if (Client.instance.destructed) {
			return;
		}
		
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (String s : lines) {
			String[] args = s.split(":");
			if (s.toLowerCase().startsWith("mod:")) {
				Module m = Client.instance.moduleManager.getModule(args[1]);
				if (m != null) {
					m.setToggled(Boolean.parseBoolean(args[2]));
					m.setKey(Integer.parseInt(args[3]));
				}
			} else if (s.toLowerCase().startsWith("set:")) {
				Module m = Client.instance.moduleManager.getModule(args[2]);
				if (m != null) {
					Setting set = Client.instance.settingsManager.getSettingByName(m, args[1]);
					if (set != null) {
						if (set.isCheck()) {
							set.setValBoolean(Boolean.parseBoolean(args[3]));
						}
						if (set.isCombo()) {
							set.setValString(args[3]);
						}
						if (set.isSlider()) {
							set.setValDouble(Double.parseDouble(args[3]));
						}
					}
				}
			}
		}
	}
	
	
    public void setFileName(String fileName) {
        this.fileName = fileName;
        this.dataFile = new File(dir, this.fileName + this.extension);
        if (!this.dataFile.exists()) {
            try {
                this.dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
    public static ArrayList<String> getConfigs() {
        File[] files = new File(Client.instance.CLIENT_NAME + "/Configs/").listFiles();
        ArrayList<String> results = new ArrayList<String>();

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return results;
    }
}
