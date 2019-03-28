package projet.license;


import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;


import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static projet.license.MyViewAction.singleTouchAt;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DrawDetectorValider {
    static boolean PAUSE = false;
    @Rule
    public ActivityTestRule<DrawDetectorActivity> mActivityTestRule = new ActivityTestRule<>(DrawDetectorActivity.class);


    @Before
    public void startTest(){
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }



    @Test
    public void drawDetectorValider() {
        DrawDetectorActivity demo = mActivityTestRule.getActivity();
        PaintView canvas =   demo.findViewById(R.id.myCanvas);
        int limite = 150;
        int x = 0;
        int initialisation = 0; // pour le button Effacer

        ViewInteraction button2 = onView(
                allOf(withId(R.id.start1), withText("START"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        button2.perform(click());

        onView(withId(R.id.myCanvas)).perform(singleTouchAt(x,x, MotionEvent.ACTION_DOWN));
        assertEquals("taille de la liste de point", x + 1, (int) canvas.getTicks());


        for(x = 1; x < limite; x++){
            onView(withId(R.id.myCanvas)).perform(singleTouchAt(x, x, MotionEvent.ACTION_MOVE));
            assertEquals("taille de la liste de point", x + 1, (int) canvas.getTicks());

            try {
                if (PAUSE) TimeUnit.MILLISECONDS.sleep(50); // pour avoir le temps de voir...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        onView(withId(R.id.myCanvas)).perform(singleTouchAt(x, x, MotionEvent.ACTION_UP));
        assertEquals("taille de la liste de point", x + 1, (int) canvas.getTicks());



        ViewInteraction button3 = onView(
                allOf(withId(R.id.valider), withText("Valider"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        button3.perform(click());

        // Verification si le button Effacer initalise le nombre de touche
        ViewInteraction button4 = onView(
                allOf(withId(R.id.effacer), withText("EFFACER"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        button4.perform(click());
        assertEquals("taille de la liste de point", initialisation, (int) canvas.getTicks());
        onView(withId(R.id.click)).check(matches(withText("0")));
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
