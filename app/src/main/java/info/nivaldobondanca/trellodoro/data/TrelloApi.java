package info.nivaldobondanca.trellodoro.data;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Nivaldo Bondança
 */
public interface TrelloApi {

	@GET("/1/authorize")
	// https://trello.com/1/authorize
		// ?key=f5eca466898f391475cfeac26e032fa0
		// &name=Trellodoro
		// &scope=read,write
	Observable<Void> authorize(
			@Query("key")   String applicationKey,
			@Query("name")  String name,
			@Query("scope") String scope
	);

	@GET("/1/members/me/boards")
	// https://trello.com/1/members/me/boards
		// ?filter=open
		// &fields=name
		// &lists=open
	Observable<Void> boards(
			@Query("filter") String filter,
			@Query("fields") String fields,
			@Query("lists")  String lists
	);

	@GET("/1/boards/{boardId}/cards")
	// https://trello.com/1/boards/4eea4ffc91e31d174600004a/cards
		// ?filter=open
		// &fields=idList,name,desc
	Observable<Void> cards(
			@Path("boardId") String boardId,
			@Query("filter") String filter,
			@Query("fields") String fields
	);
}
