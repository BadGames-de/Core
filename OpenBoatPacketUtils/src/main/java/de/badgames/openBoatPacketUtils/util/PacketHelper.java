package de.badgames.openBoatPacketUtils.util;

import com.google.common.io.ByteArrayDataOutput;

/**
 * Utility class for packet handling.
 */
public class PacketHelper {

    /**
     * The number of bits used for a segment in a varint.
     */
    private static final int SEGMENT_BITS = 0x7F;

    /**
     * The bit used to indicate that there are more segments in a varint.
     */
    private static final int CONTINUE_BIT = 0x80;

    /**
     * Writes a string to the output stream as a varint length followed by the string bytes.
     * @param out the output stream to write to.
     * @param stringValue the string to write.
     */
    public static void writeString(ByteArrayDataOutput out, String stringValue) {
        int length = stringValue.length();

        // write the length as a varint
        while (true) {
            if(((length & ~SEGMENT_BITS)) == 0) {
                out.writeByte(length);
                break;
            }

            out.writeByte((length & SEGMENT_BITS) | CONTINUE_BIT);
            length >>>= 7;
        }

        // write the bytes of the string
        out.writeBytes(stringValue);
    }
}
