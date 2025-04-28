package oschwa.ledger.player;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import oschwa.ledger.exceptions.MemberExistsException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LedgerGroupTest {

    @Mock
    Player player;
    @Mock
    Player otherPlayer;

    @BeforeEach
    public void setUp() {
        player = Mockito.mock(Player.class);
        otherPlayer = Mockito.mock(Player.class);
    }

    @Test
    public void ledgerGroupWithOnePlayerTest() {
        UUID uuid = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(uuid);

        LedgerGroup ledgerGroup = new LedgerGroup(player);

        Assertions.assertNotNull(ledgerGroup.getOwner());
        Assertions.assertEquals(uuid, ledgerGroup.getOwner().getUniqueId());
    }

    @Test
    public void ledgerGroupAddPlayerTest() {
        UUID uuid = UUID.randomUUID();
        when(otherPlayer.getUniqueId()).thenReturn(uuid);

        LedgerGroup ledgerGroup = new LedgerGroup(player);
        ledgerGroup.addPlayer(otherPlayer);

        Assertions.assertNotNull(ledgerGroup.getPlayer(uuid));
        Assertions.assertEquals(uuid, ledgerGroup.getPlayer(uuid).getUniqueId());
    }

    @Test
    public void ledgerGroupDoesNotAddExistingPlayerTest() {
        UUID uuid = UUID.randomUUID();
        when(otherPlayer.getUniqueId()).thenReturn(uuid);

        LedgerGroup ledgerGroup = new LedgerGroup(player);
        ledgerGroup.addPlayer(otherPlayer);

        assertThrows(MemberExistsException.class, () -> ledgerGroup.addPlayer(otherPlayer));
        assertEquals(2, ledgerGroup.getSize());
    }

}