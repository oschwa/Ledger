package oschwa.ledger.registries;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.exceptions.GroupExistsException;
import oschwa.ledger.exceptions.MemberDoesNotExistException;
import oschwa.ledger.player.LedgerGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LedgerGroupRegistry {

    private Map<Player, LedgerGroup> ledgerGroupMap;

    public LedgerGroupRegistry() {
        ledgerGroupMap = new HashMap<>();
    }

    public boolean containsGroup(Player player) {
        return ledgerGroupMap.containsKey(player);
    }

    public void addGroup(Player player) throws GroupExistsException {
        if (ledgerGroupMap.containsKey(player)) throw new GroupExistsException(ChatColor.YELLOW + player.getName()
                + " already has an assigned Ledger.");
        else {
            ledgerGroupMap.put(player, new LedgerGroup(player));
            player.sendMessage(ChatColor.YELLOW + "New Ledger created!");
        }
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

    public Player getOwner(UUID uuid) throws MemberDoesNotExistException {
        for (Map.Entry<Player, LedgerGroup> entry : ledgerGroupMap.entrySet()) {
            if (entry.getValue().hasMember(uuid)) {
                return entry.getKey();
            }
        }
        throw new MemberDoesNotExistException("Member does not exist in any registered Ledger");
    }

    public int getSize() {
        return ledgerGroupMap.size();
    }
}
