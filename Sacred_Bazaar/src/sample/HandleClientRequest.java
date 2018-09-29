package sample;

import java.io.*;
import java.net.Socket;

public class HandleClientRequest implements Runnable{
    private Socket socket;
    ObjectInputStream ois;//Input Stream of client socket
    ObjectOutputStream oos;//Output Stream of client socket

    public HandleClientRequest(Socket socket){
        this.socket=socket;
        try {
            ois=new ObjectInputStream(socket.getInputStream());
            oos=new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                Object obj=null;
                try{
                    obj = ois.readObject();
                }catch (EOFException e){
                    System.out.println("Client disconnected");
                    break;
                }
                String req = (String) obj.toString();
                if (req.equals(String.valueOf(ServerRequest.SIGNUP_REQUEST))) {
                    Signup newsignup = new Signup((SignupRequest) obj);
                    String status = newsignup.put();
                    oos.writeObject(status);
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.LOGIN_REQUEST))) {
                    Login login = new Login((LoginRequest) obj);
                    User user = login.check();
                    oos.writeObject(user);
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.RETAILER_REGISTRATION))) {
                    RetailerRegistration retailerRegistration = new RetailerRegistration((RetailerRRequest) obj);
                    Retailer retailer = retailerRegistration.register();
                    oos.writeObject(retailer);
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.CATEGORIES))) {
                    CategoriesFetch categoriesFetch = new CategoriesFetch();
                    Categories categories = categoriesFetch.fetch();
                    oos.writeObject(categories);
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.PRODUCT_FETCH_REQUEST))) {
                    ProductFetchRequest productFetchRequest = (ProductFetchRequest) obj;
                    ProductFetch productFetch = new ProductFetch(productFetchRequest.getCategory());
                    if (!productFetchRequest.getName().equals("")) {
                        oos.writeObject(productFetch.fetch(productFetchRequest.getName()));
                    } else if (productFetchRequest.getCheck() != 0) {
                        oos.writeObject(productFetch.fetch(productFetchRequest.getCheck()));
                    } else
                        oos.writeObject(productFetch.fetch());
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.PRODUCT_ADD_REQUEST))) {
                    ProductAddRequest productAddRequest = (ProductAddRequest) obj;
                    ProductOperation productOperation = new ProductOperation(productAddRequest);
                    oos.writeObject(productOperation.add());
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.PRODUCT_EDIT_REQUEST))) {
                    ProductEditRequest productEditRequest = (ProductEditRequest) obj;
                    ProductOperation productOperation = new ProductOperation(productEditRequest);
                    oos.writeObject(productOperation.edit());
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.RETAILER_LOGIN))) {
                    RetailerLoginRequest retailerLoginRequest = (RetailerLoginRequest) obj;
                    RetailerLogin retailerLogin = new RetailerLogin(retailerLoginRequest);
                    Retailer retailer = retailerLogin.login();
                    oos.writeObject(retailer);
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.PRODUCT_BUY_REQUEST))) {
                    ProductBuyRequest productBuyRequest = (ProductBuyRequest) obj;
                    ProductBuy productBuy = new ProductBuy(productBuyRequest);
                    String buy = productBuy.buy();
                    oos.writeObject(buy);
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.MONEY_SPEND_REQUEST))) {
                    MoneySpendRequest moneySpendRequest = (MoneySpendRequest) obj;
                    MoneySpendCalculation moneySpendCalculation = new MoneySpendCalculation(moneySpendRequest.getUser());
                    if (moneySpendRequest.getStart() == null && moneySpendRequest.getEnd() == null) {
                        oos.writeObject(moneySpendCalculation.fetch());
                    } else if (moneySpendRequest.getEnd() == null) {
                        oos.writeObject(moneySpendCalculation.fetch(moneySpendRequest.getStart()));
                    } else {
                        oos.writeObject(moneySpendCalculation.fetch(moneySpendRequest.getStart(), moneySpendRequest.getEnd()));
                    }
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.OFFER_ADD_REQUEST))) {
                    OfferAddRequest offerAddRequest = (OfferAddRequest) obj;
                    OfferAdd offerAdd = new OfferAdd(offerAddRequest);
                    String add = offerAdd.add();
                    oos.writeObject(add);
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.PRODUCT_DELETE_REQUEST))) {
                    ProductDeleteRequest productDeleteRequest = (ProductDeleteRequest) obj;
                    ProductOperation productOperation = new ProductOperation(productDeleteRequest.getProduct());
                    String status = productOperation.delete();
                    oos.writeObject(status);
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.ADMIN_LOGIN_REQUEST))) {
                    AdminLoginRequest adminLoginRequest = (AdminLoginRequest) obj;
                    AdminLogin adminLogin = new AdminLogin(adminLoginRequest);
                    oos.writeObject(adminLogin.check());
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.USER_FETCH_REQUEST))) {
                    UserFetchRequest userFetchRequest = (UserFetchRequest) obj;
                    UserFetch userFetch = new UserFetch();
                    if (!userFetchRequest.getName().equals(""))
                        oos.writeObject(userFetch.fetch(userFetchRequest.getName()));
                    else if (userFetchRequest.getStatus() != -1)
                        oos.writeObject(userFetch.fetch(userFetchRequest.getStatus()));
                    else
                        oos.writeObject(userFetch.fetch());
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.TRANSACTION_FETCH_REQUEST))) {
                    TransactionFetchRequest transactionFetchRequest = (TransactionFetchRequest) obj;
                    TransactionFetch transactionFetch = new TransactionFetch(transactionFetchRequest);
                    oos.writeObject(transactionFetch.fetch());
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.CATEGORY_ADD_REQUEST))) {
                    CategoryAddRequest categoryAddRequest = (CategoryAddRequest) obj;
                    CategoryAdd categoryAdd = new CategoryAdd(categoryAddRequest);
                    oos.writeObject(categoryAdd.add());
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.CATEGORY_REMOVE_REQUEST))) {
                    CategoryRemoveRequest categoryRemoveRequest = (CategoryRemoveRequest) obj;
                    CategoryRemove categoryRemove = new CategoryRemove(categoryRemoveRequest);
                    oos.writeObject(categoryRemove.remove());
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.OFFER_REQUEST))) {
                    OfferRequest offerRequest = (OfferRequest) obj;
                    OfferPut offerPut = new OfferPut();
                    offerPut.setRetailerID(offerRequest.getRetailerID());
                    oos.writeObject(offerPut.put());
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.RETAILER_FETCH_REQUEST))) {
                    RetailerFetchRequest retailerFetchRequest = (RetailerFetchRequest) obj;
                    RetailerFetch retailerFetch = new RetailerFetch();
                    oos.writeObject(retailerFetch.fetchByName(retailerFetchRequest.getSearch()));
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.ANALYZE_CATEGORY_REQUEST))) {
                    AnalyseCategoryRequest analyseCategoryRequest = (AnalyseCategoryRequest) obj;
                    SellingSummary sellingSummary = new SellingSummary();
                    oos.writeObject(sellingSummary.fetchUsersByCategory(analyseCategoryRequest.getRetailerID(), analyseCategoryRequest.getStartDate(), analyseCategoryRequest.getEndDate()));
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.ANALYZE_USER_REQUEST))) {
                    AnalyseUserRequest analyseUserRequest = (AnalyseUserRequest) obj;
                    SellingSummary sellingSummary = new SellingSummary();
                    oos.writeObject(sellingSummary.fetchUsersByDate(analyseUserRequest.getRetailerID()));
                    oos.flush();
                }
                if (req.equals(String.valueOf(ServerRequest.EXTRA_OFFER_SET_REQUEST))) {
                    ExtraOfferSetRequest extraOfferSetRequest = (ExtraOfferSetRequest) obj;
                    ExtraOfferSet extraOfferSet = new ExtraOfferSet();
                    extraOfferSet.setPricex(extraOfferSetRequest.getPricex());
                    extraOfferSet.setEndDate(extraOfferSetRequest.getEndDate());
                    extraOfferSet.setEndDateOffer(extraOfferSetRequest.getEndDateOffer());
                    if (extraOfferSet.getPricex() == 0) {
                        oos.writeObject(extraOfferSet.setBonusOffer());
                    } else if (extraOfferSet.getEndDateOffer() == null) oos.writeObject(extraOfferSet.setOffer());
                    else {
                        String s1 = extraOfferSet.setBonusOffer();
                        String s2 = extraOfferSet.setOffer();
                        if (s1.equals(String.valueOf(ExtraOfferSetStatus.SUCCESS)) && s2.equals(String.valueOf(ExtraOfferSetStatus.SUCCESS))) {
                            oos.writeObject(String.valueOf(ExtraOfferSetStatus.SUCCESS));
                            oos.flush();
                        } else {
                            oos.writeObject(String.valueOf(ExtraOfferSetStatus.FAILED));
                            oos.flush();
                        }
                    }
                }

            } catch (StreamCorruptedException e){
                try {
                    ois=new ObjectInputStream(socket.getInputStream());
                    oos=new ObjectOutputStream(socket.getOutputStream());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
