package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;

/**
 * Packet used to reset movement values back to the Vanilla ones.
 * <a href="https://github.com/o7Moon/OpenBoatUtils/wiki/Packets#reset">Specification.</a>
 */
public class OpenBoatResetPacket extends OpenBoatPacketBase {
    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(0);
    }
}
