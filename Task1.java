import java.util.Scanner;

    /*                                TASK 1
                           Bank Account Class: 
Create a BankAccount class with attributes for account holder name, account number, and balance. 
Include methods to deposit, withdraw, and display the current balance. 
Implement checks for insufficient funds during withdrawals.

1) Follow the concepts of OOP
2) Must use the concepts of OOP to implement the above task
3) Maintain code modularity/reusability */

class ATM_Machine {
    BankAccount account;
    public ATM_Machine(BankAccount bankAccount) {
        this.account = bankAccount;
    }

    public void withdraw(int amount) {
        int availableBalance = account.balance;
        if(amount>=100) // minimum withdrawl limit 100 rupees
        {
            if(availableBalance-amount>=0) // checking if sufficient funds are available
            {
                account.balance -= amount;
                System.out.println("\nWithdrawl of "+amount+".00 rupees Successful");
            } else {
                System.out.println("\nTransaction failed. Insufficient funds!");
            }
        } else {
            System.out.println("\nTransaction failed! Minimum withdrawl limit is 100 rupees");
        }
    }

    public void deposit(int amount) {
        int availableBalance = account.balance;
        if(amount>=100 && amount <= 50000) // minimum deposit 100 rupees maximum deposit 50,000 rupees
        {
            account.balance += amount;
            System.out.println("\nDeposit of "+amount+".00 rupees Successful");
        } else {
            System.out.println("\nTransaction failed! Deposit should be between 100 and 50,000 rupees only");
        }
    }

    public void checkBalance() {
        System.out.println("\nTransaction Successful! Available balance in your account is : "+account.balance+".00 rupees");
    }
}

class BankAccount {
    String accountHolderName;
    int balance = 0;
    public BankAccount(String accountHolderName,int firstMandatoryDeposit) {
        this.accountHolderName = accountHolderName;
        this.balance = firstMandatoryDeposit;
    }
}

class Task1 {
    public static void main(String[] args) {
        BankAccount userAccount = null;
        Scanner scanner = new Scanner(System.in);

        while(userAccount==null) {
            String name;
            int firstMandatoryDeposit;
            System.out.println("\n\nYou don't have a bank account to access the ATM Machine create one by filling details below.");
            System.out.print("Enter Name:");
            name = scanner.nextLine();
            while(true) {
            System.out.println("You must deposit a minimum of 1000 rupees to make your bank account active:");
            System.out.print("Enter amount to deposit:");
            firstMandatoryDeposit = checkNumberValidOrNot(scanner.nextLine());
            if(firstMandatoryDeposit>=1000) {
                break;
            } else {
                System.out.println("\nInvalid Deposit!");
            }
            }
            userAccount = new BankAccount(name,firstMandatoryDeposit);
            System.out.println("Congratulations! You've successfully opened your bank account. Now you can start using the ATM Machine");
        }

        ATM_Machine atmMachine = new ATM_Machine(userAccount);

        boolean userPressedExit = false;
        while(!userPressedExit) {
            int choice = -1;
            while(choice==-1) {
            System.out.print("\n\nWelcome! "+userAccount.accountHolderName+"\nChoose your Operation:\n1.Widthdrawl\n2.Deposit\n3.Check Balance\n4.Exit\nEnter Here:");
            choice = checkNumberValidOrNot(scanner.nextLine());
            if(choice==1) {
                System.out.print("Enter amount to withdraw:");
                int amount = checkNumberValidOrNot(scanner.nextLine());
                if(amount!=-1) {
                    atmMachine.withdraw(amount);
                } else {
                    System.out.println("Bad input! Please enter valid amount and in numericals only");
                }
            } else if(choice==2) {
                System.out.print("Enter amount to deposit:");
                int amount = checkNumberValidOrNot(scanner.nextLine());
                if(amount!=-1) {
                    atmMachine.deposit(amount);
                } else {
                    System.out.println("Bad input! Please enter valid amount and in numericals only");
                }
            } else if(choice==3) {
                atmMachine.checkBalance();
            } else if(choice==4) {
                System.out.println("\nThank you Mr/Mrs "+userAccount.accountHolderName+" for using our ATM Service!\n");
                userPressedExit = true;
            } else {
                System.out.println("Bad input! Choose operations based on 1 or 2 or 3 or 4 as inputs.");
            }
            }
        }
    }

    public static int checkNumberValidOrNot(String s) {
        try {
            int number = Integer.parseInt(s);
            return number;
        } catch(Exception e) {
            return -1;
        }
    }
}