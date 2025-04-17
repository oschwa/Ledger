package player;

import org.bukkit.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import oschwa.ledger.Ledger;

public class LedgerGroupTest {

    private ServerMock server;
    private Ledger plugin;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(Ledger.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }
}
