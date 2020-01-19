import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class PinkHouseNetworkTableWriter extends NetworkTableWriter {
    
    public PinkHouseNetworkTableWriter(BallPipelineInterpreter interpreter, NetworkTable publishingTable){
        super(interpreter, publishingTable);
    }

    public String getBallFoundKey() {
        return "PinkHouseFound";
    }
    public String getBallCountKey() {
        return "PinkHouseCount";
    }

}