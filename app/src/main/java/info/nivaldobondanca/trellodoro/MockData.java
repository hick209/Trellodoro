package info.nivaldobondanca.trellodoro;

import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import info.nivaldobondanca.trellodoro.model.TrelloBoard;
import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.model.TrelloList;
import info.nivaldobondanca.trellodoro.model.TrellodoroCard;
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

	public static TrellodoroList listForType(@TrellodoroList.Type int type) {
		switch (type) {
			case TrellodoroList.TYPE_TODO:
				return todoList;

			case TrellodoroList.TYPE_DOING:
				return doingList;

			case TrellodoroList.TYPE_DONE:
				return doneList;

			case TrellodoroList.TYPE_NONE:
			default:
				throw new IllegalArgumentException("Must be a valid type. type = " + type);
		}
	}

	public static List<TrellodoroCard> trellodoroCardsForList(@Nullable TrellodoroList list) {
		if (list == null) return Collections.emptyList();

		return (List) list.cards();
	}
}
