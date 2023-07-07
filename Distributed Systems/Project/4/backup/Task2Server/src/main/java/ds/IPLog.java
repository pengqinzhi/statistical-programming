package ds;

/*
 * @author Qinzhi Peng, qinzhip
 */

public class IPLog {

    public String ObjectID;
    public int id;
    public String searchTerm;
    public String requestFromPhone;
    public String requestToAPI;
    public String responseFromAPI;
    public String pictureURL;
    public double latency;

    IPLog(String ObjectID, int id, String searchTerm, String requestFromPhone, String requestToAPI, String responseFromAPI, String pictureURL, double latency) {
        this.ObjectID = ObjectID;
        this.id = id;
        this.searchTerm = searchTerm;
        this.requestFromPhone = requestFromPhone;
        this.requestToAPI = requestToAPI;
        this.responseFromAPI = responseFromAPI;
        this.pictureURL = pictureURL;
        this.latency = latency;
    }
}