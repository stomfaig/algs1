import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class PointSET {

    private SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }
    public boolean isEmpty() {
        return points.isEmpty();
    }
    public int size() {
        return points.size();
    }
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> within_range = new ArrayList<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) within_range.add(p);
        }
        return within_range;
    }
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        double min_dist = Double.MAX_VALUE;
        Point2D closes_point = null;
        for (Point2D that : points) {
            double dist = p.distanceTo(that);
            if (dist < min_dist) {
                closes_point = that;
                min_dist = dist;
            }
        }
        return closes_point;
    }

    public static void main(String[] args) {
        
    }
}