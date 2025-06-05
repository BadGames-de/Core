package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatAirControlPacket extends OpenBoatPacketBase {

    boolean airControl;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(6);
        stream.writeBoolean(airControl);
    }
}
