import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.lang.Math;

import java.util.ArrayList;

public class KdTree {

    private class Node {
        Point2D p;
        Node left;
        Node right;
        int size;
        int level;
    }

    private Node root;

    public KdTree() {
        root = null;
    }
    public boolean isEmpty() {
        return (root == null);
    }
    public int size() {
        if (isEmpty()) return 0;
        return root.size;
    }
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (contains(p)) return;

        Node newNode = new Node();
        newNode.p = p;
        newNode.left = null;
        newNode.right = null;
        newNode.size = 1;

        if (root == null) { root = newNode; return; }
        insert_from(newNode, root);
    }
    private void insert_from(Node newNode, Node n) {
        n.size++;
        if (n.level % 2 == 0) {
            if (newNode.p.x() < n.p.x()) { // newNode is _before_ n, so we go left
                if (n.left == null) {
                    newNode.level = n.level + 1;
                    n.left = newNode;
                }
                else insert_from(newNode, n.left);
            } else { // newNode is _after_ n, so we go right
                if (n.right == null) {
                    newNode.level = n.level + 1;
                    n.right = newNode;
                }
                else insert_from(newNode, n.right);
            }
        } else {
            if (newNode.p.y() < n.p.y()) { // newNode is _before_ n, so we go left
                if (n.left == null) {
                    newNode.level = n.level + 1;
                    n.left = newNode;
                }
                else insert_from(newNode, n.left);
            } else { // newNode is _after_ n, so we go right
                if (n.right == null) {
                    newNode.level = n.level + 1;
                    n.right = newNode;
                }
                else insert_from(newNode, n.right);
            }
        }
    }
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        
        Node current = root;
        int level = 0;
        while (current != null) {
            if (current.p.x() == p.x() && current.p.y() == p.y()) return true;
            if (level++ % 2 == 0) {
                if (current.p.x() <= p.x()) current = current.right; // p is after current, so we go right.
                else current = current.left;
            } else {
                if (current.p.y() <= p.y()) current = current.right;
                else current = current.left;
            }
        }
        return false;
    }
    public void draw() {
        draw_from(root);
    }
    private void draw_from(Node node) {
        node.p.draw();
        if (node.right != null) draw_from(node.right);
        if (node.left != null) draw_from(node.left);
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        return range_from(rect, root);
    }

    private ArrayList<Point2D> range_from(RectHV rect, Node n) {
        ArrayList<Point2D> within_range = new ArrayList<Point2D>();
        if (n == null) return within_range;
        if (rect.contains(n.p)) within_range.add(n.p);

        if (n.level % 2 == 0) { // check against y coord
            if(n.p.x() < rect.xmax()) within_range.addAll(range_from(rect, n.right));
            if(n.p.x() >= rect.xmin()) within_range.addAll(range_from(rect, n.left));
        } else { // check against x coord
            if(n.p.y() < rect.ymax()) within_range.addAll(range_from(rect, n.right));
            if(n.p.y() >= rect.ymin()) within_range.addAll(range_from(rect, n.left));
        }

        return within_range;
    }


    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearestFrom(p, root);
    }

    private Point2D nearestFrom(Point2D p, Node node) {
        
        double minDistance = p.distanceTo(node.p);
        Point2D minPoint = node.p;

        Point2D closest_in_subtree;

        if (node.level % 2 == 0) { // x coordinate
            Node sameSide, otherSide;
            if (p.x() < node.p.x()) { sameSide = node.left; otherSide = node.right; }
            else { sameSide = node.right; otherSide = node.left; }

            Point2D sameSideClose;
            if (sameSide != null) {
                sameSideClose = nearestFrom(p, sameSide);
                double sameSideCloseDist = p.distanceTo(sameSideClose);
                if (sameSideCloseDist < minDistance) {
                    minDistance = sameSideCloseDist;
                    minPoint = sameSideClose;
                }
            }
            Point2D otherSideClose;
            if (otherSide != null && Math.abs(node.p.x() - p.x()) < minDistance) {
                otherSideClose = nearestFrom(p, otherSide);
                double otherSideCloseDist = p.distanceTo(otherSideClose);
                if (otherSideCloseDist < minDistance) {
                    minDistance = otherSideCloseDist;
                    minPoint = otherSideClose;
                }
            }            
        } else {
            Node sameSide, otherSide;
            if (p.y() < node.p.y()) { sameSide = node.left; otherSide = node.right; }
            else { sameSide = node.right; otherSide = node.left; }

            Point2D sameSideClose;
            if (sameSide != null) {
                sameSideClose = nearestFrom(p, sameSide);
                double sameSideCloseDist = p.distanceTo(sameSideClose);
                if (sameSideCloseDist < minDistance) {
                    minDistance = sameSideCloseDist;
                    minPoint = sameSideClose;
                }
            }
            Point2D otherSideClose;
            if (otherSide != null && Math.abs(node.p.y() - p.y()) < minDistance) {
                otherSideClose = nearestFrom(p, otherSide);
                double otherSideCloseDist = p.distanceTo(otherSideClose);
                if (otherSideCloseDist < minDistance) {
                    minDistance = otherSideCloseDist;
                    minPoint = otherSideClose;
                }
            }              
        }


        return minPoint;
    }

    public static void main(String[] args) {
    }
}