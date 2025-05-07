package oschwa.ledger.registries;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import oschwa.ledger.Ledger;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.exceptions.GroupExistsException;
import oschwa.ledger.exceptions.MemberExistsException;

import java.lang.reflect.Field;

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

        Assertions.assertNotNull(ledgerGroupRegistry.getGroup(player));
        assertEquals(player, ledgerGroupRegistry.getGroup(player).getOwner());
    }

    @Test
    public void noNewLedgerGroupForExistingEntryTest() {
        ledgerGroupRegistry.addGroup(player);

        assertThrows(GroupExistsException.class, () -> ledgerGroupRegistry.addGroup(player));
        assertEquals(1, ledgerGroupRegistry.getSize());
    }

    @Test
    public void removesLedgerGroupTest() {
        ledgerGroupRegistry.addGroup(player);
        ledgerGroupRegistry.removeGroup(player);

        assertNull(ledgerGroupRegistry.getGroup(player));
        assertEquals(0, ledgerGroupRegistry.getSize());
    }

    @Test
    public void failsRemovingNonExistingLedgerGroupTest() {
        assertThrows(GroupDoesNotExistException.class, () -> ledgerGroupRegistry.removeGroup(player));
        assertNull(ledgerGroupRegistry.getGroup(player));
        assertEquals(0, ledgerGroupRegistry.getSize());
    }
}