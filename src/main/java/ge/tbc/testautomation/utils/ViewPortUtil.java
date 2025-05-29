package ge.tbc.testautomation.utils;


import com.microsoft.playwright.Page;

public class ViewPortUtil {

        public static void setViewportSize(Page page, int width, int height) {
            page.setViewportSize(width, height);
        }

    }


