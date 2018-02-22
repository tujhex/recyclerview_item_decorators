package com.example.erychkov.mytestapplication;

import com.example.erychkov.mytestapplication.decoration.BaseAdapter;
import com.example.erychkov.mytestapplication.decoration.Rule;
import com.example.erychkov.mytestapplication.decoration.StartVerticalSectionItemDecoration;
import com.example.erychkov.mytestapplication.decoration.TopHorizontalSectionItemDecoration;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_main)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        try {
            bindRV();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void bindRV() throws ParseException {
        final List<CommentAdapterUiModelImpl> uiModels = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM");
        String dateStart;
        dateStart = "11/03/14 09:29:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "1", "text qw dqwdq wdqwd qwdqw dqw dqwdq wdq wdq wdqwdqw dqwd qdqw dqwd qwd", format.parse(dateStart), false, false));
        dateStart = "11/03/14 09:31:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "1", "text1", format.parse(dateStart), false, false));
        dateStart = "11/03/14 09:34:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "2", "text2", format.parse(dateStart), false, false));
        dateStart = "11/03/14 09:35:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "1", "text3", format.parse(dateStart), false, false));
        dateStart = "11/03/14 09:40:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "4", "text4", format.parse(dateStart), false, true));
        dateStart = "11/03/15 09:29:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "4", "text5  qw dqwdq wdqwd qwdqw dqw dqwdq wdq wdq wdqwdqw dqwd qdqw dqwd qwd", format.parse(dateStart), false, true));
        dateStart = "11/03/15 09:35:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "4", "text6", format.parse(dateStart), false, true));
        dateStart = "11/03/16 09:43:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "1", "text7", format.parse(dateStart), false, false));
        dateStart = "11/03/16 09:45:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "1", "text8", format.parse(dateStart), false, false));
        dateStart = "11/03/17 09:29:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "1", "text9", format.parse(dateStart), false, false));
        dateStart = "11/03/17 19:29:58";
        uiModels.add(new CommentAdapterUiModelImpl("", "1", "text10", format.parse(dateStart), false, false));

        BaseAdapter adapter = new BaseAdapter();
        adapter.setData(uiModels);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        final Rule date = new Rule() {
            @Override
            public boolean isSection(int position) {
                if (position == 0) return true;
                long days = TimeUnit.MILLISECONDS.toDays(uiModels.get(position).getCreationDate().getTime()) -
                    TimeUnit.MILLISECONDS.toDays(uiModels.get(position - 1).getCreationDate().getTime());
                return days > 0;
            }

            @Override
            public void bindData(View view, int position) {
                TextView textView = view.findViewById(R.id.header);
                if (textView != null) {
                    textView.setText(dateFormat.format(uiModels.get(position).getCreationDate()));
                }
            }

        };

        final Rule image = new Rule() {
            @Override
            public boolean isSection(int position) {
                return !uiModels.get(position).isMyComment() && (date.isSection(position) || !uiModels.get(position).getAuthor().equals(uiModels.get(position - 1).getAuthor()));
            }

            @Override
            public void bindData(View view, int position) {
                ImageView imageView = view.findViewById(R.id.header);
                if (imageView != null) {
                    imageView.setBackgroundColor(getResources().getColor(R.color.test));

                    int width = imageView.getWidth();
                    int height = imageView.getHeight();

                    Bitmap newImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                    Canvas c = new Canvas(newImage);
                    c.drawARGB(0xff, 0xDE,0xDF,0xAE);
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setTextSize(40);
                    c.drawText(uiModels.get(position).getAuthor(), width/2, height/2, paint);

                    imageView.setImageBitmap(newImage);
                }
            }

        };
        mRecyclerView.addItemDecoration(new TopHorizontalSectionItemDecoration(this, R.layout.date_item_decor, date, R.dimen.date_item_decor_height));
        mRecyclerView.addItemDecoration(new StartVerticalSectionItemDecoration(this, R.layout.image_item_decor, image, R.dimen.image_item_decor_width, R.dimen.side_padding));
    }
}
