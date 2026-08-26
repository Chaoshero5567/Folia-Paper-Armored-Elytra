package de.chaos.armoredElytra.utils.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ConfigObject {
    private String PackURL;
    private String PackHash;
    private String PackName;
    private String netherite_name;
    private String diamond_name;
    private String iron_name;
    private String gold_name;
    private Boolean internal_enforce_pack;
}
