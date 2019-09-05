package acceptance;

import com.bankkata.Account;
import com.bankkata.Clock;
import com.bankkata.StatementPrinter;
import com.bankkata.Transactions;
import com.bankkata.Console;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PrintStatementFeature {

	@Mock Console console;
	@Mock Clock clock;

	private Account account;

	@Before
	public void initialise() {
		Transactions transactions = new Transactions();
		StatementPrinter statementPrinter = new StatementPrinter(console);
		account = new Account(clock, transactions, statementPrinter);
	}

	@Test public void
	should_print_statement_containing_all_transactions() {
		given(clock.dateAsString()).willReturn("04/09/2019", "05/09/2019", "06/09/2019");
		account.deposit(1000);
		account.withdraw(100);
		account.deposit(500);

		account.printStatement();

		verify(console).printLine("DATE | AMOUNT | BALANCE");
		verify(console).printLine("06/09/2019 | 500,00 | 1400,00");
		verify(console).printLine("05/09/2019 | -100,00 | 900,00");
		verify(console).printLine("04/09/2019 | 1000,00 | 1000,00");
	}
}
