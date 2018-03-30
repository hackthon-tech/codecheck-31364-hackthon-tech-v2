package net.tech.yboy.alarm.model;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by manabu on 2018/03/21.
 */

public class GoogleHomeVoiceData {

    boolean result = false;

    public Single<Boolean> get(Context context, String voice, boolean music) {
        result = false;
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(SingleEmitter<Boolean> emitter) throws Exception {

                GoogleHomeDns dns = new GoogleHomeDns();
                dns.set(new GoogleHomeDns.DnsListener() {
                    @Override
                    public void onSuccess(String ip) {

                        if (result) {
                            return;
                        }
                        result = true;

                        GoogleHomeNotify nofity = new GoogleHomeNotify();
                        nofity.set(new GoogleHomeNotify.NotifyListener() {
                            @Override
                            public void onSuccess() {
                                emitter.onSuccess(true);
                            }

                            @Override
                            public void onFail() {
                                emitter.onError(new IOException("fail google home notify voice"));
                            }
                        });
                        nofity.notifyVoice(ip, voice, music);
                    }

                    @Override
                    public void onTimeOut() {
                        emitter.onError(new IOException("fail google home ip time out"));
                    }

                    @Override
                    public void onFail() {
                        emitter.onError(new IOException("fail google home ip"));
                    }
                });
                dns.setUp(context);
            }
        });
    }
}
