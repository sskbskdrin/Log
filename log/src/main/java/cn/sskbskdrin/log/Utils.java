package cn.sskbskdrin.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * Created by ex-keayuan001 on 2019-08-09.
 *
 * @author ex-keayuan001
 */
class Utils {
    static void objToString(StringBuilder builder, Object obj) {
        if (builder == null) {
            return;
        }
        if (obj == null) {
            builder.append("null");
        } else if (obj instanceof Throwable) {
            builder.append('\n');
            builder.append(getStackTraceString((Throwable) obj));
        } else if (obj.getClass().isArray()) {
            builder.append('[');
            arrayString(builder, obj);
            builder.append(']');
        } else {
            builder.append(obj);
        }
    }

    static void arrayString(StringBuilder builder, Object obj) {
        if (obj instanceof boolean[]) {
            boolString(builder, (boolean[]) obj);
        } else if (obj instanceof byte[]) {
            byteString(builder, (byte[]) obj);
        } else if (obj instanceof char[]) {
            charString(builder, (char[]) obj);
        } else if (obj instanceof short[]) {
            shortString(builder, (short[]) obj);
        } else if (obj instanceof int[]) {
            intString(builder, (int[]) obj);
        } else if (obj instanceof float[]) {
            floatString(builder, (float[]) obj);
        } else if (obj instanceof long[]) {
            longString(builder, (long[]) obj);
        } else if (obj instanceof double[]) {
            doubleString(builder, (double[]) obj);
        } else {
            objectString(builder, (Object[]) obj);
        }
    }

    private static void boolString(StringBuilder builder, boolean[] value) {
        if (value.length > 0) {
            for (boolean b : value) {
                builder.append(b);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static void byteString(StringBuilder builder, byte[] value) {
        if (value.length > 0) {
            for (byte b : value) {
                builder.append(Integer.toHexString(b & 0xff));
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static void charString(StringBuilder builder, char[] value) {
        if (value.length > 0) {
            for (char b : value) {
                builder.append(b);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static void shortString(StringBuilder builder, short[] value) {
        if (value.length > 0) {
            for (short b : value) {
                builder.append(b);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static void intString(StringBuilder builder, int[] value) {
        if (value.length > 0) {
            for (int b : value) {
                builder.append(b);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static void floatString(StringBuilder builder, float[] value) {
        if (value.length > 0) {
            for (float b : value) {
                builder.append(b);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static void longString(StringBuilder builder, long[] value) {
        if (value.length > 0) {
            for (long b : value) {
                builder.append(b);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static void doubleString(StringBuilder builder, double[] value) {
        if (value.length > 0) {
            for (double b : value) {
                builder.append(b);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static void objectString(StringBuilder builder, Object[] value) {
        if (value.length > 0) {
            for (Object b : value) {
                builder.append(b);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
        }
    }

    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

}
