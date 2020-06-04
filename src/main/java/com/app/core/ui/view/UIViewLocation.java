package com.app.core.ui.view;

import android.support.annotation.NonNull;
import android.view.View;

class UIViewLocation {

   /**
    * Determines if given points are inside view
    *
    * @param x    - x coordinate of point
    * @param y    - y coordinate of point
    * @param view - view object to compare
    * @return true if the points are within view bounds, false otherwise
    */
   public static boolean isPointInsideView(@NonNull View view, float x, float y) {
       int location[] = new int[2];
       view.getLocationOnScreen(location);
       int viewX = location[0];
       int viewY = location[1];

       return (x >= viewX && x <= (viewX + view.getWidth())) && (y >= viewY && y <= (viewY + view.getHeight()));
   }

    /**
     *
     * @param view View need have parent
     * @return int array 4 : 1.x 2.y 3.lastX 4. lastY
     */
   public static int[] locateViewAtParent(@NonNull View view) {
       int location[] = new int[4];
       location[0] = view.getLeft();
       location[1] = view.getTop();
       location[2] = location[0] + view.getWidth();
       location[3] = location[1] + view.getHeight();
       return location;
   }

    /**
     *
     * @param view View need have parent
     * @return int array 2 : 1.x 2.y 3.lastX 4. lastY
     */
    public static int[] locateViewAtRoot(@NonNull View view) {
        int location[] = new int[2];
        location[0] = getLeft(view);
        location[1] = getTop(view);
        location[2] = location[0] + view.getWidth();
        location[3] = location[1] + view.getHeight();
        return location;
    }

   private static int getLeft(View view) {
       return view.getParent() == view.getRootView() ? view.getLeft() : (view.getLeft() + getLeft((View) view.getParent()));
   }

   private static int getTop(View view) {
       return view.getParent() == view.getRootView() ? view.getTop() : (view.getTop() + getTop((View) view.getParent()));
   }
}
