package oschwa.ledger.registries;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import oschwa.ledger.player.LedgerGroup;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LedgerGroupRegistryTests {

    private LedgerGroupRegistry ledgerGroupRegistry;

    @Mock
    private Player player;

    @BeforeEach
    public void setUp() {
        ledgerGroupRegistry = new LedgerGroupRegistry();
        player = Mockito.mock(Player.class);
    }

    @Test
    public void makesNewLedgerGroupTest() {
        ledgerGroupRegistry.add(player, new LedgerGroup(player));

        Optional<LedgerGroup> ledgerGroup =
                ledgerGroupRegistry.get(player);

        Assertions.assertNotNull(ledgerGroup.get());
        assertEquals(player, ledgerGroup.get().getOwner());
    }

    @Test
    public void removesLedgerGroupTest() {
        ledgerGroupRegistry.add(player, new LedgerGroup(player));
        ledgerGroupRegistry.remove(player);

        assertFalse(ledgerGroupRegistry.contains(player));
        assertEquals(0, ledgerGroupRegistry.getSize());
    }
}