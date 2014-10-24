package collision;

import collision.shapes.Shape;
import math.Vector2;

public class CollisionDetection {


    /**
     *
     * @param s1 The collider
     * @param s2 the colidie
     * @return
     */
    public static Vector2 collision(Shape s1, Shape s2) {
        Vector2[] axes1 = s1.getAxes();
        Vector2[] axes2 = s2.getAxes();
        Vector2 smallest = null;
        double overlap = 100000000;

        for (int i = 0; i < axes1.length; i++) {
            Vector2 axis = axes1[i];

            Projection p1 = s1.project(axis);
            Projection p2 = s2.project(axis);

            if (!p1.isOverlapping(p2)) return new Vector2(0, 0);
            else {
                double o = p1.getOverlap(p2);
                if (o < overlap) {
                    overlap = o;
                    smallest = axis;
                }
            }

        }

        for (int i = 0; i < axes2.length; i++) {
            Vector2 axis = axes2[i];

            Projection p1 = s1.project(axis);
            Projection p2 = s2.project(axis);

            if (!p1.isOverlapping(p2)) return new Vector2(0, 0);
            else {
                double o = p2.getOverlap(p1);
                if (o < overlap) {
                    overlap = o;
                    smallest = axis;
                }
            }
        }

        Vector2 V = smallest.times((float) overlap);
        Vector2 A = s1.getCenter();
        Vector2 B = s2.getCenter();
        Vector2 AB = A.minus(B);
        if (V.dot(AB.normalise()) >= 0) {
            V.negate();
        }
        return V;
    }
}
