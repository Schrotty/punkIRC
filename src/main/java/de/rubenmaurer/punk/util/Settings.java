package de.rubenmaurer.punk.util;

import de.rubenmaurer.punk.Punk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Helper for accessing properties.
 *
 * @author Ruben Maurer
 * @version 1.0
 * @since 1.0
 */
public class Settings {

    /**
     * The loaded properties
     */
    private Properties properties = new Properties();

    /**
     * The Singleton
     */
    private static Settings self = new Settings();

    /**
     * Get the port specified in properties.
     *
     * @return the port
     */
    public static int port() {
        return Integer.parseInt(getInstance().get("defaultPort"));
    }

    /**
     * Get the hostname specified in properties.
     *
     * @return the hostname
     */
    public static String hostname() {
        return getInstance().get("hostname");
    }

    /**
     * Get the name of this server.
     *
     * @return the server name
     */
    public static String servername() {
        return Settings.getInstance().get("servername");
    }

    /**
     * Get the version of this server.
     *
     * @return the server version
     */
    public static String version() {
        return Punk.class.getPackage().getImplementationVersion();
    }

    /**
     * Get the servers build date.
     *
     * @return the build date
     */
    public static String buildDate() {
        return "01.01.1970";
    }

    /**
     * Get the count of parse workers.
     *
     * @return the parse worker count
     */
    public static int parseWorker() {
        return Integer.parseInt(getInstance().get("parseWorker"));
    }

    /**
     * Get specific parser-worker name.
     *
     * @param index the index to use
     * @return the worker name
     */
    public static String parseWorkerName(int index) {
        return getInstance().get("parseWorkerNames").split(";")[index];
    }

    /**
     * Get the message of the day.
     *
     * @return the message of the day
     */
    public static String messageofTheDay() {
        StringBuilder builder = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new FileReader("./motd.txt"))) {
            reader.lines().forEach(builder::append);
        } catch (Exception exception) {

        }

        if (builder.toString().isEmpty()) return Notification.get(Notification.Error.ERR_NOMOTD);
        return builder.toString();
    }

    /**
     * Get the singleton.
     *
     * @return the singleton
     */
    private static Settings getInstance() {
        return self;
    }

    /**
     * Constructor for new settings singleton.
     */
    private Settings() {
        try (InputStream input = Punk.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Get the value of a given property.
     *
     * @param key the property
     * @return the loaded value
     */
    public String get(String key) {
        return properties.getProperty(key);
    }
}
