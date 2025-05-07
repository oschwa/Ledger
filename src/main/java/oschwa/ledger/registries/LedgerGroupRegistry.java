package oschwa.ledger.registries;

import org.bukkit.entity.Player;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.exceptions.GroupExistsException;
import oschwa.ledger.player.LedgerGroup;

import java.util.HashMap;
import java.util.Map;

public class LedgerGroupRegistry {

    private Map<Player, LedgerGroup> ledgerGroupMap;

    public LedgerGroupRegistry() {
        ledgerGroupMap = new HashMap<>();
    }

    public boolean containsGroup(Player player) {
        return ledgerGroupMap.containsKey(player);
    }

    public void addGroup(Player player) throws GroupExistsException {
        if (ledgerGroupMap.containsKey(player)) throw new GroupExistsException(player.getName()
                + " already has assigned Ledger");
        else ledgerGroupMap.put(player, new LedgerGroup(player));
    }

    public void removeGroup(Player player) throws GroupDoesNotExistException {
        if (!ledgerGroupMap.containsKey(player)) throw new GroupDoesNotExistException(player.getName() +
                " does not have a registered Ledger");
        else ledgerGroupMap.remove(player);
    }

    public LedgerGroup getGroup(Player player) {
        if (!ledgerGroupMap.containsKey(player)) throw new GroupDoesNotExistException(player.getName() +
                " does not have a registered Ledger");
        else return ledgerGroupMap.get(player);
    }

    public int getSize() {
        return ledgerGroupMap.size();
    }
}
