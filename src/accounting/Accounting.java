/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package accounting;

/**
 *
 * @author Ryan
 */
import statics.ChartofAccounts;
import system.*;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import statics.*;
public class Accounting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /** Accounting system
         *  
         * input: date, account titles, debit and credit amounts
         * perpetual inventory system
         * credit terms & discounts must be tracked to apply 2/10 n/30
         * input validation for double entry, no unbalance entry 
         * 
         * general journal
         * general ledger
         * 
         * output: unadjusted trial balance, and statements of financial performance, financial position, and changes in equity
         * 
         * TO-DO list: 
         * ===========================================================================================================
         * User interface - wire framing and JFrame
            Perpetual inventory system - sales must reduce inventory
            Credit term tracking - 2/10 n/30
            Chart of accounts and opening trial balance can be pulled from a csv file or a database
            Database for transactions that can be exported to csv file
            Unadjusted trial balance and:
            1. Statement of Financial Performance - Income statement
            2. Statement of Financial Position - Balance sheet
            3. Statement of Changes in Equity 

            date for transaction is set to the date of input, must be changed to user input date
            every time user use an account, the system search the chart of account, idk if there is an efficient way
         * ============================================================================================================
         */
        
        ChartofAccounts coa = new ChartofAccounts();
//        coa.display();

        Transaction t0 = new OpeningTrialBalance().createOpeningBalanceTransaction(LocalDate.now());
        
        Account cash = coa.getAccounts()
                .stream()
                .filter(acc -> acc.name().equalsIgnoreCase("cash"))
                .findFirst()
                .orElse(null); // handle if null
        Account accountsPayable = coa.getAccounts()
                .stream()
                .filter(acc -> acc.name().toLowerCase().equals("accounts payable"))
                .findFirst()
                .orElse(null);
        
        
        
        
        
        JournalEntryItem entry1 = new JournalEntryItem(cash, BigDecimal.valueOf(1000), EntryType.DEBIT); // inputs
        JournalEntryItem entry2 = new JournalEntryItem(accountsPayable, BigDecimal.valueOf(1000), EntryType.CREDIT);
        
        
        Transaction t1 = new Transaction(UUID.randomUUID(), LocalDate.now(), "Loan 1000 pesos", List.of(entry1, entry2)); // aggregating to one transaction
        
        System.out.println(t1.getDate() + " | " + t1.getItems().get(0).account().name() + " | " + t1.getItems().get(0).entryType() + ": " + t1.getItems().get(0).amount());
        System.out.println(t1.getDate() + " | " + t1.getItems().get(1).account().name() + " | " + t1.getItems().get(1).entryType() + ": " + t1.getItems().get(1).amount());
        System.out.println(t1.getDescription());
        
        
        JournalEntryItem entry3 = new JournalEntryItem(accountsPayable, BigDecimal.valueOf(500), EntryType.DEBIT); // inputs
        JournalEntryItem entry4 = new JournalEntryItem(cash, BigDecimal.valueOf(500), EntryType.CREDIT);
        
        
        System.out.println("");
        
        Transaction t2 = new Transaction(UUID.randomUUID(), LocalDate.now(), "Paid 500 pesos", List.of(entry3, entry4)); // aggregating to one transaction
        
        System.out.println(t1.getDate() + " | " + t2.getItems().get(0).account().name() + " | " + t2.getItems().get(0).entryType() + ": " + t2.getItems().get(0).amount());
        System.out.println(t1.getDate() + " | " + t2.getItems().get(1).account().name() + " | " + t2.getItems().get(1).entryType() + ": " + t2.getItems().get(1).amount());
        System.out.println(t2.getDescription());
        
        
        
        
        
        
        
        
        TAccount cashTAccount = new TAccount(cash);
        cashTAccount.addEntry(t0.getItems().stream().filter(acc -> acc.account().name().equalsIgnoreCase("cash")).findFirst().orElse(null));
        cashTAccount.addEntry(entry1);
        cashTAccount.addEntry(entry4);
//        cashTAccount.addEntry(entry2);
        
        System.out.println("\n" + cashTAccount.getAccount().name());
        System.out.println("Debits: ");
        cashTAccount.getDebits().stream().forEach(deb -> System.out.println(deb.amount()));
        System.out.println("Credits: ");
        cashTAccount.getCredits().stream().forEach(cred -> System.out.println(cred.amount()));
        System.out.println("\ncash balance: " + cashTAccount.getNetBalance());
    }
}
