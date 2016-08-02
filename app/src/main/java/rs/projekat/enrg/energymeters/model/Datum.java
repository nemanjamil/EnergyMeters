package rs.projekat.enrg.energymeters.model;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("IpAddress")
    @Expose
    private String ipAddress;
    @SerializedName("IdSmetersId")
    @Expose
    private String idSmetersId;

    /**
     * @return The ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress The IpAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return The idSmetersId
     */
    public String getIdSmetersId() {
        return idSmetersId;
    }

    /**
     * @param idSmetersId The IdSmetersId
     */
    public void setIdSmetersId(String idSmetersId) {
        this.idSmetersId = idSmetersId;
    }

}
