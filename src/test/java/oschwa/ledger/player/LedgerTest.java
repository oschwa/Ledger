package oschwa.ledger.player;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LedgerTest {

    @Mock
    private Player player;
    @Mock
    private Player otherPlayer;

    private UUID uuid;

    private Ledger ledger;

    @BeforeEach
    public void setUp() {
        player = Mockito.mock(Player.class);
        otherPlayer = Mockito.mock(Player.class);
        ledger = new Ledger(player);
        uuid = UUID.randomUUID();
    }

    @Test
    public void ledgerGroupWithOneMemberTest() {
        when(player.getUniqueId()).thenReturn(uuid);

        Assertions.assertNotNull(ledger.getOwner());
        Assertions.assertEquals(uuid, ledger.getOwner().getUniqueId());
    }

    @Test
    public void ledgerGroupAddMemberTest() {
        when(otherPlayer.getUniqueId()).thenReturn(uuid);
        ledger.addMember(otherPlayer);
        Optional<Player> player = ledger.getMember(uuid);
        assertTrue(player.isPresent());
    }

    @Test
    public void ledgerGroupDoesNotAddExistingMemberTest() {
        when(otherPlayer.getUniqueId()).thenReturn(uuid);
        ledger.addMember(otherPlayer);
        assertFalse(ledger.addMember(otherPlayer));
    }

    @Test
    public void ledgerGroupRemovesMemberTest() {
        when(otherPlayer.getUniqueId()).thenReturn(uuid);

        ledger.addMember(otherPlayer);

        ledger.removeMember(uuid);

        assertFalse(ledger.hasMember(uuid));
    }

    @Test
    public void ledgerGroupFailsRemovingNonExistingMemberTest() {
        assertFalse(ledger.removeMember(uuid));
    }

}