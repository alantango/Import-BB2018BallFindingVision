import java.util.List;

import org.opencv.core.*;

public class RedBallPipeline implements IBallPipeline {
    private RedBallGripPipeline pipeline = new RedBallGripPipeline();

    public void process(Mat source0) {
        pipeline.process(source0);
    }

    public MatOfKeyPoint findBlobsOutput() {
        return pipeline.findBlobsOutput();
    }

    public String getColor() {
        return "Red";
    }

    @Override
    public String getInfo() {
        return getColor();
    }

    @Override
    public List<MatOfPoint> getCoutourMatPoints() {
        // TODO Auto-generated method stub
        return null;
    }



}