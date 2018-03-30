package net.tech.yboy.alarm.model;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import net.tech.yboy.alarm.javax.jmdns.JmDNS;
import net.tech.yboy.alarm.javax.jmdns.ServiceEvent;
import net.tech.yboy.alarm.javax.jmdns.ServiceInfo;
import net.tech.yboy.alarm.javax.jmdns.ServiceListener;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

/**
 * Created by manabu on 2018/03/21.
 */

public class GoogleHomeDns {

    public interface DnsListener {
        public void onSuccess(String ip);
        public void onTimeOut();
        public void onFail();
    }

    public void set(GoogleHomeDns.DnsListener listener) {
        mListener = listener;
    }

    GoogleHomeDns.DnsListener mListener;

    boolean result = false;
    private final String SERVICE_TYPE = "_googlecast._tcp.local.";
    private JmDNS jmdns = null;
    private WifiManager.MulticastLock lock;
    public  void setUp(Context context) {

        result = false;

        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(android.content.Context.WIFI_SERVICE);
        lock = wifi.createMulticastLock("mylockthereturn");
        lock.setReferenceCounted(true);
        lock.acquire();

        try {

            String ip = Formatter.formatIpAddress(wifi.getConnectionInfo().getIpAddress());
            jmdns = JmDNS.create(InetAddress.getByName(ip));

            jmdns.setDelegate(new JmDNS.Delegate() {
                @Override
                public void cannotRecoverFromIOError(JmDNS dns, Collection<ServiceInfo> infos) {
                }
            });

            jmdns.addServiceListener(SERVICE_TYPE, new ServiceListener() {
                @Override
                public void serviceResolved(ServiceEvent ev) {
                    for (String str : ev.getInfo().getHostAddresses()) {
                        if (mListener != null) {
                            mListener.onSuccess(str);
                            result = true;
                        }
                    }
                }

                @Override
                public void serviceRemoved(ServiceEvent ev) {
                }

                @Override
                public void serviceAdded(ServiceEvent event) {
                    jmdns.requestServiceInfo(event.getType(), event.getName(), 1);
                }
            });

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }

        } catch (IOException e) {
            if (mListener != null) {
                mListener.onFail();
            }
            return;
        } finally {
            try {
                if (jmdns != null) {
                    jmdns.close();
                }
            } catch (IOException e) {
            }
            if (lock != null) {
                lock.release();
            }
        }

        if (!result && mListener != null) {
            mListener.onTimeOut();
        }

    }
}
