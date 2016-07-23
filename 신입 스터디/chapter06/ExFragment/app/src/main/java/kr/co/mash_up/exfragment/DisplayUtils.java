package kr.co.mash_up.exfragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;

/**
 * Created by Administrator on 2016-03-30.
 */
public class DisplayUtils {

    //테블릿인지 아닌지
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //화면이 가로인지 아닌지
    public static boolean isScreenOrientationLandscape(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();

        Point point = new Point();
        display.getSize(point);

        if (point.x > point.y) {  //landscape
            return true;
        } else {  //portrait
            return false;
        }
    }
}
