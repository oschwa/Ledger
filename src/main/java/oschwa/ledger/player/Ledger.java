package oschwa.ledger.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import oschwa.ledger.labels.Label;
import oschwa.ledger.registries.LabelRegistry;

import java.util.*;

public class Ledger {
    private Player owner;
    private LabelRegistry labelRegistry;
    private Map<UUID, Player> members;
    private int size;

    public Ledger(Player owner) {
        this.owner = owner;
        members = new HashMap<UUID, Player>();
        labelRegistry = new LabelRegistry(this);
        members.put(owner.getUniqueId(), owner);
    }

    public void addLabel(Label label) {
        labelRegistry.add(label.getName(), label);
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

    public Optional<Label> getLabel(String name) {
        return labelRegistry.get(name);
    }

    public boolean hasMember(UUID uuid) {
        return members.containsKey(uuid);
    }

    public Player getOwner() {
        return owner;
    }

    public Map<UUID, Player> getMembers() {
        return members;
    }

    public String showRegisteredLabels() {
        return labelRegistry.toString();
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
