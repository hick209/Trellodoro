package info.nivaldobondanca.trellodoro.model.factory;

import java.util.List;

import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.model.TrelloList;
import info.nivaldobondanca.trellodoro.model.TrellodoroList;

/**
 * @author Nivaldo Bondança
 */
public class ListFactory {
	private ListFactory() {}

	public static TrelloList create(String id, String name, List<TrelloCard> cards) {
		return new TrelloListImpl(id, name, cards);
	}

	public static TrellodoroList create(String id, String name, @TrellodoroList.Type int type, List<TrelloCard> cards) {
		return new TrellodoroListImpl(id, name, type, cards);
	}

	public static TrellodoroList create(TrelloList trelloList, @TrellodoroList.Type int type) {
		return new TrellodoroListImpl(trelloList, type);
	}
}

class TrelloListImpl implements TrelloList {
	private final String           id;
	private final String           name;
	private final List<TrelloCard> cards;

	public TrelloListImpl(String id, String name, List<TrelloCard> cards) {
		this.id = id;
		this.name = name;
		this.cards = cards;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public List<TrelloCard> cards() {
		return cards;
	}
}

class TrellodoroListImpl extends TrelloListImpl implements TrellodoroList {
	@Type
	private final int type;

	public TrellodoroListImpl(TrelloList trelloList, int type) {
		this(trelloList.id(), trelloList.name(), type, trelloList.cards());
	}

	public TrellodoroListImpl(String id, String name, List<TrelloCard> cards) {
		this(id, name, TYPE_NONE, cards);
	}

	public TrellodoroListImpl(String id, String name, @Type int type, List<TrelloCard> cards) {
		super(id, name, cards);
		this.type = type;
	}

	@Override
	public int type() {
		return type;
	}
}
