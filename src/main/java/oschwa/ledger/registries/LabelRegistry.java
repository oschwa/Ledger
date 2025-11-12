package oschwa.ledger.registries;

import org.bukkit.ChatColor;
import oschwa.ledger.enums.LedgerConfigMessage;
import oschwa.ledger.labels.Label;
import oschwa.ledger.player.Ledger;

public class LabelRegistry extends Registry<String, Label> {
    private final Ledger ledger;

    public LabelRegistry(Ledger ledger) {
        super();
        this.ledger = ledger;

    }

    @Override
    public String toString() {
        if (registry.isEmpty()) {
            return LedgerConfigMessage.NO_LABELS.get();
        }

        StringBuilder registeredLabels = new StringBuilder();

        registeredLabels.append(ChatColor.YELLOW + "==============================\n");
        registeredLabels.append(ChatColor.YELLOW + "Active labels in " + ledger.getOwner().getName() + "'s Ledger\n");
        registeredLabels.append(ChatColor.YELLOW + "==============================\n");

        for (String s : registry.keySet()) {
            registeredLabels.append(s).append("\n");
        }

        return registeredLabels.toString();
    }
}
