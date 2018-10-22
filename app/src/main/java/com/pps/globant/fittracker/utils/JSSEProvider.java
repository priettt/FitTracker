package com.pps.globant.fittracker.utils;

import java.security.AccessController;
import java.security.Provider;

public class JSSEProvider extends Provider {
    public static final String NAME = "HarmonyJSSE";
    public static final double VERSION = 1.0;
    public static final String INFO = "HarmonyJSSE Provider";
    public static final String SSLCONTEXT_TLS = "SSLContext.TLS";
    public static final String ORG_APACHE_HARMONY_XNET_PROVIDER_JSSE_SSLCONTEXT_IMPL = "org.apache.harmony.xnet.provider.jsse.SSLContextImpl";
    public static final String ALG_ALIAS_SSLCONTEXT_TLSV1 = "Alg.Alias.SSLContext.TLSv1";
    public static final String TLS = "TLS";
    public static final String KEY_MANAGER_FACTORY_X509 = "KeyManagerFactory.X509";
    public static final String ORG_APACHE_HARMONY_XNET_PROVIDER_JSSE_KEY_MANAGER_FACTORY_IMPL = "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl";
    public static final String TRUST_MANAGER_FACTORY_X509 = "TrustManagerFactory.X509";
    public static final String ORG_APACHE_HARMONY_XNET_PROVIDER_JSSE_TRUST_MANAGER_FACTORY_IMPL = "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl";

    public JSSEProvider() {
        super(NAME, VERSION, INFO);
        AccessController.doPrivileged(new java.security.PrivilegedAction<Void>() {
            public Void run() {
                put(SSLCONTEXT_TLS,
                        ORG_APACHE_HARMONY_XNET_PROVIDER_JSSE_SSLCONTEXT_IMPL);
                put(ALG_ALIAS_SSLCONTEXT_TLSV1, TLS);
                put(KEY_MANAGER_FACTORY_X509,
                        ORG_APACHE_HARMONY_XNET_PROVIDER_JSSE_KEY_MANAGER_FACTORY_IMPL);
                put(TRUST_MANAGER_FACTORY_X509,
                        ORG_APACHE_HARMONY_XNET_PROVIDER_JSSE_TRUST_MANAGER_FACTORY_IMPL);
                return null;
            }
        });
    }
}
