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
        ledgerGroupRegistry.addGroup(player);

        Optional<LedgerGroup> ledgerGroup =
                ledgerGroupRegistry.getGroupByOwner(player);

        Assertions.assertNotNull(ledgerGroup.get());
        assertEquals(player, ledgerGroup.get().getOwner());
    }

    @Test
    public void removesLedgerGroupTest() {
        ledgerGroupRegistry.addGroup(player);
        ledgerGroupRegistry.removeGroup(player);

        assertFalse(ledgerGroupRegistry.containsGroup(player));
        assertEquals(0, ledgerGroupRegistry.getSize());
    }
}