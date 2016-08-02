package info.nivaldobondanca.trellodoro.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Nivaldo Bondança
 */
public interface TrellodoroList extends TrelloList {
	@Type
	int type();


	int TYPE_NONE  = -1;
	int TYPE_TODO  = 0;
	int TYPE_DOING = 1;
	int TYPE_DONE  = 2;

	@IntDef({
			TYPE_NONE,
			TYPE_TODO,
			TYPE_DOING,
			TYPE_DONE,
	})
	@Retention(RetentionPolicy.SOURCE)
	@interface Type {}
}
