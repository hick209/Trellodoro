package info.nivaldobondanca.trellodoro.model;

/**
 * @author Nivaldo Bondança
 */
public interface TrelloCard {
	String       id();
	CharSequence title();

	// Extra data
	long totalTimeSpent();
	int  pomodoroCount();
}
