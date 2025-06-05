package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;

public class OpenBoatClearAllSlipPacket extends OpenBoatPacketBase {
    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(23);
    }
}
