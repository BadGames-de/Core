package de.badgames.openBoatPacketUtils.packets.impl;

import com.google.common.io.ByteArrayDataOutput;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;
import org.bukkit.Material;

import java.util.List;

@AllArgsConstructor
public class OpenBoatClearBlockSlipPacket extends OpenBoatPacketBase {

    List<Material> materials;

    @Override
    public void writePacket(ByteArrayDataOutput stream) {
        stream.writeShort(22);
        List<String> allMaterialNames = materials.stream().filter(Material::isBlock).map(x -> String.valueOf(x.getId())).toList();
        stream.writeUTF(String.join(",", allMaterialNames));
    }
}
