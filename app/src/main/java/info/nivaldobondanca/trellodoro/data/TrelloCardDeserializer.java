package info.nivaldobondanca.trellodoro.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.model.factory.CardFactory;

/**
 * @author Nivaldo Bondança
 */
public class TrelloCardDeserializer implements JsonDeserializer<TrelloCard> {
	@Override
	public TrelloCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		final String id = jsonObject.get("id").getAsString();
		final String boardId = jsonObject.get("idBoard").getAsString();
		final String listId = jsonObject.get("idList").getAsString();
		final String name = jsonObject.get("name").getAsString();

		return CardFactory.create(id, name, boardId, listId);
	}
}
