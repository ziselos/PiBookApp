package gr.mobile.zisis.pibook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;

import butterknife.BindView;
import gr.mobile.zisis.pibook.activity.base.BaseActivity;
import gr.mobile.zisis.pibook.activity.recognition.RecognitionActivity;

/**
 * Created by zisis on 912//17.
 */

public class RecognizedPageActivity extends BaseActivity {

    private final static String ARGUMENTS_PAGE_RECOGNIZED = "arguments_page_recognized";
    private String pageRecognized;

    @BindView(R.id.pageRecognizedTextView)
    AppCompatTextView pageRecognizedTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPassData();
        setContentView(R.layout.layout_page_recognized);

    }
    private void getPassData() {
        Bundle getPassDataBundle = this.getIntent().getExtras();
        if (getPassDataBundle != null) {
            pageRecognized = getPassDataBundle.getString(ARGUMENTS_PAGE_RECOGNIZED);
        }
    }

    @Override
    public void initLayout() {
        pageRecognizedTextView.setText(pageRecognized);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RecognitionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
