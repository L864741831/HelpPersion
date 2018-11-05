package com.tianjistar.helppersion.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.tianjistar.helppersion.R;


/**
 * 验证码计时器
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/16 0016 10:47
 */
public class CaptchaTimer extends CountDownTimer {

    private TextView btn_get_captcha;

    public CaptchaTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public CaptchaTimer(long millisInFuture, long countDownInterval, TextView btn_get_captcha) {
        super(millisInFuture, countDownInterval);
        this.btn_get_captcha = btn_get_captcha;
    }

    public void onFinish() {
        btn_get_captcha.setText("重发验证码");
        btn_get_captcha.setEnabled(true);
    }

    public void onTick(long millisUntilFinished) {
        //strategy1是一个TextView
        String texts = millisUntilFinished / 1000L + "s"+" 重发";//45s后再次获取
        btn_get_captcha.setText(texts);
        btn_get_captcha.setTextColor(Color.parseColor("#a3a5a8"));
        btn_get_captcha.setBackgroundResource(R.drawable.tv_bg_yzm1);
        SpannableStringBuilder builder1 = new SpannableStringBuilder(btn_get_captcha.getText().toString());
        //设置前景色为蓝色
        ForegroundColorSpan blue=new ForegroundColorSpan(Color.GRAY);
//        //改变第3-最后个字体颜色为蓝色
//        builder1.setSpan(blue,3,builder1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        btn_get_captcha.setText(builder1);
        btn_get_captcha.setEnabled(false);
    }


}