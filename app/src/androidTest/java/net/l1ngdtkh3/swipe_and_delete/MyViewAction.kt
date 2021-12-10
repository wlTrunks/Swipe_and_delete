package net.l1ngdtkh3.swipe_and_delete

import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.CoreMatchers.any
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.StringDescription
import java.util.concurrent.TimeoutException

object MyViewAction {
    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }

    fun withViewAtPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description?) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    fun recyclerViewItemCountAssertion(expectedCount: Int): ViewAssertion {
        return ViewAssertion { view, exception ->
            if (view !is RecyclerView) {
                throw exception
            }
            val adapter = view.adapter
            assertThat(adapter!!.itemCount, `is`(expectedCount))
        }
    }

    /**
     * @param childId view id on view holder
     * @param position position in recycler view list
     * @param childMatcher matcher to match
     */
    fun childOfViewAtPositionWithMatcher(childId: Int, position: Int, childMatcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("Checks child $childId on view holder on position $position")
            }

            override fun matchesSafely(recyclerView: RecyclerView?): Boolean {
                val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position)
                val matcher = hasDescendant(allOf(withId(childId), childMatcher))
                return viewHolder != null && matcher.matches(viewHolder.itemView)
            }
        }
    }

    fun waitUntil(matcher: Matcher<View>): ViewAction = object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return any(View::class.java)
        }

        override fun getDescription(): String {
            return StringDescription().let {
                matcher.describeTo(it)
                "wait until: $it"
            }
        }

        override fun perform(uiController: UiController, view: View) {
            if (!matcher.matches(view)) {
                ViewPropertyChangeCallback(matcher, view).run {
                    try {
                        IdlingRegistry.getInstance().register(this)
                        view.viewTreeObserver.addOnDrawListener(this)
                        uiController.loopMainThreadUntilIdle()
                    } finally {
                        view.viewTreeObserver.removeOnDrawListener(this)
                        IdlingRegistry.getInstance().unregister(this)
                    }
                }
            }
        }
    }

    /**
     * @param viewId view id to show
     * @param millis time to await
     */
    fun waitUntilShow(viewId: Int, millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view <$viewId> is changed during $millis millis."
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadUntilIdle()
                val viewMatcher: Matcher<View> = withId(viewId)
                uiController.loopMainThreadForAtLeast(millis)
                for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                    // found view with required ID
                    if (viewMatcher.matches(child)) {
                        return
                    }
                }
                throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(TimeoutException())
                    .build()
            }
        }
    }
}

private class ViewPropertyChangeCallback(private val matcher: Matcher<View>, private val view: View) : IdlingResource,
    ViewTreeObserver.OnDrawListener {

    private lateinit var callback: IdlingResource.ResourceCallback
    private var matched = false

    override fun getName() = "View property change callback"

    override fun isIdleNow() = matched

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }

    override fun onDraw() {
        matched = matcher.matches(view)
        callback.onTransitionToIdle()
    }
}

