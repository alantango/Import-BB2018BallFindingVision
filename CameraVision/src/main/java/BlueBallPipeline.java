import java.util.List;

import org.opencv.core.*;

public class BlueBallPipeline implements IBallPipeline {
    private BlueBallGripPipeline pipeline = new BlueBallGripPipeline();

    public void process(Mat source0) {
        pipeline.process(source0);
    }

    public MatOfKeyPoint findBlobsOutput() {
        return pipeline.findBlobsOutput();
    }

    public String getColor() {
        return "Blue";
    }

    public String getInfo() {
        // MatOfKeyPoint mkp = pipeline.findBlobsOutput();
        return getColor();
    }

    @Override
    public Rect getRectOfContour() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MatOfPoint> getCoutourMatPoints() {
        // TODO Auto-generated method stub
        return null;
    }


 
}