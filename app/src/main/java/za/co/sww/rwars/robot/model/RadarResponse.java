package za.co.sww.rwars.robot.model;

import java.util.List;

/**
 * Response from a radar scan, detailing detections.
 */
public class RadarResponse {
    private int range;
    private List<Detection> detections;

    public RadarResponse() {
    }

    public RadarResponse(int range, List<Detection> detections) {
        this.range = range;
        this.detections = detections;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public List<Detection> getDetections() {
        return detections;
    }

    public void setDetections(List<Detection> detections) {
        this.detections = detections;
    }
}
