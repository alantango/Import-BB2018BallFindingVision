import org.opencv.core.*;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;
import java.util.concurrent.*;

/**
 * This class performs the processing and resulting action to a given
 * ball pipeline.
 */
public class ImageProcessor {
    private IBallPipeline pipeline;
    private INetworkTableWriter networkTableWriter;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Mat outputImage = new Mat();
    private Future<?> processAsyncFuture;

    /**
     * ImageProcessor requires a pipeline to process and a network table writer to write
     * results to.
     * @param pipeline              The pipeline to process
     * @param networkTableWriter    A network table writer to send results to
     */
    public ImageProcessor(IBallPipeline pipeline, INetworkTableWriter networkTableWriter) {
        if (pipeline == null) {
            throw new IllegalArgumentException();
        }
        this.pipeline = pipeline;
        this.networkTableWriter = networkTableWriter;
        this.processAsyncFuture = null;
    }

    /**
     * Process an image asynchronously.  Call awaitProcessCompletion to wait for completion.
     * You can only process one image at a time.
     * @param inputImage    The image to process
     */
    public void processAsync(Mat inputImage)
    {
        if (processAsyncFuture != null) {
            throw new IllegalAccessError("Only one process can be awaited at a time.");            
        }
        // Hold on the the future...use the awaiter to wait for completion
        processAsyncFuture = executor.submit(() -> {
            // Apply the pipeline to the image.
            pipeline.process(inputImage);

            // Update network table
            networkTableWriter.write();
            return null;
        });
    }

    /**
     * Await an image process async call to finish.
     */
    public void awaitProcessCompletion() {
        try {
            processAsyncFuture.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            throw new IllegalAccessError("You must call processAsync first before awaiting completion.");
        }
        // Reset our future
        processAsyncFuture = null;
    }

    public Mat annotate(Mat inputImage){
        return annotate(inputImage, false);
    }

    /**
     * Annotate an image of found balls with circles and labels.
     * @param inputImage    The image to annotate.
     * @return              The annotated image.
     */
    public Mat annotate(Mat inputImage, boolean withContour) {

        Scalar drawColor = new Scalar(20,30,254);

        // Write a processed image that you want to restream
        // This is a marked up image of what the camera sees
        
        Features2d.drawKeypoints(
            inputImage, 
            pipeline.findBlobsOutput(), 
            outputImage, 
            // new Scalar(2,254,255),              // yellowish circle 
            drawColor,              // yellowish circle 
            Features2d.DRAW_RICH_KEYPOINTS);    // draws a full-sized circle around found point(s)
        
        // Ident balls on image
        for (KeyPoint k : pipeline.findBlobsOutput().toArray()) {
                        //pipeline.getInfo(), 
            Imgproc.putText(outputImage, pipeline.getColor(),
                        k.pt, Core.FONT_HERSHEY_SIMPLEX, .65, drawColor);
        }
        
        if(withContour){

            Imgproc.drawContours(outputImage, pipeline.getCoutourMatPoints(), -1, drawColor, 2);
            
            Rect r = pipeline.getRectOfContour();
            if(r==null){
                return outputImage;
            }
            
            String rectInfo = "contour TL: (" + r.x+", "+r.y+"), size: "+r.width+" X "+r.height+")";
            Imgproc.putText(outputImage, rectInfo,  new Point(20,20), Core.FONT_HERSHEY_SIMPLEX, .65, new Scalar(255,255,255));
            Imgproc.rectangle(outputImage, new Point(r.x, r.y), 
                                new Point(r.x+r.width, r.y+r.height), 
                                drawColor);

            Imgproc.putText(outputImage, "* [" + (r.x+r.width/2) + ", " + (r.y+r.height/2) + "]",
                    new Point(r.x+r.width/2, r.y+r.height/2), Core.FONT_HERSHEY_SIMPLEX, .55, drawColor);

        }
        return outputImage;
    }
}