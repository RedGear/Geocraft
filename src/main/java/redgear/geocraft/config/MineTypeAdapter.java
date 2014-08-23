package redgear.geocraft.config;

import java.lang.reflect.Type;

import redgear.geocraft.api.gen.Mine;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MineTypeAdapter implements JsonSerializer<Mine>, JsonDeserializer<Mine> {

	@Override
	public JsonElement serialize(Mine src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("mineType", new JsonPrimitive(src.getClass().getName()));
		result.add("properties", context.serialize(src, src.getClass()));
		return result;
	}

	@Override
	public Mine deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObj = json.getAsJsonObject();

		JsonElement mineElement = jsonObj.get("mineType");
		if (mineElement == null)
			throw new JsonParseException("Missing element: mineType");

		String mineType = mineElement.getAsString();

		JsonElement elements = jsonObj.get("properties");
		if (elements == null)
			throw new JsonParseException("Missing element: properties");

		Class<?> mineClass = null;

		Mine mine = null;

		try {
			mineClass = Class.forName(mineType);

			mine = context.deserialize(elements, mineClass);

		} catch (ClassNotFoundException e) {
			throw new JsonParseException("Could not find mine type: " + mineType);
		} catch (Exception e) {
			throw new JsonParseException("Mine of type " + mineType + " was found but could not be created.", e);
		}
		return mine;
	}
}
