import java.util.Arrays;

public class BruteCollinearPoints {

    private int numOfSegments;
    private Node first;

    private class Node {
        LineSegment item;
        Node next;
    }

    
    public BruteCollinearPoints(Point[] points) {
        
        if (points == null) throw new IllegalArgumentException();

        int N = points.length;
        Point[] copy = new Point[N];

        for (int i = 0; i < N; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            copy[i] = points[i];
        }

        Arrays.sort(copy);

        numOfSegments = 0;
        first = null;
        
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                double slope_j = copy[i].slopeTo(copy[j]);
                if (slope_j == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                for (int k = j+1; k < N; k++) {
                    double slope_k = copy[i].slopeTo(copy[k]);
                    for (int l = k+1; l < N; l++) {
                        if (slope_j == slope_k &&
                            slope_j == copy[i].slopeTo(copy[l])   )
                            {
                                numOfSegments++;

                                LineSegment segment = new LineSegment(copy[i], copy[l]);
                                Node newFirst = new Node();
                                newFirst.item = segment;
                                newFirst.next = first;
                                first = newFirst;
                            }
                    }
                }
            }
        }


    } 

    public int numberOfSegments() {
        return numOfSegments;
    }

    public LineSegment[] segments()  {
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