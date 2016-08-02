package info.nivaldobondanca.trellodoro.model;

import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public interface TrelloBoard {
	String           id();
	String           name();
	List<TrelloList> lists();
}
