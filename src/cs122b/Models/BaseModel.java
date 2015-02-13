package cs122b.Models;

/**
 * Created by dinhho on 1/12/15.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@XmlRootElement
public class BaseModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9097878790450670831L;
//    protected ModelStatus model_status;
    public BaseModel() {
    }

    @Override
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
}
