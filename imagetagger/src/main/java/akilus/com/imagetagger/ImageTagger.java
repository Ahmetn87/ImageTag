package akilus.com.imagetagger;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AhmetNM on 6/3/16.
 *
 * Image-tagger library for android
 */
public class ImageTagger extends ImageView {

    private static final String TAG = "ImageTag";

    /**
     * Whether user clicked to view or not. In each click we will deploy a tag
     */
    private boolean clicked = false;

    /**
     * also we may say number of clicked for user
     * first tag will be 1 second tag 2 and so on
     */
    private int numberOfTag = 0;


    /**
     * the size of tag
     * Default value is 40
     */
    private float tagSize = 40f;

    /**
     * the color for tag to fill
     * if colorFilled = false this color will be stroke color
     */
    private int tagColor = Color.parseColor("#000000");

    /**
     *
     * */
    private static Rect textRect;
    /**
     * Size of a text it is half of tag size
     * it is half of it couse of proportion
     */
    private int textSize;
    /**
     * textColor and size
     */
    private Paint textPaint;
    /**
     * the width of tag stroke
     */
    private float strokeWidth = 10f;

    /**
     * color tag text, which is number
     */
    private int textColor = Color.parseColor("#CC000000");

    boolean colorFilled = false;

    /**
     * There will be a scenario where u want users to tag or just show the tags on the picture
     * so if taggable true user will be able to tag, if false then only show built in tags
     */
    boolean taggable = true;

    /**
     * x coordinate of tag
     */
    float x;

    /**
     * y coordinate of tag
     */
    float y;

    /**
     *
     * */
    private Paint tagPaint;

    /**
     * The list of whole tags
     */
    List<Tag> tagList = new ArrayList<>();

    Tag currentTag;


    Dialog tagDescriptionDialog;

    String tagDescription;

    public ImageTagger(Context context) {
        super(context);
        init(context, null);
    }

    public ImageTagger(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageTagger(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageTagger(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);

        TypedArray a = context.obtainStyledAttributes(attrs, akilus.com.imagetagger.R.styleable.imageTagger);
        tagSize = a.getDimension( akilus.com.imagetagger.R.styleable.imageTagger_tagSize, tagSize);
        strokeWidth = a.getDimension(akilus.com.imagetagger.R.styleable.imageTagger_strokeWidth, strokeWidth);
        textColor = a.getColor(akilus.com.imagetagger.R.styleable.imageTagger_textColor, textColor);
        tagColor = a.getColor(akilus.com.imagetagger.R.styleable.imageTagger_tagColor, tagColor);
        colorFilled = a.getBoolean(akilus.com.imagetagger.R.styleable.imageTagger_colorFilled, colorFilled);
        taggable = a.getBoolean(akilus.com.imagetagger.R.styleable.imageTagger_taggable, taggable);
        a.recycle();

        tagPaint = new Paint();
        tagPaint.setAntiAlias(true);
        tagPaint.setColor(tagColor);
        if (colorFilled) tagPaint.setStyle(Paint.Style.FILL);
        else tagPaint.setStyle(Paint.Style.STROKE);

        tagPaint.setStrokeWidth(strokeWidth);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);

        textPaint.setColor(textColor);

        textSize = (int) (tagSize);
        textPaint.setTextSize(textSize);


        textRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (clicked) {
            insertTag(canvas);
        } else placeTag(canvas);


    }

    /**
     * If we want users to be able to insert their own tag then use this
     */
    private void insertTag(Canvas canvas) {
        for (Tag tag : tagList) {
            canvas.drawCircle(tag.getX(), tag.getY(), tagSize, tagPaint);

            if (String.valueOf(tag.getTagNumber()).length() > 1) {
                canvas.drawText(String.valueOf(tag.getTagNumber()), tag.getX() - textSize / 2, (float) (tag.getY() + textSize / 2.5), textPaint);
                clicked = false;
                //numberOfTag ++;
            } else {
                canvas.drawText(String.valueOf(tag.getTagNumber()), tag.getX() - textSize / 4, tag.getY() + textSize / 4, textPaint);
                clicked = false;
                //numberOfTag++;
            }

        }
    }

    /**
     * If taggable is false and we have tags to show to user
     */
    private void placeTag(Canvas canvas) {
        for (Tag tag : tagList) {
            canvas.drawCircle(tag.getX(), tag.getY(), tagSize, tagPaint);

            if (String.valueOf(tag.getTagNumber()).length() > 1) {

                canvas.drawText(String.valueOf(tag.getTagNumber()), tag.getX() - textSize / 2, (float) (tag.getY() + textSize / 2.5), textPaint);

            } else {

                canvas.drawText(String.valueOf(tag.getTagNumber()), tag.getX() - textSize / 4, tag.getY() + textSize / 4, textPaint);

            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP: {
                if (!taggable) return true; // disable click event
                clicked = true;
                numberOfTag++;
                currentTag = new Tag(x, y, numberOfTag);
                tagList.add(currentTag);

                invalidate();
                if (tagDescriptionDialog != null)
                    tagDescriptionDialog.show();
                else Log.w(TAG, " Provide a dialog and get Tag description..." );

                Log.w(TAG, "onTouchEvent: " + x + " " + y);
            }
            default:
                break;

        }
        return super.onTouchEvent(event);
    }

    public void setTagSize(int tagSize) {
        this.tagSize = tagSize;
    }


    public List<Tag> getTagList() {
        return tagList;
    }

    public Tag getLastTag()
    {
        if (tagList !=null && tagList.size()>0)
        {
            return tagList.get(tagList.size()-1);
        }
        return null;
    }

    /**
     * Add bulk tag list
     * If you add your own tag, (instead of grabbing user click) to this list, it will draw all of them on OnCreate()
     * */
    public void setTagList(List<Tag> tagList) {
        if (tagList.size()>0)
        {
            for (int i = 0;i<tagList.size();i++)
            {
                tagList.get(i).setTagNumber(i+1);
                numberOfTag = i+1;
                Log.w(TAG, "setTagList: "+tagList.get(i).getTagDescription() );

            }
        }

        this.tagList = tagList;

    }
    /**
     * Add tag to tagList
     * @param tag your custom tag with specific coordinates
     * */
    public void addTag(Tag tag)
    {
        tagList.add(tag);
    }

    public void deleteLastTag()
    {
        if (tagList.size()>0)
        {
            tagList.remove(tagList.size()-1);
            numberOfTag = numberOfTag - 1;
            Log.w(TAG, "deleted and current tag number "+numberOfTag );
            invalidate();
        }
    }
    /**
     * Provide your own custom dialog, and implement the TagDialogListener interface for click event
     * @param tagDescriptionDialog your own custom dialog
     * */
    public void showTagDescriptionDialog(Dialog tagDescriptionDialog) {
        this.tagDescriptionDialog = tagDescriptionDialog;
        tagDescriptionDialog.setCanceledOnTouchOutside(false);
    }




}
