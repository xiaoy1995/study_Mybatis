package site.aiduoduo.mybatis.pojo;

/**
 * @Author yangtianhao
 * @Date 2020/2/14 2:42 下午
 * @Version 1.0
 */
public class User {
    private String name;
    private Integer gender;
    private String phone;
    private String address;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", gender=" + gender + ", phone='" + phone + '\'' + ", address='"
            + address + '\'' + '}';
    }
}
