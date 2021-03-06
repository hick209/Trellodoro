package info.nivaldobondanca.trellodoro;

import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import info.nivaldobondanca.trellodoro.model.TrelloBoard;
import info.nivaldobondanca.trellodoro.model.TrelloCard;
import info.nivaldobondanca.trellodoro.model.TrelloList;
import info.nivaldobondanca.trellodoro.model.TrellodoroCard;
import info.nivaldobondanca.trellodoro.model.TrellodoroList;
import info.nivaldobondanca.trellodoro.model.factory.BoardFactory;
import info.nivaldobondanca.trellodoro.model.factory.CardFactory;
import info.nivaldobondanca.trellodoro.model.factory.ListFactory;

/**
 * @author Nivaldo Bondança
 */
public class MockData {

	private static TrelloBoard helloBoard = BoardFactory.create("209L", "Hello World!",
			Arrays.asList(
					ListFactory.create("123to7do", "Scratch", "209L", Arrays.<TrelloCard>asList(
							CardFactory.create("card-2", "Do me!", "209L", "123to7do", 0, 0),
							CardFactory.create("card-5", "TODO, XXX, FIXME", "209L", "123to7do", 0, 0)
					)),
					ListFactory.create("123doing", "Writing", "209L", Arrays.<TrelloCard>asList(
							CardFactory.create("card-3", "Almost there!", "209L", "123doing", 42 * 60 * 1000, 7),
							CardFactory.create("card-4", "Oh yeah ;)", "209L", "123doing", 0, 1)
					)),
					ListFactory.create("123done1", "Published", "209L", Arrays.<TrelloCard>asList(
							CardFactory.create("card-1", "Hello World card!", "209L", "123done1", 5 * 60_000, 1),
							CardFactory.create("card-6", "This is done!", "209L", "123done1", 0, 0)
					))
			));
	private static TrelloBoard emptyBoard = BoardFactory.create("1001L", "Empty board", Collections.<TrelloList>emptyList());

	public static List<TrelloBoard> trelloBoards() {
		return Arrays.asList(helloBoard, emptyBoard);
	}


	private static TrellodoroList todoList = ListFactory.create(helloBoard.lists().get(0), TrellodoroList.TYPE_TODO);
	private static TrellodoroList doingList = ListFactory.create(helloBoard.lists().get(1), TrellodoroList.TYPE_DOING);
	private static TrellodoroList doneList = ListFactory.create(helloBoard.lists().get(2), TrellodoroList.TYPE_DONE);

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

	public static TrellodoroCard trellodoroCardForId(String id) {
		final List<TrelloBoard> boards = trelloBoards();
		for (TrelloBoard board : boards) {
			for (TrelloList list : board.lists()) {
				for (TrelloCard card : list.cards()) {
					if (Objects.equals(card.id(), id)) {
						return (TrellodoroCard) card;
					}
				}
			}
		}

		return null;
	}

	public static TrellodoroCard updateCard(TrellodoroCard card, long totalTimeSpent, int pomodoroCount) {
		return CardFactory.create(card, totalTimeSpent, pomodoroCount);
	}
}
