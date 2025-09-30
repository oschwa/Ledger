package oschwa.ledger.registries;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import oschwa.ledger.Ledger;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.exceptions.GroupExistsException;
import oschwa.ledger.exceptions.MemberDoesNotExistException;
import oschwa.ledger.player.LedgerGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class LedgerGroupRegistry {

    private Map<Player, LedgerGroup> ledgerGroupMap;

    public LedgerGroupRegistry() {
        ledgerGroupMap = new HashMap<>();
    }

    public boolean containsGroup(Player player) {
        return ledgerGroupMap.containsKey(player);
    }

    public void addGroup(Player player)  {
        ledgerGroupMap.put(player, new LedgerGroup(player));
    }

    public void removeGroup(Player player) {
        ledgerGroupMap.remove(player);
    }

    public Optional<LedgerGroup> getGroupByOwner(Player player) {
        return Optional.ofNullable(ledgerGroupMap.get(player));
    }

    public Optional<LedgerGroup> getGroupByPlayer(Player player) {
        for (LedgerGroup ledgerGroup : ledgerGroupMap.values()) {
            if (ledgerGroup.hasMember(player.getUniqueId())) {
                return Optional.of(ledgerGroup);
            }
        }
        return Optional.empty();
    }

    public int getSize() {
        return ledgerGroupMap.size();
    }
}
