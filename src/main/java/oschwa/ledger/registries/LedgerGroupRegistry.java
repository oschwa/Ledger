package oschwa.ledger.registries;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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

    public Optional<LedgerGroup> getGroup(Player player) {
        return Optional.ofNullable(ledgerGroupMap.get(player));
    }

    public int getSize() {
        return ledgerGroupMap.size();
    }
}
