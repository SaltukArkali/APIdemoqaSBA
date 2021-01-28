package pojos;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
        "userName",
        "password"
})

public class DemoqaPojo1 {

    @JsonProperty("userName")
    private String userName;
    @JsonProperty("password")
    private String password;

    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public DemoqaPojo1(){
    }
    public DemoqaPojo1(String userName, String password){
        this.userName=userName;
        this.password=password;
    }

    @Override
    public String toString() {
        return "DemoqaPojo1{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

