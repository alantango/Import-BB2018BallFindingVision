import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class PinkHouseImageProcessorFactory {

    public static ImageProcessor CreateImageProcessor(NetworkTable networkTable) {
        IBallPipeline pinkHousePipeline = new PinkHousePipeline();
        return 
        new ImageProcessor(
            pinkHousePipeline, 
            new PinkHouseNetworkTableWriter(
                  new BallPipelineInterpreter(pinkHousePipeline), networkTable)
            );
    }


}