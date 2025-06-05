package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatSwimForcePacket extends OpenBoatPacketBase {

    float swimForce;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(21);
        stream.writeFloat(swimForce);
    }
}
