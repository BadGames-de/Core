package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatSurfaceWaterControlPacket extends OpenBoatPacketBase {

    boolean allowSurfaceWaterControl;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(17);
        stream.writeBoolean(allowSurfaceWaterControl);
    }
}
