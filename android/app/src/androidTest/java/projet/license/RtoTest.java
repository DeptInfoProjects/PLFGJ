package projet.license;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import gestion.Affichage;
import gestion.Connexion;
import gestion.Controleur;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static projet.license.DrawDetectorTest.PAUSE;
import static projet.license.MyViewAction.singleTouchAt;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RtoTest  {
    static boolean PAUSE = false;


    @Rule
    public ActivityTestRule<RtoDetectorActivity> mActivityTestRule = new ActivityTestRule<>(RtoDetectorActivity.class);

    @Test
    public void rtoTest() {
        RtoDetectorActivity demo = mActivityTestRule.getActivity();
        TextView resJoueur = demo.findViewById(R.id.coupJoueur);
        TextView resServeur = demo.findViewById(R.id.coupAdversaire);

        Controleur ctrl = new Controleur( demo);
        int x = 0;
        int y = 100;
        int limite = 300;
        Bundle bundle = new Bundle();
        bundle.putString("coupJoueur", "Circle");
        bundle.putString("coupServeur", "Triangle");
        bundle.putString("resultat", "Joueur");


        Connexion connexion = new Connexion("http://192.168.0.18:10101", ctrl);
        connexion.seConnecter();



        for(x = 100; x < limite; x++){
            onView(withId(R.id.paintView)).perform(singleTouchAt(x, y, MotionEvent.ACTION_DOWN));
            y ++;

            try {
                if (PAUSE) TimeUnit.MILLISECONDS.sleep(50); // pour avoir le temps de voir...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        int cordX = x;
        int cordY = x;


        for(x = cordY; x > 0; x--){
            onView(withId(R.id.paintView)).perform(singleTouchAt(cordX, x, MotionEvent.ACTION_MOVE));

            try {
                if (PAUSE) TimeUnit.MILLISECONDS.sleep(50); // pour avoir le temps de voir...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(x = 1; x < limite; x++){
            onView(withId(R.id.paintView)).perform(singleTouchAt(x, x, MotionEvent.ACTION_MOVE));

            try {
                if (PAUSE) TimeUnit.MILLISECONDS.sleep(50); // pour avoir le temps de voir...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ViewInteraction button3 = onView(
                allOf(withId(R.id.valider), withText("VALIDER"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        button3.perform(click());

        try {
            if (PAUSE) TimeUnit.MILLISECONDS.sleep(50); // pour avoir le temps de voir...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctrl.resultatRto("Circle","Triangle","Joueur");
        String reponseJoueur = resJoueur.getText().toString();
        onView(withId(R.id.coupJoueur)).check(matches(withText(reponseJoueur)));
        Log.e("name",reponseJoueur);

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
