package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatUnderwaterControlPacket extends OpenBoatPacketBase {

    boolean allowUnderwaterControl;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(16);
        stream.writeBoolean(allowUnderwaterControl);
    }
}
