package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.constants.CollisionModes;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatAirSteppingPacket extends OpenBoatPacketBase {

    boolean allowAirStepping;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(28);
        stream.writeBoolean(allowAirStepping);
    }
}
