package oschwa.ledger.registries;

import org.bukkit.entity.Player;
import oschwa.ledger.player.LedgerGroup;

import java.util.HashMap;
import java.util.Map;

public class LedgerGroupRegistry {
    public static final LedgerGroupRegistry INSTANCE = new LedgerGroupRegistry();
    private Map<Player, LedgerGroup> ledgerGroupMap;

    public LedgerGroupRegistry() {
        ledgerGroupMap = new HashMap<>();
    }

    public static LedgerGroupRegistry getInstance() {
        return INSTANCE;
    }

    public boolean containsGroup(Player player) {
        return ledgerGroupMap.containsKey(player);
    }

    public void addGroup(Player player) {
        ledgerGroupMap.put(player, new LedgerGroup(player));
    }

    public LedgerGroup getGroup(Player player) {
        return ledgerGroupMap.get(player);
    }
}
