package info.nivaldobondanca.trellodoro.model;

import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public interface Board {
	long           id();
	List<TaskList> lists();
}
