package akilus.com.imagetagger;

/**
 * Created by AhmetNM on 6/3/16.
 */
public class Tag {

    private static final String TAG = "Tag";
    private float x;
    private float y;
    private int tagNumber;
    private String tagDescription;


    /**
     * Custom Tag with coordinate and description
     * */
    public Tag(float x, float y, int tagNumber)
    {
        this.x = x;
        this.y= y;
        this.tagNumber = tagNumber;
    }

    public Tag(float x, float y, int tagNumber, String tagDescription)
    {
        this.x = x;
        this.y= y;
        this.tagNumber = tagNumber;
        this.tagDescription = tagDescription;
    }
    public Tag(float x, float y, String tagDescription)
    {
        this.x = x;
        this.y= y;
        this.tagNumber = tagNumber;
        this.tagDescription = tagDescription;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(int tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;

    }
}
