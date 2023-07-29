package lj.app;

public class CustomList {
    String name, price, discription;
    int image;

    public CustomList(String name, int image, String price,String discription) {
        this.name=name;
        this.image=image;
        this.price=price;
        this.discription=discription;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
