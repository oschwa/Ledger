package oschwa.ledger.player;

import org.bukkit.entity.Player;
import oschwa.ledger.exceptions.MemberDoesNotExistException;
import oschwa.ledger.exceptions.MemberExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LedgerGroup {
    private Player owner;
    private Map<UUID, Player> members;
    private int size;

    public LedgerGroup(Player owner) {
        this.owner = owner;
        members = new HashMap<UUID, Player>();
        size = 1;
    }

    public void addMember(Player player) {
        if (!members.containsKey(player.getUniqueId())) {
            members.put(player.getUniqueId(), player);
            ++size;
        } else {
            throw new MemberExistsException(player.getName() + " is already assigned to this Ledger");
        }
    }

    public void removeMember(UUID uuid) {
        if (members.containsKey(uuid)) {
            members.remove(uuid);
            --size;
        } else {
            throw new MemberDoesNotExistException("Player is not a member of this Ledger");
        }
    }

    public Player getMember(UUID uuid) {
        return members.get(uuid);
    }

    public Player getOwner() {
        return owner;
    }

    public int getSize() {
        return size;
    }
}
