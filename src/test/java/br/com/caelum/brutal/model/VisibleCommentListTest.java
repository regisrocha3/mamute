package br.com.caelum.brutal.model;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.brutal.dao.TestCase;

public class VisibleCommentListTest extends TestCase{
	
	private VisibleCommentList commentList;
	private User leo;
	private List<Comment> invisibleComments;
	private User moderatorLeo;

	@Before
	public void setup(){
		commentList = new VisibleCommentList();
		leo = user("leonardo", "leo@leo.com");
		moderatorLeo = user("moderator", "moderator@leo.com").asModerator();
		invisibleComments = asList(comment(leo, "meu teste :)").remove());
	}
	
	@Test
	public void should_filter_comments_if_user_is_not_moderator() {
		List<Comment> visibleComments = commentList.getVisibleCommentsFor(leo, invisibleComments);
		assertTrue(visibleComments.isEmpty());
	}
	
	@Test
	public void should_not_filter_if_user_is_moderator() {
		List<Comment> visibleComments = commentList.getVisibleCommentsFor(moderatorLeo, invisibleComments);
		assertFalse(visibleComments.isEmpty());
	}
	
	@Test
	public void should_filter_if_user_is_null() {
		List<Comment> visibleComments = commentList.getVisibleCommentsFor(null, invisibleComments);
		assertTrue(visibleComments.isEmpty());
	}
}
