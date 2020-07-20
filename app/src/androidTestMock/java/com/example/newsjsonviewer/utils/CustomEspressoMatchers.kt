package com.example.newsjsonviewer.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


/**
 * how to use it:
 * onView(withId(R.id.my_list_recycler)).check(matches(recyclerViewSizeMatcher(4)));
 */
fun recyclerViewSizeMatcher(matcherSize: Int): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with list size: $matcherSize")
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            (matcherSize == recyclerView.adapter!!.itemCount).let {
                if (!it) System.err.println("Expected items: $matcherSize but got ${recyclerView.adapter!!.itemCount}")
                return it
            }
        }
    }
}

/**
 * how to use it >> checks that item in position 2 has a text on an specific view of the holder
 * onView(withId(R.id.my_recycler_view)).check(
 *          matches(recyclerViewAtPositionOnView(2, withText(ITEM_TEXT_TO_CHECK), R.id.pickup_location_desc)));
 */
fun recyclerViewAtPositionOnView(
    position: Int,
    itemMatcher: Matcher<View>, targetViewId: Int
): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has view id $itemMatcher at position $position")
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder =
                recyclerView.findViewHolderForAdapterPosition(position)
            val targetView =
                viewHolder!!.itemView.findViewById<View>(targetViewId)
            return itemMatcher.matches(targetView)
        }
    }
}