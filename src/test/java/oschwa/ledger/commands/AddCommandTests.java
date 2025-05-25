package oschwa.ledger.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oschwa.ledger.exceptions.GroupDoesNotExistException;
import oschwa.ledger.exceptions.MemberExistsException;
import oschwa.ledger.registries.LedgerGroupRegistry;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddCommandTests {

    private AddCommand addCommand;
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
        addCommand = new AddCommand(mockServer, ledgerGroupRegistry);

        mockPlayer = mock(Player.class);
        mockPlayer2 = mock(Player.class);
        mockCommand = mock(Command.class);

        mockPlayer2UUID = UUID.randomUUID();

        when(mockPlayer2.getName()).thenReturn("playerTwo");
        when(mockServer.getPlayer("playerTwo")).thenReturn(mockPlayer2);
        when(mockPlayer2.getUniqueId()).thenReturn(mockPlayer2UUID);

    }

    @Test
    public void addCommandAddsMemberToLedger() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        assertTrue(ledgerGroupRegistry.getGroup(mockPlayer).hasMember(mockPlayer2UUID));
    }

    @Test
    public void addCommandFailsToAddNonExistentPlayerTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerThree"});
        assertFalse(ledgerGroupRegistry.getGroup(mockPlayer).hasMember(mockPlayer2UUID));
    }

    @Test
    public void addCommandSendsMessageTest() {
        ledgerGroupRegistry.addGroup(mockPlayer);
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        verify(mockPlayer).sendMessage("playerTwo has been added to your Ledger.");
    }

    @Test
    public void addCommandSendsMessageForMemberExistsExceptionTest() {

        ledgerGroupRegistry.addGroup(mockPlayer);
        ledgerGroupRegistry.getGroup(mockPlayer).addMember(mockPlayer2);

        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        verify(mockPlayer).sendMessage("playerTwo is already assigned to this Ledger");
    }

    @Test
    public void addCommandFailsIfLedgerDoesNotExistTest() {
        assertFalse(addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"}));
    }

    @Test
    public void addCommandSendsMessageForGroupDoesNotExistExceptionTest() {
        when(mockPlayer.getName()).thenReturn("playerOne");
        addCommand.onCommand(mockPlayer, mockCommand, "add", new String[]{"playerTwo"});
        verify(mockPlayer).sendMessage("playerOne does not have a registered Ledger");
    }
}