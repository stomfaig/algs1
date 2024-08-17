import java.util.Arrays;

public class FastCollinearPoints {

    private int numOfSegments;
    private Node first;

    private class Node {
        LineSegment item;
        Node next;
    }

    private class PointAndSlope implements Comparable<PointAndSlope> {
        Point item;
        double slope;

        public int compareTo(PointAndSlope that) {
            if (that.slope == this.slope) return this.item.compareTo(that.item);
            if (this.slope > that.slope) return 1;
            return -1;
        }
    }
     
    public FastCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();

        int N = points.length;
        numOfSegments = 0;
        first = null;

        for (int i = 0; i < N; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        for (int p = 0; p < N; p++) {
            Point base = points[p];
            PointAndSlope[] slopes = new PointAndSlope[N];

            for (int q = 0; q < N; q++) {
                PointAndSlope pas = new PointAndSlope();
                pas.item = points[q];
                pas.slope = base.slopeTo(points[q]);

                if (pas.slope == Double.NEGATIVE_INFINITY && p != q) throw new IllegalArgumentException();

                slopes[q] = pas;
            }

            Arrays.sort(slopes);

            int start = 0;
            double slope = Double.NEGATIVE_INFINITY;

            for(int q = 1; q < N; q++) {
                if (slopes[q].slope != slope) {
                    if (q - start >= 3 && points[p].compareTo(slopes[start].item) < 0) {
                        numOfSegments++;
                        LineSegment segment = new LineSegment(points[p], slopes[q-1].item);
                        Node node = new Node();
                        node.item = segment;
                        node.next = first;
                        first = node;
                    }
                    start = q;
                    slope = slopes[q].slope;
                }
            }
            if (N-start >= 3 && points[p].compareTo(slopes[start].item) < 0) { // some order condition
                numOfSegments++;
                
                LineSegment segment = new LineSegment(points[p], slopes[N-1].item);
                Node node = new Node();
                node.item = segment;
                node.next = first;
                first = node;

                if (start == 0) break;
            }
        }


    }    
    public int numberOfSegments() {
        return numOfSegments;

    }
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[numOfSegments];
        int i = 0;
        Node current = first; 
        while(current != null) {
            segments[i++] = current.item;
            current = current.next;
        }

        return segments;
    }
}