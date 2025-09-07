package oschwa.ledger.player;

import org.bukkit.entity.Player;
import oschwa.ledger.exceptions.MemberDoesNotExistException;
import oschwa.ledger.exceptions.MemberExistsException;

import java.sql.Timestamp;
import java.util.*;

public class LedgerGroup {
    private Player owner;
    private Map<UUID, Player> members;
    private int size;

    public LedgerGroup(Player owner) {
        this.owner = owner;
        members = new HashMap<UUID, Player>();
        size = 1;
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

    public Player getMember(UUID uuid) throws MemberDoesNotExistException {
        if (members.containsKey(uuid)) {
            return members.get(uuid);
        } else throw new MemberDoesNotExistException("Player is not a member of this Ledger");
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

    public String[] getMembersList() {
        List<String> membersList = new ArrayList<>();

        membersList.add(owner.getName());

        for (Player player : members.values()) {
            membersList.add(player.getName());
        }

        Collections.sort(membersList);

        membersList.addFirst("Members of " + owner.getName() + "'s Ledger:");

        return membersList.toArray(new String[size]);
    }
}
