package de.chaos.armoredElytra.utils;

import de.chaos.armoredElytra.utils.config.ConfigObject;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;

import java.net.URI;

public class TexturePack {
    private ConfigObject configObject;

    private ResourcePackInfo PACK_INFO;

    public TexturePack(ConfigObject configObject) {
        this.configObject = configObject;

        if (configObject.getInternal_enforce_pack()) {
             PACK_INFO = ResourcePackInfo.resourcePackInfo()
                    .uri(URI.create(configObject.getPackURL()))
                    .hash(configObject.getPackHash())
                    .build();
        }
    }



    public void sendResourcePack(final Audience target) {
        if (configObject.getInternal_enforce_pack()) {
            ResourcePackRequest request = ResourcePackRequest.resourcePackRequest()
                    .packs(PACK_INFO)
                    .prompt(Component.text("Please download the resource pack!"))
                    .required(true)
                    .build();

            // Send the resource pack request to the target audience
            target.sendResourcePacks(request);
        }
    }
}
