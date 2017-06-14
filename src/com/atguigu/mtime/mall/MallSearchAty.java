package com.atguigu.mtime.mall;

import com.atguigu.mtime.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MallSearchAty extends Activity implements OnClickListener {

	private ImageView background;
	private RelativeLayout back;
	private Button search;
	private EditText search_content;
	private ImageButton cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mall_search);

		getViews();
		setListeners();

	}

	private void setListeners() {
		back.setOnClickListener(this);
		search.setOnClickListener(this);
		cancel.setOnClickListener(this);

		search_content.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				cancel.setVisibility(View.VISIBLE);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void getViews() {
		background = (ImageView) findViewById(R.id.background);
		background.getBackground().setAlpha(255);
		back = (RelativeLayout) findViewById(R.id.back);
		search = (Button) findViewById(R.id.search);
		search_content = (EditText) findViewById(R.id.search_content);
		cancel = (ImageButton) findViewById(R.id.cancel);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.search:
			String content = search_content.getText().toString();
			Intent intent = new Intent(MallSearchAty.this, BrowserNoTitleActivity.class);
			intent.putExtra("url", "http://m.mtime.cn/#!/commerce/list/?q=" + content);
			startActivity(intent);
			finish();
			break;

		case R.id.cancel:
			search_content.setText("");
			cancel.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}
}
