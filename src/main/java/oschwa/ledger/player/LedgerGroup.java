package oschwa.ledger.player;

import org.bukkit.entity.Player;
import oschwa.ledger.exceptions.MemberExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LedgerGroup {
    private Player owner;
    private Map<UUID, Player> players;
    private int size;

    public LedgerGroup(Player owner) {
        this.owner = owner;
        players = new HashMap<UUID, Player>();
        size = 1;
    }

    public void addPlayer(Player player) {
        if (!players.containsKey(player.getUniqueId())) {
            players.put(player.getUniqueId(), player);
            ++size;
        } else {
            throw new MemberExistsException("Player is already assigned to this Ledger");
        }
    }

    public Player getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public Player getOwner() {
        return owner;
    }

    public int getSize() {
        return size;
    }
}
