package de.badgames.pluginCore.particle;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import de.badgames.pluginCore.particle.shapes.CircleShape;
import de.badgames.pluginCore.particle.shapes.LineShape;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.awt.*;

@Getter
public class ParticleBuilder {

    private Color particleColor = Color.RED;
    private float particleSize = 1.0f;

    private ParticleRunnable renderer;

    public ParticleBuilder() {
        renderer = (player, location) -> ParticleDisplay
                .of(XParticle.DUST.or(XParticle.BLOCK))
                .withColor(particleColor, particleSize)
                .onlyVisibleTo(player)
                .spawn(location);
    }

    public ParticleBuilder(ParticleRunnable renderer) {
        this.renderer = renderer;
    }

    public ParticleBuilder withColor(Color color) {
        this.particleColor = color;
        return this;
    }

    public ParticleBuilder withSize(float size) {
        this.particleSize = size;
        return this;
    }

    public void renderLine(Player player, Location loc1, Location loc2) {

        LineShape lineShape = new LineShape();
        lineShape.renderShape(player, new Location[]{loc1, loc2}, this);
    }

    public void renderCircle(Player player, Location center, int radius) {

        CircleShape circleShape = new CircleShape(radius);
        circleShape.renderShape(player, new Location[]{center}, this);
    }

    public void renderShape(Player player, ParticleShape shape, Location[] locations) {
        shape.renderShape(player, locations, this);
    }

    public void renderPoint(Player player, Location location) {
        renderer.renderParticle(player, location);
    }

    public interface ParticleRunnable {

        void renderParticle(Player player, Location location);

    }

}
