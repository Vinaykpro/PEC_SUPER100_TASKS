class ATM:
    def __init__(self):
        self.accounts = [Account("vinay","qwerty","1234",0),Account("vishnu","torch","0000",-2000),Account("mohan","123456","2421",1000)]
        self.account = None

    def login(self):
        print("Enter username:",end="")
        u = input()
        isuseravailable = False
        for i in self.accounts:
            if i.username == u:
                isuseravailable = True
                self.account = i
        if(isuseravailable):
          attempts = 3
          while(attempts>0):
            print("Enter password:",end="")
            p = input()
            if p==self.account.password:
                print("Login successful, welcome "+self.account.username)
                self.welcome()
                return
            else:
                attempts-=1
                print("Incorrect password, "+str(attempts)+" attempts left")
                if(attempts==0):
                    print("Account Blocked, please contact customer care for more assistance")
        else:
            print("User not found")
            return
        
    def welcome(self):
        i = -1
        while(True):
            print("Choose an operation to perform:\n1.withdraw\n2.deposit\n3.check balance\n4.change password\n5.Exit\nEnter here:",end="")
            
            try:
                i = int(input())
            except:
                print("Invalid input! Please enter numerical data between 1 and 4")
                continue

            if i>0 and i<6:
                if i==1:
                    self.withdraw()
                elif i==2:
                    self.deposit()
                elif i==3:
                    self.checkbalance()
                elif i==4:
                    self.changepassword()
                elif i==5:
                    print("Thanks for visiting our bank")
                    return
            else:
                print("Invalid option! Please enter numbers data between 1 and 4 only")

    def withdraw(self):
        while True:
            print("Enter amount to withdraw:",end="")
            try:
                n = int(input())
                print("Enter PIN:",end="")
                pin = input()
                if(pin==self.account.pin):
                    if self.account.balance>=n:
                        self.account.balance-=n
                        print("Withdrawl successful! Available balance is "+str(self.account.balance))
                        return
                    else:
                        print("Insufficient funds!")
                        return
                else:
                    print("Incorrect PIN")
                    return
            except:
                print("Invalid amount! Please try again")
    
    def deposit(self):
        print("Enter amount to deposit:",end="")
        try:
            n = int(input())
            self.account.balance+=n
            print("Successfully deposited "+str(n))
        except:
            print("Invalid amount entered!")

    def checkbalance(self):
        print("Enter PIN:",end="")
        pin = input()
        if(pin==self.account.pin):
            print("Available balance is "+str(self.account.balance))
        else:
            print("Incorrect PIN")
            return

    def changepassword(self):
        print("Enter current password:",end="")
        n = input()
        if n==self.account.password:
            while 1:
             print("Enter new password:",end="")
             n = input()
             if len(n)<6:
                print("Password must be atleast 6 characters long! Please try again")
                continue
             print("Re-enter new password",end="")
             n2 = input()
             if n==n2:
                self.account.password = n2
                print("Password changed successfully")
                break
             else:
                print("Pasawords didn't match! PLease try again")

class Account:
    def __init__(self,username,password,pin,balance):
        self.username = username
        self.password = password
        self.pin = pin
        self.balance = balance

ATM().login()