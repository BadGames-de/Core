package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatResendVersionPacket extends OpenBoatPacketBase {

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(15);
    }
}
