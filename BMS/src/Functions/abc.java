package Functions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;

public class abc {
    //create bill ,bill banse
        static String createbill_for_billing(String Name, String username) throws Exception {

            Date d = new Date();

            Date currentDate = d;

            double totalAmount = 0;
            if (user_LL.head == null) {

                System.out.println("NO ITEMS IN CART!");
                return "";

            } else {
                FileWriter fw = new FileWriter(username + "ShopingBill.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);

                linked_list_of_cart.Node temp = user_LL.head;

                Date date = new Date();

                bw.write("-------------------ONLINE DIGITAL MALL BILL---------------------------");
                bw.newLine();
                bw.newLine();
                bw.write("----------------------------" + Name + "----------------------------------");
                bw.newLine();
                bw.newLine();
                bw.write("                                                                  " + date);
                bw.newLine();
                bw.newLine();

                while (temp != null) {
                    int pid = temp.userCart.getpID();
                    String pname = temp.userCart.getpName();
                    int quantity = temp.userCart.getpQuantity();
                    double price = temp.userCart.getpPrice();

                    System.out.println("Product Id:- " + pid);
                    System.out.println("Product Name:-" + pname);
                    System.out.println("Product Quantity:-" + quantity);
                    System.out.println("Product Price:-" + price);

                    bw.write("Product Id:- " + pid + "  ");
                    bw.write("Product Name:-" + pname + "  ");
                    bw.write("Product Quantity:-" + quantity + "  ");
                    bw.write("Product Price:-" + price);
                    bw.newLine();

                    totalAmount = totalAmount + price;
                    temp = temp.next;

                }

                System.out.println("-------------------------------------------------------------------");
                System.out.println("|                                                                  |");
                System.out.println("|TOTAL AMOUNT OF YOUR BILL IS :- " + totalAmount + "               |");
                System.out.println("|                                                                  |");
                System.out.println("--------------------------------------------------------------------");

                String paymentMethod = "";
                System.out.println("--------------------------------------------------------");
                System.out.println("|choose payment method:                                |");
                System.out.println("| 1. for Cash On Delivery:                             |");
                System.out.println("| 2. for UPI:                                          |");
                System.out.println("| 3. for Debit Card:                                   |");
                System.out.println("--------------------------------------------------------");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:

                        paymentMethod = "PAYMENT BY CASH ON DELIVERY.";
                        System.out.println("Order successfully done.. \n Payment by Cash On Delivery...");
                        linked_list_of_cart.Node temp1 = user_LL.head;
                        while (temp1 != null) {
                            String pname = temp1.userCart.getpName();
                            int quantity = temp1.userCart.getpQuantity();
                            double price = temp1.userCart.getpPrice();

                            String sql = "insert into orderhistory (username,product_name,total_quantity,total_price,order_date) values('" + username + "','" + pname + "','" + quantity + "','" + price + "','" + currentDate + "')";
                            pst = con.prepareStatement(sql);
                            pst.execute();

                            String sql2 = "{call updateQuantity(?,?)}";
                            pst = con.prepareCall(sql2);
                            pst.setInt(1, (quantity));
                            pst.setString(2, pname);
                            pst.executeUpdate();
                            temp1 = temp1.next;
                        }
                        break;

                    case 2:

                        paymentMethod = "PAYMENT BY UPI.";
                        System.out.println("ENTER UPI ID:");
                        String upi = sc.next();

                        boolean p = true;
                        while (p) {
                            System.out.println("ENTER AMOUNT:");
                            double amount = sc.nextDouble();

                            if (amount == totalAmount) {
                                try {
                                    System.out.println("Payment is procesing.");
                                    Thread.sleep(5000);
                                    System.out.println("Payment Successfully completed.... Thank you!");
                                } catch (Exception e) {
                                    System.out.println("Payment is failed!");
                                }

                                linked_list_of_cart.Node temp2 = user_LL.head;
                                while (temp2 != null) {
                                    String pname = temp2.userCart.getpName();
                                    int quantity = temp2.userCart.getpQuantity();
                                    double price = temp2.userCart.getpPrice();

                                    String sql = "insert into orderhistory (username,product_name,total_quantity,total_price,order_date) values('" + username + "','" + pname + "','" + quantity + "','" + price + "','" + currentDate + "')";
                                    pst = con.prepareStatement(sql);
                                    pst.execute();

                                    String sql2 = "{call updateQuantity(?,?)}";
                                    pst = con.prepareCall(sql2);
                                    pst.setInt(1, (quantity));
                                    pst.setString(2, pname);
                                    pst.executeUpdate();
                                    temp2 = temp2.next;
                                }
                                p = false;
                            } else {
                                System.out.println("THE PAYMENT OF AN INVOICE THAT IS LESS THAN FULL AMOUNT DUE...");
                            }
                        }
                        break;

                    case 3:

                        paymentMethod = "PAYMENT BY DEBIT CARD.";
                        boolean flag = true;
                        while (flag) {
                            int flag1 = 0;
                            System.out.println("ENTER DEBITCARD NUMBER:");
                            String accountNumber = sc.nextLine();
                            if (String.valueOf(accountNumber).length() == 16) {
                                for (int i = 0; i < 16; i++) {
                                    if ((String.valueOf(accountNumber).charAt(i) >= '0') && (String.valueOf(accountNumber).charAt(i) <= '9')) {
                                        flag1++;
                                    }
                                }

                                if (flag1 == 16) {
                                    flag = false;

                                    boolean p1 = true;
                                    while (p1) {
                                        System.out.println("ENTER AMOUNT:");
                                        double amount = sc.nextDouble();

                                        if (amount == totalAmount) {
                                            try {
                                                System.out.println("Payment is procesing.");
                                                Thread.sleep(2000);
                                                System.out.println("Payment Successfully completed.... Thank you!");
                                            } catch (Exception e) {
                                                System.out.println("Payment is failed!");
                                            }

                                            linked_list_of_cart.Node temp3 = user_LL.head;
                                            while (temp3 != null) {
                                                String pname = temp3.userCart.getpName();
                                                int quantity = temp3.userCart.getpQuantity();
                                                double price = temp3.userCart.getpPrice();

                                                String sql = "insert into orderhistory (username,product_name,total_quantity,total_price,order_date) values('" + username + "','" + pname + "','" + quantity + "','" + price + "','" + currentDate + "')";
                                                pst = con.prepareStatement(sql);
                                                pst.execute();

                                                String sql2 = "{call updateQuantity(?,?)}";
                                                pst = con.prepareCall(sql2);
                                                pst.setInt(1, (quantity));
                                                pst.setString(2, pname);
                                                pst.executeUpdate();
                                                temp3 = temp3.next;
                                            }
                                            p1 = false;
                                        } else {
                                            System.out.println("THE PAYMENT OF AN INVOICE THAT IS LESS THAN FULL AMOUNT DUE...");
                                        }
                                    }
                                } else {
                                    System.out.println("debitcard number should be only digits!");
                                }
                            } else {
                                System.out.println("###---debitcard number should be 16 digits!---###");
                            }
                        }

                        break;

                    default:
                        System.out.println("Select invalid payment method");
                }

                bw.write("--------------------------------------------------------");
                bw.newLine();
                bw.write("|                                                      |");
                bw.newLine();
                bw.write("|TOTAL AMOUNT OF YOUR BILL IS :- " + totalAmount + "                |");
                bw.newLine();
                bw.write("|                                                      |");
                bw.newLine();
                bw.write("| Payment method : " + paymentMethod + "                                    |");
                bw.newLine();
                bw.write("--------------------------------------------------------");
                bw.newLine();

                bw.close();
                fw.close();

                System.out.println("You want to continue your shoping (yes/no) :");
                String ans = sc.next();
                user_LL.head = null;
                if (ans.equalsIgnoreCase("no")) {
                    return "no";
                } else {
                    return "yes";
                }
            }

        }
}
