package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.constants.CollisionModes;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenBoatCollisionModePacket extends OpenBoatPacketBase {

    CollisionModes collisionMode;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(27);
        stream.writeShort(collisionMode.ordinal());
    }
}
