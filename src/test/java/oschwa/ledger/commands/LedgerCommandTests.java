package oschwa.ledger.commands;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import oschwa.ledger.registries.LedgerGroupRegistry;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LedgerCommandTests {

    private LedgerCommand command;
    private String[] testManual;
    @Mock
    LedgerGroupRegistry ledgerGroupRegistry;
    @Mock
    Player player;
    @Mock
    Command mockCommand;

    @BeforeEach
    public void setUp() {
        command = new LedgerCommand();
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
}