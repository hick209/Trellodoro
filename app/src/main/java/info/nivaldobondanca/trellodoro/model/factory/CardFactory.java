package info.nivaldobondanca.trellodoro.model.factory;

import com.google.gson.JsonObject;

import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.model.TrellodoroCard;

/**
 * @author Nivaldo Bondança
 */
public final class CardFactory {
	private CardFactory() {}

	public static TrelloCard create(String id, String name, String boardId, String listId) {
		return new TrelloCardImpl(id, name, boardId, listId);
	}

	public static TrellodoroCard create(String id, String name, String boardId, String listId, long totalTimeSpent, int pomodoroCount) {
		return new TrellodoroCardImpl(id, name, boardId, listId, totalTimeSpent, pomodoroCount);
	}

	public static TrellodoroCard create(TrelloCard card, long totalTimeSpent, int pomodoroCount) {
		return create(card.id(), card.name(), card.idBoard(), card.idList(), totalTimeSpent, pomodoroCount);
	}
}

class TrelloCardImpl implements TrelloCard {
	private final String id;
	private final String boardId;
	private final String listId;
	private final String name;

	public TrelloCardImpl(String id, String name, String boardId, String listId) {
		this.id = id;
		this.name = name;
		this.boardId = boardId;
		this.listId = listId;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public String idBoard() {
		return boardId;
	}

	@Override
	public String idList() {
		return listId;
	}

	@Override
	public String name() {
		return name;
	}
}

class TrellodoroCardImpl extends TrelloCardImpl implements TrellodoroCard {
	private final long totalTimeSpent;
	private final int  pomodoroCount;

	public TrellodoroCardImpl(String id, String name, String boardId, String listId, long totalTimeSpent, int pomodoroCount) {
		super(id, name, boardId, listId);
		this.totalTimeSpent = totalTimeSpent;
		this.pomodoroCount = pomodoroCount;
	}

	@Override
	public long totalTimeSpent() {
		return totalTimeSpent;
	}

	@Override
	public int pomodoroCount() {
		return pomodoroCount;
	}
}
