package com.hunter.library.point;

/**
 * Asm will insert bytecode of {@link PointManager#pointMethod(String)} to end of some method.
 */
public class PointManager {

    private static IPointHandler iPointHandler = IPointHandler.DEFAULT;

    public static void installPointManager(IPointHandler custom) {
        PointManager.iPointHandler = custom;
    }

    public static void pointMethod(String method) {
        iPointHandler.pointMethod(method);
    }

}
