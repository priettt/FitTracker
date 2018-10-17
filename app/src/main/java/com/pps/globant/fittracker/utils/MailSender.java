package com.pps.globant.fittracker.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender extends Authenticator {
    public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String SMTP = "smtp";
    public static final String MAIL_HOST = "mail.host";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String TRUE = "true";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String PORT = "465";
    public static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
    public static final String JAVAX_NET_SSL_SSLSOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    public static final String MAIL_SMTP_SOCKET_FACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
    public static final String FALSE = "false";
    public static final String MAIL_SMTP_QUITWAIT = "mail.smtp.quitwait";
    public static final String TEXT_PLAIN = "text/plain";

    static {
        Security.addProvider(new JSSEProvider());
    }

    private String mailhost;
    private String user;
    private String password;
    private Session session;

    public MailSender(String user, String password, String mailhost) {
        this.user = user;
        this.password = password;
        this.mailhost = mailhost;

        Properties props = new Properties();
        props.setProperty(MAIL_TRANSPORT_PROTOCOL, SMTP);
        props.setProperty(MAIL_HOST, mailhost);
        props.put(MAIL_SMTP_AUTH, TRUE);
        props.put(MAIL_SMTP_PORT, PORT);
        props.put(MAIL_SMTP_SOCKET_FACTORY_PORT, PORT);
        props.put(MAIL_SMTP_SOCKET_FACTORY_CLASS,
                JAVAX_NET_SSL_SSLSOCKET_FACTORY);
        props.put(MAIL_SMTP_SOCKET_FACTORY_FALLBACK, FALSE);
        props.setProperty(MAIL_SMTP_QUITWAIT, FALSE);
        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipients) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), TEXT_PLAIN));
        message.setSender(new InternetAddress(sender));
        message.setSubject(subject);
        message.setDataHandler(handler);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        Transport.send(message);
    }

    public class ByteArrayDataSource implements DataSource {
        public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
        public static final String BYTE_ARRAY_DATA_SOURCE = "ByteArrayDataSource";
        public static final String NOT_SUPPORTED = "Not Supported";
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return APPLICATION_OCTET_STREAM;
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return BYTE_ARRAY_DATA_SOURCE;
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException(NOT_SUPPORTED);
        }
    }
}
