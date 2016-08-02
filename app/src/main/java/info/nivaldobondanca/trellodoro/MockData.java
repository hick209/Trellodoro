package info.nivaldobondanca.trellodoro;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import info.nivaldobondanca.trellodoro.model.TrelloBoard;
import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.model.TrelloList;
import info.nivaldobondanca.trellodoro.model.TrellodoroList;
import info.nivaldobondanca.trellodoro.model.factory.BoardFactory;
import info.nivaldobondanca.trellodoro.model.factory.ListFactory;

/**
 * @author Nivaldo Bondança
 */
public class MockData {
	public static List<TrelloBoard> trelloBoards() {
		return Arrays.asList(
				BoardFactory.create("209L", "Hello World!",
						Arrays.asList(
								ListFactory.create("123to7do", "Scratch", Collections.<TrelloCard>emptyList()),
								ListFactory.create("123doing", "Writing", Collections.<TrelloCard>emptyList()),
								ListFactory.create("123done1", "Published", Collections.<TrelloCard>emptyList())
						)),
				BoardFactory.create("1001L", "Empty board", Collections.<TrelloList>emptyList())
		);
	}


	private static TrellodoroList todoList;
	private static TrellodoroList doingList;
	private static TrellodoroList doneList;

	public static void saveLists(TrellodoroList todoList, TrellodoroList doingList, TrellodoroList doneList) {
		MockData.todoList = todoList;
		MockData.doingList = doingList;
		MockData.doneList = doneList;
	}

	public static TrellodoroList todoList() {
		return todoList;
	}

	public static TrellodoroList doingList() {
		return doingList;
	}

	public static TrellodoroList doneList() {
		return doneList;
	}
}
