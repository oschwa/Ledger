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

    public boolean addGroup(Player player)  {
        if (!containsGroup(player)) {
            ledgerGroupMap.put(player, new LedgerGroup(player));
            return true;
        }
        return false;
    }

    public void removeGroup(Player player) {
        if (!ledgerGroupMap.containsKey(player)) throw new GroupDoesNotExistException(player.getName() +
                " does not have a registered Ledger");
        else ledgerGroupMap.remove(player);
    }

    public LedgerGroup getGroup(Player player) {
        if (!ledgerGroupMap.containsKey(player)) {
            String errorMessage;
            if (player == null) errorMessage = "You do not have a registered Ledger.";
            else errorMessage = player.getName() + " does not have a registered Ledger.";
            throw new GroupDoesNotExistException(errorMessage);
        }
        else return ledgerGroupMap.get(player);
    }

    public int getSize() {
        return ledgerGroupMap.size();
    }
}
