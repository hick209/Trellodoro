package info.nivaldobondanca.trellodoro.model.factory;

import java.util.List;

import info.nivaldobondanca.trellodoro.model.TrelloBoard;
import info.nivaldobondanca.trellodoro.model.TrelloList;

/**
 * @author Nivaldo Bondança
 */
public final class BoardFactory {
	private BoardFactory() {}

	public static TrelloBoard create(String id, String name, List<TrelloList> taskLists) {
		return new BoardImpl(id, name, taskLists);
	}
}

class BoardImpl implements TrelloBoard {
	private final String           id;
	private final String           name;
	private final List<TrelloList> taskLists;

	public BoardImpl(String id, String name, List<TrelloList> taskLists) {
		this.id = id;
		this.name = name;
		this.taskLists = taskLists;
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
	public List<TrelloList> lists() {
		return taskLists;
	}
}
