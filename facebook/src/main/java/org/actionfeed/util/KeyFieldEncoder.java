package org.actionfeed.util;

import org.actionfeed.domain.model.Volunteer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * A class to convert key fields to and from the various parts using the TEA algorithm.
 */
public final class KeyFieldEncoder {

    /** We keep the pattern around for speed. */
    private static final Pattern PATTERN = Pattern.compile(":");

    protected static final Logger LOGGER = LoggerFactory.getLogger(KeyFieldEncoder.class);

    /** Keep the path to all of the models in a static so we don't have to encode the full class path to the browser */
    private static final String MODEL_PACKAGE;

    /**
     * Constructor KeyFieldEncoder creates a new KeyFieldEncoder instance.
     */
    private KeyFieldEncoder() {
    }

    static {
        MODEL_PACKAGE = Volunteer.class.getPackage().getName() + ".";
    }

    /**
     * Encode a key field for placement in a link or hidden variable.
     *
     * @param clazz      The class of the BaseModel to populate.
     * @param primaryKey The primary key of the BaseModel can be null and version should be as well.
     * @param version    The version number of the BaseModel.
     * @return A string for embedding.
     */
    public static String getKeyField(Class clazz, Long primaryKey, Integer version) {
        String result;
        if (null != primaryKey) {
            result = TEA.encode(String.format("%s:%d:%d", clazz.getSimpleName(), primaryKey, (null == version) ? 0 : version));
        } else {
            result = TEA.encode(String.format("%s:-1:-1", clazz));
        }
        return result;
    }

    /**
     * @param keyField The key field passed in from a list or a form.
     * @return Extracted class name from the provided key field. May be null if the field isn't correct.
     */
    public static Class<?> getBaseModelFromKeyField(String keyField) {
        if (StringUtils.hasText(keyField)) {
            String result = TEA.decode(keyField);
            String[] values = PATTERN.split(result);
            if (values.length > 0) {
                try {
                    String className = MODEL_PACKAGE + values[0].trim();
                    return Class.forName(className);
                } catch (ClassNotFoundException cnfe) {
                    LOGGER.warn("Cannot locate class [{}]", values[0]);
                }
            } else {
                LOGGER.warn("Cannot unparse key field [{}], returned null.", keyField);
            }
        }
        return null;
    }

    /**
     * @param keyField The key field passed in from a list or a form.
     * @return The primary key defined as a long for this application but should be factored to Number at some point.
     */
    public static Long getPrimaryKeyFromKeyField(String keyField) {
        if (StringUtils.hasText(keyField)) {
            String result = TEA.decode(keyField);
            String[] values = PATTERN.split(result);
            if (values.length > 1) {
                try {
                    return Long.parseLong(values[1].trim());
                } catch (NumberFormatException nfe) {
                    LOGGER.warn("Cannot locate convert id [{}]", values[1]);
                }
            } else {
                LOGGER.warn("Cannot unparse key field [{}], returned null.", keyField);
            }
        }
        return null;
    }

    /**
     * @param keyField The key field passed in from a list or a form.
     * @return The version field defined as an integer but should be a number at some point.
     */
    public static Integer getVersionFromKeyField(String keyField) {
        if (StringUtils.hasText(keyField)) {
            String result = TEA.decode(keyField);
            String[] values = PATTERN.split(result);
            if (values.length > 2) {
                try {
                    return Integer.parseInt(values[2].trim());
                } catch (NumberFormatException nfe) {
                    LOGGER.warn("Cannot locate convert version [{}]", values[2]);
                }
            } else {
                LOGGER.warn("Cannot unparse key field [{}], returned null.", keyField);
            }
        }
        return null;
    }

}
