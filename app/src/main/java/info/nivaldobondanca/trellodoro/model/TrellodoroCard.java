package info.nivaldobondanca.trellodoro.model;

/**
 * @author Nivaldo Bondança
 */
public interface TrellodoroCard {
	TrelloCard trelloCard();

	String id();
	String name();

	long totalTimeSpent();
	int  pomodoroCount();
}
