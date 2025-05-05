package oschwa.ledger.registries;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LedgerGroupRegistryTests {
    @Test
    public void returnsValidInstanceTest() {
        Assertions.assertEquals(LedgerGroupRegistry.class, LedgerGroupRegistry.getInstance().getClass());
    }
    @Test
    public void makesNewLedgerGroupTest() {
        Player player = Mockito.mock(Player.class);

        LedgerGroupRegistry ledgerGroupRegistry = LedgerGroupRegistry.getInstance();
        ledgerGroupRegistry.addGroup(player);

        Assertions.assertNotNull(ledgerGroupRegistry.getGroup(player));
        Assertions.assertEquals(player, ledgerGroupRegistry.getGroup(player).getOwner());
    }
}