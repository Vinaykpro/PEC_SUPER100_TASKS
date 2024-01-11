import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

class Book {
    String bookTitle,bookDescription;
    int index;

    public Book(String title, String description, int index) {
        this.bookTitle = title;
        this.bookDescription = description;
        this.index = index;
    }
}

class Library {
    List<List<Book>> availableFreeBooks = new ArrayList<>();
    List<List<Book>> availableMemberOnlyBooks = new ArrayList<>();
    public Library() {
      String[] freeBookTitles = {"Tech Visions","Code Craft","Future Gadgets"};
      String[] freeBookDescs = {"Explore cutting-edge advancements in AI, blockchain and more...","Dive into the world of programming languages and software world.","Explore blueprints and insights into next wave of innovation."};
      String[] memberOnlyBookTitles = {"TechElite","CodeLux","InnovateX","QuantumQuotient Premium","TechSavvy Premium","InnoElegance","FututreLux Premium"};
      String[] memberOnlyBookDescs = {"Unlock the future exclusively for members. Delve into advanced insights on AI, quantum computing, and emerging technologies.","Elevate your coding skills with premium insights, exclusively crafted for our members.","Exclusive access for members to tomorrow's opulent gadgets and elite tech trends.","Members gateway to the quantum realm - harness the power of quantum computing.","An exclusive exploration for premium members - insider insights on the latest tech trends.","Elegant tech exploration reserved for our subscribed members.","Premium access to a luxurious tech journey - tomorrow's tech exclusively for members."};

        for(int i=0;i<3;i++) {
            List<Book> freeBookSet = new ArrayList<>();
            int stock = (int) Math.ceil(Math.random()*6);
            for(int j=0;j<stock;j++) {
                freeBookSet.add(new Book(freeBookTitles[i],freeBookDescs[i],i));
            }
            availableFreeBooks.add(freeBookSet);
        }
        for(int i=0;i<7;i++) {
            List<Book> memberOnlyBookSet = new ArrayList<>();
            int stock = (int) Math.ceil(Math.random()*15);
            for(int j=0;j<stock;j++) {
                memberOnlyBookSet.add(new Book(memberOnlyBookTitles[i],memberOnlyBookDescs[i],i));
            }
            availableMemberOnlyBooks.add(memberOnlyBookSet);
        }
    }

    public void showAvailableBooks() {
        System.out.println("\n\n----------------------------------\nAvailable Books with Free Access :\n----------------------------------");
        int i=0;
        for(List<Book> bs : availableFreeBooks) {
            if(bs.size()>0) {
                showDetails(i,bs.get(0),bs.size());
                i++;
            }
        }
        System.out.println("\n------------------------------------------------------------------\nAvailable Premium Books (Only accessible for premium subscribed members) :\n------------------------------------------------------------------");
        for(List<Book> bs : availableMemberOnlyBooks) {
            if(bs.size()>0) {
                showDetails(i,bs.get(0),bs.size());
                i++;
            }
        }
    }

    public void showDetails(int index,Book b, int availableStock) {
        System.out.println((index+1)+".Book Title: "+b.bookTitle+" ( "+availableStock+" Left )\n  Description: "+b.bookDescription+"\n");
    }

    public void borrowBook(Member m,Scanner s) {
        showAvailableBooks();
        int opt = -1;
        while (opt<0) {
            Task2.p("\nChoose a Book to borrow based on Index (press 0 to go back) : ");
            opt = Task2.getOptionIfValid(s.nextLine());
            if(opt==0) {
                return;
            } else if (opt>0 && opt<=(availableFreeBooks.size()+availableMemberOnlyBooks.size())) {
                if(opt>availableFreeBooks.size() && !m.isPremiumMember) {
                    Task2.p("\nThe choosen Book is a Premium One & not accessable for General members, please go back and upgrade to become a premium member or choose another book.\n");
                    opt = -1;
                }
            } else {
                Task2.p("\nINVALID INPUT! Please try again.\n");
                opt = -1;
            }
        }
        Book borrowingBook;
        opt-=1;
        if(opt<availableFreeBooks.size()) {
            borrowingBook = availableFreeBooks.get(opt).get(0);
            availableFreeBooks.get(opt).remove(0);
            m.myBooks.add(borrowingBook);
        } else {
            opt-=availableFreeBooks.size();
            borrowingBook = availableMemberOnlyBooks.get(opt).get(0);
            availableMemberOnlyBooks.get(opt).remove(0);
            m.myBooks.add(borrowingBook);
        }
        Task2.p("\nSuccessfully borrowed the book \""+borrowingBook.bookTitle+"\" from Library.\n");
    }

    public void returnBook(Member m,Scanner s) {
        if(m.myBooks.size()==0) {
            Task2.p("You haven't borrowed any books to return one.");
        } else if(m.myBooks.size()==1) {
            Task2.p("Successfully returned the book \""+m.myBooks.get(0).bookTitle+"\" to Library.");
        } else {
            int opt = -1;
            while(opt<0) {
            HashMap<Integer,Integer> map = new HashMap<>();
            Task2.p("\n-----------------------------------------\n"+
                      " Choose a book based on Index to return: \n"+
                      "-----------------------------------------\n");
            int i=0;
            for(Book b : m.myBooks) {
                Task2.p("\n"+(b.index+1)+"."+b.bookTitle+"\n");
                map.put(b.index,i);
            }
            Task2.p("\nEnter Here (Enter 0 to go back):");
            opt = Task2.getOptionIfValid(s.nextLine());
            if(opt==0) {
                return;
            } else if(map.containsKey(--opt)) {
                Book returningBook = m.myBooks.get(map.get(opt));
                if(opt>=availableFreeBooks.size()) {
                    availableMemberOnlyBooks.get(opt-availableFreeBooks.size()).add(returningBook);
                } else {
                    availableFreeBooks.get(opt).add(returningBook);
                }
                m.myBooks.remove(returningBook);
                    Task2.p("Successfully returned the book \""+returningBook.bookTitle+"\" to Library.");
            } else {
                opt = -1;
                Task2.p("\nINVALID INPUT! Please try again.\n");
            }
            }
         }
    }
}

class Member {
    String memberName, memberId;
    boolean isPremiumMember = false;
    List<Book> myBooks;
    public Member(String memberName, String memberId, boolean isPremiumMember) {
        this.memberName = memberName;
        this.memberId = memberId;
        this.isPremiumMember = isPremiumMember;
        myBooks = new ArrayList<>();
    }
}

public class Task2 {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);
        p("\nWelcome to PEC Library!\nYou are not a member of this Library, please choose an option to create your member profile:\n1.General Member (access to limited Books)\n2.Premium Member(Can access all available Books at 149₹ per Month)\nEnter Here:");
        int memberType = -1;
        boolean isPremiumMember = false;
        String memberName,memberId;
        while(true) {
            memberType = getOptionIfValid(sc.nextLine());
            if(memberType!=1 && memberType!=2)
                System.out.print("\nPlease choose either 1.General Member or 2.Premium Member\nEnter Here (1 or 2) :");
            else
                break;
        }
        isPremiumMember = memberType == 2 ? true : false;
        p("\nEnter your Name:");
        memberName = sc.nextLine();
        memberId = ((int)(Math.random()*900000)+99999)+"";

        Member member = new Member(memberName, memberId, isPremiumMember);

        String mt = isPremiumMember ? "Premium " : "";
        p("\nCongratulations! "+memberName+", You're now a "+mt+"Member of this Library.\n");
        int opt = -1;
        String premiumOpt = !isPremiumMember ? "\n5.Upgrade to Premium at 149₹ per Month" : "";
        while(opt!=4) {
            p("\nChoose what you want to do:\n1.Show Available Books\n2.Borrow a Book\n3.Return a Book\n4.Exit"+premiumOpt+"\n\nEnter Here:");
            opt = getOptionIfValid(sc.nextLine());
            switch (opt) {
                case 1:
                    library.showAvailableBooks();
                    continue;
                case 2:
                    library.borrowBook(member,sc);
                    continue;
                case 3:
                    library.returnBook(member,sc);
                    continue;
                case 4:
                    p("\nThank you for visiting us! See you soon...");
                    break;
                case 5:
                    if(isPremiumMember)
                    {
                        p("\nINVALID OPTION ENTERED! Please try again\n");
                    } else {
                        isPremiumMember = true;
                        p("\nCongratulations! "+memberName+", You're now a Premium Member of this Library.\n");
                        member.isPremiumMember = true;
                        premiumOpt = "";
                    }
                    continue;
                default:
                    p("\nINVALID OPTION ENTERED! Please try again\n");
                    continue;
            }
        }


    }

    public static void p(String s) {
        System.out.print(s);
    }

    public static int getOptionIfValid(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return -1;
        }
    }
}