package oschwa.ledger.commands;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.exceptions.GroupExistsException;
import oschwa.ledger.player.LedgerGroup;
import oschwa.ledger.registries.LedgerGroupRegistry;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LedgerCommandTests {

    private LedgerCommand command;
    private String[] testManual;

    private LedgerGroupRegistry ledgerGroupRegistry;
    @Mock
    private Player player;
    @Mock
    private Command mockCommand;

    @BeforeEach
    public void setUp() {
        ledgerGroupRegistry = new LedgerGroupRegistry();
        command = new LedgerCommand(ledgerGroupRegistry);
        player = Mockito.mock(Player.class);
        mockCommand = Mockito.mock(Command.class);

        when(mockCommand.getName()).thenReturn("ledger");
    }

    @Test
    public void manCommandOutputsMessageForPlayerTest() {
        command.onCommand(player, mockCommand, "ledger", new String[]{});

        testManual = new String[] {
                "/ledger -> manual page"
        };

        verify(player).sendMessage(testManual);
    }

    @Test
    public void newCommandOutputsMessageForPlayerTest() {
        command.onCommand(player, mockCommand, "ledger", new String[]{"new"});
        verify(player).sendMessage("New Ledger created");
    }

    @Test
    public void newCommandMakesNewLedgerGroupTest() {
        command.onCommand(player, mockCommand, "ledger", new String[]{"new"});
        Assertions.assertTrue(ledgerGroupRegistry.containsGroup(player));
    }

    @Test
    public void newCommandFailsIfLedgerGroupExistsTest() {
        ledgerGroupRegistry.addGroup(player);
        LedgerGroup ledgerGroup = ledgerGroupRegistry.getGroup(player);

        when(player.getName()).thenReturn("test");

        assertThrows(GroupExistsException.class, () ->
                command.onCommand(player, mockCommand, "ledger", new String[]{"new"}));
        assertEquals(ledgerGroup, ledgerGroupRegistry.getGroup(player));
    }

    @Test
    public void scrapCommandSendsPlayerMessageTest() {
        ledgerGroupRegistry.addGroup(player);

        command.onCommand(player, mockCommand, "ledger", new String[]{"scrap"});

        verify(player).sendMessage("Ledger scrapped");
    }

    @Test
    public void scrapCommandRemovesLedgerGroupTest() {
        ledgerGroupRegistry.addGroup(player);

        command.onCommand(player, mockCommand, "ledger", new String[]{"scrap"});

        assertFalse(ledgerGroupRegistry.containsGroup(player));
    }

    @Test
    public void scrapCommandFailsRemovingNonExistingLedgerGroupTest() {
        when(player.getName()).thenReturn("test");
        assertThrows(GroupDoesNotExistException.class, () ->
                command.onCommand(player, mockCommand, "ledger", new String[]{"scrap"}));
    }

    @Test
    public void membersCommandShowsOwnerTest() {
        String[] members = {
                "Members of owner's Ledger:",
                "owner"
        };
        when(player.getName()).thenReturn("owner");

        ledgerGroupRegistry.addGroup(player);
        command.onCommand(player, mockCommand, "ledger", new String[]{"members"});

        verify(player).sendMessage(members);
    }

    @Test
    public void membersCommandShowsAllMembersTest() {
        String[] members = {
                "Members of owner's Ledger:",
                "owner",
                "player1",
                "player2"
        };

        when(player.getName()).thenReturn("owner");

        Player player1 = Mockito.mock(Player.class);
        when(player1.getName()).thenReturn("player1");
        when(player1.getUniqueId()).thenReturn(UUID.randomUUID());

        Player player2 = Mockito.mock(Player.class);
        when(player2.getName()).thenReturn("player2");
        when(player2.getUniqueId()).thenReturn(UUID.randomUUID());

        ledgerGroupRegistry.addGroup(player);

        LedgerGroup ownerGroup = ledgerGroupRegistry.getGroup(player);
        ownerGroup.addMember(player1);
        ownerGroup.addMember(player2);

        command.onCommand(player, mockCommand, "ledger", new String[]{"members"});

        verify(player).sendMessage(members);
    }
}