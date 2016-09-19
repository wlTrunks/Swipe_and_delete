package net.l1ngdtkh3.swipe_and_delete;


import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testCancel() {

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(3, swipeRight()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(3, MyViewAction.clickChildViewWithId(R.id.cancel)));

    }
    @Test
    public void testDelete() {

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(7));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(7, swipeRight()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(7, MyViewAction.clickChildViewWithId(R.id.delete)));

    }
}
