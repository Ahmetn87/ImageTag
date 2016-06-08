# ImageTag
#### Tag images as you like
<img src="https://github.com/Ahmetn87/ImageTag/blob/master/assets/f.gif" alt="Drawing" style="width: 270px;"/>

```xml
<akilus.com.imagetagger.ImageTagger
        android:id="@+id/imgTag"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".8"
        app:strokeWidth = "1dp"
        android:src="@drawable/thumb"
        app:tagColor = "#CCFFFFFF"
        app:textColor = "#CCFFFFFF"
        app:taggable = "true"
        app:colorFilled = "false"
        />
```

```java
ImageTagger tagger;
Button btnDelete;
TextView lblTagDesc;

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
```


Emphasis
Copyright 2016 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.