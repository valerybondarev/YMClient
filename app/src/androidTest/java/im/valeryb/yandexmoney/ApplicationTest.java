package im.valeryb.yandexmoney;

import android.content.ContentUris;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import im.valeryb.yandexmoney.provider.categories.CategoriesContentValues;
import im.valeryb.yandexmoney.provider.categories.CategoriesSelection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void emptyListViewTest() {
        new CategoriesSelection().delete(activityRule.getActivity().getContentResolver());
        onView(withId(R.id.empty_list_view));
    }

    @Test
    public void findSwipeRefreshLayout() {
        onView(withId(R.id.activity_main_swiperefresh));
    }

    @Test
    public void findRefreshButton() {
        onView(withId(R.id.action_refresh));
    }

    @Test
    public void dataItemInAdapter() {
        CategoriesContentValues itemShow = new CategoriesContentValues().putTitle("Show").putParentid(-1).putServerid("0");
        itemShow.insert(activityRule.getActivity().getContentResolver());
        onView(withText("Show"));
        new CategoriesSelection().delete(activityRule.getActivity().getContentResolver());
    }

    @Test
    public void dataItemNotInAdapter() {
        CategoriesContentValues itemHide = new CategoriesContentValues().putTitle("Hide").putParentid(100500).putServerid("0");
        itemHide.insert(activityRule.getActivity().getContentResolver());
        onView(not(withText("Hide")));
        new CategoriesSelection().delete(activityRule.getActivity().getContentResolver());
    }

    @Test
    public void clickNoChildrenItem() {
        CategoriesContentValues itemHide = new CategoriesContentValues().putTitle("Show").putParentid(-1).putServerid("0");
        itemHide.insert(activityRule.getActivity().getContentResolver());
        onView((withText("Show"))).perform(click());
        onView(withText(R.string.no_children));
        new CategoriesSelection().delete(activityRule.getActivity().getContentResolver());
    }

    @Test
    public void clickHaveChildrenItem() {
        CategoriesContentValues itemParent = new CategoriesContentValues().putTitle("Parent").putParentid(-1).putServerid("0");
        long parentId =  ContentUris.parseId(itemParent.insert(activityRule.getActivity().getContentResolver()));
        CategoriesContentValues itemChild = new CategoriesContentValues().putTitle("Child").putParentid(parentId).putServerid("0");
        itemChild.insert(activityRule.getActivity().getContentResolver());
        onView((withText("Parent"))).perform(click());
        //Not smart enough, still better, than nothing
        onView(withText("Parent"));//Test title
        onView(withText("Child"));//Test adapter item
        new CategoriesSelection().delete(activityRule.getActivity().getContentResolver());
    }
}
