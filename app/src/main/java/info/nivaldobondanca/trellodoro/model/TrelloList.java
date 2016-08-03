package info.nivaldobondanca.trellodoro.model;

import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public interface TrelloList {
	String           id();
	String           idBoard();
	String           name();
	List<TrelloCard> cards();
}
