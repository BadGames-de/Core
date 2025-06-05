package de.badgames.openBoatPacketUtils.packets;

import com.google.common.io.ByteArrayDataOutput;
import lombok.Getter;

@Getter
public abstract class OpenBoatPacketBase {

    public abstract void writePacket(ByteArrayDataOutput stream);
}
