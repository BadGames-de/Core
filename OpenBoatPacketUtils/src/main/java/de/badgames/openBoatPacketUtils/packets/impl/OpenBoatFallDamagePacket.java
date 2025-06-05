package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;

/**
 * Sets if the boat takes fall damage and breaks.
 * <a href="https://github.com/o7Moon/OpenBoatUtils/wiki/Packets#set-boat-fall-damage">Specification.</a>
 */
@AllArgsConstructor
public class OpenBoatFallDamagePacket extends OpenBoatPacketBase {

    boolean fallDamage;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(4);
        stream.writeBoolean(fallDamage);
    }
}
