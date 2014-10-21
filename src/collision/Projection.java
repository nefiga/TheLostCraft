package collision;

public class Projection {

    float minimum, maximum;

    public Projection(float minimum, float maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public boolean isOverlapping(Projection p) {
        return !(this.minimum >= p.maximum || this.maximum <= p.minimum);
    }

    public float getOverlap(Projection p) {
        if (this.minimum > p.minimum && this.minimum < p.maximum)
            return p.maximum - this.minimum;
        if (this.maximum > p.minimum && this.maximum < p.maximum)
            return this.maximum - p.minimum;
        return 1000;
    }
}
