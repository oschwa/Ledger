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
    private Player player;
    @Mock
    private Player otherPlayer;

    private UUID uuid;

    private LedgerGroup ledgerGroup;

    @BeforeEach
    public void setUp() {
        player = Mockito.mock(Player.class);
        otherPlayer = Mockito.mock(Player.class);
        ledgerGroup = new LedgerGroup(player);
        uuid = UUID.randomUUID();
    }

    @Test
    public void ledgerGroupWithOneMemberTest() {
        when(player.getUniqueId()).thenReturn(uuid);

        Assertions.assertNotNull(ledgerGroup.getOwner());
        Assertions.assertEquals(uuid, ledgerGroup.getOwner().getUniqueId());
    }

    @Test
    public void ledgerGroupAddMemberTest() {
        when(otherPlayer.getUniqueId()).thenReturn(uuid);

        ledgerGroup.addMember(otherPlayer);

        Assertions.assertNotNull(ledgerGroup.getMember(uuid));
        Assertions.assertEquals(uuid, ledgerGroup.getMember(uuid).getUniqueId());
    }

    @Test
    public void ledgerGroupDoesNotAddExistingMemberTest() {
        when(otherPlayer.getUniqueId()).thenReturn(uuid);

        ledgerGroup.addMember(otherPlayer);

        assertThrows(MemberExistsException.class, () -> ledgerGroup.addMember(otherPlayer));
        assertEquals(2, ledgerGroup.getSize());
    }

    @Test
    public void ledgerGroupRemovesMemberTest() {
        when(otherPlayer.getUniqueId()).thenReturn(uuid);

        ledgerGroup.addMember(otherPlayer);

        ledgerGroup.removeMember(uuid);

        assertThrows(MemberDoesNotExistException.class, () -> ledgerGroup.getMember(uuid));
        assertEquals(1, ledgerGroup.getSize());
    }

    @Test
    public void ledgerGroupFailsRemovingNonExistingMemberTest() {
        assertThrows(MemberDoesNotExistException.class, () -> ledgerGroup.removeMember(uuid));
    }

}