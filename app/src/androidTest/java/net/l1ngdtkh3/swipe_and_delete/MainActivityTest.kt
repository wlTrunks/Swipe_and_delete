package net.l1ngdtkh3.swipe_and_delete

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import net.l1ngdtkh3.swipe_and_delete.MyViewAction.childOfViewAtPositionWithMatcher
import net.l1ngdtkh3.swipe_and_delete.MyViewAction.clickChildViewWithId
import net.l1ngdtkh3.swipe_and_delete.MyViewAction.recyclerViewItemCountAssertion
import net.l1ngdtkh3.swipe_and_delete.MyViewAction.waitUntilShow
import net.l1ngdtkh3.swipe_and_delete.MyViewAction.withViewAtPosition
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCancel() {
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(3, swipeRight()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(3, clickChildViewWithId(R.id.cancel)))
            .check(matches(withViewAtPosition(3, hasDescendant(allOf(withId(R.id.front), isDisplayed())))))
    }

    @Test
    fun testDelete() {
        val position = 7
        var itemCount = 0
        var itemList: List<ItemList>? = null
        activityScenarioRule.scenario.onActivity {
            val list = it.findViewById<RecyclerView>(R.id.recycler_view) ?: throw Exception("Recycler view not found")
            itemCount = list.adapter?.itemCount ?: throw Exception("Recycler adapter null")
            itemList = (list.adapter as ListAdapter<ItemList, ItemAdapter.ItemViewHolder>).currentList
        }
        val textMatch = itemList?.get(position + 1)?.data ?: throw Exception("item list null")
        onView(withId(R.id.recycler_view))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(position))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, swipeRight()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, clickChildViewWithId(R.id.delete)))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, waitUntilShow(R.id.details, 500)))
            .check(recyclerViewItemCountAssertion(itemCount - 1))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.details, position, withText(textMatch))))
    }
}