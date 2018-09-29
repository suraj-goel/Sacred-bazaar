package sample;
//importing appropriate classes
import java.util.List;

/**
 * Class for generation of
 * Bill for the products brought
 * by user
 */
public class Bill {

    //Declaring Local Variable
    private List<CartProduct> cartProducts;//List of CartProducts of the brought by user
    private User user;//Object of the user who brought the products

    /**
     * Constructor for initialisation of bill
     * by a list of Cart Products
     * and User Object
     * @param cartProducts list of cart products brought by user
     * @param user User who brought the product
     */
    public Bill(List<CartProduct> cartProducts,User user) {
        this.setUser(user);
        this.setCartProducts(cartProducts);
    }

    /**
     * Method for returning a list of
     * cart products brought by user
     * @return List of cart Products
     */
    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    /**
     * Method for setting products brought by user
     * by a list of cart products
     * @param cartProducts List of Cart products Brought by user
     */
    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    /**
     * Method for getting the user
     * who has brought the products
     * @return User brought the Products
     */
    public User getUser() {
        return user;
    }

    /**
     * Method for setting the user
     * buyig the products
     * @param user User buying the products
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Method for generating bill
     * of products brought by user
     * @return String of the bill generated
     */
    public String generate(){
        String bill="****************************** SACRED BAZAAR ***********************************\n\n\n\n\n\n";
              bill+=" S.no   Name                                               Qty.      Price       \n\n";
        int i=1;
        double totalprice=0;
        for (CartProduct cartProduct:cartProducts){
            bill+="  ";
            String temp=""+i;
            for (int i1=0;i1<4;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            String Name;
            Name=cartProduct.getProduct().getName();
            temp=Name;
            for (int i1=0;i1<50;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+="     ";
            int quantity;
            quantity=cartProduct.getQuantity();
            temp=quantity+"";
            for (int i1=0;i1<5;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+="   ";
            Offer curentOffer=cartProduct.getOffer();
            String offertype=curentOffer.getType();
            double totalgrossprice=quantity*cartProduct.getProduct().getPrice();
            temp=totalgrossprice+"";
            for (int i1=0;i1<10;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+=" \n      ";
            if (offertype.equals(String.valueOf(OfferType.BUY_X_GET_Y)) && quantity>=curentOffer.getX()){
                quantity-=(int) curentOffer.getY();
            }
            double netPrice=cartProduct.getProduct().getPrice();
            if (offertype.equals(String.valueOf(OfferType.X_PERCENT_DISCOUNT))){
                netPrice=netPrice-(netPrice*curentOffer.getX()/100);
            }
            double totalNetPrice=quantity*netPrice;
            double discount=totalgrossprice-totalNetPrice;
            totalprice+=totalNetPrice;
            try {
                temp = Name.substring(50);
            }
            catch (Exception e){
                temp="";
            }
            for (int i1=50;i1<100;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+=" Dis         ";
            temp=""+discount;
            for (int i1=0;i1<10;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+=" \n      ";
            try {
                temp = Name.substring(100);
            }
            catch (Exception e){
                temp="";
            }
            for (int i1=100;i1<140;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+="          Total       ";
            temp=""+totalNetPrice;
            for (int i1=0;i1<10;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+="\n\n";
            i++;
        }
        bill+="      ";
        String temp;
        if (user.isBonusOffer()){
            bill+="Total Price :          ";
            temp=""+totalprice;
            for (int i1=0;i1<10;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+="\n";
            bill+="Extra Discount :       ";
            temp=""+(totalprice*0.1);
            for (int i1=0;i1<10;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            totalprice=totalprice-totalprice*0.1;
            bill+="Total Payable :        ";
            temp=""+totalprice;
            for (int i1=0;i1<10;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+="\n";

        }
        else if (!user.isBonusOffer() && user.isExtraOffer()){
            bill+="Total Price :          ";
            temp=""+totalprice;
            for (int i1=0;i1<10;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+="\n";
            bill+="Extra Discount :       ";
            temp=""+(totalprice*0.05);
            for (int i1=0;i1<10;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            totalprice=totalprice-totalprice*0.1;
            bill+="Total Payable :        ";
            temp=""+totalprice;
            for (int i1=0;i1<10;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+="\n";
        }
        else {
            bill+="Total Payable :        ";
            temp=""+totalprice;
            for (int i1=0;i1<10;i1++){
                if (temp.isEmpty()){
                    bill+=" ";
                }
                else {
                    String tempchar=temp.charAt(0)+"";
                    bill+=tempchar;
                    temp=temp.substring(1);
                }
            }
            bill+="\n";
        }
        System.out.println(bill);
        return bill;
    }
}
