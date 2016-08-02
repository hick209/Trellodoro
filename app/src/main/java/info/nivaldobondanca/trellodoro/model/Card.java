package info.nivaldobondanca.trellodoro.model;

/**
 * @author Nivaldo Bondança
 */
public interface Card {
	long         id();
	CharSequence title();

	// Extra data
	long totalTimeSpent();
	int  pomodoroCount();
}
