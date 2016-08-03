package info.nivaldobondanca.trellodoro.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import info.nivaldobondanca.trellodoro.model.TrelloList;
import info.nivaldobondanca.trellodoro.model.factory.ListFactory;

/**
 * @author Nivaldo Bondança
 */
public class TrelloListDeserializer implements JsonDeserializer<TrelloList> {
	@Override
	public TrelloList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		final String id = jsonObject.get("id").getAsString();
		final String boardId = jsonObject.get("idBoard").getAsString();
		final String name = jsonObject.get("name").getAsString();

		return ListFactory.create(id, name, boardId);
	}
}
