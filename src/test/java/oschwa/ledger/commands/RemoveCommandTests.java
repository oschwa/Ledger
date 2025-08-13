package oschwa.ledger.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oschwa.ledger.exceptions.MemberDoesNotExistException;
import oschwa.ledger.player.LedgerGroup;
import oschwa.ledger.registries.LedgerGroupRegistry;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class RemoveCommandTests {

    private RemoveCommand removeCommand;
    private LedgerGroupRegistry ledgerGroupRegistry;

    private Server mockServer;
    private Player mockPlayer;
    private Player mockPlayer2;
    private UUID mockPlayer2UUID;
    private Command mockCommand;

    @BeforeEach
    public void setUp() {
        ledgerGroupRegistry = new LedgerGroupRegistry();

        mockServer = mock(Server.class);
        removeCommand = new RemoveCommand(mockServer, ledgerGroupRegistry);

        mockPlayer = mock(Player.class);
        mockPlayer2 = mock(Player.class);
        mockCommand = mock(Command.class);

        mockPlayer2UUID = UUID.randomUUID();

        when(mockPlayer2.getName()).thenReturn("playerTwo");
        when(mockServer.getPlayer("playerTwo")).thenReturn(mockPlayer2);
        when(mockPlayer2.getUniqueId()).thenReturn(mockPlayer2UUID);
    }

    @Test
    public void existingPlayerRemovedSuccessfullyTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        LedgerGroup ledgerGroup = ledgerGroupRegistry.getGroup(mockPlayer);
        ledgerGroup.addMember(mockPlayer2);

        removeCommand.onCommand(mockPlayer, mockCommand, "remove", new String[]{"playerTwo"});
        assertFalse(ledgerGroup.hasMember(mockPlayer2UUID));
    }

    @Test
    public void nonExistingPlayerRemovalFailsTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        LedgerGroup ledgerGroup = ledgerGroupRegistry.getGroup(mockPlayer);
        assertFalse(removeCommand.onCommand(mockPlayer, mockCommand, "remove", new String[]{"playerTwo"}));
        verify(mockPlayer).sendMessage("playerTwo is not assigned to your Ledger.");
    }

    @Test
    public void nonExistingGroupFailsRemovalTest() {
        assertFalse(removeCommand.onCommand(mockPlayer, mockCommand, "remove", new String[]{"playerTwo"}));
        verify(mockPlayer).sendMessage("You do not have a registered Ledger.");
    }
}
