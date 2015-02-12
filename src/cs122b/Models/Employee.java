package cs122b.Models;

import com.sun.xml.internal.rngom.parse.host.Base;

/**
 * Created by dinhho on 1/12/15.
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee extends BaseModel {
    private String email;
    private String password;
    private String fullname;

    public Employee() {
        super();
        this.email = "";
        this.password = "";
        this.fullname = "";
    }

    public Employee(String e, String p, String f) {
        super();
        this.email = e;
        this.password = p;
        this.fullname = f;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public void setFullname(String f) {
        this.fullname = f;
    }

}
