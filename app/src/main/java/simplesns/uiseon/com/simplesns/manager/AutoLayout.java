package simplesns.uiseon.com.simplesns.manager;

import android.app.ActionBar;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AutoLayout {
    private static AutoLayout instance;

    public static AutoLayout GetInstance() {
        return instance;
    }

    static {
        instance = new AutoLayout();
    }

    public void init(Context context, int initWidth, int initHeight) {
        disPlayWidth = initWidth;
        disPlayHeight = initHeight;
    }

    public static int disPlayWidth = 0;
    public static int disPlayHeight = 0;

    public void setView(View tempView) {
        setByWidth(tempView);
        if (tempView instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) tempView).getChildCount(); i++) {
                View child = ((ViewGroup) tempView).getChildAt(i);
                setView(child);
            }
        }
    }

    private void set(View tempView) {
        ViewGroup.LayoutParams tempParam = tempView
                .getLayoutParams();

        if (tempParam instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams LParam = (LinearLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calHeight(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calHeight(LParam.topMargin);
            LParam.bottomMargin = calHeight(LParam.bottomMargin);

        } else if (tempParam instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams LParam = (RelativeLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calHeight(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calHeight(LParam.topMargin);
            LParam.bottomMargin = calHeight(LParam.bottomMargin);

        } else if (tempParam instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams LParam = (FrameLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calHeight(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calHeight(LParam.topMargin);
            LParam.bottomMargin = calHeight(LParam.bottomMargin);

        } else if (tempParam instanceof ViewPager.LayoutParams) {
            ViewPager.LayoutParams LParam = (ViewPager.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calHeight(LParam.height);
        } else if (tempParam instanceof AbsListView.LayoutParams) {
            AbsListView.LayoutParams LParam = (AbsListView.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calHeight(LParam.height);
        } else if (tempParam instanceof ActionBar.LayoutParams) {
            ActionBar.LayoutParams LParam = (ActionBar.LayoutParams) tempParam;
            // LParam.width = calWidth(LParam.width);
            // LParam.height = calHeight(LParam.height);
            // LParam.leftMargin = calWidth(LParam.leftMargin);
            // LParam.rightMargin = calWidth(LParam.rightMargin);
            // LParam.topMargin = calHeight(LParam.topMargin);
            // LParam.bottomMargin = calHeight(LParam.bottomMargin);
            Log.e("test", "ActionBar no resize functions");
        } else if (tempParam instanceof ViewGroup.LayoutParams) {
            ViewGroup.LayoutParams LParam = (ViewGroup.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calHeight(LParam.height);
        } else if (tempParam instanceof WindowManager.LayoutParams) {
            WindowManager.LayoutParams LParam = (WindowManager.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calHeight(LParam.height);
        } else if (tempParam instanceof GridLayout.LayoutParams) {
            GridLayout.LayoutParams LParam = (GridLayout.LayoutParams) tempParam;
            // LParam.width = calWidth(LParam.width);
            // LParam.height = calHeight(LParam.height);
            // LParam.leftMargin = calWidth(LParam.leftMargin);
            // LParam.rightMargin = calWidth(LParam.rightMargin);
            // LParam.topMargin = calHeight(LParam.topMargin);
            // LParam.bottomMargin = calHeight(LParam.bottomMargin);
            Log.e("test", "GridLayout no resize functions");
        } else if (tempParam instanceof RadioGroup.LayoutParams) {
            RadioGroup.LayoutParams LParam = (RadioGroup.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calHeight(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calHeight(LParam.topMargin);
            LParam.bottomMargin = calHeight(LParam.bottomMargin);
        } else if (tempParam instanceof TableLayout.LayoutParams) {
            TableLayout.LayoutParams LParam = (TableLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calHeight(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calHeight(LParam.topMargin);
            LParam.bottomMargin = calHeight(LParam.bottomMargin);
        } else if (tempParam instanceof TableRow.LayoutParams) {
            TableRow.LayoutParams LParam = (TableRow.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calHeight(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calHeight(LParam.topMargin);
            LParam.bottomMargin = calHeight(LParam.bottomMargin);
        } else if (tempParam == null) {
            Log.e("test", "param is null !!");
        } else {
            Log.e("test", "no resize functions !!");
        }

        int left = calWidth(tempView.getPaddingLeft());
        int right = calWidth(tempView.getPaddingRight());
        int top = calHeight(tempView.getPaddingTop());
        int bottom = calHeight(tempView.getPaddingBottom());
        tempView.setPadding(left, top, right, bottom);
        if (tempView instanceof TextView) {
            TextView textview = (TextView) tempView;
            textview.setTextSize(0, calHeight((int) textview.getTextSize()));
        }
    }

    public void setView1080(View tempView) {
        set1080(tempView);
        if (tempView instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) tempView).getChildCount(); i++) {
                View child = ((ViewGroup) tempView).getChildAt(i);
                setView1080(child);
            }
        }
    }

    private void set1080(View tempView) {
//		setFont(tempView);
        ViewGroup.LayoutParams tempParam = tempView
                .getLayoutParams();

        if (tempParam instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams LParam = (LinearLayout.LayoutParams) tempParam;
            LParam.width = calWidth1080(LParam.width);
            LParam.height = calHeight1080(LParam.height);
            LParam.leftMargin = calWidth1080(LParam.leftMargin);
            LParam.rightMargin = calWidth1080(LParam.rightMargin);
            LParam.topMargin = calHeight1080(LParam.topMargin);
            LParam.bottomMargin = calHeight1080(LParam.bottomMargin);

        } else if (tempParam instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams LParam = (RelativeLayout.LayoutParams) tempParam;
            LParam.width = calWidth1080(LParam.width);
            LParam.height = calHeight1080(LParam.height);
            LParam.leftMargin = calWidth1080(LParam.leftMargin);
            LParam.rightMargin = calWidth1080(LParam.rightMargin);
            LParam.topMargin = calHeight1080(LParam.topMargin);
            LParam.bottomMargin = calHeight1080(LParam.bottomMargin);

        } else if (tempParam instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams LParam = (FrameLayout.LayoutParams) tempParam;
            LParam.width = calWidth1080(LParam.width);
            LParam.height = calHeight1080(LParam.height);
            LParam.leftMargin = calWidth1080(LParam.leftMargin);
            LParam.rightMargin = calWidth1080(LParam.rightMargin);
            LParam.topMargin = calHeight1080(LParam.topMargin);
            LParam.bottomMargin = calHeight1080(LParam.bottomMargin);

        } else if (tempParam instanceof ViewPager.LayoutParams) {
            ViewPager.LayoutParams LParam = (ViewPager.LayoutParams) tempParam;
            LParam.width = calWidth1080(LParam.width);
            LParam.height = calHeight1080(LParam.height);
        } else if (tempParam instanceof AbsListView.LayoutParams) {
            AbsListView.LayoutParams LParam = (AbsListView.LayoutParams) tempParam;
            LParam.width = calWidth1080(LParam.width);
            LParam.height = calHeight1080(LParam.height);
        } else if (tempParam instanceof ActionBar.LayoutParams) {
            ActionBar.LayoutParams LParam = (ActionBar.LayoutParams) tempParam;
            Log.e("test", "ActionBar no resize functions");
        } else if (tempParam instanceof ViewGroup.LayoutParams) {
            ViewGroup.LayoutParams LParam = (ViewGroup.LayoutParams) tempParam;
            LParam.width = calWidth1080(LParam.width);
            LParam.height = calHeight1080(LParam.height);
        } else if (tempParam instanceof WindowManager.LayoutParams) {
            WindowManager.LayoutParams LParam = (WindowManager.LayoutParams) tempParam;
            LParam.width = calWidth1080(LParam.width);
            LParam.height = calHeight1080(LParam.height);
        } else if (tempParam instanceof GridLayout.LayoutParams) {
            GridLayout.LayoutParams LParam = (GridLayout.LayoutParams) tempParam;
            Log.e("test", "GridLayout no resize functions");
        } else if (tempParam instanceof RadioGroup.LayoutParams) {
            RadioGroup.LayoutParams LParam = (RadioGroup.LayoutParams) tempParam;
            LParam.width = calWidth1080(LParam.width);
            LParam.height = calHeight1080(LParam.height);
            LParam.leftMargin = calWidth1080(LParam.leftMargin);
            LParam.rightMargin = calWidth1080(LParam.rightMargin);
            LParam.topMargin = calHeight1080(LParam.topMargin);
            LParam.bottomMargin = calHeight1080(LParam.bottomMargin);
        } else if (tempParam instanceof TableLayout.LayoutParams) {
            TableLayout.LayoutParams LParam = (TableLayout.LayoutParams) tempParam;
            LParam.width = calWidth1080(LParam.width);
            LParam.height = calHeight1080(LParam.height);
            LParam.leftMargin = calWidth1080(LParam.leftMargin);
            LParam.rightMargin = calWidth1080(LParam.rightMargin);
            LParam.topMargin = calHeight1080(LParam.topMargin);
            LParam.bottomMargin = calHeight1080(LParam.bottomMargin);
        } else if (tempParam instanceof TableRow.LayoutParams) {
            TableRow.LayoutParams LParam = (TableRow.LayoutParams) tempParam;
            LParam.width = calWidth1080(LParam.width);
            LParam.height = calHeight1080(LParam.height);
            LParam.leftMargin = calWidth1080(LParam.leftMargin);
            LParam.rightMargin = calWidth1080(LParam.rightMargin);
            LParam.topMargin = calHeight1080(LParam.topMargin);
            LParam.bottomMargin = calHeight1080(LParam.bottomMargin);
        } else if (tempParam == null) {
            Log.e("test", "param is null !!");
        } else {
            Log.e("test", "no resize functions !!");
        }

        int left = calWidth1080(tempView.getPaddingLeft());
        int right = calWidth1080(tempView.getPaddingRight());
        int top = calHeight1080(tempView.getPaddingTop());
        int bottom = calHeight1080(tempView.getPaddingBottom());

        tempView.setPadding(left, top, right, bottom);
        if (tempView instanceof TextView) {
            TextView textview = (TextView) tempView;
            textview.setTextSize(0, calHeight1080((int) textview.getTextSize()));
        }
    }

    private int calWidth1080(int getwidth) {
        if (getwidth > 0) {
            return getwidth * disPlayWidth / 1080;
            // return getwidth * disPlayWidth / 720;
        } else {
            return getwidth;
        }
    }

    private int calHeight1080(int getheight) {
        if (getheight > 0) {
            return getheight * disPlayHeight / 1920;
            // return getheight * disPlayHeight / 1080;
        } else {
            return getheight;
        }
    }

    public void setWidthView(View tempView) {
        setByWidth(tempView);
        if (tempView instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) tempView).getChildCount(); i++) {
                View child = ((ViewGroup) tempView).getChildAt(i);
                setWidthView(child);
            }
        }
    }

    private void setByWidth(View tempView) {
//		setFont(tempView);
        ViewGroup.LayoutParams tempParam = tempView
                .getLayoutParams();

        if (tempParam instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams LParam = (LinearLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calWidth(LParam.topMargin);
            LParam.bottomMargin = calWidth(LParam.bottomMargin);

        } else if (tempParam instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams LParam = (RelativeLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calWidth(LParam.topMargin);
            LParam.bottomMargin = calWidth(LParam.bottomMargin);

        } else if (tempParam instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams LParam = (FrameLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calWidth(LParam.topMargin);
            LParam.bottomMargin = calWidth(LParam.bottomMargin);

        } else if (tempParam instanceof ViewPager.LayoutParams) {
            ViewPager.LayoutParams LParam = (ViewPager.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
        } else if (tempParam instanceof AbsListView.LayoutParams) {
            AbsListView.LayoutParams LParam = (AbsListView.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
        } else if (tempParam instanceof ActionBar.LayoutParams) {
            ActionBar.LayoutParams LParam = (ActionBar.LayoutParams) tempParam;
            // LParam.width = calWidth(LParam.width);
            // LParam.height = calWidth(LParam.height);
            // LParam.leftMargin = calWidth(LParam.leftMargin);
            // LParam.rightMargin = calWidth(LParam.rightMargin);
            // LParam.topMargin = calWidth(LParam.topMargin);
            // LParam.bottomMargin = calWidth(LParam.bottomMargin);
            Log.e("test", "ActionBar no resize functions");
        } else if (tempParam instanceof ViewGroup.LayoutParams) {
            ViewGroup.LayoutParams LParam = (ViewGroup.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
        } else if (tempParam instanceof WindowManager.LayoutParams) {
            WindowManager.LayoutParams LParam = (WindowManager.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
        } else if (tempParam instanceof GridLayout.LayoutParams) {
            GridLayout.LayoutParams LParam = (GridLayout.LayoutParams) tempParam;
            // LParam.width = calWidth(LParam.width);
            // LParam.height = calWidth(LParam.height);
            // LParam.leftMargin = calWidth(LParam.leftMargin);
            // LParam.rightMargin = calWidth(LParam.rightMargin);
            // LParam.topMargin = calWidth(LParam.topMargin);
            // LParam.bottomMargin = calWidth(LParam.bottomMargin);
            Log.e("test", "GridLayout no resize functions");
        } else if (tempParam instanceof RadioGroup.LayoutParams) {
            RadioGroup.LayoutParams LParam = (RadioGroup.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calWidth(LParam.topMargin);
            LParam.bottomMargin = calWidth(LParam.bottomMargin);
        } else if (tempParam instanceof TableLayout.LayoutParams) {
            TableLayout.LayoutParams LParam = (TableLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calWidth(LParam.topMargin);
            LParam.bottomMargin = calWidth(LParam.bottomMargin);
        } else if (tempParam instanceof TableRow.LayoutParams) {
            TableRow.LayoutParams LParam = (TableRow.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calWidth(LParam.topMargin);
            LParam.bottomMargin = calWidth(LParam.bottomMargin);
        } else if (tempParam == null) {
            Log.e("test", "param is null !!");
        } else {
            Log.e("test", "no resize functions !!");
        }

        int left = calWidth(tempView.getPaddingLeft());
        int right = calWidth(tempView.getPaddingRight());
        int top = calWidth(tempView.getPaddingTop());
        int bottom = calWidth(tempView.getPaddingBottom());
        tempView.setPadding(left, top, right, bottom);
        if (tempView instanceof TextView) {
            TextView textview = (TextView) tempView;
            textview.setTextSize(0, calWidth((int) textview.getTextSize()));
        }
    }

    private void setWidthStand(View tempView) {
        ViewGroup.LayoutParams tempParam = tempView
                .getLayoutParams();

        if (tempParam instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams LParam = (LinearLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calWidth(LParam.topMargin);
            LParam.bottomMargin = calWidth(LParam.bottomMargin);

        } else if (tempParam instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams LParam = (RelativeLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calWidth(LParam.topMargin);
            LParam.bottomMargin = calWidth(LParam.bottomMargin);

        } else if (tempParam instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams LParam = (FrameLayout.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
            LParam.leftMargin = calWidth(LParam.leftMargin);
            LParam.rightMargin = calWidth(LParam.rightMargin);
            LParam.topMargin = calWidth(LParam.topMargin);
            LParam.bottomMargin = calWidth(LParam.bottomMargin);

        } else if (tempParam instanceof ViewPager.LayoutParams) {
            ViewPager.LayoutParams LParam = (ViewPager.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);

        } else if (tempParam instanceof AbsListView.LayoutParams) {
            AbsListView.LayoutParams LParam = (AbsListView.LayoutParams) tempParam;
            LParam.width = calWidth(LParam.width);
            LParam.height = calWidth(LParam.height);
        } else {
            Log.e("test", "no resize functions");
        }

        int left = calWidth(tempView.getPaddingLeft());
        int right = calWidth(tempView.getPaddingRight());
        int top = calWidth(tempView.getPaddingTop());
        int bottom = calWidth(tempView.getPaddingBottom());
        tempView.setPadding(left, top, right, bottom);
    }

    private void setHeightStand(View tempView) {
        ViewGroup.LayoutParams tempParam = tempView
                .getLayoutParams();

        if (tempParam instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams LParam = (LinearLayout.LayoutParams) tempParam;
            LParam.width = calHeight(LParam.width);
            LParam.height = calHeight(LParam.height);
            LParam.leftMargin = calHeight(LParam.leftMargin);
            LParam.rightMargin = calHeight(LParam.rightMargin);
            LParam.topMargin = calHeight(LParam.topMargin);
            LParam.bottomMargin = calHeight(LParam.bottomMargin);

        } else if (tempParam instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams LParam = (RelativeLayout.LayoutParams) tempParam;
            LParam.width = calHeight(LParam.width);
            LParam.height = calHeight(LParam.height);
            LParam.leftMargin = calHeight(LParam.leftMargin);
            LParam.rightMargin = calHeight(LParam.rightMargin);
            LParam.topMargin = calHeight(LParam.topMargin);
            LParam.bottomMargin = calHeight(LParam.bottomMargin);

        } else if (tempParam instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams LParam = (FrameLayout.LayoutParams) tempParam;
            LParam.width = calHeight(LParam.width);
            LParam.height = calHeight(LParam.height);
            LParam.leftMargin = calHeight(LParam.leftMargin);
            LParam.rightMargin = calHeight(LParam.rightMargin);
            LParam.topMargin = calHeight(LParam.topMargin);
            LParam.bottomMargin = calHeight(LParam.bottomMargin);

        } else if (tempParam instanceof ViewPager.LayoutParams) {
            ViewPager.LayoutParams LParam = (ViewPager.LayoutParams) tempParam;
            LParam.width = calHeight(LParam.width);
            LParam.height = calHeight(LParam.height);

        } else if (tempParam instanceof AbsListView.LayoutParams) {
            AbsListView.LayoutParams LParam = (AbsListView.LayoutParams) tempParam;
            LParam.width = calHeight(LParam.width);
            LParam.height = calHeight(LParam.height);
        } else {
            Log.e("test", "no resize functions");
        }

        int left = calHeight(tempView.getPaddingLeft());
        int right = calHeight(tempView.getPaddingRight());
        int top = calHeight(tempView.getPaddingTop());
        int bottom = calHeight(tempView.getPaddingBottom());
        tempView.setPadding(left, top, right, bottom);
    }

    private int calWidth(int getwidth) {
        if (getwidth > 0) {
            return getwidth * disPlayWidth / 1080;
        } else {
            return getwidth;
        }
    }

    private int calHeight(int getheight) {
        if (getheight > 0) {
            return getheight * disPlayHeight / 1920;
        } else {
            return getheight;
        }
    }
}
