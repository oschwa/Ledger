package oschwa.ledger.player;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import oschwa.ledger.exceptions.MemberDoesNotExistException;
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
    public void ledgerGroupWithOneMemberTest() {
        UUID uuid = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(uuid);

        LedgerGroup ledgerGroup = new LedgerGroup(player);

        Assertions.assertNotNull(ledgerGroup.getOwner());
        Assertions.assertEquals(uuid, ledgerGroup.getOwner().getUniqueId());
    }

    @Test
    public void ledgerGroupAddMemberTest() {
        UUID uuid = UUID.randomUUID();
        when(otherPlayer.getUniqueId()).thenReturn(uuid);

        LedgerGroup ledgerGroup = new LedgerGroup(player);
        ledgerGroup.addMember(otherPlayer);

        Assertions.assertNotNull(ledgerGroup.getMember(uuid));
        Assertions.assertEquals(uuid, ledgerGroup.getMember(uuid).getUniqueId());
    }

    @Test
    public void ledgerGroupDoesNotAddExistingMemberTest() {
        UUID uuid = UUID.randomUUID();
        when(otherPlayer.getUniqueId()).thenReturn(uuid);

        LedgerGroup ledgerGroup = new LedgerGroup(player);
        ledgerGroup.addMember(otherPlayer);

        assertThrows(MemberExistsException.class, () -> ledgerGroup.addMember(otherPlayer));
        assertEquals(2, ledgerGroup.getSize());
    }

    @Test
    public void ledgerGroupRemovesMemberTest() {
        UUID uuid = UUID.randomUUID();
        when(otherPlayer.getUniqueId()).thenReturn(uuid);

        LedgerGroup ledgerGroup = new LedgerGroup(player);
        ledgerGroup.addMember(otherPlayer);

        ledgerGroup.removeMember(uuid);

        assertNull(ledgerGroup.getMember(uuid));
        assertEquals(1, ledgerGroup.getSize());
    }

    @Test
    public void ledgerGroupFailsRemovingNonExistingMemberTest() {
        UUID uuid = UUID.randomUUID();

        LedgerGroup ledgerGroup = new LedgerGroup(player);

        assertThrows(MemberDoesNotExistException.class, () -> ledgerGroup.removeMember(uuid));
    }

}