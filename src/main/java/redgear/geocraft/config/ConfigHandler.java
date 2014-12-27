package redgear.geocraft.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import net.minecraft.util.EnumTypeAdapterFactory;
import redgear.core.api.item.ISimpleItem;
import redgear.core.simpleitem.SimpleItemTypeAdapter;
import redgear.geocraft.api.mine.Mine;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.generation.MineRegistry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigHandler {

	public File base;

	public static final ConfigHandler inst = new ConfigHandler();
	Gson gson = new GsonBuilder().registerTypeAdapter(Mine.class, new MineTypeAdapter())
			.registerTypeAdapter(ISimpleItem.class, new SimpleItemTypeAdapter())
			.registerTypeAdapterFactory(new EnumTypeAdapterFactory())
			.setPrettyPrinting().create();

	public void saveJson(Mine mine) {
		Writer w = null;

		try {
			File file = new File(base, mine.name + ".json");
			
			if(file.exists())
				file.delete();
			
			w = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

			mine.preSave();
			gson.toJson(mine, Mine.class,  w);
		} catch (Throwable e) {
			Geocraft.inst.myLogger.error("Something went wrong trying to save config file " + mine.name + ".json", e);
		} finally {
			if (w != null)
				try {
					w.close();
				} catch (IOException e) {

				}
		}
	}

	public void loadAll(MineRegistry reg) {
		base = new File(Geocraft.inst.getConfigDirectory(), "geocraft");

		if (!base.exists()) {
			base.mkdir();
			return;
		}

		File[] subFiles = base.listFiles();

		if (subFiles != null && subFiles.length > 0)
			for (File file : subFiles) {
				Mine mine = loadJson(file);
				if (mine != null)
					reg.registerMine(mine);
			}

	}

	public Mine loadJson(File file) {
		if (file == null || !file.exists() || !file.getName().endsWith(".json"))
			return null;

		Reader r = null;

		try {
			r = new InputStreamReader(new FileInputStream(file));
			return gson.fromJson(r, Mine.class);
		} catch (FileNotFoundException e) {
			//Files are found via checking the folder, so files shouldn't be missing. Just ignore errors here.
		} catch (Exception e) {
			Geocraft.inst.myLogger.error("Something went wrong trying to load config file " + file.getName()
					+ " this mine will have to be ignored!", e);
		} finally {
			if (r != null)
				try {
					r.close();
				} catch (IOException e) {
				}
		}

		return null;

	}
}
