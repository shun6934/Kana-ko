package smartphoneapp_project.kanazawaapp_2017.WashiGame;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import smartphoneapp_project.kanazawaapp_2017.R;

public class Washi2Activity extends Activity  implements View.OnTouchListener {
    private ImageView kurobaView;
    private ImageView momijiView;
    private ImageView otibaView;
    private Rect rect = new Rect();
    private ImageView momijishadow;
    private ImageView otibashadow;
    private int imageoldX = 0;//ドラッグ前の画像のx座標を保持する変数
    private int imageoldY = 0;//ドラッグ前の画像のy座標を保持する変数
    private int count = 0;
    private int x = 0;
    private int y = 0;
    private Button button;
    private ViewGroup.MarginLayoutParams startmarginkuroba;
    private ViewGroup.MarginLayoutParams startmarginmomiji;
    private ViewGroup.MarginLayoutParams startmarginotiba;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washi2);
        Resources r = getResources();
        Bitmap kurobaBmp = BitmapFactory.decodeResource(r,R.drawable.kuroba);
        Bitmap momijiBmp = BitmapFactory.decodeResource(r, R.drawable.momiji1);
        Bitmap otibaBmp = BitmapFactory.decodeResource(r, R.drawable.momiji2);
        Bitmap momijikage = BitmapFactory.decodeResource(r, R.drawable.momiji1kage);
        Bitmap otibakage = BitmapFactory.decodeResource(r, R.drawable.momiji2kage);
        Button returnButton = (Button) findViewById(R.id.washi1_back_button);
        kurobaView = (ImageView) findViewById(R.id.momiji0);
        momijiView = (ImageView) findViewById(R.id.momiji1);
        otibaView = (ImageView) findViewById(R.id.momiji2);
        momijishadow = (ImageView) findViewById(R.id.momiji_Shadow1);
        momijishadow.setImageBitmap(momijikage);
        otibashadow = (ImageView) findViewById(R.id.momiji_Shadow2);
        otibashadow.setImageBitmap(otibakage);
        kurobaView.setImageBitmap(kurobaBmp);
        momijiView.setImageBitmap(momijiBmp);
        otibaView.setImageBitmap(otibaBmp);
        this.kurobaView.setOnTouchListener(this);
        this.momijiView.setOnTouchListener(this);
        this.otibaView.setOnTouchListener(this);
        startmarginkuroba = (ViewGroup.MarginLayoutParams) kurobaView.getLayoutParams();
        startmarginmomiji = (ViewGroup.MarginLayoutParams) momijiView.getLayoutParams();
        startmarginotiba = (ViewGroup.MarginLayoutParams) otibaView.getLayoutParams();
        button = (Button) findViewById(R.id.washi3_next_button);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.momiji0://黄色のクローバーのドラッグアンドドロップの動作
                // タッチした時の位置を取得
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //viewの位置を計算
                    int diffX = kurobaView.getLeft() + (x - imageoldX);
                    int diffY = kurobaView.getTop() + (y - imageoldY);
                    //viewの移動
                    kurobaView.layout(diffX, diffY, diffX + kurobaView.getWidth(),
                            diffY + kurobaView.getHeight());
                    //今回のタッチ位置を保持
                    imageoldX = x;
                    imageoldY = y;
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imageoldX = x;
                    imageoldY = y;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    kurobaView.setLayoutParams(startmarginkuroba);
                }
                break;
            case R.id.momiji1://枝分かれしている紅葉のドラッグアンドドロップの操作
                Resources r = getResources();
                Bitmap bmp2 = BitmapFactory.decodeResource(r, R.drawable.momiji1);
                //タッチしたときの位置を取得
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //viewの位置を計算
                    int diffX = momijiView.getLeft() + (x - imageoldX);
                    int diffY = momijiView.getTop() + (y - imageoldY);
                    //viewの移動
                    momijiView.layout(diffX, diffY, diffX + momijiView.getWidth()
                            , diffY + momijiView.getHeight());
                    //今回のviewの位置を保持
                    imageoldX = x;
                    imageoldY = y;
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imageoldX = x;
                    imageoldY = y;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    momijishadow.getHitRect(rect);
                    if (rect.contains(x, y)) {
                        //シルエットにドロップしたときの処理
                        momijishadow.setImageBitmap(bmp2);
                        momijiView.setVisibility(momijiView.INVISIBLE);
                        count++;
                        if (count == 2){
                            button.setVisibility(button.VISIBLE);
                        }
                    } else {
                        //シルエット以外の場所にドロップしたとき
                        momijiView.setLayoutParams(startmarginmomiji);
                    }
                }
                break;
            case R.id.momiji2://一枚の葉っぱの形をしたモミジのドラッグアンドドロップ
                //タッチしたときのviewの位置を取得
                int startX = (int) otibaView.getX();
                int startY = (int) otibaView.getY();
                Resources r2 = getResources();
                Bitmap bmp3 = BitmapFactory.decodeResource(r2, R.drawable.momiji2);
                //タッチしたviewの位置を取得
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //viewの位置を計算
                    int diffX = otibaView.getLeft() + (x - imageoldX);
                    int diffY = otibaView.getTop() + (y - imageoldY);
                    //viewの移動
                    otibaView.layout(diffX, diffY, diffX + otibaView.getWidth()
                            , diffY + otibaView.getHeight());
                    //今回のviewの位置を保持
                    imageoldX = x;
                    imageoldY = y;
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imageoldX = x;
                    imageoldY = y;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    otibashadow.getHitRect(rect);
                    if (rect.contains(x, y)) {
                        //シルエットにドロップしたときの処理
                        otibashadow.setImageBitmap(bmp3);
                        otibaView.setVisibility(otibaView.INVISIBLE);
                        count++;
                        if (count == 2){
                            button.setVisibility(button.VISIBLE);
                        }
                    } else {
                        //シルエット以外にドロップしたときの処理
                        otibaView.setLayoutParams(startmarginotiba);
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        if(event.getAction() == KeyEvent.ACTION_UP){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    //ダイアログ表示などの処理を行う時はここに記述する
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    //戻るボタンで和紙1の画面に行く処理
    public void returnClick(View view){
        Intent intent= new Intent(this,Washi1Activity.class);
        startActivity(intent);
    }
    //ゲームクリア時に出てくるボタンの処理(結果画面に進む)
    public void onClick(View view) {
      Intent intent = new Intent(this, Washi3Activity.class);
       startActivity(intent);
    }

}