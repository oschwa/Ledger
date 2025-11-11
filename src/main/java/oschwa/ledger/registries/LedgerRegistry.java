package oschwa.ledger.registries;
import org.bukkit.entity.Player;
import oschwa.ledger.player.Ledger;
import java.util.Optional;

public class LedgerRegistry extends Registry<Player, Ledger> {

    public LedgerRegistry() {
        super();
    }

    public Optional<Ledger> getGroupByPlayer(Player player) {
        for (Ledger ledger : registry.values()) {
            if (ledger.hasMember(player.getUniqueId())) {
                return Optional.of(ledger);
            }
        }
        return Optional.empty();
    }
}
