package com.bwei.like.yunifang.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.like.yunifang.R;
import com.bwei.like.yunifang.base.BaseActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

public class ZXingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back_image;
    private TextView include_middle_tv;
    private ImageView include_share_image;
    private ImageView iv_zXing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        initView();
        Bitmap qrBitmap = generateBitmap("御泥坊", 300, 300);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_share);
        Bitmap bitmap = addLogo(qrBitmap, logoBitmap);
        iv_zXing.setImageBitmap(bitmap);

    }

    private void initView() {
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setOnClickListener(this);
        include_middle_tv = (TextView) findViewById(R.id.include_meddim_tv);
        include_middle_tv.setText("关于我们");
        include_share_image = (ImageView) findViewById(R.id.include_share_image);
        include_share_image.setVisibility(View.VISIBLE);
        include_share_image.setOnClickListener(this);
        iv_zXing = (ImageView) findViewById(R.id.iv_zXing);
    }


    /**
     * 生成带logo二维码
     * @param
     */

    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }


    private Bitmap generateBitmap(String content,int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_image:
                finish();
                ZXingActivity.this.overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;

            //扫一扫监听
            case R.id.include_share_image:

                break;
        }
    }
}
