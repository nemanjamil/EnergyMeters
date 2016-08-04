package rs.projekat.enrg.energymeters.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PodaciGrafik {

    @SerializedName("IdInt")
    @Expose
    private Integer idInt;
    @SerializedName("IdSmetersId")
    @Expose
    private String idSmetersId;
    @SerializedName("MeasurementId")
    @Expose
    private Integer measurementId;
    @SerializedName("DateTimeFrom")
    @Expose
    private String dateTimeFrom;
    @SerializedName("DateTimeTo")
    @Expose
    private String dateTimeTo;
    @SerializedName("DateTimeBaza")
    @Expose
    private String dateTimeBaza;
    @SerializedName("Consumption")
    @Expose
    private Double consumption;
    @SerializedName("TypeChar")
    @Expose
    private String typeChar;
    @SerializedName("IpAddress")
    @Expose
    private String ipAddress;

    /**
     * @return The idInt
     */
    public Integer getIdInt() {
        return idInt;
    }

    /**
     * @param idInt The IdInt
     */
    public void setIdInt(Integer idInt) {
        this.idInt = idInt;
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

    /**
     * @return The measurementId
     */
    public Integer getMeasurementId() {
        return measurementId;
    }

    /**
     * @param measurementId The MeasurementId
     */
    public void setMeasurementId(Integer measurementId) {
        this.measurementId = measurementId;
    }

    /**
     * @return The dateTimeFrom
     */
    public String getDateTimeFrom() {
        return dateTimeFrom;
    }

    /**
     * @param dateTimeFrom The DateTimeFrom
     */
    public void setDateTimeFrom(String dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    /**
     * @return The dateTimeTo
     */
    public String getDateTimeTo() {
        return dateTimeTo;
    }

    /**
     * @param dateTimeTo The DateTimeTo
     */
    public void setDateTimeTo(String dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    /**
     * @return The dateTimeBaza
     */
    public String getDateTimeBaza() {
        return dateTimeBaza;
    }

    /**
     * @param dateTimeBaza The DateTimeBaza
     */
    public void setDateTimeBaza(String dateTimeBaza) {
        this.dateTimeBaza = dateTimeBaza;
    }

    /**
     * @return The consumption
     */
    public Double getConsumption() {
        return consumption;
    }

    /**
     * @param consumption The Consumption
     */
    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    /**
     * @return The typeChar
     */
    public String getTypeChar() {
        return typeChar;
    }

    /**
     * @param typeChar The TypeChar
     */
    public void setTypeChar(String typeChar) {
        this.typeChar = typeChar;
    }

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

}
