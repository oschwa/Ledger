package oschwa.ledger.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.*;

public class LedgerGroup {
    private Player owner;
    private Map<UUID, Player> members;
    private int size;

    public LedgerGroup(Player owner) {
        this.owner = owner;
        members = new HashMap<UUID, Player>();
        members.put(owner.getUniqueId(), owner);
    }

    public boolean addMember(Player player) {
        if (!members.containsKey(player.getUniqueId())) {
            members.put(player.getUniqueId(), player);
            ++size;
            return true;
        }
        return false;
    }

    public boolean removeMember(UUID uuid) {
        if (members.containsKey(uuid)) {
            members.remove(uuid);
            --size;
            return true;
        }
        return false;
    }

    public Optional<Player> getMember(UUID uuid) {
        return Optional.ofNullable(members.get(uuid));
    }

    public boolean hasMember(UUID uuid) {
        return members.containsKey(uuid);
    }

    public Player getOwner() {
        return owner;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String ledgerString = "";

        ledgerString += ChatColor.YELLOW + "==============================\n";
        ledgerString += ChatColor.YELLOW + "Members of " + owner.getName() + "'s Ledger\n";
        ledgerString += ChatColor.YELLOW + "==============================\n";

        int i = 1;
        for (Player player : members.values()) {
            ledgerString += ChatColor.YELLOW + String.valueOf(i) + " " + player.getName() + "\n";
            i++;
        }

        return ledgerString;
    }
}
