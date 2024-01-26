package com.TobStamina;

import com.TobStamina.TobStaminaPlugin;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;

public class TobStaminaOverlayPanel extends OverlayPanel {
    @Getter
    @Setter
    private boolean visible = false;

    private boolean everythingGood;


    @Inject
    public TobStaminaOverlayPanel(TobStaminaPlugin plugin, boolean everythingGood) {
        super(plugin);

        this.everythingGood = everythingGood;

        setPriority(OverlayPriority.MED);
        setPosition(OverlayPosition.TOP_CENTER);
    }

    @Override
    public Dimension render(Graphics2D graphics) {

        panelComponent.getChildren().add(TitleComponent.builder()
                .text(everythingGood ? "You have a stam" : "You have NO Stam!!!!!")
                .color(everythingGood ? Color.GREEN : Color.RED)
                .build());
        return super.render(graphics);
    }

}