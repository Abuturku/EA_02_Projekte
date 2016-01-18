/**
 * Created by Jan.Rissmann on 18.01.2016.
 */
public class Range {

    double min;

    double max;

    Range(double min, double max){
        this.min = min;
        this.max = max;
    }

    public boolean inRange(double value){
        if(value >= min && value <= max) return true;
        return false;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }


}
