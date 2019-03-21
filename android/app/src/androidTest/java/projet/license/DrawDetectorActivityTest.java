package projet.license;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewAction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import static projet.license.MyViewAction.pamatreziedSingleTouchAt;


@RunWith(AndroidJUnit4.class)
public class DrawDetectorActivityTest {


    @Rule
    public ActivityTestRule<DrawDetectorActivity> mActivityRule = new ActivityTestRule<>(DrawDetectorActivity.class);

    @Before
    public void StartTest(){
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }
    public void drawAndTest(){
        DrawDetectorActivity testActivity = mActivityRule.getActivity();
        int colorAvant = testActivity.myCanvas.mPaint.getColor();
        onView(withId(R.id.color)).perform(click());
        int colorApres = testActivity.myCanvas.mPaint.getColor();
        boolean testEgal = (colorAvant == colorAvant);
        assertFalse("Bon changement de couleur",testEgal);
        }
    }

