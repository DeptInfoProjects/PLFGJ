package projet.license;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeVersActivity {
    private final static String PACKAGE_NAME = "projet.license";

    @Rule
    public IntentsTestRule<HomeActivity> mActivityTestRule = new IntentsTestRule<>(HomeActivity.class);

    @Test
    public void homeVersActivity() {
        ViewInteraction button2 = onView(
                allOf(withId(R.id.DrawDetector), withText("Draw Detector"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        button2.perform(click());
        intended(AllOf.allOf(hasComponent(hasShortClassName(".DrawDetectorActivity")),toPackage(PACKAGE_NAME)));

        pressBack();



        ViewInteraction button3 = onView(
                allOf(withId(R.id.RtoDetector), withText("RTO DETECTOR"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        button3.perform(click());
        intended(AllOf.allOf(hasComponent(hasShortClassName(".RtoDetectorActivity")),toPackage(PACKAGE_NAME)));

        pressBack();



        ViewInteraction button4 = onView(
                allOf(withId(R.id.TimeDetector), withText("TIME DETECTOR"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        button4.perform(click());
        intended(AllOf.allOf(hasComponent(hasShortClassName(".TimeDetectorActivity")),toPackage(PACKAGE_NAME)));
        pressBack();

        ViewInteraction button5 = onView(
                allOf(withId(R.id.Riddle), withText("RIDDLES"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        button5.perform(click());

        intended(AllOf.allOf(hasComponent(hasShortClassName(".RiddleActivity")),toPackage(PACKAGE_NAME)));

        pressBack();



        ViewInteraction button6 = onView(
                allOf(withId(R.id.TrainingDetector), withText("WarmUp"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        button6.perform(click());

        intended(AllOf.allOf(hasComponent(hasShortClassName(".TrainingActivity")),toPackage(PACKAGE_NAME)));

        pressBack();




        ViewInteraction button7 = onView(
                allOf(withId(R.id.stat), withText("STATS"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        button7.perform(click());

        intended(AllOf.allOf(hasComponent(hasShortClassName(".StatisticsActivity")),toPackage(PACKAGE_NAME)));

        pressBack();


    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
