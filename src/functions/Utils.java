/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package functions;

/**
 *
 * @author Ryan
 */
import system.*;
import statics.*;

import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class Utils {
    public static Transaction initializeOpeningTrialBalance(LocalDate date){
        return new OpeningTrialBalance().createOpeningBalanceTransaction(date);
    }
    
    public static Account findAccount(String accountName){
        ChartofAccounts coa = new ChartofAccounts();
        
        Account account = coa.getAccounts()
                .stream()
                .filter(acc -> acc.name().equalsIgnoreCase(accountName))
                .findFirst()
                .orElse(null);
        
        if(account == null){
            JOptionPane.showMessageDialog(null, "Invalid account: " + accountName);
        }
        
        return account;
    }
    
    public static JournalEntryItem createEntry(Account acc, int amount, EntryType entry){
        return new JournalEntryItem(acc, BigDecimal.valueOf(amount), entry);
    }
    
    public static Transaction createTransaction(LocalDate date, String description, JournalEntryItem[] entries){
        List entry = new ArrayList();
        
        for(int i=0; i<entries.length; i++){
            entry.add(entries[i]);
        }
        
        return new Transaction(UUID.randomUUID(), LocalDate.now(), "Loan 1000 pesos", entry);
    }
    
    public static TAccount createTAccountAndInitializeBeginingBalance(String accountName, Transaction t0){
        Account account = findAccount(accountName);
        TAccount taccount = new TAccount(account);
        
        JournalEntryItem beginingBalance = t0
                .getItems()
                .stream()
                .filter(acc -> acc.account().name().equalsIgnoreCase(accountName))
                .findFirst()
                .orElse(null)
        ;
        
        if(beginingBalance != null){
            taccount.addEntry(beginingBalance);        
        }
        
        return taccount;
    }
    
    public static void addEntries(TAccount taccount, JournalEntryItem[] entries){
        for(int i=0; i<entries.length; i++){
            taccount.addEntry(entries[i]);
        }
    }
    
    public static void displayTransaction(Transaction t){
        System.out.println(t.getDate() + " | " + t.getItems().get(0).account().name() + " | " + t.getItems().get(0).entryType() + ": " + t.getItems().get(0).amount());
        System.out.println(t.getDate() + " | " + t.getItems().get(1).account().name() + " | " + t.getItems().get(1).entryType() + ": " + t.getItems().get(1).amount());
        System.out.println(t.getDescription());
        System.out.println("-------------------------------------"); 
    }
    
    public static void displayTAcount(TAccount tacount){
        System.out.println("\n" + tacount.getAccount().name());
        System.out.println("-----------------------");
        System.out.println("Debits \n----------");
        tacount.getDebits().stream().forEach(deb -> System.out.println(deb.amount()));
        System.out.println("Credits \n----------");
        tacount.getCredits().stream().forEach(cred -> System.out.println(cred.amount()));
        System.out.println("\n" + tacount.getAccount().name() + " balance: " + tacount.getNetBalance());
    }
    
    public static void displayChartOfAccount(){
        ChartofAccounts coa = new ChartofAccounts();
        coa.display();
    }
}
