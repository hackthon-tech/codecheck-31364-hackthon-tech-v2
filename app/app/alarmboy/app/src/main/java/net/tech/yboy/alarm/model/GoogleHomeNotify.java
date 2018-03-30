package net.tech.yboy.alarm.model;

import android.support.v4.app.DialogFragment;
import android.util.Log;

import net.tech.yboy.alarm.view.dialog.CheckListDialog;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import tutorial.CastMessageOuterClass;

/**
 * Created by manabu on 2018/03/21.
 */

public class GoogleHomeNotify {

    public interface NotifyListener {
        public void onSuccess();
        public void onFail();
    }

    NotifyListener mListener;

    public void set(GoogleHomeNotify.NotifyListener listener) {
        mListener = listener;
    }

    public void notifyVoice(String ip, String voice, boolean music) {

        boolean musicPlay = false;
        InputStream is = null;
        OutputStream os = null;
        Socket socket = null;
        try {
            InetSocketAddress address;
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new X509TrustAllManager() }, new SecureRandom());
            socket = sc.getSocketFactory().createSocket();
            address = new InetSocketAddress(ip, 8009);
            socket.connect(address);
            is = socket.getInputStream();
            os = socket.getOutputStream();
            final OutputStream finalOs = os;

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        // コネクト
                        CastMessageOuterClass.CastMessage castMessage =
                                CastMessageOuterClass.
                                        CastMessage.newBuilder()
                                        .setDestinationId("receiver-0")
                                        .setNamespace("urn:x-cast:com.google.cast.tp.connection")
                                        .setSourceId("sender-0")
                                        .setPayloadType(CastMessageOuterClass.CastMessage.PayloadType.STRING)
                                        .setProtocolVersion(CastMessageOuterClass.CastMessage.ProtocolVersion.CASTV2_1_0)
                                        .setPayloadUtf8("{\"type\":\"CONNECT\"}")
                                        .build();

                        byte[] len = toArray(castMessage.getSerializedSize());
                        finalOs.write(len);
                        castMessage.writeTo(finalOs);

                        // 一応PING
                        CastMessageOuterClass.CastMessage castMessage2 =
                                CastMessageOuterClass.
                                        CastMessage.newBuilder()
                                        .setDestinationId("receiver-0")
                                        .setNamespace("urn:x-cast:com.google.cast.tp.heartbeat")
                                        .setSourceId("sender-0")
                                        .setPayloadType(CastMessageOuterClass.CastMessage.PayloadType.STRING)
                                        .setProtocolVersion(CastMessageOuterClass.CastMessage.ProtocolVersion.CASTV2_1_0)
                                        .setPayloadUtf8("{\"type\":\"PING\"}")
                                        .build();

                        byte[] len2 = toArray(castMessage2.getSerializedSize());
                        finalOs.write(len2);
                        castMessage2.writeTo(finalOs);

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        CastMessageOuterClass.CastMessage castMessage4 =
                                CastMessageOuterClass.
                                        CastMessage.newBuilder()
                                        .setDestinationId("receiver-0")
                                        .setNamespace("urn:x-cast:com.google.cast.receiver")
                                        .setSourceId("sender-0")
                                        .setPayloadType(CastMessageOuterClass.CastMessage.PayloadType.STRING)
                                        .setProtocolVersion(CastMessageOuterClass.CastMessage.ProtocolVersion.CASTV2_1_0)
                                        .setPayloadUtf8("{ \"type\" : \"LAUNCH\", \"appId\" : \"CC1AD845\", \"requestId\" : 5 }")
                                        .build();

                        byte[] len4 = toArray(castMessage4.getSerializedSize());
                        finalOs.write(len4);
                        castMessage4.writeTo(finalOs);
                    } catch (Throwable e) {
                    }
                }
            }).start();

            int i = 0;
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            int count = 0;
            byte[] number = new byte[4];

            boolean result = false;

            while ((i = is.read()) >= 0) {
                number[count] = (byte)i;
                count++;
                if (count == 4) {
                    count = 0;
                    int l = fromArray(number);
                    byte[] se = new byte[l];
                    is.read(se);

                    String str = new String(se);
                    String sessionId = null;
                    String transportId = null;

                    boolean ping = false;

                    {
                        Pattern p = Pattern.compile("\"PING\"");
                        Matcher m = p.matcher(str);

                        if (m != null) {

                            while (m.find()) {
                                String group = m.group();
                                ping = true;
                            }
                        }
                    }

                    {
                        Pattern p = Pattern.compile("\"sessionId\":\".*\"");
                        Matcher m = p.matcher(str);

                        if (m != null) {

                            while (m.find()) {
                                String group = m.group();
                                group = group.replace("\"sessionId\":\"", "");
                                String a = "";
                                for (int y = 0; y < group.length(); y++) {
                                    char aaa = group.charAt(y);
                                    if (aaa == '\"') {
                                        break;
                                    } else {
                                        a = a + aaa;
                                    }
                                }
                                sessionId = a;
                            }
                        }
                    }

                    {
                        Pattern p = Pattern.compile("\"transportId\":\".*\"");
                        Matcher m = p.matcher(str);

                        if (m != null) {

                            while (m.find()) {
                                String group = m.group();
                                group = group.replace("\"transportId\":\"", "");
                                String a = "";
                                for (int y = 0; y < group.length(); y++) {
                                    char aaa = group.charAt(y);
                                    if (aaa == '\"') {
                                        break;
                                    } else {
                                        a = a + aaa;
                                    }
                                }
                                transportId = a;
                            }
                        }
                    }

                    if (ping) {
                        {
                            CastMessageOuterClass.CastMessage castMessage2 =
                                    CastMessageOuterClass.
                                            CastMessage.newBuilder()
                                            .setDestinationId("receiver-0")
                                            .setNamespace("urn:x-cast:com.google.cast.tp.heartbeat")
                                            .setSourceId("sender-0")
                                            .setPayloadType(CastMessageOuterClass.CastMessage.PayloadType.STRING)
                                            .setProtocolVersion(CastMessageOuterClass.CastMessage.ProtocolVersion.CASTV2_1_0)
                                            .setPayloadUtf8("{\"type\":\"PONG\"}")
                                            .build();

                            byte[] len2 = toArray(castMessage2.getSerializedSize());
                            finalOs.write(len2);
                            castMessage2.writeTo(finalOs);
                        }
                    }

                    if (sessionId != null && transportId != null && result) {
                        // 終了
                        if (mListener != null) {
                            mListener.onSuccess();
                        }

                        return;
                    }

                    if (sessionId != null && transportId != null && !result) {

                        {
                            if (musicPlay) {
                                if (!str.contains("Ready To Cast")) {
                                    continue;
                                }
                                musicPlay = false;
                                music = false;
                            }
                        }

                        {
                            CastMessageOuterClass.CastMessage castMessage =
                                    CastMessageOuterClass.
                                            CastMessage.newBuilder()
                                            .setDestinationId(transportId)
                                            .setNamespace("urn:x-cast:com.google.cast.tp.connection")
                                            .setSourceId("sender-5")
                                            .setPayloadType(CastMessageOuterClass.CastMessage.PayloadType.STRING)
                                            .setProtocolVersion(CastMessageOuterClass.CastMessage.ProtocolVersion.CASTV2_1_0)
                                            .setPayloadUtf8("{\"type\":\"CONNECT\"}")
                                            .build();

                            byte[] len = toArray(castMessage.getSerializedSize());
                            finalOs.write(len);
                            castMessage.writeTo(finalOs);
                        }
                        {
                            if (music && !musicPlay) {
                                CastMessageOuterClass.CastMessage castMessage3 =
                                        CastMessageOuterClass.
                                                CastMessage.newBuilder()
                                                .setDestinationId(transportId)
                                                .setNamespace("urn:x-cast:com.google.cast.media")
                                                .setSourceId("sender-5")
                                                .setPayloadType(CastMessageOuterClass.CastMessage.PayloadType.STRING)
                                                .setProtocolVersion(CastMessageOuterClass.CastMessage.ProtocolVersion.CASTV2_1_0)
                                                .setPayloadUtf8("{ \"type\" : \"LOAD\", \"requestId\" : 5, \"media\": {  \"contentId\" : \"https://soundeffect-lab.info/sound/anime/mp3/piano1.mp3" + "\",  \"streamType\" : \"BUFFERED\", \"contentType\" : \"audio/mp3\" }}")
                                                .build();

                                byte[] len3 = toArray(castMessage3.getSerializedSize());
                                finalOs.write(len3);
                                castMessage3.writeTo(finalOs);
                                musicPlay = true;
                            }
                        }
                        {

                            if (!musicPlay) {

                                CastMessageOuterClass.CastMessage castMessage3 =
                                        CastMessageOuterClass.
                                                CastMessage.newBuilder()
                                                .setDestinationId(transportId)
                                                .setNamespace("urn:x-cast:com.google.cast.media")
                                                .setSourceId("sender-5")
                                                .setPayloadType(CastMessageOuterClass.CastMessage.PayloadType.STRING)
                                                .setProtocolVersion(CastMessageOuterClass.CastMessage.ProtocolVersion.CASTV2_1_0)
                                                .setPayloadUtf8("{ \"type\" : \"LOAD\", \"requestId\" : 5, \"media\": {  \"contentId\" : \"https://translate.google.com/translate_tts?ie=UTF-8&tl=ja&client=tw-ob&q=" + voice + "\",  \"streamType\" : \"BUFFERED\", \"contentType\" : \"audio/mp3\" }}")
                                                .build();

                                byte[] len3 = toArray(castMessage3.getSerializedSize());
                                finalOs.write(len3);
                                castMessage3.writeTo(finalOs);
                                result = true;
                            }
                        }

                    }
                }
            }
        } catch (Throwable e) {

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

        if (mListener != null) {
            mListener.onFail();
        }

    }

    private byte[] toArray(int value) {
        return new byte[] {
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value };
    }

    private int fromArray(byte[] payload) {
        return payload[0] << 24 | (payload[1] & 0xFF) << 16 | (payload[2] & 0xFF) << 8 | (payload[3] & 0xFF);
    }

}
