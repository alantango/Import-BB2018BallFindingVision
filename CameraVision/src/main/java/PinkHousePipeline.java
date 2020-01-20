import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
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

    public List<MatOfPoint> getCoutourMatPoints(){
        List<MatOfPoint> mps = pipeline.findContoursOutput();
        return mps;
    }
    
    Rect rect;

    @Override
    public String getInfo() {
        List<MatOfPoint> mps = pipeline.findContoursOutput();
        if(mps!=null && mps.size()>0){
            List<Point> allPoints = new ArrayList<>();
            for(MatOfPoint mp : mps){
                allPoints.addAll(mp.toList());
            }
            Rect r = Imgproc.boundingRect(new MatOfPoint(allPoints.toArray(new Point[0])));
            this.rect = r;
            return "contour: (" + r.x+", "+r.y+"), size: "+r.width+" X "+r.height+")";
        }
        return "(none)";
    }

    public Rect getRectOfContour(){
        return this.rect;
    }

    

}