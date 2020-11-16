package sdp_lab02;

import java.io.Serializable;

public class Payload implements Serializable {
    private String data;
    public Payload(String data){
        this.data = data;
    }
    public String getData(){
        return data;
    }
    public void setData(String data){
        this.data = data;
    }
}
