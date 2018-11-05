package com.tianjistar.helppersion.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/5/22.
 * APP常量
 */

public class Constants {
    public final static File ROOT_FILE = new File(Environment.getExternalStorageDirectory(), "/天轮");/*文件存储根目录*/
    public final static File Find_SCAN_FILE = new File(Environment.getExternalStorageDirectory(), "/天轮");/*文件存储根目录*/

    public static final String FILE_DATA_ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/天轮/";
    public static final String FILE_DATA_PICTURE_PATH = FILE_DATA_ROOT_PATH + "picture/";
    public static final String FILE_DATA_AUDIO_PATH = FILE_DATA_ROOT_PATH + "audio/";
    public static final String FILE_DATA_AVATAR_PATH = FILE_DATA_ROOT_PATH + "avatar/";

    public static final String FILE_IMG_PATH = FILE_DATA_ROOT_PATH + "img";

    public static final String SERVER_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwRJT8MwFTfB+qOngFjxJ" +
            "WtqfECknF5lT/XtxPG9dZBkNdzF3T+7rSKgbtyE1pSIFiq3KRw4WZ9rjtYbhpA5g" +
            "074hCpbuvqbnCh1cS2pFPxdLaHs/oXaJH8Y5cqYBWsvPBXea6L/wODVPMXxYlA9C" +
            "eNyO0iPO6p9uIuObSOQrTb5hiEyNwBP+j4Xx1XvyLx+XsmtcSrUCrynmZENvV8ZF" +
            "2w8aKJDb73pMssVOMwqYYbAFSWJlvWMzCI7jXt0vHhM7S/Q6Hoq4qf2oMnurDpK9" +
            "aoA5XjnMfHZmn/QyGO5v2FyZcrKhMzACzOkEvXWQlbR/q9jM1IuG+SpGmKS9buko" +
            "wQIDAQAB" ;//服务器公钥

//客户端私钥
    public static final String CLIEND_PRIVATE_KEY="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCP1jrDQEG5YXmQ1XPEE9u2r22D4s3ihJ/6Xuc/U7zo3N4+JP373uK0AHtjxvX02dKEoFb77jINMa1KHKD0zdrQTeuSdbtv4MCvFzFeDYUz3NJZRf4F5kMxSPf3AAwzO7rC8MoiTBfxDBNtZAGJuHspfp2+Wv/L50XqJ83WtarhgVIZII6CtfML1ujs0ant+0n0qD1e9mk/IJRn+esxW52DDGIa8Dv2GfwrvbmRtm2j/VDfnKI65LVEuLyIQb4BXj1w8s5TTbTaiWP7pW+noawbMnh2ZNbdSTDHUFO4is0yFhZIhKFXTZeSRo6G+GwyR+FiR3kzN7nin1UYsDRWXCULAgMBAAECggEALGkd7alKYb2p9kEhpFnpTeeubbk6joUajAh/Z/sn6dJAQHnVfc4cuy0m6/DDgl069b43/UNXauF6utZKECv7MrPMV4VIYUvI0obbtEmCZjf5Lp1AyHuHJZF9FmOKYMtBZ+1Gy/4oqkFrQixNmUM/q9kvRVX9guyelqTBllizMTrwO62Hj8VMQHs+tJiHDfmMwp8ARyNnB6eQVdk+pLYhdXcjb06Fsusq1ezdZycr5+aigmqFzPcjkQ/n320I8r7fIk2EcgGaD7hs3auxDke0/UxCBHjm7AbXqwrnQGn4g7X6+AmoR23HPW7RfHwJ1XHIeRjC9uBZX7BXyY/qWVhGWQKBgQDZgIBezdM+t1r1GPaZnVS1VzaGRPZJm2Q3aCZHj3iGm2bkpQizu9gcOjTAi8zX1lnzXTnqbLIFn07THNy0BY/LDogGAQev3BHaAkqelPsoR1Tv2fI55n+AK++zSJND7829s++rIhmmg5Niw5RyqTS5Xg8J3F8Jub1CIN+vw2dUtwKBgQCpS8wO5dkaDLTxxHTBt/D5StI/DRmjpJ0nDHu+eqU8IVd4cet3dX4cZzJE16bftiaBAiN1YTUYhfTcw5NjiFo+52Zbk5EHSt/7JtrGn4nx3nOP3R9WROmznpItpc7p4Zt+pegDio+RHp1StoE7uJ+ZltI/zND3txm4qfY+zG+mTQKBgHjxRXByDxb+qetEGEvO+n1jo4QB8Nl5OL6+gRqloqVm4E4fnrkiqMb5br/qfNs/7ACxRDUgf9U0goZNtEO+G/kTeXutMvMM8jhBsfCAczAwigBdNp4Pl7JTvBP7NTg/ri3gUlLcwVqgK2Jt0qv3fsPck4wuKAKmb6DH2tpHU7v9AoGAS9HlftqTw6XjN8/7VnLx2kQXKyPL0s07kKx3kxRMgkSQbyHeCe813rSc/9XxJ4nlbqutg7lYVTZRNrnkfZQojrmFzwek0TZIX6vEGWaEGi29ZbXrffB5FfezkfwLUhW3VR4qbcOVjrG+Vbfk4wFJgTjZyrcI85guTdTYssFOU5UCgYApCdlczk1K7ZOoV5MGiIV3HifrP3eCnvyzfyvzzGpn1S8RqkA8yJJ7NN/uukAW3wrKmMMu2xcdl23aTUyPUrlUMqf/zIqFKzEf6iVerV9Gh0Ddz5QP+YSq6/QWw/JfATuJx+an0nG1gI719+AXKURZcRIyLtC8ZuTBlN0ixkZzvw==";//客户端私钥

}
