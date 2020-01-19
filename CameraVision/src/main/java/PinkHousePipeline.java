import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class PinkHousePipeline implements IBallPipeline {
    private PinkHouseGripPipeline pipeline = new PinkHouseGripPipeline();

    public void process(Mat source0) {
        pipeline.process(source0);
    }
    public MatOfKeyPoint findBlobsOutput() {
        return pipeline.findBlobsOutput();
    }
    public String getColor() {
        MatOfKeyPoint mkp = pipeline.findBlobsOutput();
        return "*";
    }

    @Override
    public String getInfo() {
        List<MatOfPoint> mps = pipeline.findContoursOutput();
        if(mps!=null && mps.size()>0){
            Rect r = Imgproc.boundingRect(mps.get(0));
            return "("+r.x+","+r.y+" "+r.width+"x"+r.y+")";
        }
        return "(none)";
    }

    

}