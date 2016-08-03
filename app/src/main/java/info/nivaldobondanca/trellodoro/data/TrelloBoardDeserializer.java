package info.nivaldobondanca.trellodoro.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import info.nivaldobondanca.trellodoro.model.TrelloBoard;
import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.model.TrelloList;
import info.nivaldobondanca.trellodoro.model.factory.BoardFactory;
import info.nivaldobondanca.trellodoro.model.factory.CardFactory;

/**
 * @author Nivaldo Bondança
 */
public class TrelloBoardDeserializer implements JsonDeserializer<TrelloBoard> {
	@Override
	public TrelloBoard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		final String id = jsonObject.get("id").getAsString();
		final String name = jsonObject.get("name").getAsString();
		final List<TrelloList> taskLists = context.deserialize(jsonObject.get("lists"), List.class);

		return BoardFactory.create(id, name, taskLists);
	}
}
