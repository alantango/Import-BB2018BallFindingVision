import java.util.List;

import org.opencv.core.*;

interface IBallPipeline {
    public void process(Mat source0);
    public MatOfKeyPoint findBlobsOutput();
    public String getColor();
    // public String getInfo();
    public Rect getRectOfContour();
    public List<MatOfPoint> getCoutourMatPoints();
}