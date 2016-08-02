package info.nivaldobondanca.trellodoro.model.factory;

import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.model.TrellodoroCard;

/**
 * @author Nivaldo Bondança
 */
public final class CardFactory {
	private CardFactory() {}

	public static TrelloCard create(String id, String name) {
		return new TrelloCardImpl(id, name);
	}

	public static TrellodoroCard create(String id, String name, long totalTimeSpent, int pomodoroCount) {
		return new TrellodoroCardImpl(id, name, totalTimeSpent, pomodoroCount);
	}

	public static TrellodoroCard create(TrelloCard card, long totalTimeSpent, int pomodoroCount) {
		return create(card.id(), card.name(), totalTimeSpent, pomodoroCount);
	}
}

class TrelloCardImpl implements TrelloCard {
	private final String id;
	private final String name;

	public TrelloCardImpl(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public String name() {
		return name;
	}
}

class TrellodoroCardImpl extends TrelloCardImpl implements TrellodoroCard {
	private final long totalTimeSpent;
	private final int  pomodoroCount;

	public TrellodoroCardImpl(String id, String name, long totalTimeSpent, int pomodoroCount) {
		super(id, name);
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
