package info.nivaldobondanca.trellodoro.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public interface TaskList {
	@IntDef({
			TASK_LIST_TODO,
			TASK_LIST_DOING,
			TASK_LIST_DONE,
	})
	@Retention(RetentionPolicy.SOURCE)
	@interface Type {}

	int TASK_LIST_TODO  = 0;
	int TASK_LIST_DOING = 1;
	int TASK_LIST_DONE  = 2;

	long       id();
	List<Card> cards();

	// Extra fields
	@Type
	int type();
}
