import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    String name,details,price;
    int availableStockQuantity;
    Product(String name, String details, String price,  int availableStockQuantity) {
        this.name = name;
        this.price = price;
        this.details = details;
        this.availableStockQuantity = availableStockQuantity;
    }
    Product(Product product) {
        this.name = product.name;
        this.price = product.price;
        this.availableStockQuantity = product.availableStockQuantity;
    }
    public String getName() {
        return name;
    }
    public String getDetails() {
        return details;
    }
    public String getPrice() {
        return price;
    }
    public int getAvailableStockQuantity() {
        return availableStockQuantity;
    }
}

class ShoppingCart {
    List<Product> productsInCart;
    ShoppingCart() {
        productsInCart = new ArrayList<>();
    }

    void addProductToCart(Product p, List<String> orderHistory) {
        if(p.availableStockQuantity==0) {
            System.out.println("This product is currently out of stock! please choose another one.");
            return;
        }
        boolean isProductAlreadyInCart = false;
        for(Product product : productsInCart) {
            if(product.getName().equals(p.getName()))
            {
                isProductAlreadyInCart = true;
                p.availableStockQuantity -= 1;
                product.availableStockQuantity += 1;
                orderHistory.add("Added into cart '"+p.name+"' of Price "+p.price+(" (Current Quantity: "+product.availableStockQuantity+")"));
                System.out.println("This product '"+p.name+"' is already in your cart! Now you have "+product.availableStockQuantity+"x in Quantity");
            }
        }
        if(!isProductAlreadyInCart) {
            Product newProduct = new Product(p);
            orderHistory.add("Added into cart '"+p.name+"' of Price "+p.price);
            System.out.println("Successfully added the item '"+p.name+"' into your cart!");
            p.availableStockQuantity -= 1;
            newProduct.availableStockQuantity = 1;
            productsInCart.add(newProduct);
        }
    }

    void removeProductFromCart(Product p,List<String> orderHistory) {
        for(Product product : productsInCart) {
            if(product.getName().equals(p.getName())) {
                System.out.println("Successfully removed the item '"+product.getName()+"' from your cart.");
                orderHistory.add("Removed the item '"+product.getName()+"' from the cart.");
                p.availableStockQuantity += product.getAvailableStockQuantity();
                productsInCart.remove(product);
                return;
            }
        }
    }
}

class User {
    String userName,password;
    List<String> orderHistory;
    ShoppingCart cart;
    User(String userName,String password) {
        this.userName = userName;
        this.password = password;
        cart = new ShoppingCart();
        orderHistory = new ArrayList<>();
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public ShoppingCart getCart() {
        return cart;
    }
}

class PragShop {
    List<User> users;
    User currentLoggedInUser;
    List<List<Product>> productsByCategory;
    String[] mainProductCategories;
    PragShop() {
        users = new ArrayList<>();
        users.add(new User("hi","hi"));
        productsByCategory = new ArrayList<>();
        mainProductCategories = new String[] {"Electronics and Computers","Books","Home and Kitchen","Toys and games","Beauty & Personal care","Sports & Outdoors","Jewelry & Watches"};
        String[] c1 = {"OnePlus 12|\nDetails:\n  Colour: Flowy Emerald\n  Size: 16 GB RAM, 512GB\n  Brand: OnePlus\n  Model Name: OnePlus 12\n  Network Service Provider: Unlocked for All Carriers\n  Operating System: OxygenOS\n  Cellular Technology: 5G|$64,999","Samsung Galaxy S24 Ultra 5G (Titanium Black, 12GB, 256GB Storage)|\nDetails:\n  Brand: Samsung\n  Model Name: Samsung Galaxy S24 Ultra 5G\n  Network Service Provider: Unlocked for All Carriers\n  Operating System: Android 14.0\n  Cellular Technology: 5G|$1,24,999","Dell 15 Laptop|\nDetails: Intel Core i5-1135G7 Processor/16GB DDR4/512GB SSD/Intel UHD Graphic/15.6\" (39.562cm) FHD Display/Win 11+MSO'21/15 Month McAfee/Spill-Resistant Keyboard/Carbon/Thin & Light 1.69kg|$49,990","Samsung Galaxy Tab S6 Lite|\nDetails:\n  Brand: Samsung\n  Model Name: Samsung Galaxy Tab S6 Lite\n  Memory Storage Capacity: 64 GB\n  Screen Size: 10.4 Inches\n  Display Resolution Maximum: 2000 x 1200 (WUXGA+) Pixels|$23,930","Apple Watch Ultra|\nDetails: [GPS + Cellular 49 mm] smart watch w/Rugged Titanium Case & Orange Alpine Loop - Small. Fitness Tracker, Precision GPS, Action Button, Extra-Long BatteryLife, Brighter Retina Display|$79,900","Sony Alpha Digital Camera|\nDetails:\n  Brand: Sony\n  Model Name: APSC\n  Maximum Webcam Image Resolution: 24.2 MP\n  Photo Sensor Size: APS-C fps\n  Image Stabilisation: Optical\n  Maximum Shutter Speed: 767011 Seconds\n  Minimum Shutter Speed: 30 Seconds\n  Metering Description: Evaluative\n  Exposure Control Type: Automatic\n  Form Factor: Mirrorless|$78,988"};
        String[] c2 = {"Tech Visions|\nDescription: Explore cutting-edge advancements in AI, blockchain and more...|$120","Code Craft|\nDescription: Dive into the world of programming languages and software world.|$200"};
        String[] c3 = {};
        String[] c4 = {};
        String[] c5 = {};
        String[] c6 = {};
        String[] c7 = {};
        List<String[]> productDetails = new ArrayList<>();

        productDetails.add(c1);
        productDetails.add(c2);
        productDetails.add(c3);
        productDetails.add(c4);
        productDetails.add(c5);
        productDetails.add(c6);
        productDetails.add(c7);

        for(int i=0;i<mainProductCategories.length;i++) { // adding only 2 categories for now as the store is still expanding...
            String[] s = productDetails.get(i);
            List<Product> productsList = new ArrayList<>();
            for(String pDetails : s) {
                String[] parts = pDetails.split("\\|");
                Product p = new Product(parts[0], parts[1], parts[2], ((int)Math.ceil(Math.random()*10))+1);
                productsList.add(p);
            }
            productsByCategory.add(productsList);
        }
    }

    void explore(Scanner scanner) {
        System.out.println("Explore PragShop Products");
        int opt = -1;
        while(opt==-1) {
            System.out.println("Choose the Category of Products to Explore:");
            for(int i=0;i<mainProductCategories.length;i++) {
                System.out.println((i+1)+". "+mainProductCategories[i]);
            }
            System.out.print("Enter here [1-"+mainProductCategories.length+"] (0 to go Back):");
            opt = Task3.getOptIfValid(scanner.nextLine(), 0, mainProductCategories.length);
            if(opt==0) break;
            if(opt!=-1) {
                List<Product> productsInThisCategory = productsByCategory.get(opt-1);
                if(productsInThisCategory.size()==0)
                {
                    System.out.println("\nThere were no products available in this category! Please choose another category.\n");
                    opt=-1;
                    continue;
                } else {
                    System.out.println("Products available under '"+mainProductCategories[opt-1]+"':");
                    for(int i=0;i<productsInThisCategory.size();i++) {
                        Product p = productsInThisCategory.get(i);
                        System.out.println((i+1)+". "+p.getName()+" -> "+p.getPrice());
                    }
                    opt = -1;
                    System.out.print("Select a product based on the index (1-"+productsInThisCategory.size()+") to view details / add to cart.\nEnter Here (0 to go back):");
                    while(opt==-1) {
                        opt = Task3.getOptIfValid(scanner.nextLine(), 0, productsInThisCategory.size());
                        if(opt==0) {
                            opt = -1;
                            break;
                        } else if(opt!=-1) {
                            Product p = productsInThisCategory.get(opt-1);
                            showProductDetails(p);
                            opt = -1;
                            while(opt==-1) {
                                System.out.print("\nChoose an option:\n1.Add this product to cart\n2.Go Back\nEnter Here(1 or 2):");
                                opt = Task3.getOptIfValid(scanner.nextLine(), 1, 2);
                            }
                            if(opt == 1) {
                                currentLoggedInUser.getCart().addProductToCart(p,currentLoggedInUser.orderHistory);

                            } else {
                                opt = -1;
                                break;
                            }
                        }
                    }
                }
            }
        }

    }

    void openShoppingCart(Scanner scanner) {
        List<Product> productsInCart = currentLoggedInUser.cart.productsInCart;
        System.out.println("\n YOUR SHOPPING CART \n--------------------\n");
        if(productsInCart.size()==0)
        {  
            System.out.println("There's nothing in here! Add some items to cart to be able to see them here."); return;
        }
        int index=1;
        for(Product p : productsInCart) {
            System.out.println(index+". "+p.getName()+" -> "+p.getPrice()+" ["+p.getAvailableStockQuantity()+"]");
            index++;
        }
        int opt = -1;
        System.out.print("\nChoose an option:\n1.Add an item to cart\n2.Remove an item from cart\n3.Go Back to Main Menu\nEnter here:");
        while(opt==-1) {
            opt = Task3.getOptIfValid(scanner.nextLine(), 1, 3);
        }
        if(opt==1) {
            System.out.println("Select 'Explore Products' from main menu and choose fav item to add them into the cart.");
            return;
        } else if(opt==2) {
            System.out.println("Choose an item based on index to remove it from your cart:");
            for(int i=0;i<productsInCart.size();i++) {
                Product p = productsInCart.get(i);
                System.out.println((i+1)+". "+p.getName()+" -> "+p.getPrice()+" ["+p.getAvailableStockQuantity()+"]");
            }
            System.out.print("Enter Here:");
            opt = -1;
            while(opt==-1) {
                opt = Task3.getOptIfValid(scanner.nextLine(), 1, productsInCart.size());
            }
            currentLoggedInUser.cart.removeProductFromCart(productsInCart.get(opt-1),currentLoggedInUser.orderHistory);
        } else {
            return;
        }
    }
    
    void viewOrderHistory(Scanner scanner) {
        System.out.println("\n ORDER HISTORY \n---------------\n");
        for(String s : currentLoggedInUser.orderHistory) {
            System.out.println(s);
        }
        System.out.print("\nPress Enter to continue:");
        scanner.nextLine();
    }

    void showProductDetails(Product p) {
        System.out.println("\n PRODUCT DETAILS \n-----------------\nProduct Name: "+p.getName()+"\n"+p.getDetails()+"\nPrice: "+p.getPrice()+"\nStock: "+(p.getAvailableStockQuantity() == 0 ? "Out of stock!" : (p.getAvailableStockQuantity() <= 5 ? "Hurry up! Only " + p.getAvailableStockQuantity() + " Left" : p.getAvailableStockQuantity() + " Left")));
    }

    public User register(Scanner scanner) {
        while(true) {
            boolean isUserNameUnique = true;
            System.out.print("Enter a username:");
            String username = scanner.nextLine(); // Ignoring blank username "" 😴
            for(User u : users) {
                if(u.getUserName().equals(username))
                {
                    isUserNameUnique = false;
                    System.out.println("This username already exists! Choose another one.");
                    break;
                }
            }
            if(isUserNameUnique) {
                System.out.print("Create a password:");
                String password = scanner.nextLine(); // Ignoring password strength or blank password "" 😴
                currentLoggedInUser = new User(username, password);
                users.add(currentLoggedInUser);
                return currentLoggedInUser;
            }
        }
    }

    public User login(Scanner scanner) {
        User user = null;
        System.out.println("Login using your credentials below:");
        boolean isUserNameValid = false;
        while(!isUserNameValid) {
            System.out.print("Enter username:");
            String s = scanner.nextLine();
        if(users.size()==0) {
            isUserNameValid = false;
        } else {
             for(User u : users) {
                if(u.getUserName().equals(s))
                {
                    user = u;
                    isUserNameValid = true;
                    break;
                }
            }
        }
        if(isUserNameValid) {
            System.out.println("Welcome! "+user.getUserName());
            int passwordEntryCount = 0;
            while(true) {
                System.out.print("Enter your password:");
                if(user.getPassword().equals(scanner.nextLine())) {
                    System.out.println("You've been logged in successfully!");
                    currentLoggedInUser = user;
                    return user;
                } else {
                    System.out.println("The password you entered is Incorrect! Please try again.\n");
                    passwordEntryCount++;
                }
                if(passwordEntryCount>=5) break;
            }

        } else {
            System.out.print("The username you entered haven't found on our records!\nChoose an option\n1.Retry\n2.Go back to Home\nEnter here:");
            int opt = -1;
            while(opt<0) {
                opt = Task3.getOptIfValid(scanner.nextLine(), 1, 2);
            }
            if(opt == 2)
                return null;
        }
        }
        System.out.println("You entered too many wrong passwords! Try again after sometime.");
        return null;
    }
}

class Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isUserLoggedIn = false;
        PragShop shop = new PragShop();
        User user = null;
        while(!isUserLoggedIn) {
            int opt=-1;
            while(opt==-1) {
            System.out.print("Welcome to PragShop!\nPlease Login or register to start exploring the store :\n1.Login\n2.Register\nEnter your choice(1 or 2):");
            opt = getOptIfValid(scanner.nextLine(),1,2);
                if(opt==1) {
                    user = shop.login(scanner);
                    if(user==null)
                    {
                        opt=-1;
                    } else {
                        isUserLoggedIn = true;
                        shop.currentLoggedInUser = user;
                    }
                } else if(opt==2) {
                    user = shop.register(scanner);
                    isUserLoggedIn = true;
                    shop.currentLoggedInUser = user;
                }
            }
            opt = -1;
            while (opt==-1) {
                System.out.print("\nWelcome to PragShop!\nChoose what you wanna do:\n1.Explore products\n2.Go to ShoppingCart\n3.View Order History\n4.Logout\n5.Exit\nEnter your choice:");
                opt = getOptIfValid(scanner.nextLine(), 1, 5);
                if(opt!=-1) {
                    switch (opt) {
                        case 1:
                            shop.explore(scanner);
                            opt = -1;
                            break;
                        case 2:
                            shop.openShoppingCart(scanner);
                            opt = -1;
                            break;
                        case 3:
                            shop.viewOrderHistory(scanner);
                            opt = -1;
                            break;
                        case 4:
                            isUserLoggedIn = false;
                            break;
                        case 5:
                            isUserLoggedIn = true;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        System.out.println("Thank you so much for shopping with PragShop! Come back soon.");
    }

    public static int getOptIfValid(String s,int rstart,int rend) {
        try {
            int x = Integer.parseInt(s);
            if(x>=rstart && x<=rend) {
                return x;
            } else {
                System.out.print("INVALID INPUT! Please choose a valid option number between "+rstart+" and "+rend+"\nEnter here:");
                return -1;
            }
        } catch (Exception e) {
            System.out.print("INVALID INPUT! Please enter numerical values only in range "+rstart+" and "+rend+"\nEnter here:");
            return -1;
        }
    }
}
