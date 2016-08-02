package info.nivaldobondanca.trellodoro.model;

import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public interface TrelloList {
	String           id();
	String           name();
	List<TrelloCard> cards();
}
