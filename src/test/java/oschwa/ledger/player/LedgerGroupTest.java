package oschwa.ledger.player;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import oschwa.ledger.Ledger;
import oschwa.ledger.exceptions.MemberDoesNotExistException;
import oschwa.ledger.exceptions.MemberExistsException;

import java.lang.reflect.Member;
import java.util.Optional;
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
    public void ledgerGroupAddMemberTest() throws MemberDoesNotExistException {
        when(otherPlayer.getUniqueId()).thenReturn(uuid);
        ledgerGroup.addMember(otherPlayer);
        Optional<Player> player = ledgerGroup.getMember(uuid);
        assertTrue(player.isPresent());
    }

    @Test
    public void ledgerGroupDoesNotAddExistingMemberTest() {
        when(otherPlayer.getUniqueId()).thenReturn(uuid);
        ledgerGroup.addMember(otherPlayer);
        assertFalse(ledgerGroup.addMember(otherPlayer));
    }

    @Test
    public void ledgerGroupRemovesMemberTest() throws MemberDoesNotExistException {
        when(otherPlayer.getUniqueId()).thenReturn(uuid);

        ledgerGroup.addMember(otherPlayer);

        ledgerGroup.removeMember(uuid);

        assertFalse(ledgerGroup.hasMember(uuid));
    }

    @Test
    public void ledgerGroupFailsRemovingNonExistingMemberTest() {
        assertFalse(ledgerGroup.removeMember(uuid));
    }

}