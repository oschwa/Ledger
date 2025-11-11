package oschwa.ledger.registries;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import oschwa.ledger.player.Ledger;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LedgerRegistryTests {

    private LedgerRegistry ledgerRegistry;

    @Mock
    private Player player;

    @BeforeEach
    public void setUp() {
        ledgerRegistry = new LedgerRegistry();
        player = Mockito.mock(Player.class);
    }

    @Test
    public void makesNewLedgerGroupTest() {
        ledgerRegistry.add(player, new Ledger(player));

        Optional<Ledger> ledgerGroup =
                ledgerRegistry.get(player);

        Assertions.assertNotNull(ledgerGroup.get());
        assertEquals(player, ledgerGroup.get().getOwner());
    }

    @Test
    public void removesLedgerGroupTest() {
        ledgerRegistry.add(player, new Ledger(player));
        ledgerRegistry.remove(player);

        assertFalse(ledgerRegistry.contains(player));
        assertEquals(0, ledgerRegistry.getSize());
    }
}