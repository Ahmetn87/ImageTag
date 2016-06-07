package akilus.com.imagetagging;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import akilus.com.imagetagger.ImageTagger;
import akilus.com.imagetagger.Tag;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    ImageTagger tagger;
    Button btnDelete;
    TextView lblTagDesc;
    List<Tag> tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagger = (ImageTagger)findViewById(R.id.imgTag);
        btnDelete = (Button)findViewById(R.id.delete_tag);
        lblTagDesc = (TextView)findViewById(R.id.lbl_tag_desc);
        tagList = new ArrayList<>();
        Tag tag1 = new Tag(733.63f,674.41f,"Some Brand");
        Tag tag2 = new Tag(409.35f,1049.58f,"Bag");

        tagList.add(tag1);
        tagList.add(tag2);

        tagger.setTagList(tagList);

        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tag_description_dialog);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        final EditText tagDescription = (EditText)dialog.findViewById(R.id.txtTagDescription);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tag currentTag = tagger.getLastTag();
                currentTag.setTagDescription(tagDescription.getText().toString());
                Log.w(TAG, "setTagDescription: "+currentTag.getTagDescription() );
                tagDescription.setText(null);
                dialog.dismiss();
                updateLabel();
            }
        });

        //dialog.getWindow().setLayout(400,400);

        tagger.showTagDescriptionDialog(dialog);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagger.deleteLastTag();
                updateLabel();
            }
        });

        updateLabel();

    }

    private void updateLabel()
    {
        String desc = "";

        for (Tag t : tagList) {
            //lblTagDesc.setText(String.format("* %s",t.getTagDescription()));
            desc += String.format(" %s %s",t.getTagNumber(),t.getTagDescription());
        }
        lblTagDesc.setText(desc);
    }

}

// 733.63403 674.41406 Gucci mont
// 392.34375 1248.6914 Zara canta
/**
 * private void dialogShow() {
 Dialog d = new Dialog(PaymentActivity.this);
 d.requestWindowFeature(Window.FEATURE_NO_TITLE);
 d.setContentView(R.layout.popup_other_amount);
 d .getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
 d.show();

 Window window = d.getWindow();
 window.setLayout(400, 500);
 new CustomAmountDialog(d, currentNeedy, PaymentActivity.this);

 }
 *
 *
 *
 * */