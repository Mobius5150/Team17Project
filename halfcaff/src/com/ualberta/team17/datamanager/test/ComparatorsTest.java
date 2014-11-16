package com.ualberta.team17.datamanager.test;

import java.util.Date;

import com.ualberta.team17.AnswerItem;
import com.ualberta.team17.CommentItem;
import com.ualberta.team17.QAModel;
import com.ualberta.team17.QuestionItem;
import com.ualberta.team17.datamanager.IItemComparator;
import com.ualberta.team17.datamanager.IItemComparator.SortDirection;
import com.ualberta.team17.datamanager.comparators.DateComparator;
import com.ualberta.team17.datamanager.comparators.UpvoteComparator;

import junit.framework.TestCase;

public class ComparatorsTest extends TestCase {
	private void testComparator(IItemComparator comp, QAModel item, QAModel less, QAModel equal, QAModel greater) {
		comp.setCompareDirection(SortDirection.Ascending);
		assertEquals("Compare direction properly set", SortDirection.Ascending, comp.getCompareDirection());
		assertEquals("Equals on Ascending compare direction", 0, comp.compare(item, equal));
		assertEquals(1, comp.compare(item, less));
		assertEquals(-1, comp.compare(item, greater));

		comp.setCompareDirection(SortDirection.Descending);
		assertEquals("Compare direction properly set", SortDirection.Descending, comp.getCompareDirection());
		assertEquals("Equals on Descending compare direction", 0, comp.compare(item, equal));
		assertEquals(-1, comp.compare(item, less));
		assertEquals(1, comp.compare(item, greater));
	}

	/**
	 * Tests that the date comparator properly compares various QAModel items in the ascending and descending directions.
	 */
	public void testDateComparator() {
		IItemComparator comp = new DateComparator();

		// Test straight-up comparison with question items
		testComparator(comp, 
				// Item to compare against
				new QuestionItem(null, null, null, new Date(10), null, 0, null),

				// An item less than the compare item
				new QuestionItem(null, null, null, new Date(0), null, 0, null),

				// An item equal to the compare item
				new QuestionItem(null, null, null, new Date(10), null, 0, null),

				// An item greater than the compare item
				new QuestionItem(null, null, null, new Date(20), null, 0, null));

		// Test comparison with mixed types
		testComparator(comp, 
				// Item to compare against
				new QuestionItem(null, null, null, new Date(20), null, 0, null),

				// An item less than the compare item
				new AnswerItem(null, null, null, new Date(10), null, 0),

				// An item equal to the compare item
				new CommentItem(null, null, null, new Date(20), null, 0),

				// An item greater than the compare item
				new CommentItem(null, null, null, new Date(40), null, 0));

		// Test comparison with mixed types
		testComparator(comp, 
				// Item to compare against
				new AnswerItem(null, null, null, new Date(20), null, 0),

				// An item less than the compare item
				new QuestionItem(null, null, null, new Date(10), null, 0, null),

				// An item equal to the compare item
				new QuestionItem(null, null, null, new Date(20), null, 0, null),

				// An item greater than the compare item
				new CommentItem(null, null, null, new Date(40), null, 0));
	}

	/**
	 * Tests that the upvote comparator properly compares various QAModel items in the ascending and descending directions.
	 */
	public void testUpvoteComparator() {
		IItemComparator comp = new UpvoteComparator();

		// Test straight-up comparison with question items
		testComparator(comp, 
				// Item to compare against
				new QuestionItem(null, null, null, null, null, 10, null),

				// An item less than the compare item
				new QuestionItem(null, null, null, null, null, 0, null),

				// An item equal to the compare item
				new QuestionItem(null, null, null, null, null, 10, null),

				// An item greater than the compare item
				new QuestionItem(null, null, null, null, null, 20, null));

		// Test comparison with mixed types
		testComparator(comp, 
				// Item to compare against
				new QuestionItem(null, null, null, null, null, 100, null),

				// An item less than the compare item
				new AnswerItem(null, null, null, null, null, 50),

				// An item equal to the compare item
				new CommentItem(null, null, null, null, null, 100),

				// An item greater than the compare item
				new CommentItem(null, null, null, null, null, 1029));

		// Test comparison with mixed types
		testComparator(comp, 
				// Item to compare against
				new AnswerItem(null, null, null, null, null, 50),

				// An item less than the compare item
				new QuestionItem(null, null, null, null, null, 0, null),

				// An item equal to the compare item
				new QuestionItem(null, null, null, null, null, 50, null),

				// An item greater than the compare item
				new CommentItem(null, null, null, null, null, 51));
	}
}
